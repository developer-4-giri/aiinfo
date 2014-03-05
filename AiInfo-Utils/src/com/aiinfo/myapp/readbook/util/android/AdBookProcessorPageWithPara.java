package com.aiinfo.myapp.readbook.util.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;

import com.aiinfo.myapp.readbook.model.BookInfo;
import com.aiinfo.myapp.readbook.model.ChapterInfo;
import com.aiinfo.myapp.readbook.model.LangNumber;
import com.aiinfo.myapp.readbook.model.PageInfo;

public class AdBookProcessorPageWithPara {

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

    			String[] propStr = line.split("=");
    			bookRelProp.setProperty(propStr[0], propStr[1]);
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
		PageInfo pageInfo = null;
		List<String> paraList = null;
		String paraContent = null;
		
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
    			if(line.trim().length() >0){

    				if(line.contains(bookRelProp.getProperty("new_page_identifier","*** NEW PAGE ***"))){
    					//store the previous page in the chapter's page list
    					if(pageInfo != null && paraList != null) {
    						
    						if(paraContent != null ) {
        						paraList.add(paraContent);
        						paraContent = null;
        					}
    						pageInfo.setPageParagraphs(paraList.toArray(new String[0]));
    						pages.add(pageInfo);
    						paraList = null;
    					}  
    						
    	       			pageInfo = new PageInfo();
    	       			paraList = new ArrayList<String>();
            			if(bookRelProp.getProperty("page_title_"+pageCounter) != null)
            				pageInfo.setPageTitle(bookRelProp.getProperty("page_title_"+pageCounter));
            			else
            				pageInfo.setPageTitle(bookRelProp.getProperty("page_title_default")+"_"+bookRelLangNum.convertNum(pageCounter));
            				
            			pageInfo.setPageNumber(pageCounter++);
    				}
    				else  if(line.contains(bookRelProp.getProperty("new_para_identifier","*** NEW PARA ***"))){
    					//this are actual page contents and so we should parse it and store it in paragraphs in page.
    					
    					//First check if previous para is already set
    					if(paraContent != null) {
    						paraList.add(paraContent);
    						paraContent = null;
    					}
    				}
    				else{
    					if(paraContent ==null) 
    						paraContent = line.trim();
    					else
    						paraContent += SystemUtils.LINE_SEPARATOR+ line.trim();
    				}
    				
    			}
    		}
			
    		//Check for the last para and last page ending.
			if(paraContent !=null) {
				paraList.add(paraContent);
				pageInfo.setPageParagraphs(paraList.toArray(new String[0]));
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
