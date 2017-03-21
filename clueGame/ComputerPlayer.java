package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.sun.prism.paint.Color;

public class ComputerPlayer extends Player {
	
	private BoardCell visited;
	private Set<Card> seenCards = new HashSet<>();
	
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
		
		ArrayList<Card> cards = Board.getCards();
		Random rand = new Random();
		String room = cards.get(rand.nextInt(6)).getCardName();
		String person = cards.get(rand.nextInt(6) + 9).getCardName();
		String weapon = cards.get(rand.nextInt(6) + 15).getCardName();
		Solution accusation = new Solution();
		accusation.person = person;
		accusation.room = room;
		accusation.weapon = weapon;
		Board.checkAccusation(accusation);
	}
	
	public void createSuggestion() {
		BoardCell currentCell = Board.getCellAt(getRow(), getCol());
		String room = Board.legend.get(currentCell.getInitial());
		ArrayList<Card> cards = Board.getCards();
		Random rand = new Random();
		String person = cards.get(rand.nextInt(6) + 9).getCardName();
		String weapon = cards.get(rand.nextInt(6) + 15).getCardName();
		
		Solution suggestion = new Solution();
		suggestion.person = person;
		suggestion.room = room;
		suggestion.weapon = weapon;
		
		//Board.handleSuggestion(suggestion);
	}
	
	public void setVisited(BoardCell b) {
		visited = b;
	}
	
}
