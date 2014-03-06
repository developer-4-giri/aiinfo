package com.aiinfo.common.book.model;

import java.util.List;

public class BookInfo {
	private String bookTitle;
	private String bookLanguage;
	private static List<ChapterInfo> bookChapters;
	
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getBookLanguage() {
		return bookLanguage;
	}
	public void setBookLanguage(String bookLanguage) {
		this.bookLanguage = bookLanguage;
	}
	public List<ChapterInfo> getBookChapters() {
		return bookChapters;
	}
	public void setBookChapters(List<ChapterInfo> bookChapters) {
		BookInfo.bookChapters = bookChapters;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookInfo [bookTitle=");
		builder.append(bookTitle);
		builder.append(", bookLanguage=");
		builder.append(bookLanguage);
		builder.append(", bookChapters=");
		builder.append(bookChapters);
		builder.append("]");
		return builder.toString();
	}

}
