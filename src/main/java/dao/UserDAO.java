package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.*;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import model.User;
import util.HibernateUtil;

public class UserDAO implements InterfaceDAO<User> {
	
	private static UserDAO Instance = null;
	
	public static UserDAO getInstance() {
		if(Instance == null) Instance = new UserDAO();
		return Instance;
	}
	
	private UserDAO() {
		
	}

	@Override 
	public User selectById(String id, Session session) {
		return HQLutil.getInstance().doSelectById(User.class, id, session);
	}

	@Override
	public ArrayList<User> selectAll(Session session) {
		return (ArrayList<User>) HQLutil.getInstance().doSelectAll(User.class, session);
	}

	@Override
	public void insert(User t, Session session) {
		HQLutil.getInstance().doInsert(t, session);
	}

	@Override
	public void update(User t, Session session) {
		HQLutil.getInstance().doUpdate(t, session);
		
	}

	@Override
	public void delete(User user, Session session) {
		HQLutil.getInstance().doDeleteById(user, session);
		
	}
	
	public User selectUserByUsernameAndPassword(String username,String password,Session session) {
		try {
			String hql = "FROM User u WHERE u.username = :username AND u.password = :password";
			Query<User> query = session.createQuery(hql).setParameter("username", username)
														.setParameter("password", password);
			List<User> li = query.list();
			if(li.size() > 0) return li.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> selectUserToAdd(String id,Session session) {
		ArrayList<User> res = new ArrayList<User>();
		try {
			
			String sql = "SELECT *\r\n"
					+ "FROM user u\r\n"
					+ "WHERE u.id NOT IN (\r\n"
					+ "    SELECT gu.id_user\r\n"
					+ "    FROM group_users gu\r\n"
					+ "    WHERE gu.id_group = :id\r\n"
					+ "    )";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("id", id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			List results = query.list();
			
			for(Object result : results) {
				Map row = (Map) result;
				String id_user = (String) row.get("id");
				String avatar = (String) row.get("avatar");
				String name = (String) row.get("fullname");
				User u  = new User(id_user, name, avatar);
				res.add(u);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void setStatus(String id,Boolean status,Session session) {
		try {
			String hql = "UPDATE User SET satatus_activity = :status WHERE id = :id";
			Transaction tr = session.getTransaction();
			tr.begin();
			Query query = session.createQuery(hql);
			query.setParameter("status", status);
			query.setParameter("id", id);
			int result = query.executeUpdate();
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> selectUserToSendRequest(String id,String name_query,Session session) {
		ArrayList<User> res = new ArrayList<User>();
		try {
			
			String sql = "select * from user u\r\n"
					+ "where u.id not in (\r\n"
					+ "	select user.id\r\n"
					+ "from user\r\n"
					+ "inner join friend on user.id = friend.id_sender\r\n"
					+ "where friend.id_receiver = :id \r\n"
					+ "union\r\n"
					+ "select user.id\r\n"
					+ "from user\r\n"
					+ "inner join friend on user.id = friend.id_receiver\r\n"
					+ "where friend.id_sender = :id \r\n"
					+ ") and u.id != :id and u.fullname LIKE :name ";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("id", id);
			query.setParameter("name", "%" + name_query + "%");
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			List results = query.list();
			
			for(Object result : results) {
				Map row = (Map) result;
				String id_user = (String) row.get("id");
				String avatar = (String) row.get("avatar");
				String name = (String) row.get("fullname");
				User u  = new User(id_user, name, avatar);
				res.add(u);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ArrayList<User> li = getInstance().selectUserToSendRequest("2","Phuc" ,session);
		for(User u : li) {
			System.out.println(u.getId());
		}
	}

}
