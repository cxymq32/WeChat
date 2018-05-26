package com.bkk.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bkk.domain.User;

@Service
@SuppressWarnings("unchecked")
public class UserService extends BaseService {
	public void saveJsonUser(JSONObject userJson) {
		this.save(JsonToUser(userJson, new User()));
	}

	public void updateJsonUser(JSONObject userJson, User user) {
		this.update(JsonToUser(userJson, user));
	}

	private User JsonToUser(JSONObject userJson, User user) {
		user.setOpenid(userJson.getString("openid"));
		user.setNickname(userJson.getString("nickname"));
		user.setSex(userJson.getString("sex"));
		user.setLanguage(userJson.getString("language"));
		user.setCity(userJson.getString("city"));
		user.setProvince(userJson.getString("province"));
		user.setCountry(userJson.getString("country"));
		user.setHeadimgurl(userJson.getString("headimgurl"));
		user.setSubscribe_time(userJson.getString("subscribe_time"));
		user.setUserInfoStr(userJson.toJSONString());
		return user;
	}

	public List<User> findByShopId(Long shopId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("shopId", shopId));
		return (List<User>) getHibernateTemplate().findByCriteria(criteria);
	}

	public User findByOpenid(String openid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("openid", openid));
		List<User> list = (List<User>) getHibernateTemplate().findByCriteria(criteria);
		return list.size() > 0 ? list.get(0) : null;
	}

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