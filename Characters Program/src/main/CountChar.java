package main;

public class CountChar {

	private String input;
	private String condensed;
	private String[] wordsArr;
	private int uniqueCount;
	private WordCounter[] wcArr;
	private int uniqueCharCount;
	private CharCounter[] chArr;
	
	public CountChar(String str) throws inputException{
		input = str.trim().toLowerCase();
		if(input.equals("")) {
			throw new inputException("Please input a valid string with at least one character.");
		}
		if(!(containsNonPunctuation(input))) {
			throw new inputException("Please input a string with at least one non-punctuation char.");
		}
		uniqueCount = 0;
		wcArr = new WordCounter[getWordCount()];
		uniqueCharCount = 0;
		chArr = new CharCounter[26];
	}
	
	public String getCounts() throws inputException { //returns a string containing various counts
		String out = "";
		out += String.format("%d characters", getCharacters());
		if(getCharacters() == 1) {
			out = out.substring(0, out.length()-1);
		}
		out += String.format("\n%d words", getWordCount());
		if(getWordCount() == 1) {
			out = out.substring(0, out.length()-1);
		}
		countRepeats();
		out += String.format("\n\n%d unique words", uniqueCount);
		if(uniqueCount == 1) {
			out = out.substring(0, out.length()-1);
		}
		for(int i=0; i<uniqueCount; i++) {
			out += "\n " + wcArr[i].getWord().substring(0,1).toUpperCase() + wcArr[i].getWord().substring(1,wcArr[i].getWord().length()) + String.format(": %d", wcArr[i].getCount());
		}
		countRepeatCharacters();
		out += String.format("\n\n%d unique characters", uniqueCharCount);
		if(uniqueCharCount == 1) {
			out = out.substring(0, out.length()-1);
		}
		for(int i=0; i<uniqueCharCount; i++) {
			out += "\n " + chArr[i].getLetter() + String.format(": %d", chArr[i].getCount());
		}
		return out;
	}
	
	private int getCharacters() {
		condensed = input.replace(" ", ""); //space
		condensed = condensed.replace("	", ""); //tab
		return condensed.length();
	}
	
	private int getWordCount() throws inputException {
		String[] initialWordsArr = new String[input.length()];
		String str = "";
		int count = 0;
		for(int i=0; i<input.length(); i++) {
			if(!(input.charAt(i)==' ' || input.charAt(i)=='	')) {
				str += input.charAt(i);
			}
			else if(!(input.charAt(i-1)==' ')) {
				initialWordsArr[count] = str;
				str = "";
				count++;
			}
		}
		if(!(str.equals(""))) {
			initialWordsArr[count] = str;
			count++;
		}
		str = "";
		
		wordsArr = new String[count];
		int wordCount = 0;
		for(int i=0; i<count; i++) {
			if(containsNonPunctuation(initialWordsArr[i])) { //skips if word contains only punctuation, as it is invalid
				while(isPunctuation(initialWordsArr[i].charAt(0)) && initialWordsArr[i].length()>1) { //removes leading punctuation
					initialWordsArr[i] = initialWordsArr[i].substring(1, initialWordsArr[i].length());
				}
				while(isPunctuation(initialWordsArr[i].charAt(initialWordsArr[i].length()-1)) && initialWordsArr[i].length()>1) { //removes trailing punctuation
					initialWordsArr[i] = initialWordsArr[i].substring(0, initialWordsArr[i].length()-1);
				}
				wordsArr[wordCount] = initialWordsArr[i];
				wordCount++;
			}
		}
		if(wordCount == 0) {
			throw new inputException("There are no words in the input.");
		}
		return wordCount;
	}
	
	private void countRepeats() throws inputException {
		uniqueCount = 1;
		boolean repeat = false;
		wcArr[0] = new WordCounter(wordsArr[0]);
		for(int i=1; i<getWordCount(); i++) {
			for(int prev=0; prev<uniqueCount; prev++) {
				if(wordsArr[i].equals(wcArr[prev].getWord())) {
					wcArr[prev].addCount();
					repeat = true;
				}
			}
			if(!repeat) {
				wcArr[uniqueCount] = new WordCounter(wordsArr[i]);
				uniqueCount++;
			}
			repeat = false;
		}
		rearrangeWords();
	}
	
	private void rearrangeWords() { //orders word objects based on their counts
		WordCounter[] reArr = new WordCounter[wcArr.length];
		int x = 0;
		for(int i=0; i<uniqueCount; i++) {
			if(wcArr[i].getCount()>x) {
				x = wcArr[i].getCount();
			}
		}
		int count = 0;
		for(int val=x; val>0; val--) {
			for(int i=0; i<uniqueCount; i++) {
				if(wcArr[i].getCount()==val) {
					reArr[count] = wcArr[i];
					count++;
				}
			}
		}
		wcArr = reArr; //updates word object array to be in order
	}
	
	private void countRepeatCharacters() throws inputException {
		String letters = "";
		for(int i=0; i<getWordCount(); i++) {
			letters += wordsArr[i];
		}
		uniqueCharCount = 1;
		boolean repeat = false;
		chArr[0] = new CharCounter(letters.charAt(0));
		for(int i=1; i<letters.length(); i++) {
			for(int prev=0; prev<uniqueCharCount; prev++) {
				if(letters.charAt(i)==(chArr[prev].getLetter())) {
					chArr[prev].addCount();
					repeat = true;
				}
			}
			if(!repeat) {
				chArr[uniqueCharCount] = new CharCounter(letters.charAt(i));
				uniqueCharCount++;
			}
			repeat = false;
		}
		rearrangeCharacters();
	}
	
	private void rearrangeCharacters() { //orders character objects based on their counts
		CharCounter[] reChArr = new CharCounter[chArr.length];
		int x = 0;
		for(int i=0; i<uniqueCharCount; i++) {
			if(chArr[i].getCount()>x) {
				x = chArr[i].getCount();
			}
		}
		int count = 0;
		for(int val=x; val>0; val--) {
			for(int i=0; i<uniqueCharCount; i++) {
				if(chArr[i].getCount()==val) {
					reChArr[count] = chArr[i];
					count++;
				}
			}
		}
		chArr = reChArr; //updates character object array to be in order
	}
	
	private boolean isPunctuation(char c) {
		return (c=='.' || c=='?' || c=='!' || c==',');
	}
	
	private boolean containsNonPunctuation(String str) {
		for(int i=0; i<str.length(); i++) {
			if(!(isPunctuation(str.charAt(i)))) {
				return true;
			}
		}
		return false;
	}
}