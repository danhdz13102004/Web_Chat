package services.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Queue;

import org.hibernate.Session;

import dao.FriendDAO;
import dao.GroupDAO;
import dao.GroupUsersDAO;
import dao.UserDAO;
import model.ChatGroup;
import model.Friend;
import model.Group_Users;
import model.User;
import model.Group_Users.Role;
import model.dto.FileDTO;
import model.dto.MessageDTO;
import services.ChatServiceAbstract;
import socket.ChatWebSocket;
import util.HibernateUtil;

public class ChatService extends ChatServiceAbstract{
	
	private static ChatService chatService = null;

//	private UserDaoInterface userDaoInterface = UserDao.getInstace();

	private ChatService() {
	}

	public synchronized static ChatService getInstance() {
		if (chatService == null) {
			chatService = new ChatService();
		}
		return chatService;
	}

	@Override
	public boolean register(ChatWebSocket chatWebsocket) {
		
		if(chatWebsockets.get(chatWebsocket.getId()) == null) {
			chatWebsockets.put(chatWebsocket.getId(), chatWebsocket);
			return true;
		}
		return false;
	}

	@Override
	public boolean close(ChatWebSocket chatWebsocket) {
		return chatWebsockets.remove(chatWebsocket.getId()) != null;
	
	}

	@Override
	public void sendMessageToAllUsers(MessageDTO message) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ArrayList<Friend> res = FriendDAO.getInstance().selectAllFriend(message.getSender(), session);
			for(Friend f : res) {
				ChatWebSocket receiver = null;
				if(!f.getSender().getId().equals(message.getSender())) {
					receiver = chatWebsockets.get(f.getSender().getId());
				}
				else {
					receiver = chatWebsockets.get(f.getReceiver().getId());
				}
				if(receiver != null) receiver.getSession().getBasicRemote().sendObject(message);					
			}

		} catch (Exception e) {
			e.printStackTrace();
		}	
		session.close();
	}

	@Override
	public void sendMessageToOneUser(MessageDTO message) {
		try {
			if(message.getGroupId() == 0) {
//				for(ChatWebSocket c : chatWebsockets) {
//					if(c.getId().equals(message.getReceiver()) || c.getId().equals(message.getSender())) {
//						c.getSession().getBasicRemote().sendObject(message);
//					}
//				}
				ChatWebSocket receiver = chatWebsockets.get(message.getReceiver());
				ChatWebSocket sender = chatWebsockets.get(message.getSender());
				if(receiver != null) {
					receiver.getSession().getBasicRemote().sendObject(message);
				}
				if(sender != null) {
					sender.getSession().getBasicRemote().sendObject(message);
				}
			}
			else {
				Session session = HibernateUtil.getSessionFactory().openSession();
				ArrayList<Group_Users> li = GroupUsersDAO.getInstance().selectByGroupId(message.getGroupId(), session);
				for(Group_Users g : li) {
					ChatWebSocket receiver = chatWebsockets.get(g.getId_user());
					if(receiver != null && receiver.getSession().isOpen()) {
						System.out.println("gui " + receiver.getId());
						receiver.getSession().getBasicRemote().sendObject(message);
					}
				}
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void handleFileUpload(ByteBuffer byteBuffer, boolean last, Queue<FileDTO> fileDTOs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUserOnline(String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void handleCreateGroup(MessageDTO messageDTO) {
		// create chat group
		ChatGroup chatGroup = new ChatGroup();
		chatGroup.setName(messageDTO.getNamefile());
		chatGroup.setAvatar(messageDTO.getAvatar());
		chatGroup.setStatus(true);
		
		// create group_users
		Session session = HibernateUtil.getSessionFactory().openSession();
		GroupDAO.getInstance().insert(chatGroup, session);
		messageDTO.setGroupId(chatGroup.getId());
		ArrayList<String> listuser = messageDTO.getListId();
		GroupUsersDAO.getInstance().addNewGroupUsers(listuser, chatGroup, session);
		
		// add admin
		Group_Users g = new Group_Users();
		g.setGroup(chatGroup);
		g.setId_user(messageDTO.getSender());
		g.setRole(Group_Users.Role.AD);
		g.setStatus(true);
		GroupUsersDAO.getInstance().insert(g, session);
		
		
		// broadcast new group
		listuser.add(messageDTO.getSender());
		try {
			for(String id : listuser) {
				ChatWebSocket receiver = chatWebsockets.get(id);
				if(receiver != null) {
					receiver.getSession().getBasicRemote().sendObject(messageDTO);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public void handleAddMember(MessageDTO messageDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ChatGroup chatGroup = GroupDAO.getInstance().selectById(messageDTO.getGroupId(), session);
		MessageDTO m = new MessageDTO();
		m.setAvatar(chatGroup.getAvatar());
		m.setGroupId(chatGroup.getId());
		m.setNamefile(chatGroup.getName());
		m.setMessage("You were add to this group");
		m.setType("group");
		for(String s : messageDTO.getListId()) {
			Group_Users u = new Group_Users();
			u.setId_user(s);
			u.setGroup(chatGroup);
			u.setRole(Role.ME);
			u.setStatus(true);
			GroupUsersDAO.getInstance().insert(u, session);
			ChatWebSocket receiver = chatWebsockets.get(s);
			try {
				if(receiver != null) {
					receiver.getSession().getBasicRemote().sendObject(m);
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void handleAcceptRequest(MessageDTO messageDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = UserDAO.getInstance().selectById(messageDTO.getSender(), session);
		messageDTO.setAvatar(user.getAvatar());
		messageDTO.setMessage(user.getFullname());
		messageDTO.setNamefile(String.valueOf(user.isSatatus_activity()));
		session.close();
		ChatWebSocket receiver = chatWebsockets.get(messageDTO.getReceiver());
		try {
			if(receiver != null) {
				receiver.getSession().getBasicRemote().sendObject(messageDTO);
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
