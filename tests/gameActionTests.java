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
		
	
		Set<BoardCell> targets1 = new HashSet<BoardCell>(); //holds possible target locations
		Set<BoardCell> targets2 = new HashSet<BoardCell>();
		//Set<BoardCell> targets3 = new HashSet<BoardCell>();
		
		ComputerPlayer computer1 = new ComputerPlayer();
		ComputerPlayer computer2 = new ComputerPlayer();
		computer1 = (ComputerPlayer) players.get(1); //tests the first computer player, WOOKIE SLAVE for random movement
		computer2 = (ComputerPlayer) players.get(2); //tests Bruce Willis to make sure he enters a room
		computer2.setRow(7); //change the location to 7,3 to test door entry 
		computer2.setCol(3);
		
		
		board.calcTargets(computer1.getRow(), computer1.getCol(), 2); //generates targets with 2 steps for computer1
		targets1 = board.getTargets();
		BoardCell targetCell1 = new BoardCell();  //gets the target cell by calling pickLocation
		
		
		
		board.calcTargets(computer2.getRow(), computer2.getCol(), 2); //targets with 6 steps for computer2
		targets2 = board.getTargets();
		BoardCell targetCell2 = new BoardCell(); //gets target cell for computer2
		
		int C1counter1 = 0; //holds the amount of times each cell is visited for computer1   
		int C1counter2 = 0; 
		int C1counter3 = 0;
		
		int C2counter = 0;
		
		for (int i = 0; i < 100; i++) {  //calcs the targets 100 times 
			targetCell1 = computer1.pickLocation(targets1);
			targetCell2 = computer2.pickLocation(targets2);
	
			if (targetCell1 == board.getCellAt(16, 4)) C1counter1++;
			if (targetCell1 == board.getCellAt(15, 5)) C1counter2++;
			if (targetCell1 == board.getCellAt(14, 6)) C1counter3++;
			
			if (targetCell2 == board.getCellAt(5,3)) C2counter++;
		}
		
		assertTrue(C1counter1 > 5);
		assertTrue(C1counter2 > 5);
		assertTrue(C1counter3 > 5);
		
		assertEquals(C2counter, 100); //ensures computer goes into room everytime 
		
		//TODO check it doesn't go into room 
	}

}
