package clueGame;

import java.util.Set;

import com.sun.prism.paint.Color;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
	}
	
	public ComputerPlayer() {
		super("Empty", "RED", 0, 0); //DO NOT USE THIS CONSTRUCTOR
	}
	

	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		for (BoardCell c : targets) {
			if (c.isDoorway()) {
				return c;
			}
			
			
		}
		
		return null;
	}
	
	public void makeAccusation() {
		//TODO
	}
	
	public void createSuggestion() {
		//TODO
	}
	
}
