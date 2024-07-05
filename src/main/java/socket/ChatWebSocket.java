package socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


import com.fasterxml.jackson.databind.ObjectMapper;

import dao.MessageDAO;
import dao.UserDAO;
import model.ContextListener;
import model.MessageDecoder;
import model.MessageEncoder;
import model.dto.FileDTO;
import model.dto.MessageDTO;
import services.ChatServiceAbstract;
import services.MessageInterface;
import services.impl.ChatService;
import services.impl.MessageService;
import util.HibernateUtil;

@ServerEndpoint(value = "/chat/{id}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatWebSocket {

	private String id;
	private Session session;
	private Queue<MessageDTO> fileDTOs = new LinkedList<>();
	private List<ByteBuffer> messageParts = new ArrayList<>();
	private String nameOfFile = "";

	private ChatServiceAbstract chatService = ChatService.getInstance();

	private MessageInterface messageService = MessageService.getInstance();
	
	 private ServletContext servletContext = ContextListener.getServletContext(); 

	@OnOpen
	public void onOpen(@PathParam("id") String id, Session session) {
		this.id = id;
		if (chatService.register(this)) {
			System.out.println("New user: " + id);
			this.session = session;
			MessageDTO msgResponse = new MessageDTO();
			msgResponse.setSender(id);
			msgResponse.setType("online");
			chatService.sendMessageToAllUsers(msgResponse);
			org.hibernate.Session session2 = HibernateUtil.getSessionFactory().openSession();
			UserDAO.getInstance().setStatus(id, true, session2);
			session2.close();
		}
	}

	@OnError
	public void onError(Session session, Throwable throwable) {

	}

//	@OnMessage
//	public void onMessage(MessageDTO message, Session session) {
//		System.out.println(message.getSender() + " " + message.getReceiver() + " " + message.getMessage());
//		chatService.sendMessageToOneUser(message, fileDTOs);
//		messageService.saveMessage(message);
//	}

	@OnMessage
	public void onMessage(String messageJson, Session session) {
		System.out.println("message: " + messageJson);
		try {
			ObjectMapper mapper = new ObjectMapper();
			MessageDTO message = mapper.readValue(messageJson, MessageDTO.class);
			System.out.println(message.getSender() + " " + message.getReceiver() + " " + message.getMessage());
			if (message.getType().equals("text")) {
				chatService.sendMessageToOneUser(message);
				messageService.saveMessage(message);
			} else if (message.getType().equals("add")) {
				chatService.handleAddMember(message);
			} 
			else if(message.getType().equals("accept")) {
				chatService.handleAcceptRequest(message);
			}
			else {
				fileDTOs.add(message);
			}

			ArrayList<String> arr = message.getListId();
			for (String s : arr)
				System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void processUploading(ByteBuffer byteBuffer, boolean last, Session session) {
		messageParts.add(byteBuffer);

		if (last) {
			System.out.println("url: " + servletContext.getRealPath("/file/"));
			// Khi nhận được mảnh cuối cùng
			System.out.println("Last fragment");

			// Tính tổng kích thước của thông điệp
			int totalSize = messageParts.stream().mapToInt(ByteBuffer::remaining).sum();
			ByteBuffer completeBuffer = ByteBuffer.allocate(totalSize);

			// Ghép các mảnh lại với nhau
			for (ByteBuffer part : messageParts) {
				completeBuffer.put(part);
			}
			completeBuffer.flip();
			String fileType = getFileType(completeBuffer.array());
			String endStr = "";

			MessageDTO m = null;
			if (!fileDTOs.isEmpty()) {
				m = fileDTOs.poll();
			}

			if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
				endStr = ".jpg";
				m.setType("image");
			} else if (fileType.equals("video/mp4")) {
				endStr = ".mp4";
				m.setType("video");
			} else if (fileType.equals("word")) {
				endStr = ".docx";
				m.setType("doc");
			} else if (fileType.equals("pdf")) {
				endStr = ".pdf";
				m.setType("doc");
			} else if (fileType.equals("txt")) {
				endStr = ".txt";
				m.setType("doc");
			} else if (fileType.equals("audio/mpeg")) {
				endStr = ".mp3";
				m.setType("audio");
			} else if (fileType.equals("audio/wav")) {
				endStr = ".wav";
				m.setType("audio");
			}

			Random rd = new Random();
			String fileName = "receive" + rd.nextInt(10000) + endStr;
			if (!m.getNamefile().equals("none")) {
				fileName = m.getNamefile();
			}
			String directoryPath = servletContext.getRealPath("/file/")
					+ m.getSender() + "_" + m.getReceiver() + "/";

			if (m.getType().equals("image") && m.getGroupId() == -1) {
				directoryPath = servletContext.getRealPath("/file/group/");
			}
			String filePath = directoryPath + fileName;

			// Kiểm tra và tạo thư mục nếu chưa tồn tại
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Lưu dữ liệu vào tệp
			File file = new File(filePath);
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(completeBuffer.array());
				fos.flush();
				System.out.println("File saved at: " + filePath);
			} catch (IOException e) {
				System.err.println("Error saving file: " + e.getMessage());
				return;
			}
			if (m.getType().equals("image") && m.getGroupId() == -1) {
				m.setAvatar("\\file\\group\\" + fileName);
				m.setType("group");
				m.setNamefile(m.getMessage());
				m.setMessage("A user just created this group");
				chatService.handleCreateGroup(m);
				return;
			}

			m.setMessage("\\file\\" + m.getSender() + "_" + m.getReceiver() + "\\" + fileName);
			messageService.saveMessage(m);
			chatService.sendMessageToOneUser(m);

			// Reset buffer for next message
			messageParts.clear();
		}
	}

	@OnClose
	public void onClose(Session session) {
		if (chatService.close(this)) {
			MessageDTO msgResponse = new MessageDTO();
			msgResponse.setSender(id);
			msgResponse.setType("offline");
			chatService.sendMessageToAllUsers(msgResponse);
			org.hibernate.Session session2 = HibernateUtil.getSessionFactory().openSession();
			UserDAO.getInstance().setStatus(id, false, session2);
			session2.close();
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String getFileType(byte[] bytes) {
		// Check for JPEG magic numbers
		if (bytes.length >= 3 && bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF) {
			return "image/jpeg";
		}

		// Check for PNG magic numbers
		if (bytes.length >= 8 && bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x4E
				&& bytes[3] == (byte) 0x47 && bytes[4] == (byte) 0x0D && bytes[5] == (byte) 0x0A
				&& bytes[6] == (byte) 0x1A && bytes[7] == (byte) 0x0A) {
			return "image/png";
		}

		// Check for MP4 magic numbers
		if (bytes.length >= 8 && bytes[4] == (byte) 0x66 && bytes[5] == (byte) 0x74 && bytes[6] == (byte) 0x79
				&& bytes[7] == (byte) 0x70) {
			return "video/mp4";
		}

		if (bytes.length >= 4 && bytes[0] == (byte) 0x50 && bytes[1] == (byte) 0x4B && bytes[2] == (byte) 0x03
				&& bytes[3] == (byte) 0x04) {
			return "word";
		}

		// Check for PDF magic numbers
		if (bytes.length >= 4 && bytes[0] == (byte) 0x25 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x44
				&& bytes[3] == (byte) 0x46) {
			return "pdf";
		}

		if (bytes.length >= 2 && bytes[0] == (byte) 0xFF && (bytes[1] & (byte) 0xE0) == (byte) 0xE0) {
			return "audio/mpeg";
		}

		// Check for WAV magic numbers (RIFF header)
		if (bytes.length >= 12 && bytes[0] == (byte) 0x52 && bytes[1] == (byte) 0x49 && bytes[2] == (byte) 0x46
				&& bytes[3] == (byte) 0x46 && bytes[8] == (byte) 0x57 && bytes[9] == (byte) 0x41
				&& bytes[10] == (byte) 0x56 && bytes[11] == (byte) 0x45) {
			return "audio/wav";
		}

		System.out.println("Ko biết type");
		return "audio/wav";
	}

}
