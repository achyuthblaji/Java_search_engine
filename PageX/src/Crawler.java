import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Ashutosh Sadana
 */
public class Crawler {
	public static final int maxDepth=2;
	public static final String URL_pattern = "((http|https)://)(www.)?[a-zA-Z0-9@:%.\\+~#?&//=]{2,256}\\.[a-z]{2,6}([-a-zA-Z0-9@:%.\\+~#?&//=]*)";
	
	public static HashMap<String, String> urlDict = new HashMap<String, String>(); // This will store url to filename mapping
	
	
	public static HashMap<String, String> webCrawl(int depth,int count, String url,ArrayList<String> visited) {
		
		if (depth <= maxDepth && !checkURL(url) && count>=0) {
			Document doc = request(url,visited);
			
			if(doc!=null && count>=0) {
			for(Element link : doc.select("a[href]")) {
				String nextLink = link.absUrl("href");
				
				if(visited.contains(nextLink) == false && count>=0) {
					count--;
					//System.out.println(count);
					webCrawl(depth++,count,nextLink,visited);
					}
			}
			}
		}
		return urlDict;

			
	}
	private static Document request(String url,ArrayList<String>v) {         
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			if(con.response().statusCode()==200) {
				System.out.println("Link: "+url);
				System.out.println(doc.title());
				
				System.out.println();
				v.add(url);
				int i = v.indexOf(url);
				String fileNumber = Integer.toString(i);
				String fileLink = url;
				downloadURL(fileNumber, fileLink);
				addToDictionary(fileNumber, fileLink);
				return doc;
			}
			return null;
		}
		catch(IOException e) {
			return null;
		}
		
	}
	public static void addToDictionary(final String filenumber, final String fileLink){
		String temp = (filenumber+".txt");
		urlDict.put(temp,fileLink);
//		System.out.println(urlDict);
		
	}
	
	
	///Downloads the html page at url and stores it in local repository.
	public static void downloadURL(final String filename, final String urlString) throws IOException {

		{
			
			try {


				URL url = new URL(urlString);
				String nextLine;
				
				BufferedReader reader1 = new BufferedReader(new InputStreamReader(url.openStream()));

				String str = filename + ".html";

				BufferedWriter writer1 = new BufferedWriter(
						new FileWriter("./htmlpages/" + str));
				



				while ((nextLine = reader1.readLine()) != null) {
					writer1.write(nextLine);
				}

				reader1.close();
				writer1.close();
				System.out.println("Successfully Downloaded.");
				
				//Converts Html files to text
				htmlToText();	
			}
			
			catch (IOException e) {
				System.out.println("IOException raised");
			}
		}
	}
	private static void htmlToText() {
	    String filePath = "./htmlpages/";
        File file = new File(filePath);
        File[] file_Array = file.listFiles();
        int size = file_Array.length;
		 try {
		            	//Reference https://stackoverflow.com/questions/924394/how-to-get-the-filename-without-the-extension-in-java
			 			for(int i=0;i<size;i++) {
		            	String filenameWithoutExt = FilenameUtils.removeExtension(file_Array[i].getName());
		            	Document textDocument = Jsoup.parse(file_Array[i], "UTF-8");
		            	BufferedWriter bw = new BufferedWriter(new FileWriter("./htmlToTextPages/"+filenameWithoutExt+".txt"));
		            	bw.write(textDocument.text());					
		            	bw.close();            	
			 			}
	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	private static boolean checkURL(String url) {
		if (url != null && url != "" && Pattern.matches(URL_pattern, url))
			return false;
		return true;
	}
	

}
