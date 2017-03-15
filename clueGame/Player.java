package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private Color color;
	private int row;
	private int col;
	
	public Player(String playerName, String color) {
		this.playerName = playerName;
		this.color = convertStringtoColor(color);
	}

	private Color convertStringtoColor(String string) { //converts the String from the file read in to color
		switch(string) {
		case "RED": return Color.RED;
		case "DARK_GREY": return Color.DARK_GRAY;
		case "YELLOW": return Color.YELLOW;
		case "ORANGE": return Color.ORANGE;
		case "BLUE": return Color.BLUE;
		case "BLACK": return Color.BLACK;
		}
		
		System.out.println("Failure in Color Read in, exited switch case");
		return null;
	}
	
//	public Card disproveSuggestion(Solution suggestion) {
//		//TO DO - implement this shit
//	}
	
	
}
