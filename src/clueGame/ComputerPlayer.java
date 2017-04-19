package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	private BoardCell visited;
	private ArrayList<String> seenCards = new ArrayList<String>();
	private Solution suggestion = new Solution();

	// Constructor
	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
		visited = Board.getInstance().getCellAt(super.getRow(), super.getCol());
	}

	// DO NOT USE THIS CONSTRUCTOR, FOR TESTING PURPOSES
	public ComputerPlayer() {
		super("Empty", "RED", 0, 0);
	}

	// Picks where to go given viable options
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell currentCell = Board.getInstance().getCellAt(getRow(), getCol());

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

	// Makes accusation
	public void makeAccusation() {

		ArrayList<Card> cards = Board.getInstance().getCards();
		Random rand = new Random();
		String room = cards.get(rand.nextInt(6)).getCardName();
		String person = cards.get(rand.nextInt(6) + 9).getCardName();
		String weapon = cards.get(rand.nextInt(6) + 15).getCardName();
		Solution accusation = new Solution();
		accusation.person = person;
		accusation.room = room;
		accusation.weapon = weapon;
		Board.getInstance().checkAccusation(accusation);
	}

	// Creates a suggestion
	public void createSuggestion() {

		suggestion = new Solution(); // clears suggestion
		BoardCell currentCell = Board.getInstance().getCellAt(getRow(), getCol());
		String room = Board.getInstance().roomLegend.get(currentCell.getInitial());
		seenCards.add(room);

		ArrayList<Card> cards = Board.getInstance().getCards();
		Random rand = new Random();

		String person = null;
		String weapon = null;

		do {
			person = cards.get(rand.nextInt(6) + 9).getCardName();
		} while (seenCards.contains(person));

		do {
			weapon = cards.get(rand.nextInt(6) + 15).getCardName();
		} while (seenCards.contains(weapon));

		suggestion.person = person;
		suggestion.room = room;
		suggestion.weapon = weapon;

		// Board.handleSuggestion(suggestion);
	}

	// Getters and setters
	public void setVisited(BoardCell b) {
		visited = b;
	}
	
	public BoardCell getVisited() {
		return visited;
	}

	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSeenCards(ArrayList<String> seenCards) {
		this.seenCards = seenCards;
	}
	
	public void addSeenCard(String s) {
		seenCards.add(s);
	}

}
