package dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;

import model.Message;
import util.HibernateUtil;

public class MessageDAO implements InterfaceDAO<Message> {
	
	private static Random rd = new Random();

	private static MessageDAO Instance = null;

	public static MessageDAO getInstance() {
		if (Instance == null)
			Instance = new MessageDAO();
		return Instance;
	}
	
	private MessageDAO() {
		
	}

	@Override
	public Message selectById(String id, Session session) {
		return HQLutil.getInstance().doSelectById(Message.class, id, session);
	}

	@Override
	public ArrayList<Message> selectAll(Session session) {

		return null;
	}

	@Override
	public void insert(Message t, Session session) {
		t.setId(System.currentTimeMillis() + "" + rd.nextInt(1000));
		HQLutil.getInstance().doInsert(t, session);
	}

	@Override
	public void update(Message t, Session session) {
		t.setId(System.currentTimeMillis() + "" + rd.nextInt(1000));
		HQLutil.getInstance().doUpdate(t, session);
	}

	@Override
	public void delete(Message t, Session session) {
		HQLutil.getInstance().doDeleteById(t, session);

	}
	
	public ArrayList<Message> selectConversation(String id1,String id2,Integer page,Integer size,Session session) {
		ArrayList<Message> res;
		String hql = "FROM Message m where (m.sender = :id1 and m.receiver = :id2) OR (m.sender = :id3 and m.receiver = :id4) order by m.time desc";
		Query<Message> query = session.createQuery(hql).setParameter("id1", id1)
														.setParameter("id2", id2)
														.setParameter("id4", id1)
														.setParameter("id3", id2);
        query.setFirstResult(page* size);
        query.setMaxResults(size);
        res = (ArrayList<Message>) query.list();
        Collections.reverse(res);
		return res;
	}
	
	public ArrayList<Message> selectGroupChat(String idgr,Integer page,Integer size,Session session) {
		ArrayList<Message> res;
		String hql = "FROM Message m where m.groupId = :id order by m.time desc ";
		Query<Message> query = session.createQuery(hql).setParameter("id", idgr);
        query.setFirstResult(page* size);
        query.setMaxResults(size);
        res = (ArrayList<Message>) query.list();
        Collections.reverse(res);
		return res;
	}

	public Message selectLastMessage(String id1,String id2,Session session) {
		ArrayList<Message> res;
		String hql = "FROM Message m where (m.sender = :id1 and m.receiver = :id2) OR (m.sender = :id3 and m.receiver = :id4) order by m.time desc";
		Query<Message> query = session.createQuery(hql).setParameter("id1", id1)
														.setParameter("id2", id2)
														.setParameter("id4", id1)
														.setParameter("id3", id2);
        query.setFirstResult(0);
        query.setMaxResults(1);
        res = (ArrayList<Message>) query.list();
        if(res.size() > 0) return res.get(0);
		return null;
	}
	
	public static void main(String[] args) {
		Message m = new Message("1", "1", "2", "..", "..", "..", false, Timestamp.valueOf(LocalDateTime.now()), "..");
		Session session = HibernateUtil.getSessionFactory().openSession();
//		ArrayList<Message> li  = getInstance().selectConversation("1", "2", session);
//		for(Message ms : li) {
//			System.out.println(ms.getContent()); 
//		}
	}

}
