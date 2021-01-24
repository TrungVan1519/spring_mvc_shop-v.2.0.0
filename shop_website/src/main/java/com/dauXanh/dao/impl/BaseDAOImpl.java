package com.dauXanh.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.BaseDAO;
import com.dauXanh.dto.Page;

@Repository
@Transactional
public class BaseDAOImpl<E> implements BaseDAO<E> {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	SessionFactory sessionFactory;

	/**
	 * Return all records by fields and paging automatically
	 */
	@Override
	public List<E> findAll(StringBuilder criteria, Map<String, Object> params, Page page) {
		// Append sql commands
		StringBuilder sql = new StringBuilder().append("FROM ").append(this.getEntityClassName()).append(" ");
		StringBuilder counting = new StringBuilder().append("SELECT COUNT(*) FROM ").append(this.getEntityClassName())
				.append(" ");
		if (criteria != null && !criteria.toString().isEmpty()) {
			sql.append(criteria);
			counting.append(criteria);
		}

		// Create queries and set params to them
		Query<E> query = sessionFactory.getCurrentSession().createQuery(sql.toString());
		Query<E> countingQuery = sessionFactory.getCurrentSession().createQuery(counting.toString());
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
				countingQuery.setParameter(key, params.get(key));
			}
		}

		// Paging
		if (page != null) {
			// FROM ... WHERE ... LIMIT page.getOffset(), page.getPageSize()
			query.setFirstResult(page.getOffset());
			query.setMaxResults(page.getPageSize());

			// SELECT COUNT(*) FROM ... WHERE ...
			page.setTotalRecords((long) countingQuery.uniqueResult());
		}

		return query.getResultList();
	}

	/**
	 * Return only 1 record by id
	 */
	@Override
	public E findById(Class<E> entityClassName, Serializable id) {
		return sessionFactory.getCurrentSession().get(entityClassName, id);
	}

	@Override
	public boolean save(E obj) {
		int id = (int) sessionFactory.getCurrentSession().save(obj);
		if (id != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(E obj) {
		Object updated = sessionFactory.getCurrentSession().merge(obj);
		if (updated != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteById(Class<E> entityClassName, Serializable id) {
		E obj = sessionFactory.getCurrentSession().get(entityClassName, id);
		if (obj != null) {
			sessionFactory.getCurrentSession().delete(obj);
			return true;
		}
		return false;
	}

	/**
	 * Get class name was passed to Generic BaseDAO<E>
	 * 
	 * @return
	 */
	String getEntityClassName() {
		String className = getClass().getGenericSuperclass().toString();

		// Cach 1: return "com.dauXanh.entity.User"
		Pattern pattern = Pattern.compile("\\<(.*?)\\>");
		Matcher matcher = pattern.matcher(className);
		if (matcher.find()) {
			return matcher.group(1);
		}

		// Cach 2: return "User"
//		Pattern pattern = Pattern.compile("<(.*?)>");
//		Matcher matcher = pattern.matcher(className);
//		if (matcher.find()) {
//			String[] names = Pattern.compile("\\.").split(matcher.group(1));
//			return names[names.length - 1];
//		}

		return null;
	}
}
