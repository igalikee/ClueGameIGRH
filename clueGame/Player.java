package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Player {
	private String playerName;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> hand;
	
	public Player(String playerName, String color, int row, int col) {
		this.playerName = playerName;
		this.color = convertStringtoColor(color);
		this.row = row;
		this.col = col;	
		
		hand = new ArrayList<Card>();
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

	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> suggestions = new ArrayList<Card>();
		
		for (Card card : hand) {
			if (card.getCardName().equals(suggestion.person) || card.getCardName().equals(suggestion.weapon) || card.getCardName().equals(suggestion.room)) {
				suggestions.add(card);
			}
		}
		if (suggestions.size() != 0) {
			Random rand = new Random();
			return suggestions.get(rand.nextInt(suggestions.size()));
		}
		
		else {
			return null;
		}
		
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
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
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void addCard(Card card) {
		hand.add(card);
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", color=" + color + "]";
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
}
