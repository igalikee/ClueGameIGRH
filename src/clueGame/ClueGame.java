package clueGame;


import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ClueGame extends JFrame {
	

	private static Board board;
	
	ClueGame() {
		setTitle("Clue Game");
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setUp();
		
		add(board);
	}
	
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/GVIG_ClueLayout.csv", "data/GVIG_ClueLegend.txt", "data/GVIG_Players.txt", "data/GVIG_Weapons.txt");		
		Board.initialize();
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		
	}
	
	
	
}
