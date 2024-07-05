package dao;

import java.util.ArrayList;

import org.hibernate.Session;

import model.ChatGroup;
import util.HibernateUtil;

public class GroupDAO implements InterfaceDAO<ChatGroup>{
	
	private static GroupDAO Instance = null;

	public static GroupDAO getInstance() {
		if (Instance == null)
			Instance = new GroupDAO();
		return Instance;
	}
	
	private GroupDAO() {
		
	}
	

	@Override
	public ChatGroup selectById(String id, Session session) {
		return HQLutil.getInstance().doSelectById(ChatGroup.class, id, session);
	}

	@Override
	public ArrayList<ChatGroup> selectAll(Session session) {
		return (ArrayList<ChatGroup>) HQLutil.getInstance().doSelectAll(ChatGroup.class, session);
	}

	@Override
	public void insert(ChatGroup t, Session session) {
		HQLutil.getInstance().doInsert(t, session);
		
	}

	@Override
	public void update(ChatGroup t, Session session) {
		HQLutil.getInstance().doUpdate(t, session);
	}

	@Override
	public void delete(ChatGroup id, Session session) {
		HQLutil.getInstance().doDeleteById(id, session);	
	}
	
	public ChatGroup selectById(Long id, Session session) {
		String hql = "from ChatGroup c where c.id = :id";
		ArrayList<ChatGroup> li = (ArrayList<ChatGroup>) session.createQuery(hql).setParameter("id", id).list();
		if(li.size() > 0) return li.get(0);
		return null;
	}
	
	public static void main(String[] args) {
		ChatGroup p = getInstance().selectById(Long.parseLong("9"), HibernateUtil.getSessionFactory().openSession());
		System.out.println(p.getName());
	}
	
}
