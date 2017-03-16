package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private Color color;
	private int row;
	private int col;
	private static ArrayList<Card> hand = new ArrayList<>();
	
	public Player(String playerName, String color, int row, int col) {
		this.playerName = playerName;
		this.color = convertStringtoColor(color);
		this.row = row;
		this.col = col;	
	}

	private Color convertStringtoColor(String string) { //converts the String from the file read in to color
		switch(string) {
		case "RED": return Color.RED;
		case "DARK_GRAY": return Color.DARK_GRAY;
		case "YELLOW": return Color.YELLOW;
		case "ORANGE": return Color.ORANGE;
		case "BLUE": return Color.BLUE;
		case "BLACK": return Color.BLACK;
		}
		
		System.out.println("Failure in Color Read in, exited switch case: " + string);
		return null;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	

	public static ArrayList<Card> getHand() {
		return hand;
	}

	public static void addCard(Card card) {
		hand.add(card);
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", color=" + color + "]";
	}
	
//	public Card disproveSuggestion(Solution suggestion) {
//		//TODO - implement this shit
//	}
	
	
}
