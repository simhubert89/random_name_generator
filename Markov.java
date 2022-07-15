package pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

////////////////////////////////////////////////////////////////////////////////////////
//Markov.java		@authors wuddy
//
//This class calculates frequency and probabilities of chars in generated names.
////////////////////////////////////////////////////////////////////////////////////////
public class Markov <T>{
	private int min;
	private int max;
	private int order;
	private int nbName;
	private HashMap<String, HashMap<String, Double>> hashMap;  // HashMap = list of  keys  &&  values
																// a list of all possible states
	
	private ArrayList<String> existingName;
	
	public Markov(int min, int max, int order, int nbName) {
		this.order = order;
		this.min = min + this.order*2;
		this.max = max + this.order*2;
		this.nbName = nbName;
		hashMap = new HashMap<String, HashMap<String, Double>> ();
		existingName = new ArrayList<String> ();
		
	}
	
	public void setfileToSelectFrom() throws IOException {
		File file = new File("Names.txt");
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String fullName;
			
			while((fullName = reader.readLine()) != null) {
				existingName.add(fullName);
				seqGenerator(fullName);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Set in memory the seq of chars and is in charge of count
	 */
	public void seqGenerator(String fullName) {
		String newName = "_".repeat(order) + fullName + "_".repeat(order); // add dashes so we know when to debut and when to end.
		newName = newName.trim();
		
		for(int i=0; i< (newName.length()-order); i++) {
			HashMap<String, Double> letters = new HashMap<String, Double>();
			
			String seq = newName.substring(i+i+order);
			String prediction = newName.substring(i+order, i+order+1);
			
			if(!hashMap.containsKey(seq)) {
				letters.put(prediction, 1.0);
				letters.put("total", 1.0);
				
			}
			
			else {
				letters = hashMap.get(seq);
				
				if(letters.containsKey(prediction)) {
					letters.put("total", letters.get("total")+1.0);
					letters.put(prediction, letters.get(prediction)+1.0);
				}
				else {
					letters.put("total", letters.get("total") + 1.0);
					letters.put(prediction, 1.0);
				}
				
			}
		}
	}
	
	public void setProbabilities() {
		for(String sequence: hashMap.keySet()) {
			for(String next: hashMap.get(sequence).keySet()) {
				if(!next.equals("total")) {
					double counter = hashMap.get(sequence).get(next);
					double total = hashMap.get(sequence).get("total");
					double probability = counter / total;
					hashMap.get(sequence).put(next, probability);
				}
			}
			
			hashMap.get(sequence).remove("total");
		}
	}
	
	public ArrayList<String> createNames() {
		ArrayList<String> newNames = new ArrayList<String> ();
		String trigger = "_".repeat(this.order);
		int realMin = this.min - this.order*2;
		int realMax = this.max - this.order*2;
		
		for(int i=0; i<nbName;) {
			String newName = generateName(trigger).replace("_", "");
			if(newName.length()>=realMin && newName.length() <= realMax && !(existingName.contains(newName))) {
				newNames.add(newName);
				i++;
			}
		}
		return newNames;
	}
	
	public String generateName(String trigger) {
		String tempPlayer = trigger; // beginning of the name defined by the trigger "_"*order
		int counter = 0;
		
		while(tempPlayer.length()<this.min || (!(tempPlayer.substring(tempPlayer.length()-this.order, tempPlayer.length()).equals(trigger)))) {
			String debut = tempPlayer.substring(counter, counter + this.order);
			
			double randNo = Math.random();
			double cumulative = 0;
			
			if(hashMap.get(debut) != null) {
				HashMap<String, Double> nextChars = hashMap.get(debut); 	// HashMap of possible chars and probabilities
				
				for(String next: nextChars.keySet()) {
					cumulative += nextChars.get(next);
					
					if(randNo <= cumulative) {
						if(next.equals("_") && tempPlayer.length() < (this.min-this.order)) { // if next value is predicted
							tempPlayer+="";
						}
						else {
							tempPlayer += next;
							counter++;
							break;
						}
					}
				}
			}
			else {
				tempPlayer += "__";
				return tempPlayer;
			}
			
		}
		
		return tempPlayer;
	}

}
