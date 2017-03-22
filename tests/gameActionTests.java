package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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
		ComputerPlayer testPlayer = new ComputerPlayer();
		testPlayer.setRow(0);
		testPlayer.setCol(14);
				
		testPlayer.createSuggestion();
		Solution testSuggestion = testPlayer.getSuggestion();
		char roomInit = board.getCellAt(testPlayer.getRow(), testPlayer.getCol()).getInitial();
		String room = board.legend.get(roomInit);
		
		assertEquals(room,testSuggestion.room);  // tests that suggestion is the same room
		
		ArrayList<String> seen = new ArrayList<String>();
		
		for (Card c : board.getCards()) {
			seen.add(c.getCardName());
		}

		testPlayer.setSeenCards(seen);
		seen.remove("Captain Zapp");
		seen.remove("Poison");
		

		
		testPlayer.createSuggestion();
		testSuggestion = testPlayer.getSuggestion();
		
		assertEquals("Captain Zapp", testSuggestion.person); //if only one person not seen it's selected	
		assertEquals("Poison", testSuggestion.weapon); //if only one weapon not seen it's selected
		
		seen.remove("Butter Knife");
		seen.remove("Mark Watney");
		seen.remove("Wookie Slave");
		
		int Mark = 0;
		int Knife = 0;
		
		for (int i = 0; i < 100; i++) {
			testPlayer.createSuggestion();
			testSuggestion = testPlayer.getSuggestion();
			
			if (testSuggestion.person.equals("Mark Watney")) Mark++;
			if (testSuggestion.weapon.equals("Butter Knife")) Knife++;
		}
		
		assertTrue(Mark > 0);
		assertTrue(Knife > 0);
	}
	
	@Test
	public void disproveSuggestion() {
		ComputerPlayer testPlayer = new ComputerPlayer();
		
		ArrayList<Card> cards = new ArrayList<Card>(board.getCards()); 
		
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(cards.get(14));
		hand.add(cards.get(18));
		hand.add(cards.get(6));
		
		testPlayer.setHand(hand);
		
		Solution suggestion = new Solution();
		suggestion.person = "Mark Watney";
		suggestion.room = "Cryochamber";
		suggestion.weapon = "Bullying";
		
		testPlayer.disproveSuggestion(suggestion);
		
		//testing when player has only one matching card
		assertEquals(cards.get(14) ,testPlayer.disproveSuggestion(suggestion));
		suggestion.person = "Wookie Slave";
		
		//testing when player has no matching cards
		assertEquals(null,testPlayer.disproveSuggestion(suggestion));
		
		suggestion.person = cards.get(14).getCardName();
		suggestion.room = cards.get(18).getCardName();
		suggestion.weapon = cards.get(6).getCardName();
		
		int person = 0; 
		int room = 0; 
		int weapon = 0;
		
		
		//testint to see if >1 matching card, the card is returned randomly
		for (int i = 0; i < 100; i++) {
			if (testPlayer.disproveSuggestion(suggestion).getCardName().equals(suggestion.person)) person++;
			if (testPlayer.disproveSuggestion(suggestion).getCardName().equals(suggestion.room)) room++;
			if (testPlayer.disproveSuggestion(suggestion).getCardName().equals(suggestion.weapon)) weapon++;
		}
		
		assertTrue(person > 5);
		assertTrue(room > 5);
		assertTrue(weapon > 5);
		
	}
	
	@Test
	public void handleSuggestion() {
		
		
		
		ArrayList<Player> playerList = new ArrayList<>();
		playerList.addAll(Board.getPlayers());
		
		Player player1 = playerList.get(0);
		Player player2 = playerList.get(1);
		Player player3 = playerList.get(2);
		
		
		
		ArrayList<Card> tempHand = new ArrayList<Card>();
		ArrayList<Card> cards = new ArrayList<Card>(board.getCards()); 
		tempHand.add(cards.get(14)); //Mark Watney
		tempHand.add(cards.get(18)); //Poison
		tempHand.add(cards.get(6)); // Observatory
		player1.setHand(new ArrayList<Card>(tempHand));
		tempHand.clear();
		tempHand.add(cards.get(13)); //Murph
		tempHand.add(cards.get(19)); //Bullying
		tempHand.add(cards.get(8)); // Space Pool
		player2.setHand(new ArrayList<Card>(tempHand));
		tempHand.clear();
		tempHand.add(cards.get(3)); //Galaxy Bar
		tempHand.add(cards.get(0)); //Cryochamber
		tempHand.add(cards.get(9)); // Captain Zapp
		player3.setHand(new ArrayList<Card>(tempHand));
		
		
		
		ArrayList<Player> testList = new ArrayList<>();
		testList.add(player1);
		testList.add(player2);
		testList.add(player3);
		
		
		Solution suggestion = new Solution();
		suggestion.person = cards.get(12).getCardName(); //Leeloo
		suggestion.room = cards.get(1).getCardName(); //Space Basketball court
		suggestion.weapon = cards.get(20).getCardName(); //Butter Knife
		
		
		//tests if no one can disprove - returns null
		suggestion.person = cards.get(12).getCardName();
		assertEquals(null, board.handleSuggestion(suggestion, player1, testList));
		
		//tests only human can disprove, but human is the accuser, so it returns null
		suggestion.person = cards.get(14).getCardName(); //Mark Watney
		assertEquals(null, board.handleSuggestion(suggestion, player1, testList));
		
		//tests only computer accuser can disprove - returns null
		suggestion.person = cards.get(12).getCardName();
		assertEquals(null, board.handleSuggestion(suggestion, player2, testList));
		
		//tests that computer player is the only one who can disprove, and player is not accuser
		suggestion.room = cards.get(3).getCardName(); //Galaxy Bar
		suggestion.person = cards.get(12).getCardName(); //Leeloo
		assertEquals(cards.get(3), board.handleSuggestion(suggestion, player2, testList));
	
		//tests that the human (not accuser) is the only one who can disprove
		suggestion.person = cards.get(14).getCardName(); //Mark Watney
		assertEquals(cards.get(14), board.handleSuggestion(suggestion, player2, testList));
		
		
		
	}
	
}
