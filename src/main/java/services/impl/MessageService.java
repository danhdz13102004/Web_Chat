package services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import org.hibernate.Session;

import dao.MessageDAO;
import model.Message;
import model.dto.MessageDTO;
import services.MessageInterface;
import util.HibernateUtil;

public class MessageService implements MessageInterface{
	
	private static MessageService messageService = null;

//	private UserDaoInterface userDaoInterface = UserDao.getInstace();

	private MessageService() {
	}

	public synchronized static MessageService getInstance() {
		if (messageService == null) {
			messageService = new MessageService();
		}
		return messageService;
	}

	@Override
	public void saveMessage(MessageDTO messageDTO) {
		Message m = new Message();
		m.setAvatar(messageDTO.getAvatar());
		m.setContent(messageDTO.getMessage());
		m.setSender(messageDTO.getSender());
		m.setReceiver(messageDTO.getReceiver());
		m.setType(messageDTO.getType());
		m.setGroupId(messageDTO.getGroupId() + "");
		m.setTime(messageDTO.getTime());
		m.setStatus(true);
		m.setName(messageDTO.getNamefile());
		Session session = HibernateUtil.getSessionFactory().openSession();
		MessageDAO.getInstance().insert(m, session);
		session.close();	
	}

}
