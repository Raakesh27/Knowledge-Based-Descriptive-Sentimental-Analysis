package com.ibm.watson.developer_cloud.natural_language_understanding.v1;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import org.xml.sax.SAXException;

public class TikaExtraction {
	
   public static void main(final String[] args) throws IOException, TikaException {

      //Assume sample.txt is in your current directory		        
      File file = new File("C:\\Users\\craak\\Desktop\\Project-IBM watson NLU\\Project Training Documents\\AAP reports for thesis\\ChevronFire2012.txt");
      
      //Instantiating Tika facade class
      Tika tika = new Tika();
      String filecontent = tika.parseToString(file);
      System.out.println("Extracted Content: " + filecontent);
   }		 
}