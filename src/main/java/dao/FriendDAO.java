package dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Friend;
import model.Friend.Status;
import util.HibernateUtil;

public class FriendDAO implements InterfaceDAO<Friend> {
	private static FriendDAO Instance = null;

	public static FriendDAO getInstance() {
		if (Instance == null)
			Instance = new FriendDAO();
		return Instance;
	}

	private FriendDAO() {
		
	}

	@Override
	public Friend selectById(String id, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Friend> selectAll(Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Friend t, Session session) {
		HQLutil.getInstance().doInsert(t, session);
	}

	@Override
	public void update(Friend t, Session session) {
		HQLutil.getInstance().doInsert(t, session);
	}

	@Override
	public void delete(Friend id, Session session) {
		HQLutil.getInstance().doDeleteById(id, session);
	}

	public ArrayList<Friend> selectRequestFriend(String id, Session session, Status status) {
		String hql = "from Friend f where f.receiver = :id and f.status = :status";
		return (ArrayList<Friend>) session.createQuery(hql).setParameter("id", id).setParameter("status", status)
				.list();
	}

	public ArrayList<Friend> selectAllFriend(String id, Session session) {
		String hql = "from Friend f where (f.receiver.id = :id or f.sender.id = :id)";
		return (ArrayList<Friend>) session.createQuery(hql).setParameter("id", id)
				.list();
	}
	
	public ArrayList<Friend> selectFriendToAdd(String id,String name,Session session) {
		String hql = "from Friend f where (f.sender.id = :id and f.receiver.fullname LIKE :name) or  (f.receiver.id = :id and f.sender.fullname LIKE :name) ";
		return (ArrayList<Friend>) session.createQuery(hql).setParameter("id", id)
								.setParameter("name", name).list();
	}
	
	public void updateStatus(String id1,String id2,Status status,Session session) {
		try {
			String hql = "UPDATE Friend f SET f.status = :status where (f.sender.id = :id1 and f.receiver.id = :id2 ) or  (f.sender.id = :id2 and f.receiver.id = :id1)";
			Transaction tr = session.beginTransaction();
			session.createQuery(hql).setParameter("id1", id1)
			.setParameter("id2", id2)
			.setParameter("status", status)
			.executeUpdate();
			tr.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Friend> selectFriend(String id,Session session,Status status) {
		try {
			String hql = "from Friend f where (f.sender.id = :id or f.receiver.id = :id) and f.status = :status";
			return (ArrayList<Friend>) session.createQuery(hql).setParameter("id", id)
																.setParameter("status", status).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Friend> li = getInstance().selectFriend("1", session, Status.DAXACNHAN);
		for(Friend f : li) System.out.println(f.getSender().getId() + ' ' + f.getReceiver().getId());
	}

}
