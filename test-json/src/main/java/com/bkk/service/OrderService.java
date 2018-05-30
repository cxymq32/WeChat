package com.bkk.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.bkk.common.base.MyPage;
import com.bkk.domain.Order;

@Service
public class OrderService extends BaseService {

	public List<Order> getByPage(MyPage page, Order order) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
		if (order != null) {
			if (order.getOpenId() != null)
				criteria.add(Restrictions.eq("openId", order.getOpenId()));
		}
		criteria.addOrder(org.hibernate.criterion.Order.desc("id"));
		int start = (page.getPage() - 1) * page.getPageSize();
		return (List<Order>) getHibernateTemplate().findByCriteria(criteria, start, page.getPageSize());
	}

	public List<Order> getByShopId(Long shopId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
		if (shopId != null) {
			criteria.add(Restrictions.eq("shopId", shopId));
		}
		criteria.addOrder(org.hibernate.criterion.Order.desc("id"));
		return (List<Order>) getHibernateTemplate().findByCriteria(criteria);
	}

}
