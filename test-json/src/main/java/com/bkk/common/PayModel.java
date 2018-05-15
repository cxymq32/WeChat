package com.bkk.common;

public class PayModel {
	private String openid;
	private String total_fee = "1";// 付款金额 0 分
	private String finalsign;
	private String timestamp;
	private String packages;
	private String nonceStr;

	public String getFinalsign() {
		return finalsign;
	}

	public void setFinalsign(String finalsign) {
		this.finalsign = finalsign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
}
