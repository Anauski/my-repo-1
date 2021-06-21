package net.ent.etrs.librairie.model.daos;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import lombok.Getter;
import lombok.Setter;



public abstract class JpaBaseDao<T, ID> implements BaseDao<T, Serializable> {

	@Getter
	protected Class<T> entityClass;

	@Getter
	@Setter
	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public JpaBaseDao() {
		ParameterizedType genericSuperClass = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
	}

	@Override
	public <S extends T> S save(S entity) {
		if (entity != null) {

			entity = this.entityManager.merge(entity);

		}
		return entity;
	}

	@Override
	public T findOne(Serializable id) {
		return this.entityManager.find(this.entityClass, id);
	}

	@Override
	public Iterable<T> findAll() {
		return this.entityManager
				.createQuery("SELECT t FROM " + this.entityClass.getSimpleName() + " t", this.entityClass)
				.getResultList();
	}

	@Override
	public void delete(T entity) {
		if (entity != null) {
			
			this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));

		}
	}

	@Override
	public void delete(Serializable id) {
		T entity = this.findOne(id);
		this.delete(entity);
	}

	@Override
	public boolean exists(Serializable id) {
		return this.findOne(id) != null;
	}

	@Override
	public long count() {
		TypedQuery<Long> q = this.entityManager
				.createQuery("SELECT COUNT(t) FROM " + this.entityClass.getSimpleName() + " t", Long.class);
		return q.getSingleResult();
	}
}
