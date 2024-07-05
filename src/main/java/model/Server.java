package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/server")
public class Server {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<>());
	private List<ByteBuffer> messageParts = new ArrayList<>();
	private String nameOfFile = "";

	@OnOpen
	public void handleOpen(Session session) {
		users.add(session);
		System.out.println("New user: " + session.getId());
	}

	@OnMessage
	public synchronized void handleBinaryMessage(ByteBuffer byteBuffer, boolean last, Session session)
			throws IOException {
		System.out.println("Received binary message fragment");

		// Thêm mảnh vào danh sách
		messageParts.add(byteBuffer);

		if (last) {
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
			if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
				endStr = ".jpg";
			} else if (fileType.equals("video/mp4")) {
				endStr = ".mp4";
			} else if (fileType.equals("word")) {
				endStr = ".docx";
			} else if (fileType.equals("pdf")) {
				endStr = ".pdf";
			} else if (fileType.equals("txt")) {
				endStr = ".txt";
			} else if (fileType.equals("audio/mpeg")) {
				endStr = ".mp3";
			} else if (fileType.equals("audio/wav")) {
				endStr = ".wav";
			}

			Random rd = new Random();
			String fileName = "receive" + rd.nextInt(1000) + endStr;
			if (endStr.equals(".docx") || endStr.equals(".pdf") || endStr.equals(".txt")) {
				fileName = nameOfFile;
			}
			String directoryPath = "D:\\eclipse_pbl\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Web_chat\\image\\";
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

			if (endStr.equals(".mp4")) {
				System.out.println("gửi mp4");
				for (Session userSession : users) {
					if (!userSession.equals(session)) {
						userSession.getBasicRemote().sendText("video_sent " + filePath);
						System.out.println("video_sent");
					} else {
						session.getBasicRemote().sendText("yrvideo " + filePath);
						System.out.println("yrvideo");
					}
				}

			} else if (endStr.equals(".docx") || endStr.equals(".pdf") || endStr.equals(".txt")) {
				for (Session userSession : users) {
					if (!userSession.equals(session)) {
						userSession.getBasicRemote().sendText("docsent " + filePath);
					} else {
						session.getBasicRemote().sendText("yrdoc " + filePath);
					}
				}
			}else if (endStr.equals(".mp4") || endStr.equals(".wav") ) {
				for (Session userSession : users) {
					if (!userSession.equals(session)) {
						userSession.getBasicRemote().sendText("audiosent " + filePath);
					} else {
						session.getBasicRemote().sendText("yraudio " + filePath);
					}
				}
			}  else {
				// Gửi lại dữ liệu cho các session khác
				for (Session userSession : users) {
					if (!userSession.equals(session)) {
						userSession.getBasicRemote().sendBinary(ByteBuffer.wrap(completeBuffer.array()));
					} else {
						session.getBasicRemote().sendText("yrself " + filePath);
					}
				}

			}

			// Reset buffer for next message
			messageParts.clear();
		}
	}

	@OnMessage
	public synchronized void handleMessage(String message, Session userSession) throws IOException {
		String checkS = "nameoffile" ;
		if (message.startsWith(checkS)) {
			String tmp = "";
			for(int i=checkS.length() + 1; i < message.length() ; i++) {
				tmp += message.charAt(i);
			}
			nameOfFile = tmp;
			return;
		}
		String username = (String) userSession.getUserProperties().get("username");
		System.out.println("Goi ham send : " + message);
		if (username == null) {
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText("System: you are connectd as " + message);
		} else {
			for (Session session : users) {
				System.out.println(username + ": " + message);
				if (!userSession.equals(session)) {
					session.getBasicRemote().sendText(message);
				} else {
					System.out.println("ko đc");
				}
			}
		}
	}

	@OnClose
	public void handleClose(Session session) {
		users.remove(session);
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	public static void sendImage(Session session, File imageFile) throws IOException {
		FileInputStream fis = new FileInputStream(imageFile);
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = fis.read(buffer)) != -1) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
			session.getBasicRemote().sendBinary(byteBuffer);
		}
		fis.close();
	}

	private String getFileType(byte[] bytes) {
		// Check for JPEG magic numbers
		if (bytes.length >= 3 && bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF) {
			return "image/jpeg";
		}

		// Check for PNG magic numbers
		if (bytes.length >= 8 && bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x4E &&
				bytes[3] == (byte) 0x47 && bytes[4] == (byte) 0x0D && bytes[5] == (byte) 0x0A &&
				bytes[6] == (byte) 0x1A && bytes[7] == (byte) 0x0A) {
			return "image/png";
		}

		// Check for MP4 magic numbers
		if (bytes.length >= 8 && bytes[4] == (byte) 0x66 && bytes[5] == (byte) 0x74 && bytes[6] == (byte) 0x79 &&
				bytes[7] == (byte) 0x70) {
			return "video/mp4";
		}

		if (bytes.length >= 4 && bytes[0] == (byte) 0x50 && bytes[1] == (byte) 0x4B &&
				bytes[2] == (byte) 0x03 && bytes[3] == (byte) 0x04) {
			return "word";
		}

		// Check for PDF magic numbers
		if (bytes.length >= 4 && bytes[0] == (byte) 0x25 && bytes[1] == (byte) 0x50 &&
				bytes[2] == (byte) 0x44 && bytes[3] == (byte) 0x46) {
			return "pdf";
		}
		
		if (bytes.length >= 2 && bytes[0] == (byte) 0xFF && (bytes[1] & (byte) 0xE0) == (byte) 0xE0) {
	        return "audio/mpeg";
	    }

	    // Check for WAV magic numbers (RIFF header)
	    if (bytes.length >= 12 && bytes[0] == (byte) 0x52 && bytes[1] == (byte) 0x49 && bytes[2] == (byte) 0x46 &&
	            bytes[3] == (byte) 0x46 && bytes[8] == (byte) 0x57 && bytes[9] == (byte) 0x41 && bytes[10] == (byte) 0x56 &&
	            bytes[11] == (byte) 0x45) {
	        return "audio/wav";
	    }


		// Check for plain text (simple check)
		if (isTextFile(bytes)) {
			return "text";
		}
		
		
		System.out.println("Ko biết type");
		return "audio/wav";
	}

	private boolean isTextFile(byte[] bytes) {
		for (byte b : bytes) {
			if (b == 0) {
				return false; // Null byte indicates it's likely binary
			}
		}
		return true;
	}

}
