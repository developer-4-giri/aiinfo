package com.aiinfo.myapp.readbook.model;


public class LangNumber {
	
	private String[] langNumStrings;
	
	public String convertNum(int number){
		StringBuffer retval=null;
		if(langNumStrings != null && langNumStrings.length >0){
			retval= new StringBuffer();
			String origNum = number+"";
			
			for(int counter=0;counter <origNum.length();counter++)
				retval.append(langNumStrings[Integer.parseInt(Character.toString(origNum.charAt(counter)))]);
		}
		return retval.toString();
	}

	public LangNumber(String langNumStrings) {
		this.langNumStrings = langNumStrings.split(",");
	}

}
