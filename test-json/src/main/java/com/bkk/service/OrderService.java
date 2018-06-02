package com.bkk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

	/** 预约列表 */
	public List<Order> getByShopId(Long shopId, String state) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		if (shopId != null) {
			criteria.add(Restrictions.eq("shopId", shopId));
			if ("1".equals(state)) {// 没过期和没取消的
				criteria.add(Restrictions.gt("arriveTimeDate", calendar.getTime()));
				criteria.add(Restrictions.lt("status", 2));

			} else if ("2".equals(state)) {// 过期的
				criteria.add(Restrictions.lt("arriveTimeDate", calendar.getTime()));
			}
		}
		criteria.addOrder(org.hibernate.criterion.Order.asc("arriveTimeDate"));
		return (List<Order>) getHibernateTemplate().findByCriteria(criteria);
	}

}
