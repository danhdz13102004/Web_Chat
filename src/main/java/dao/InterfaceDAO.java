package dao;

import java.util.ArrayList;

import org.hibernate.Session;

public interface InterfaceDAO<T> {
	public T selectById(String id,Session session);
	
	public ArrayList<T> selectAll(Session session);
	
	public void insert(T t,Session session);
	
	public void update(T t,Session session);
	
	public void delete(T id,Session session);
}
