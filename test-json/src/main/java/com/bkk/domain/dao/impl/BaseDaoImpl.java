package com.bkk.domain.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkk.domain.dao.BaseDao;

/**
 * 这个类将dao成封装成了一个操作类，从网上复制过来的。
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class BaseDaoImpl implements BaseDao {
	/**
	 * 这个bean里面需要注入sessionFactory，所以把这个bean写在了配置中。
	 */
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public <T> T findById(Class<T> type, Serializable id) {
		return hibernateTemplate.get(type, id);
	}

	public <T> List<T> findAll(Class<T> type) {
		return hibernateTemplate.loadAll(type);
	}

	public void save(Object... entities) {
		for (Object entity : entities) {
			hibernateTemplate.save(entity);
		}
	}

	public void saveOrUpdate(Object entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}

	public void update(Object... entities) {
		for (Object entity : entities) {
			hibernateTemplate.update(entity);
		}
	}

	public void delete(Object... entities) {
		for (Object entity : entities) {
			if (entity != null) {
				hibernateTemplate.delete(entity);
			}
		}
	}

	public void deleteById(Class<?> type, Serializable id) {
		if (id == null) {
			return;
		}
		Object entity = findById(type, id);
		if (entity == null) {
			return;
		}
		delete(entity);
	}

	public void refresh(Object... entities) {
		for (Object entity : entities) {
			hibernateTemplate.refresh(entity);
		}
	}

	public void flush() {
		hibernateTemplate.flush();
	}
}