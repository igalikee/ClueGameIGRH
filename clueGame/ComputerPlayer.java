package clueGame;

import java.util.Random;
import java.util.Set;

import com.sun.prism.paint.Color;

public class ComputerPlayer extends Player {
	
	private BoardCell visited;
	
	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
		
		visited = new BoardCell();
	}
	
	public ComputerPlayer() {
		super("Empty", "RED", 0, 0); //DO NOT USE THIS CONSTRUCTOR
	}
	

	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		BoardCell currentCell = new BoardCell();
		currentCell = Board.getCellAt(getRow(),getCol());
		
		
		for (BoardCell c : targets) {
			if (c.isDoorway() && !currentCell.isDoorway() && !visited.isDoorway()) {
				visited = c;
				return c;
			}
		}
		
		Random rand = new Random();
		int randomChoice = rand.nextInt(targets.size());
		int i = 0;
		
		for (BoardCell c : targets) {
			if (i == randomChoice) {
				visited = c;
				return c;
			}
			
			i++;
		}
			
		return null;
	}
	
	public void makeAccusation() {
		//TODO
	}
	
	public void createSuggestion() {
		//TODO
	}
	
	public void setVisited(BoardCell b) {
		visited = b;
	}
	
}
