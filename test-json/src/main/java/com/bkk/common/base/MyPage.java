package com.bkk.common.base;

public class MyPage {
	/** 当前页 */
	private int page;
	/** 每页显示的条数 */
	private int pageSize;
	/** 排序 */
	private String sort;
	/** 排序的类型 */
	private String order;
	// ============================

	public MyPage() {
		super();
		this.page = 1;
		this.pageSize = 10;
		this.sort = "id";
		this.order = "desc";
	}

	// ============================
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
