package com.bkk.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.bkk.common.base.MyPage;
import com.bkk.domain.Shop;

@Service
public class ShopService extends BaseService {
	public List<Shop> getByPage(MyPage page, Shop shop) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
		if (shop != null) {
			if (shop.getShopName() != null)
				criteria.add(Restrictions.like("shopName", "%" + shop.getShopName() + "%"));
			if (shop.getId() != null)
				criteria.add(Restrictions.eq("id", shop.getId()));
		}
		int start = (page.getPage() - 1) * page.getPageSize();
		return (List<Shop>) getHibernateTemplate().findByCriteria(criteria, start, page.getPageSize());
	}

	public List<Shop> getByUserId(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
		criteria.add(Restrictions.eq("userId", id));
		List<Shop> list = (List<Shop>) getHibernateTemplate().findByCriteria(criteria);
		return list;
	}

	public List<Shop> getByShopCode(String code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
		criteria.add(Restrictions.eq("shopCode", code));
		return (List<Shop>) getHibernateTemplate().findByCriteria(criteria);
	}

	public void setShopCodeById(long shopId, String code, int close) {
		Shop temp = this.findById(Shop.class, shopId);
		if(close==0) {
			temp.setShopCode(code);
		}else {
			temp.setShopCode(null);
		}
		this.update(temp);
	}
}