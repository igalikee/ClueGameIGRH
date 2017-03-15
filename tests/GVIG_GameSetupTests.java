package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GVIG_GameSetupTests {
	

	//constants to use for tests
	private static final int NUM_PLAYERS = 6;

	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setCardFiles("data/GVIG_Players.txt", "data/GVIG_Weapons.txt");
		// Initialize will load ALL config files 
		board.initializeWeaponsPlayers();
	}

	@Test
	public void testPeople() { //testing Players
		ArrayList<Player> players = new ArrayList<Player>();
		players = board.getPlayers();
		
		assertEquals(players.get(0).getPlayerName(), "Captain Zapp");
		assertEquals(players.get(0).getColor(), Color.RED);
		assertEquals(HumanPlayer.class, players.get(0).getClass()); //checks if you are human
		assertTrue(players.get(0).getRow() == 8 && players.get(0).getCol() == 20); //checks spawn loc
		
		assertEquals(players.get(1).getPlayerName(), "Wookie Slave");
		assertEquals(players.get(1).getColor(), Color.DARK_GRAY);
		assertEquals(ComputerPlayer.class, players.get(1).getClass()); //checks the rest if they are AI
		assertTrue(players.get(1).getRow() == 16 && players.get(1).getCol() == 6);
		
		assertEquals(players.get(2).getPlayerName(), "Bruce Willis");
		assertEquals(players.get(2).getColor(), Color.YELLOW);
		assertEquals(ComputerPlayer.class, players.get(2).getClass());
		assertTrue(players.get(2).getRow() == 16 && players.get(2).getCol() == 9);


		assertEquals(players.get(3).getPlayerName(), "Leeloo");
		assertEquals(players.get(3).getColor(), Color.ORANGE);
		assertEquals(ComputerPlayer.class, players.get(3).getClass());
		
		assertEquals(players.get(4).getPlayerName(), "Murph");
		assertEquals(players.get(4).getColor(), Color.BLUE);
		assertEquals(ComputerPlayer.class, players.get(4).getClass());
		assertTrue(players.get(4).getRow() == 15 && players.get(4).getCol() == 15);

		assertEquals(players.get(5).getPlayerName(), "Mark Watney");
		assertEquals(players.get(5).getColor(), Color.BLACK);
		assertEquals(ComputerPlayer.class, players.get(5).getClass());
		
	}
	
	@Test
	public void testWeapons() {
		ArrayList<String> weapons = new ArrayList<String>();
		weapons = board.getWeapons();
		
		assertEquals(weapons.get(0),"Red Light Saber");
		assertEquals(weapons.get(2),"High Energy Laser");
		assertEquals(weapons.get(3),"Poison");
		assertEquals(weapons.get(5),"Butter Knife");
	}

	@Test
	public void testCards() {
		fail("Do this thang");
	}
}
