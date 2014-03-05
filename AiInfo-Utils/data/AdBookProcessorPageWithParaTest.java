package com.aiinfo.myapp.readbook.util.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import com.aiinfo.myapp.readbook.model.ChapterInfo;
import com.aiinfo.myapp.readbook.model.PageInfo;


public class AdBookProcessorPageWithParaTest {

	public static void  main(String[] args) {
		ChapterInfo chapInfo= new ChapterInfo();
		PageInfo pageInfo = null;
		List<String> paraList = null;
		String paraContent = null;
		
		String line;
		
    	List<PageInfo> pages = new ArrayList<PageInfo>();
    	int pageCounter=1;
    	BufferedReader br=null; 

    	try{
    		br= new BufferedReader(new InputStreamReader(new FileInputStream("/Volumes/DATA/MyAndriodWS/MyBook-BG-EG/assets/data/bhagvadgita/english/chapter_1.txt")));
    		
    		while((line = br.readLine()) != null) {
    			if(line.trim().length() >0){

    				if(line.contains("*** NEW PAGE ***")){
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
        				
            			pageInfo.setPageNumber(pageCounter++);
    				}
    				else  if(line.contains("*** NEW PARA ***")){
    					//this are actual page contents and so we should parse it and store it in paragraphs in page.
    					
    					//First check if previous para is already set
    					if(paraContent != null) {
    						paraList.add(paraContent);
    						paraContent = null;
    					}
    				}
    				else{
    					if(paraContent ==null) 
    						paraContent = line;
    					else
    						paraContent += SystemUtils.LINE_SEPARATOR+ line;
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
			throw new RuntimeException("Exception while loading the book chapter number :");
		}

		chapInfo.setChapterPages(pages);

	}

}
