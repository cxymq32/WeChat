package com.bkk.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shop")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;
	// ID
	@Id
	@GeneratedValue
	private Long id;
	private Date createTime;
	// 商铺名称
	private String shopName;
	// 电话
	private String phone;
	// 地址
	private String adress;
	private String location;
	// 店铺主图
	private String mainImage;
	// 轮播图
	private String image1;
	private String image2;
	private String image3;
	//优惠信息
	private String sale;
	
	//商铺持有人
	private Long userId;
	//是否显示 0 否 1是
	private int isShow;
	// 精度
	private double latitude;
	// 纬度
	private double longitude;
	//分类
	private String kind1;
	private String kind2;
	private String kind3;
	
	
	
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getMainImage() {
		return mainImage;
	}
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getKind1() {
		return kind1;
	}
	public void setKind1(String kind1) {
		this.kind1 = kind1;
	}
	public String getKind2() {
		return kind2;
	}
	public void setKind2(String kind2) {
		this.kind2 = kind2;
	}
	public String getKind3() {
		return kind3;
	}
	public void setKind3(String kind3) {
		this.kind3 = kind3;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
}
