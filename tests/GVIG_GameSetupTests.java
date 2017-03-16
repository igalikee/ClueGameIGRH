package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GVIG_GameSetupTests {
	

	//constants to use for tests
	private static final int NUM_PLAYERS = 6;
	private static final int NUM_CARDS = 21;

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
		ArrayList<Card> cards = new ArrayList<Card>();
		cards = board.getCards();
		
		//test individual card names
		assertEquals(cards.get(0).getCardName(),"CryoChamber");
		assertEquals(cards.get(6).getCardName(),"Observatory");
		assertEquals(cards.get(10).getCardName(), "Wookie Slave");
		assertEquals(cards.get(14).getCardName(),"Mark Watney");
		assertEquals(cards.get(18).getCardName(),"Poison");
		assertEquals(cards.get(20).getCardName(),"Butter Knife");
		
		//test number of rooms, players, and weapons correct
		int numRooms = 0;
		int numPlayers = 0;
		int numWeapons = 0;
		for (int i = 0; i < cards.size(); i++) {
			CardType tempType = cards.get(i).getCardType();
			if (tempType.equals(CardType.ROOM)) {
				numRooms++;
			}
			else if (tempType.equals(CardType.PLAYER)) {
				numPlayers++;
			}
			else if (tempType.equals(CardType.WEAPON)) {
				numWeapons++;
			}
		}
		assertEquals(9, numRooms);
		assertEquals(6, numPlayers);
		assertEquals(6, numWeapons);
		
		//test solution
		
		
		
		
		assertEquals(NUM_CARDS, cards.size());
		
		//TODO check individual cards
		
	}
	
	@Test
	public void testDeal() {
		fail("Do this");
	}
}
