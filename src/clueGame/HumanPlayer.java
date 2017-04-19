package clueGame;

public class HumanPlayer extends Player {
	private BoardCell visited;
	private Solution suggestion;
	
	// Constructor, no different than Player class
	public HumanPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
	}
	
	@Override
	public void setVisited(BoardCell b) {
		visited = b;
	}
	
	@Override
	public BoardCell getVisited() {
		return visited;
	}
	
	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	
	
}
