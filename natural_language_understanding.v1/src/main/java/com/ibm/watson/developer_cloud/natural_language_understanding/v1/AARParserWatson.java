package com.ibm.watson.developer_cloud.natural_language_understanding.v1;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import default package;

import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import  org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
//import org.apache.tika.sax.xpath.Matcher;
import org.xml.sax.SAXException;

//import org.apache.pdfbox.text.PDFTextStripper;


import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ConceptsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.ListModelsResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Model;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.RelationsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesResult;


public class AARParserWatson{
	private static String username="cd2cba2c-c36a-4033-b742-579c4c0a312b";
	private static String password="z6ouZgCKwIYn";
	
	
	AARParserWatson(String pdf, PrintWriter outfile, List<String> pros, List<String> cons) throws IOException, SAXException, TikaException{
		 File file=new File(pdf);
			
			BodyContentHandler handler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			FileInputStream inputstream = new FileInputStream(file);
			PDDocument document1 = PDDocument.load(file);  
			ParseContext pcontext = new ParseContext();
			PDFParser pdfparser = new PDFParser(); 
			pdfparser.parse(inputstream, handler, metadata,pcontext);
			// getting the content of the document
			String contents = handler.toString();
			PDFTextStripper pdfstrip=new PDFTextStripper();
			//pdfstrip.setParagraphStart("\t");
			
			//String line;		 
			//System.out.println(contents);
			
			String line1= pdfstrip.getText(document1);
			Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
			Matcher reMatcher = re.matcher(line1);
			
			
			List<String> sent = new ArrayList<String>();
			//int n = reMatcher.group().length();
			List<String> nsent = new ArrayList<String>();
			
			//List<String> nsent_final = new ArrayList<String>();
			//String stopWords[]={"department","services","emergency","influenza","virus","hepatitis","issues","pertusis","fire","victim","injury","hazard","hospital","terror","terrorist","triage","weapons","hazardous","police","death","hurricane","drought","wildfire","ambulance","medical","disaster","shooting","shoot","911","earthquake","mudslides","debris","bombings","bombs","bomb"};
			ArrayList<String> stop_remove = new ArrayList<String>();
			stop_remove.add("department");
			stop_remove.add("hampshire");
			stop_remove.add("action");
			stop_remove.add("after");
			stop_remove.add("After");
			stop_remove.add("AFTER");
			stop_remove.add("DEPARTMENT");
			
			int i=1;
			while(reMatcher.find())
			{
				if (i%4 !=0) {
					String line=reMatcher.group();
					line.toLowerCase();
					
					 
					String stop_line=stopremovewords(line);
					//System.out.println(stop_line );
					
					//System.out.println(sent);
					
					
					
					
		//--------------End of stop words removal code------------------------			
					
					
					
					//line=line.toLowerCase();
				/*	
					   for(int k=0;k<stopWords.length;k++){
					       if(line.contains(stopWords[k])){
					           line=line.replaceAll(stopWords[k], ""); //note this will remove spaces at the end
					           
					           
					       }
					      
					   }*/
					
					   sent.add(stop_line);
					  
					 //  Collections.replaceAll(sent, "after", "");
					  // sent.removeAll(stop_remove);
					   //System.out.println(sent);
					  
				    
					
				}
				else
				{
					
					String line=reMatcher.group();
					line.toLowerCase();
					 
					String stop_line=stopremovewords(line);
					
					
					//System.out.println(stop_line);
					 
		//--------------End of stop words removal code------------------------	
					/*
					for(int k=0;k<stopWords.length;k++){
					       if(line.contains(stopWords[k])){
					           line=line.replaceAll(stopWords[k], ""); //note this will remove spaces at the end
					          
					       }
					       
				           
					   }*/
					sent.add(stop_line);
					//System.out.println(sent);
					
					//Collections.replaceAll(sent, "after", "");
					//sent.removeAll(stop_remove);
					//System.out.println(line);
				  
				    String group=sent.get(0);
				    for(int t=1;t<=3;t++) //number of sentences considered for sentiment analysis
				    {
				    	group=group+sent.get(t);
				    	}
				    sent.clear();
				    nsent.add(group);
				    
					
				}
				i++;
				
			}	
			System.out.println("1.start#########################################");
			System.out.println(nsent);
			System.out.println("1.end#########################################");
				//-----------------------removing Domain Specific Stopwords-----------------
				
				//////-------insert here------------------------
	
	
	for (int l=0;l<nsent.size();l++)
	{
		
	
	
    NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
		  NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
		  username,
		  password
		);
    

ListModelsResults models = service
		  .getModels()
		  .execute();
		
Model model = models.getModels().get(0);
System.out.println(model.getModelId());

/*EntitiesOptions entities= new EntitiesOptions.Builder()
//  .model(model.getModelId())
//  //.sentiment(true)
//  .limit(1)
//  .build();*/

SentimentOptions senti = new SentimentOptions.Builder()
        .document(true)
        
        
       // .targets(targets)
        .build();

Features features = new Features.Builder()
  .sentiment(senti)
// .entities(entities)
  .build();

AnalyzeOptions parameters = new AnalyzeOptions.Builder()
  .text(nsent.get(l))
  .features(features)
  .build();

AnalysisResults response = service
  .analyze(parameters)
  .execute();

System.out.println(nsent.get(l));
System.out.println(response);
String rep=response.getSentiment().toString();

System.out.println(rep);
System.out.println("---------------------------------------");

System.out.println(rep.substring(30, rep.length()-4).trim());
String sss=rep.substring(30, rep.length()-4).trim();

double ci=Double.parseDouble(sss);
System.out.println(ci);
System.out.println("---------------------------------------");


// }

if(ci>=0.3) {
pros.add(nsent.get(l));
}
if(ci<=-0.2) {
cons.add(nsent.get(l));
}


}


	String pr = "";
	for (String p:pros) {
    	pr=pr+p;
    }
    
    String co="";
    for (String c:cons) {
    	co=co+c;
    }
    String combine="";
    pr=pr.replaceAll("\\r\\n|\\r|\\n|\\n\\r", "");
    //pr = pr.replaceAll("\n", "").replace("\r", "");
    //pr = pr + " | ";
  //  co = co.replace("\n", "").replace("\r", "");
    co=co.replaceAll("\\r\\n|\\r|\\n|\\n\\r", "");  
   // co = co + " | ";
    
    combine=combine + pr + " | " + "\n" + co + " | " ;
//    outfile.write("\n\n start of pros %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//    outfile.write(pr);
//	outfile.write("\n end of prosn%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//	outfile.write("\n\n start of cons %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	outfile.write(combine);
   // System.out.println("\n end of cons %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    //System.out.println(pr);  
    
   // System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    System.out.println("********************************************************************");    
}		    


	
	
		
		public static String stopremovewords(String word1) {

			//System.out.println("1.start=========================================================================");
			//System.out.println(word1);
			//System.out.println("1.end=========================================================================");
	//----------------------Stop words removal code--------------------------
			
			int c=0,a,b;
			ArrayList<String> wordsList = new ArrayList<String>();
			String sCurrentLine;
			String result="";
			String[] stopwords = new String[2000];
			try{
					String input_stop="stopwords.txt";
					File stopfile=new File (input_stop);
					String stopw=stopfile.getAbsolutePath();
					
			        FileReader fr=new FileReader(stopw);
			        BufferedReader br= new BufferedReader(fr);
			        while ((sCurrentLine = br.readLine()) != null){
			            stopwords[c]=sCurrentLine;
			            c++;
			        }
			        //String s="I love this phone, its super fast and there's so much new and cool things with jelly bean....but of recently I've seen some bugs.";
			        StringBuilder builder = new StringBuilder(word1);
			        String[] words = builder.toString().split("\\s");
			        for (String word : words){
			            wordsList.add(word);
			        }
			        for(int ii = 0; ii < wordsList.size(); ii++){
			            for(int jj = 0; jj < c; jj++){
			                if(stopwords[jj].contains(wordsList.get(ii).toLowerCase())){
			                    wordsList.set(ii,"");
			                    break;
			                }
			             }
			        }
			       // System.out.println("1.start#########################################");
			        for (String str : wordsList){
			        	
			         //   System.out.print(str+" ");
			            result += (str+ " ");
			        }   
			       // System.out.println("1.end#########################################");
			    }catch(Exception ex){
			        System.out.println(ex);
			    }
			//System.out.println(result);
			return result;
		}
	
	
public static void main(String [] ar) throws IOException, SAXException, TikaException{
	//String str_pdf;
		
	PrintWriter outfile = new PrintWriter(new FileWriter("MC_Pros_Cons.txt", false));

	
	for (int i=1;i<2;i++)
	{
		
		//String filepath = new File(+i+".pdf\"").getAbsolutePath();
		//filepath.concat("path to the property file");
		String inputFile="AAR_Sample_Tests\\"+i+".pdf";
		
        File contfile=new File (inputFile);
        String contx=contfile.getAbsolutePath();
        
		System.out.println(contx);
		List<String> pros = new ArrayList<String>();
		List<String> cons = new ArrayList<String>();
		
		new AARParserWatson(contx, outfile, pros, cons);
		
		
	}
	
	
	
	outfile.close();
    System.out.println("Your text file has been generated!");
 
	}
}
