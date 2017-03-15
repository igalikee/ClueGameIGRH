package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class IG_GameSetupTests {
	
	//constants to use for tests
	private static final int NUM_PLAYERS = 6;

	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/GVIG_ClueLayout.csv", "data/GVIG_ClueLegend.txt");
		board.setPlayerFile("data/IG_Players.txt");
		// Initialize will load ALL config files 
		board.initialize();
	}

	@Test
	public void testPeople() { //testing Players
		ArrayList<Player> players = new ArrayList<Player>();
		players = board.getPlayers();
		Player temp;
		
		assertEquals(NUM_PLAYERS, players.size());
		
		temp = new Player("Captain Zapp", "RED");
		assertTrue(players.contains(temp));
		
		temp = new Player("Wookie Slave", "DARK_GREY");
		assertTrue(players.contains(temp));
		
		temp = new Player("Bruce Willis", "YELLOW");
		assertTrue(players.contains(temp));
		
		temp = new Player("Leeloo", "ORANGE");
		assertTrue(players.contains(temp));
		
		temp = new Player("Murph", "BLUE");
		assertTrue(players.contains(temp));
		
		temp = new Player("Mark Watney", "BLACK");
		assertTrue(players.contains(temp));
	
		//TO DO test if computer or human
	}

}
