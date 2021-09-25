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

	public <T> boolean update(T object)
	{
		checkObject(object.getClass());
		try
		{
			// session.clear();
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
			return false;
		}
	}

	public <T> boolean save(T object)
	{
		checkObject(object.getClass());
		try
		{
			session.beginTransaction();
			session.saveOrUpdate(object);
			session.getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
			return false;
		}
	}

	public <T> boolean saveList(List<T> objectList)
	{
		checkObject(objectList.get(0).getClass());
		try
		{
			session.beginTransaction();
			for (T temp : objectList)
			{
				session.saveOrUpdate(temp);
			}
			session.getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
			return false;
		}
	}

	public <T> boolean deleteAll(Class<T> objectClass)
	{
		checkObject(objectClass);
		try
		{
			List<T> list = getList(objectClass);
			session.beginTransaction();
			for (T temp : list)
			{
				session.delete(temp);
			}
			session.getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
			return false;
		}
	}

	public <T> boolean delete(T object)
	{
		try
		{
			checkObject(object.getClass());
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
			return false;
		}
	}

	public <T> boolean deleteList(List<T> objectList)
	{
		try
		{
			checkObject(objectList.get(0).getClass());
			session.beginTransaction();
			for (T temp : objectList)
			{
				session.delete(temp);
			}
			session.getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Cant save object!", e);
			session.getTransaction().rollback();
			return false;
		}
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
