package com.bawei.ssm.common;

public class Page {

	private int pageSize;
	private int pageNum;
	private long pageCount;
	private int start;
	private boolean isDesc;
	private boolean isPage;
	private int pages;

	public Page() {
	}

	public Page(boolean isPage) {
		this.isPage = isPage;
	}

	public Page(int pageSize, int pageNum) {
		this(pageSize, pageNum, false);
	}

	public Page(int pageSize, int pageNum, boolean isDesc) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.isDesc = isDesc;
		this.start = (pageNum - 1) * pageSize;
		this.isPage = true;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
		this.pages = (pageSize!=0)?(int) (pageCount / pageSize + (pageCount % pageSize > 0 ? 1 : 0)):1;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public boolean isDesc() {
		return isDesc;
	}

	public void setDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}

	public boolean isPage() {
		return isPage;
	}

	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
