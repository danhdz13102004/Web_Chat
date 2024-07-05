package util;
 
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.*;

import com.mysql.cj.Session;

import model.User;

 
public class HibernateUtil {
 
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private HibernateUtil() {
    }
 
    private static SessionFactory buildSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder() //
                .configure("hibernate.cfg.xml")  // Load hibernate.cfg.xml from resource folder by default
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }	
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void close() {
        getSessionFactory().close();
    }
    public static void main(String[] args) {
		org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession();
		User u = new User();
		u.setId("1");
		u.setUsername("danh");
		u.setPassword("123");
		u.setFullname("Ngô Văn Danh");
		Transaction t = s.getTransaction();
		t.begin();
		s.save(u);
		t.commit();
		s.close();
		HibernateUtil.close();
	}
}
