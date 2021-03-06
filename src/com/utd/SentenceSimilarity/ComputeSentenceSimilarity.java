package com.utd.SentenceSimilarity;

import java.net.*;
import java.util.Map;
import java.util.Map.Entry;
import java.io.*;

import com.utd.Domain.Sentence;

public class ComputeSentenceSimilarity {
    public static void main(String[] args) throws Exception {
    
    	Sentence sentence = new Sentence();										// create a single object here
    	
    	ParseInput.parseAllInputFiles(sentence);
    	
    	sentence.chooseKRandomPoints();
    	sentence.classifyAllSentencesToClusters();
    	sentence.runKMeansAlgorithm();
    	sentence.writeToSummary();
    	
    	System.out.println("Program ends!");

    	// custom function
//    	sentence.writeAllSentencesToAFile(sentence);
    	
    	
    } 
  
    private static void testSimilarity(Sentence sentence, int i, int j) throws Exception {
    	String str1 = null, str2 = null;
    	for(Entry<String, Integer> entry : sentence.mapSentence.entrySet())
    		if(entry.getValue().equals(i))
    			str1 = entry.getKey();
    		else
    			str2 = entry.getKey();
    	System.out.println("\n\n\n");
    	System.out.println("Score >>> " + ComputeSimilarity(str1, str2));
	}

	/**
     * Function which calls the HTTP request and gets the value
     * @param str1
     * @param str2
     * @param sentence 
     * @return 
     * @throws Exception
     */
	public static Double ComputeSimilarity(String str1, String str2) throws Exception {

		str1 = ReplaceSpacesByHexDecimalValue(str1);
		str2 = ReplaceSpacesByHexDecimalValue(str2);

		String strURL = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&phrase1=" + str1 + "&phrase2=" + str2;
		Double similarityScore = null;
				
		URL url = new URL(strURL);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        
        while ((inputLine = in.readLine()) != null) {
        	similarityScore = Double.parseDouble(inputLine);
//        	System.out.println("SScore = " + inputLine);
        }
            
        in.close();
		return similarityScore;

	}

	/**
	 * Function that trims the input string and replaces the multiple spaces with '%20' hex decimal value
	 * @param str
	 * @return
	 */
	private static String ReplaceSpacesByHexDecimalValue(String str) {
		str = str.trim().replaceAll("\\s+", "%20");
		return str;
	}
    
    
}