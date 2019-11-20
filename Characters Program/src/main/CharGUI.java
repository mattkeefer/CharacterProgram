/*
 * Matt Keefer
 * Character Program
 * 11/14/19
 */
package main;
import javax.swing.*;
import BreezySwing.*;
import java.awt.*;

public class CharGUI extends GBFrame {

	JLabel label = addLabel("Enter sentence:", 1,1,2,1);
	JTextField input = addTextField("", 2,1,2,1);
	JButton inputButton = addButton("Input", 7,1,1,1);
	JButton resetButton = addButton("Reset", 7,2,1,1);
	JTextArea output = addTextArea("", 3,1,2,4);
	
	public CharGUI() {
		output.setEditable(false);
	}
	
	public static void main(String[] args) {
		JFrame frm = new CharGUI();
		frm.setTitle("Character Counter");
		frm.getContentPane().setBackground(Color.red.darker()); //represents my frustration creating this program :(
		frm.setSize(500, 700);
		frm.setVisible(true);
	}
	
	public void buttonClicked(JButton button) {
		if(button==inputButton) {
			try {
				CountChar ch = new CountChar(input.getText());
				output.setText(ch.getCounts()); //outputs various counts
			}
			catch(inputException e) { //if no input is provided
				output.setText(e.getMessage());
			}
		}
		if(button==resetButton) { //resets text fields
			input.setText("");
			output.setText("");
		}
		input.grabFocus(); //places cursor at end of input field
	}
}