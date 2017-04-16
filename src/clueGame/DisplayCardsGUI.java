package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DisplayCardsGUI extends JPanel {
	
	ArrayList<Player> players = Board.getInstance().getPlayers();
	
	JPanel mainPanel = new JPanel();
	
	public DisplayCardsGUI() {
	
	mainPanel.setBorder(new TitledBorder(new EtchedBorder(), "MyCards"));
	mainPanel.setLayout(new GridLayout(3,0));
	
	add(mainPanel);
	addCardPanels();
	
		
	}
	
	private void addCardPanels() {
		
		ArrayList<Card> cards = players.get(0).getHand();
		
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(3,0));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		rooms.setLayout(new GridLayout(3,0));
		
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weapons.setLayout(new GridLayout(3,0));
		
		
		for (Card c: cards) {
			JTextArea display = new JTextArea(2,20);
			display.setText(c.getCardName());
			switch (c.getCardType()) {
			case WEAPON: weapons.add(display);  
				break;
			case PLAYER: people.add(display);
				break;
			case ROOM: rooms.add(display);
				break;
			}
			
		}
		
		mainPanel.add(people);
		mainPanel.add(rooms);
		mainPanel.add(weapons);
		
	}
	
	
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setSize(100, 300);
		// Create the JPanel and add it to the JFrame
		DisplayCardsGUI gui = new DisplayCardsGUI();
		gui.setVisible(true);
		frame.add(gui);
		// Now let's view it
		//frame.pack();
		frame.setVisible(true);
	}
	
}
