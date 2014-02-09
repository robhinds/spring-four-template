package com.tmm.frm.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

public abstract class GenericHibernateDAO<T, PK extends Serializable> implements GenericDAO<T, PK>
{
	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em)
	{
		this.em = em;
	}

	protected Class<T> type = null;
	protected boolean distinct = false;

	 @PersistenceUnit
	 protected EntityManagerFactory entityManagerFactory;

	public EntityManager getEntityManager()
	{
		 return em;
	}

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO()
	{
		this.type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}




	public void persist(T entity)
	{
		getEntityManager().persist(entity);
	}

	
	public T merge(T entity)
	{
		return getEntityManager().merge(entity);
	}

	
	public void delete(T entity){
		T managedEntity = entity;
		if (!getEntityManager().contains(entity)){
			managedEntity = getEntityManager().merge(entity);
		}
		getEntityManager().remove(managedEntity);
	}
	

	@SuppressWarnings("unchecked")
	public T read(Class<?> clazz, PK id)
	{
		return (T) getEntityManager().find(clazz, id);
	}


	public T refresh(final T transientObject)
	{
		EntityManager em = getEntityManager();
		T managedEntity = null;
		if (em.contains(transientObject))
		{
			managedEntity = transientObject;
		} else
		{
			managedEntity = em.merge(transientObject);
		}
		// now refresh the state of the managed object
		em.refresh(managedEntity);
		return managedEntity;
	}

	public T refresh(final PK id)
	{
		if (type == null)
		{
			throw new UnsupportedOperationException(
					"The type must be set to use this method.");
		}
		EntityManager em = getEntityManager();
		T managedEntity = em.find(this.type, id);
		em.refresh(managedEntity);
		return managedEntity;
	}

	public void flushAndClear()
	{
		EntityManager entityManager = getEntityManager();
		entityManager.flush();
		entityManager.clear();
	}


	public void setType(final Class<T> aType)
	{
		this.type = aType;
	}

	public List<T> find()
	{
		return find(type);
	}


	@SuppressWarnings("unchecked")
	public List<T> find(Class<T> clazz)
	{
		String entityName = getEntityName(clazz);
		Query query = getEntityManager().createQuery(
				"SELECT instance FROM " + entityName + " instance");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T findOne(String queryStr, Object param)
	{
		Query query = getEntityManager().createQuery(queryStr);
		query.setParameter(1, param);

		List<T> list = query.getResultList();
		if ( list.size() == 1 )
		{
			return list.get(0);
		}
		else
		{
			return null;
		}
	}

	protected String getEntityName(Class<T> aType)
	{
		Entity entity = aType.getAnnotation(Entity.class);
		if (entity == null)
		{
			return aType.getSimpleName();
		}
		String entityName = entity.name();

		if (entityName == null)
		{
			return aType.getSimpleName();
		} else if (!(entityName.length() > 0))
		{
			return aType.getSimpleName();
		} else
		{
			return entityName;
		}

	}

	protected String getEntityName()
	{
		if (type == null)
		{
			throw new UnsupportedOperationException(
					"The type must be set to use this method.");
		}
		return getEntityName(this.type);
	}


	public void persist(Collection<T> entities)
	{
		for (T entity : entities)
		{
			persist(entity);
		}

	}

	public Collection<T> refresh(Collection<T> entities)
	{
		Collection<T> refreshedResults = new ArrayList<T>(entities.size());
		for (T entity : entities)
		{
			refreshedResults.add(refresh(entity));
		}
		return refreshedResults;
	}


	public void clear()
	{
		getEntityManager().clear();
	}

	public void flush()
	{
		getEntityManager().flush();
	}
}
