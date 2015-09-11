package com.rippletec.medicine.bean;

/**
 * @author Liuyi 
 * 页面Bean对象
 */
public class PageBean {

    // 当前页数
    public int currentPage;

    // 下一页
    public int nextPage;

    // 上一页
    public int lastPage;

    // 页面起始位置
    public int offset;

    // 页面条目数
    public int pageSize;

    public PageBean(int offset, int pageSize) {
	super();
	this.offset = offset;
	this.pageSize = pageSize;
    }

    public PageBean(int currentPage, int offset,int pageSize) {
	super();
	this.currentPage = currentPage;
	this.offset = offset + (currentPage-1)*pageSize;
	this.pageSize = pageSize;
	this.nextPage = currentPage+1;
	this.lastPage = currentPage-1;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public int getLastPage() {
	return lastPage;
    }

    public int getMaxSize() {
	return pageSize;
    }

    public int getNextPage() {
	return nextPage;
    }

    public int getOffset() {
	return offset;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public void setLastPage(int lastPage) {
	this.lastPage = lastPage;
    }

    public void setMaxSize(int pageSize) {
	this.pageSize = pageSize;
    }

    public void setNextPage(int nextPage) {
	this.nextPage = nextPage;
    }

    public void setOffset(int offset) {
	this.offset = offset;
    }

    @Override
    public String toString() {
	return "PageBean [currentPage=" + currentPage + ", nextPage="
		+ nextPage + ", lastPage=" + lastPage + ", offset=" + offset
		+ ", maxSize=" + pageSize + "]";
    }

}
