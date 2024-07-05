package dao;

import java.util.ArrayList;

import org.hibernate.Session;

import model.ChatGroup;
import model.Group_Users;
import model.User;
import util.HibernateUtil;

public class GroupUsersDAO implements InterfaceDAO<Group_Users> {

	private static GroupUsersDAO Instance = null;

	public static GroupUsersDAO getInstance() {
		if (Instance == null)
			Instance = new GroupUsersDAO();
		return Instance;
	}

	private GroupUsersDAO() {
		
	}

	@Override
	public Group_Users selectById(String id, Session session) {
		return HQLutil.getInstance().doSelectById(Group_Users.class, id, session);
	}

	@Override
	public ArrayList<Group_Users> selectAll(Session session) {
		return (ArrayList<Group_Users>) HQLutil.getInstance().doSelectAll(Group_Users.class, session);
	}

	@Override
	public void insert(Group_Users t, Session session) {
		HQLutil.getInstance().doInsert(t, session);
	}

	@Override
	public void update(Group_Users t, Session session) {
		HQLutil.getInstance().doUpdate(t, session);
	}

	@Override
	public void delete(Group_Users id, Session session) {
		HQLutil.getInstance().doDeleteById(id, session);
	}
	
	public ArrayList<Group_Users> selectByIdUser(String id,Session session) {
		String hql = "from Group_Users g where g.id_user = :id";
		return (ArrayList<Group_Users>) session.createQuery(hql).setParameter("id", id).list();
	}
	
	public ArrayList<Group_Users> selectByGroupId(Long id,Session session) {
		String hql = "from Group_Users g where g.chatGroup.id = :id";
		return (ArrayList<Group_Users>) session.createQuery(hql).setParameter("id", id).list();
	}
	
	public void addNewGroupUsers(ArrayList<String> arr,ChatGroup chatGroup,Session session) {
		for(String s : arr) {
			Group_Users g = new Group_Users();
			g.setGroup(chatGroup);
			g.setId_user(s);
			g.setRole(Group_Users.Role.ME);
			g.setStatus(true);
			getInstance().insert(g, session);
		}
	}
	
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Group_Users> li = getInstance().selectByIdUser("1", session);
		for(Group_Users g : li) System.out.println(g.getId() + " " + g.getGroup().getName());
		
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Group_Users g = new Group_Users();
//		User u = new User(); u.setId("1");
//		ChatGroup gr = new ChatGroup(); gr.setId(Long.parseLong("9"));
//		g.setUser(u); g.setGroup(gr);
//		getInstance().insert(g, session);
	}

}
