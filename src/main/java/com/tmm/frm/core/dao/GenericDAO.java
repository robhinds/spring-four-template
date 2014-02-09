package com.tmm.frm.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {


	public void persist(T entity);

	public T merge(T entity);

	public void persist(Collection<T> entities);

	T refresh(T transientObject);

	T refresh(PK id);

	Collection<T> refresh(Collection<T> entities);

	void flushAndClear();

	List<T> find();
	
	void clear();
	
	void flush();
}
