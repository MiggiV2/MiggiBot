package de.mymiggi.discordbot.tools.database;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

public class UniversalHibernateClient
{
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	private Session session = sessionFactory.openSession();
	private static final Logger LOGGER = Logger.getLogger(UniversalHibernateClient.class.getName());

	public <T> List<T> getList(Class<T> objectClass)
	{
		checkObject(objectClass);
		String query = "FROM " + objectClass.getSimpleName();
		return session.createQuery(query, objectClass).list();
	}

	public <T> void save(T object)
	{
		checkObject(object.getClass());
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(object);
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
		}
	}

	public <T> void saveList(List<T> objectList)
	{
		checkObject(objectList.get(0).getClass());
		session.beginTransaction();
		for (T temp : objectList)
		{
			session.saveOrUpdate(temp);
		}
		session.getTransaction().commit();
	}

	public <T> void deleteAll(Class<T> objectClass)
	{
		checkObject(objectClass);
		List<T> list = getList(objectClass);
		session.beginTransaction();
		for (T temp : list)
		{
			session.delete(temp);
		}
		session.getTransaction().commit();
	}

	public <T> void delete(T object)
	{
		checkObject(object.getClass());
		session.beginTransaction();
		session.delete(object);
		session.getTransaction().commit();
	}

	public <T> void deleteList(List<T> objectList)
	{
		checkObject(objectList.get(0).getClass());
		session.beginTransaction();
		for (T temp : objectList)
		{
			session.delete(temp);
		}
		session.getTransaction().commit();
	}

	private <T> void checkObject(Class<T> objectClass)
	{
		if (!objectClass.isAnnotationPresent(Entity.class))
		{
			LOGGER.warn(objectClass.getSimpleName() + " is has no annotations @entity! -> cancelled hibernate request!");
			throw new RuntimeException("Entity not registered! No annotations @entity!");
		}
	}
}
