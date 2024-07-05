package dao;
import java.lang.reflect.*;
import java.util.*;


import javax.persistence.criteria.*;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import util.*;	
public class HQLutil {
	private static HQLutil Instance;
	public static HQLutil getInstance()
	{
		if (Instance == null)
		{
			Instance = new HQLutil();
		}
		return Instance;
	}
	

	
	@SuppressWarnings("deprecation")
	public <T> List<T> doSelectAll(Class<T> c,Session session)
	{
		List<T> res;
//		Session s= HibernateUtil.getSessionFactory().openSession();
		Criteria cr = session.createCriteria(c);
		res = cr.list();
//		HibernateUtil.close();
		return res;
	}
	public <T>	T doSelectById (Class<T> c, String id,Session s)
	{
		T res;
		res = s.get(c, id);
		return res;
	}
	
	public <T,V> List<T> doSelectByField (Class<T> c, String field_name, V value)
	{
		List<T> res;
		Session s= HibernateUtil.getSessionFactory().openSession();
		Criteria cr = s.createCriteria(c);
		cr.add(Restrictions.eq(field_name, value));
		res = cr.list();
		HibernateUtil.close();
		return res;
	}
	public <T> boolean doDeleteById (T ob,Session session)
	{
		boolean isSuccess = true;
		Transaction ts = session.getTransaction();
		try
		{
			ts.begin();
			session.remove(ob);
			ts.commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return isSuccess;
	}

	
	public <T,V> int doDeleteAll(Class<T> c, String table_name, String field_name, V value)
	{
		int res;
		String hql = "delete from "+table_name+" o where o."+field_name+" = :value";
		Session s= HibernateUtil.getSessionFactory().openSession();
		res = s.createQuery(hql, c).setParameter("value",value).executeUpdate();
		HibernateUtil.close();
		return res;
	}
	
	

	
	public <T> boolean doInsert(T ob,Session session)
	{
		boolean isSuccess = true;
		Transaction ts = session.getTransaction();
		try
		{
			ts.begin();
			session.save(ob);
			ts.commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			ts.rollback();
			isSuccess= false;
		}
		return isSuccess;
	}
	
	public <T> boolean doInsertByMerge(T ob,Session session)
	{
		boolean isSuccess = true;
		Transaction ts = session.getTransaction();
		try
		{
			ts.begin();
			session.persist(ob);
			ts.commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
//			ts.rollback();
			isSuccess= false;
		}
		return isSuccess;
	}

	public <T> boolean doUpdate(T ob,Session s)
	{
		boolean isSuccess = true;
		Transaction ts = s.getTransaction();
		try
		{
			ts.begin();
			s.update(ob);
			ts.commit();
		}
		catch (Exception ex)
		{
			ts.rollback();
			isSuccess= false;
		}
		return isSuccess;
	}

	
	public <T> List<T> doQuery (String hql, Class<T> c, Session s,int offset,int limit, Object... params)
	{
		Query<T> query;
		if (offset>=0 && limit>=0)
		{
			query = s.createQuery(hql,c).setFirstResult(offset).setMaxResults(limit);
		}
		else
		{
			query = s.createQuery(hql,c);
		}
		for (int i=0;i<params.length;i++)
		{
			query.setParameter(i+1, params[i]);
		}
		List<T> res = query.list();
		return res;
	}
	
	
	public long doCountRecordOf (String hql, Session s, Object... params)
	{
		Query<Long> query = s.createQuery(hql,Long.class);
		for (int i=0;i<params.length;i++)
		{
			query.setParameter(i+1, params[i]);
		}
		Long res = query.getSingleResult();
		return res;
	}

}
