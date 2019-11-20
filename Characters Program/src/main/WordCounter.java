package main;

public class WordCounter {

	private String word;
	private int count;
	
	public WordCounter(String str) {
		word = str;
		count = 1;
	}
	
	public void addCount() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getWord() {
		return word;
	}
}