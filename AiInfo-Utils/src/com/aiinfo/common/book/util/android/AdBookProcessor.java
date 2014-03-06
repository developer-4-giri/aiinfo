package com.aiinfo.common.book.util.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.aiinfo.common.book.model.BookInfo;
import com.aiinfo.common.book.model.ChapterInfo;
import com.aiinfo.common.book.model.LangNumber;
import com.aiinfo.common.book.model.PageInfo;

public class AdBookProcessor {

	Properties bookRelProp;
	LangNumber bookRelLangNum;
	
	public BookInfo prepareBookRead(InputStream myBookResourcePropertyPath,String encoding){
		BookInfo retval= new BookInfo();
		String line;

		//Read book related properties
		try {
			bookRelProp = new Properties();
        	BufferedReader br = new BufferedReader(new InputStreamReader(myBookResourcePropertyPath, encoding));
    		while((line = br.readLine()) != null) {
    			if(line.length() >0 && line.contains("=")){
        			String[] propStr = line.split("=");
        			bookRelProp.setProperty(propStr[0], propStr[1]);
    			}
    		}
    		br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception while loading the book properties");
		}
		
		bookRelLangNum = new LangNumber(bookRelProp.getProperty("book_lang_num","0,1,2,3,4,5,6,7,8,9"));
		retval.setBookTitle(bookRelProp.getProperty("book_title"));
		return retval;
	}
	
	public ChapterInfo readChapterFromFile(InputStream filenameStream,String encoding,int chapterNumber){
		ChapterInfo chapInfo= new ChapterInfo();
		PageInfo pageInfo;
		String line;
		
    	List<PageInfo> pages = new ArrayList<PageInfo>();
    	int pageCounter=1;
    	BufferedReader br=null; 

    	try{
    		if(encoding != null)
    			br= new BufferedReader(new InputStreamReader(filenameStream, encoding));
    		else
    			br= new BufferedReader(new InputStreamReader(filenameStream));
    		
    		while((line = br.readLine()) != null) {
    			
    			pageInfo = new PageInfo();
    			pageInfo.setPageContents(line.split(bookRelProp.getProperty("page_content_delimiter","NNNOOOOOOTTTTTTHHIINNGGG")));	
    			
    			if(bookRelProp.getProperty("page_title_"+pageCounter) != null)
    				pageInfo.setPageTitle(bookRelProp.getProperty("page_title_"+pageCounter));
    			else
    				pageInfo.setPageTitle(bookRelProp.getProperty("page_title_default")+"_"+bookRelLangNum.convertNum(pageCounter));
    				
    			pageInfo.setPageNumber(pageCounter++);
    			pages.add(pageInfo);
    		}
    		br.close();
    	}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception while loading the book chapter number :"+chapterNumber);
		}
    	

		if(bookRelProp.getProperty("chapter_title_"+chapterNumber) != null)
			chapInfo.setChapterTitle(bookRelProp.getProperty("chapter_title_"+chapterNumber));
		else
			chapInfo.setChapterTitle(bookRelProp.getProperty("chapter_title_default")+"_"+bookRelLangNum.convertNum(chapterNumber));

		chapInfo.setChapterPages(pages);
		chapInfo.setChapterNumber(chapterNumber);

		return chapInfo;
	}
	
	public String getBookProperty(String propertyName){
		return bookRelProp.getProperty(propertyName);
	}
}
