package com.aiinfo.myapp.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.lang3.SystemUtils;

public class ProcessFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		processSlokFiles("/Volumes/DATA/MyAndriodWS/MyBook-BhagvadGita-Full/assets/data/bg","chapter_9.txt");
		
		/*int count =0;
		for(String strrr:anvay_lines)
			System.out.println("Anvay_line_"+ count++ +" : "+ strrr);*/
	}
	
	public static void processSlokFiles(String directoryPath,String fileName){
		
		String[] anvay_lines= createAnvayArray(directoryPath+"/anvay/"+fileName);
		
		try {

	        	BufferedReader in_sanskrit = new BufferedReader( new InputStreamReader( new FileInputStream(new File(directoryPath+"/sanskrit/"+fileName)), "UTF8"));
	        	//Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(directoryPath+SystemUtils.FILE_SEPARATOR+fileName)), "UTF-8"));
	        	String str;
	        	int count=0;
    			while ((str = in_sanskrit.readLine()) != null) {
    				if(str.trim().length() !=0) {
    					if(str.contains("*** NEW PARA ***"))
    						System.out.println (str+SystemUtils.LINE_SEPARATOR+anvay_lines[count++]+SystemUtils.LINE_SEPARATOR);
    					else
    						System.out.println (str);
    				}
    			}
    			in_sanskrit.close();
    			//out.close();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static String[] createAnvayArray(String directoryPath){

		String[] anvay_lines=new String[100];
		
		try {

        		BufferedReader in_anvay = new BufferedReader( new InputStreamReader( new FileInputStream(new File(directoryPath)), "UTF8"));

        		String str;
	        	int start_read_counter=0;
	        	int line_counter=0;
	        	
	        	boolean read_anvay=false;
	        	
    			while ((str = in_anvay.readLine()) != null) {
    				if(str.trim().length() !=0) {
    					if(str.contains("||"))
    						start_read_counter++;
    					else
    						if(read_anvay)
    							if(anvay_lines[line_counter] !=null )
    								anvay_lines[line_counter]+= str+SystemUtils.LINE_SEPARATOR;
    							else
    								anvay_lines[line_counter]= str+SystemUtils.LINE_SEPARATOR;
    							
    					if(read_anvay && str.contains("|")){
    						line_counter++;
    						read_anvay=false;
    					}

    					if(start_read_counter==2){
    						read_anvay=true;
    						start_read_counter=0;
    					}
    					
 						
    				}
    			}
    			in_anvay.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return anvay_lines;
	}

}
