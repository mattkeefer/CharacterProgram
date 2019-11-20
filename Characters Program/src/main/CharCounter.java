package main;

public class CharCounter {

	private char letter;
	private int count;
	
	public CharCounter(char ch) {
		letter = ch;
		count = 1;
	}
	
	public void addCount() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public char getLetter() {
		return letter;
	}
}