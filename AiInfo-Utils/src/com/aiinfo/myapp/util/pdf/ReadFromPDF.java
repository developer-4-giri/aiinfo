package com.aiinfo.myapp.util.pdf;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.pdfbox.TextToPDF;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.util.PDFTextStripper;

public class ReadFromPDF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Reads in pdf document  
		PDDocument pdDoc;
		try {
			pdDoc = PDDocument.load(new File("/Users/Giri/Downloads/jaiminiya_aranyaka.pdf"));
			//OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("/Users/Giri/Downloads/jaiminiya_aranyaka.txt"), "UTF-8");
			
			PDFTextStripper stripper = new PDFTextStripper("UTF-8");
			stripper.setStartPage(1);
			stripper.setEndPage(2);
			stripper.getText(pdDoc);

			System.out.println("No of Pages - "+pdDoc.getNumberOfPages()+" - "+	stripper.getText(pdDoc));
			
		ReadFromPDF.doIt("/Users/Giri/Downloads/ramayan.pdf", "|| वाल्मीकि रामायण - बालकाण्ड ||");
		        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void doIt(String file, String message) throws IOException, COSVisitorException
	{
	    PDDocument doc = null;
	    try
	    {
	        doc = new PDDocument();
			File fontFile = new File("/Users/Giri/Downloads/xdvng/XDVNG1__.ttf");
		    PDFont font = PDTrueTypeFont.loadTTF(doc,fontFile);

	        TextToPDF textToPdf = new TextToPDF();

	        textToPdf.setFont((PDSimpleFont) font);
	        textToPdf.setFontSize(12);
	        doc = textToPdf.createPDFFromText(new StringReader(message));
	        doc.save(file);
	    }
	    finally
	    {
	        if( doc != null )
	        {
	            doc.close();
	        }
	    }
	}

}
