package com.bkk.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.bkk.domain.User;

@Service
@SuppressWarnings("unchecked")
public class UserService extends BaseService {
	public User findByNameAndPass(User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("username", user.getUsername()));
		criteria.add(Restrictions.eq("password", user.getPassword()));
		List<User> list = (List<User>) getHibernateTemplate().findByCriteria(criteria);
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<User> checkUname(String uname) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("username", uname));
		List<User> list = (List<User>) getHibernateTemplate().findByCriteria(criteria);
		return list;
	}


}