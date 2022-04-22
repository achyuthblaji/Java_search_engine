import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import algorithmDesign.*;

/**
 * @author Abhiraj Singh
 */
public class WordSuggestions {
	public static ArrayList <String> similarWords = new ArrayList<>();
	
	
	public static void suggestWord(String word) {
		File folder = new File("./htmlToTextPages/");
		
		File[] allFiles = folder.listFiles();
		for(int i=0;i<allFiles.length;i++) {
		suggestions(allFiles[i], word);
		}
		
		HashSet<String> hset = new HashSet<String>(similarWords);
		//System.out.println(hset);
		System.out.println("Did you mean?");
		
		Iterator<String> itr= hset.iterator();
		int i=1;
		while(itr.hasNext()) {
			System.out.println("("+i+") "+itr.next());
			i++;
		}
		
	}
	
	public static void suggestions(File f,String word) {
		String data="";
		try
		{
			
			BufferedReader Object = new BufferedReader(new FileReader(f));
			String line = null;
			
			while ((line = Object.readLine()) != null){
				data= data+line;
			}
			Object.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		
		String t = data.toLowerCase(); 
		t = t.replaceAll("\\p{Punct}", "");
		
		ArrayList <String> list = new ArrayList<>();

		StringTokenizer token = new StringTokenizer(t," ");
		while(token.hasMoreTokens()) {
			list.add(token.nextToken());
		}
		int distance=0;
		if(word.length()<6) {
			distance=1;
		}
		else {
			distance=2;
		}
		
		
		for(int i=0;i<list.size();i++) {
			if(Sequences.editDistance(word, list.get(i))<=distance) {
				similarWords.add(list.get(i));
			}
		}

	}

}

