package com.dauXanh.dto;

public class Page {

	long totalRecords;
	int totalPages;

	int pageNumber; // current page index
	int pageSize = 8; // records per page = default = 8

	int offset; // = (pageNumber - 1) * pageSize;
	
	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPages() {
		if (totalRecords > 0) {
			totalPages = (int) Math.ceil((double) totalRecords / pageSize);
		}
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getOffset() {
		if (pageNumber > 0) {
			offset = (pageNumber - 1) * pageSize;
		}
		return offset;
	}
}
