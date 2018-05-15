package com.bkk.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.bkk.common.Page;
import com.bkk.domain.Order;

@Service
public class OrderService extends BaseService {

	public List<Order> getByPage(Page page, Order order) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
		if (order != null) {
			if (order.getId() != null)
				criteria.add(Restrictions.eq("userid", order.getOpenId()));
		}
		int start = (page.getPage() - 1) * page.getPageSize();
		return (List<Order>) getHibernateTemplate().findByCriteria(criteria, start, page.getPageSize());
	}

}