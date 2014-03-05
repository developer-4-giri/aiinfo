package com.aiinfo.myapp.readbook.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.aiinfo.myapp.readbook.model.BookInfo;
import com.aiinfo.myapp.readbook.model.ChapterInfo;
import com.aiinfo.myapp.readbook.model.LangNumber;
import com.aiinfo.myapp.readbook.model.PageInfo;

public class GenBookProcessor {

	private String bookResourcesPath;
	Properties bookRelProp;
	LangNumber bookRelLangNum;
	
	public void prepareBookRead(String myBookResourcePath){
		this.bookResourcesPath =  myBookResourcePath;
		//Read book related properties
		try {
			bookRelProp = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();           
			InputStream stream = loader.getResourceAsStream(bookResourcesPath+"/decoration.properties");
			System.out.println("stream" + stream);
			
			bookRelProp.load(stream);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception while loading the book properties");
		}
		
		bookRelLangNum = new LangNumber(bookRelProp.getProperty("book_lang_num","0,1,2,3,4,5,6,7,8,9"));
	}
	
	public BookInfo readBookFromFile(){
		BookInfo retval= new BookInfo();
		ChapterInfo chapInfo;
		
		//Traverse all the files in the directory
		File dir = new File(bookResourcesPath);
	    String[] chapterFiles = dir.list();
	    Arrays.sort(chapterFiles);
	    if (chapterFiles == null) {
			throw new RuntimeException("No Chapters defined for this book");
		} else {
			List<ChapterInfo> chapters = new ArrayList<ChapterInfo>();
			int chapCounter=1;
		    for (int i=0; i<chapterFiles.length; i++) {
		        String filename = chapterFiles[i];
		        //process each chapter here 
		        if(filename.startsWith("chapter_")){
		        	chapInfo = readChapterFromFile(filename,chapCounter++);
					chapters.add(chapInfo);
		        }
		        
		        retval.setBookChapters(chapters);
		        retval.setBookTitle(bookRelProp.getProperty("book_title"));
		    }
		}
		return retval;
	}
	
	public ChapterInfo readChapterFromFile(String filename,int chapterNumber){
		ChapterInfo chapInfo= new ChapterInfo();
		PageInfo pageInfo;
		String line;
		
    	List<PageInfo> pages = new ArrayList<PageInfo>();
    	int pageCounter=1;

    	try{
        	BufferedReader br = new BufferedReader(new FileReader(filename));
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
}
