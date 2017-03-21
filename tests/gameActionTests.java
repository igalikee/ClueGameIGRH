package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;


public class gameActionTests {
	
	private static Board board;

	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/GVIG_ClueLayout.csv", "data/GVIG_ClueLegend.txt","data/GVIG_Players.txt", "data/GVIG_Weapons.txt");
		// Initialize will load ALL config files 
		board.initialize();
	}

	@Test
	public void testTarget() {
		ArrayList<Player> players = new ArrayList<Player>();
		players = board.getPlayers();
		
	
		Set<BoardCell> targets = new HashSet<BoardCell>(); //holds possible target locations
		
		ComputerPlayer computer = new ComputerPlayer();
		computer = (ComputerPlayer) players.get(1); //tests the first computer player, WOOKIE SLAVE for random movement
		board.calcTargets(computer.getRow(), computer.getCol(), 2); //generates targets with 2 steps for computer1
		targets = board.getTargets();
		
		BoardCell targetCell = new BoardCell();  //gets the target cell by calling pickLocation
		
		int counter1 = 0; //holds the amount of times each cell is visited for computer1   
		int counter2 = 0; 
		int counter3 = 0;
				
		for (int i = 0; i < 100; i++) {  //calcs the targets 100 times 
			targetCell = computer.pickLocation(targets);
	
			if (targetCell == board.getCellAt(16, 4)) counter1++;
			if (targetCell == board.getCellAt(15, 5)) counter2++;
			if (targetCell == board.getCellAt(14, 6)) counter3++;
		}
		
		assertTrue(counter1 > 5);
		assertTrue(counter2 > 5);
		assertTrue(counter3 > 5);
		
		
		counter1 = 0;
		counter2 = 0; 
		counter3 = 0;
		//now test entry into room
		computer = (ComputerPlayer) players.get(2); //tests Bruce Willis to make sure he enters a room
		computer.setRow(7); //change the location to 7,3 to test door entry 
		computer.setCol(3);
		board.calcTargets(computer.getRow(), computer.getCol(), 2); //generates targets with 2 steps for computer1
		targets = board.getTargets() ;

		
		
		for (int i = 0; i < 10; i++) {  //calcs the targets 10 times 
			computer.setVisited(board.getCellAt(7, 3));
			targetCell = computer.pickLocation(targets);
			System.out.println(targetCell);
			if (targetCell == board.getCellAt(5,3)) counter1++;
		}
		
		assertEquals(10, counter1); //ensures computer goes into room everytime 
		
		
		counter1 = 0;
		counter2 = 0; 
		counter3 = 0;
		//now test entry into room
		computer = (ComputerPlayer) players.get(2); //tests Bruce Willis to make sure he enters a room
		computer.setRow(7); //change the location to 7,3 to test door entry 
		computer.setCol(3);
		board.calcTargets(computer.getRow(), computer.getCol(), 2); //generates targets with 2 steps for computer1
		targets = board.getTargets() ;
		
		//TODO check it doesn't go into room 
	}
	
	@Test
	public void testAccusation(){
		//Testing to see if accusation is true
		Solution accusation = new Solution();
		accusation.person = "Bruce Willis";
		accusation.room = "Cryochamber";
		accusation.weapon = "Bullying";
		
		Solution solution = new Solution();
		Board.solution.person = "Bruce Willis";
		Board.solution.room = "Cryochamber";
		Board.solution.weapon = "Bullying";
		assertTrue(Board.checkAccusation(accusation));

		
		//testing to see if accusation returns false with 1 person wrong
		accusation.person = "Murph";
		accusation.room = "Cryochamber";
		accusation.weapon = "Bullying";
		assertFalse(Board.checkAccusation(accusation));
		
		//testing to see if accusation returns false with 1 room wrong
		accusation.person = "Bruce Willis";
		accusation.room = "Observatory";
		accusation.weapon = "Bullying";
		assertFalse(Board.checkAccusation(accusation));
		
		//testing to see if accusation returns false with 1 weapon wrong
		accusation.person = "Bruce Willis";
		accusation.room = "Cryochamber";
		accusation.weapon = "Poison";
		assertFalse(Board.checkAccusation(accusation));
	
	}
	
	@Test
	public void testCreateSuggestion() {
		BoardCell currentCell = new BoardCell(2, 18, "A");
		
		
	}

}
