package clueGame;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String color, int row, int col) {
		super(playerName, color, row, col);
	}
	public HumanPlayer() {
		super("Empty", "RED", 0, 0); //DO NOT USE THIS CONSTRUCTOR
	}
}
