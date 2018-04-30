import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


public class WordLookup {
	
	private HashMap<String,String> dictionary;
	private HashMap<String,String[]>thesaurus;
	private HashMap<String,Integer> frequency;
	
	
	private class WordRank implements Comparable<WordRank>{
		int rank;
		String word;
		
		public WordRank(String w, int r){
			word = w;
			rank = r;
		}

		@Override
		public int compareTo(WordRank other) {
			return this.rank - other.rank;
		}
		
		public String toString(){
			return "r=" + rank + " w=" + word;
		}
	}
	
	
	
	public WordLookup(){try {
		BufferedReader in = new BufferedReader (new InputStreamReader(this.getClass().getResourceAsStream("resources/wordFrequency.txt")));
		String line = in.readLine();
		int count = 0;
		frequency = new HashMap<String,Integer>();
		while(line!= null){
			frequency.put(line, count);
			line = in.readLine();
			count++;
		}
		
		in = new BufferedReader (new InputStreamReader(this.getClass().getResourceAsStream("resources/synonyms2.txt")));
		line = in.readLine();
		thesaurus = new HashMap<String,String[]>();
		while(line!= null){
			String[] words = line.split(",");
			thesaurus.put(words[0].toUpperCase(), words);
			line = in.readLine();
		}

		in = new BufferedReader (new InputStreamReader(this.getClass().getResourceAsStream("resources/Dictionary.txt")));
		String word = in.readLine();
		line = in.readLine();
		String definition = "";
		dictionary = new HashMap<String, String>();
		while(line!= null){
			if(line.equals(line.toUpperCase()) && !line.equals("")){
				String old = "";
				if(dictionary.containsKey(word.toUpperCase())){
					old = dictionary.get(word.toUpperCase()) + '\n';
				}
				dictionary.put(word.toUpperCase(), old  + definition);
				definition = "";
				word = line;
			}else{
				definition += '\n' + line;
			}
			line = in.readLine();
		}
	} catch (Exception e) {e.printStackTrace();}}
	
	public String[] getCommonSynonyms(String word){
		String[] allWords = thesaurus.get(word.toUpperCase());
		if(allWords==null)return null;
		ArrayList<WordRank> listToSort = new ArrayList<WordRank>();
		for(String s : allWords){
			Integer rank = frequency.get(s);
			if(rank != null){
				listToSort.add(new WordRank(s,rank));
			}
		}
		Collections.sort(listToSort);
		String[] retVal = new String[5];
		for(int i=0 ; i<5 ; i++)retVal[i] = listToSort.get(i).word;
		return retVal;
	}
	
	public boolean isSpelledCorrectly(String s){
		return null != frequency.get(s);
	}
	
	public String[] getSimilarSpellings(String s){
		Set<String> allWords = frequency.keySet();
		ArrayList<WordRank> listToSort = new ArrayList<WordRank>();
		for(String str : allWords){
			if(levenshteinDistance(s,str) == 1){
				listToSort.add(new WordRank(str,frequency.get(str)));
			}
		}
		Collections.sort(listToSort);
		String[] retVal = new String[5];
		for(int i=0 ; i<5 && i<listToSort.size() ; i++)retVal[i] = listToSort.get(i).word;
		return retVal;
	}
	
	public String getDefinition (String s){
		return dictionary.get(s.toUpperCase());
	}
	
	private int levenshteinDistance (String lhs, String rhs) {                          
	    int len0 = lhs.length() + 1;                                                     
	    int len1 = rhs.length() + 1;                                                     
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	    for (int j = 1; j < len1; j++) {                                                
	        newcost[0] = j;                                                             
	        for(int i = 1; i < len0; i++) {                                             
	            int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	    return cost[len0 - 1];                                                          
	}

}
