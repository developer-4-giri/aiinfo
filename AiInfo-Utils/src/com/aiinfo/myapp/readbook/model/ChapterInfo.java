package com.aiinfo.myapp.readbook.model;

import java.util.List;

public class ChapterInfo {
	

	private int chapterNumber;
	private String chapterTitle;
	private String chapterLanguage;
	private List<PageInfo> chapterPages;
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	public String getChapterTitle() {
		return chapterTitle;
	}
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	public String getChapterLanguage() {
		return chapterLanguage;
	}
	public void setChapterLanguage(String chapLanguage) {
		chapterLanguage = chapLanguage;
	}
	public List<PageInfo> getChapterPages() {
		return chapterPages;
	}
	public void setChapterPages(List<PageInfo> newchapterPages) {
		chapterPages = newchapterPages;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChapterInfo [chapterNumber=");
		builder.append(chapterNumber);
		builder.append(", chapterTitle=");
		builder.append(chapterTitle);
		builder.append(", chapterLanguage=");
		builder.append(chapterLanguage);
		builder.append(", chapterPages=");
		builder.append(chapterPages);
		builder.append("]");
		return builder.toString();
	}

}
