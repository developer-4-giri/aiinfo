package com.aiinfo.common.book.model;

import java.util.Arrays;

public class PageInfo {

	private int pageNumber;
	private String pageTitle;
	private String pageLanguage;
	private String[] pageContents;
	private String[] pageParagraphs;
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageLanguage() {
		return pageLanguage;
	}
	public void setPageLanguage(String pageLanguage) {
		this.pageLanguage = pageLanguage;
	}
	public String[] getPageContents() {
		return pageContents;
	}
	public void setPageContents(String[] pageContents) {
		this.pageContents = pageContents;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageInfo [pageNumber=");
		builder.append(pageNumber);
		builder.append(", pageTitle=");
		builder.append(pageTitle);
		builder.append(", pageLanguage=");
		builder.append(pageLanguage);
		builder.append(", pageContents=");
		builder.append(Arrays.toString(pageContents));
		builder.append(", pageParagraphs=");
		builder.append(Arrays.toString(pageParagraphs));
		builder.append("]");
		return builder.toString();
	}
	public String[] getPageParagraphs() {
		return pageParagraphs;
	}
	public void setPageParagraphs(String[] pageParagraphs) {
		this.pageParagraphs = pageParagraphs;
	}
}
