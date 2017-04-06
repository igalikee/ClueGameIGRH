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
		
		assertEquals(10, counter1); //ensures computer goes into room every time 
		
		
		//now spawn computer in room and move him to ensure he chooses randomly 
		computer.setRow(4);
		computer.setCol(10);
		board.calcTargets(computer.getRow(), computer.getCol(), 1);
		computer.setRow(4);
		computer.setCol(11);
		
		board.calcTargets(computer.getRow(), computer.getCol(), 2);
		targets = board.getTargets();
		
		counter1 = 0;
		counter2 = 0; 
		counter3 = 0;
		int counter4 = 0;
		
		for (int i = 0; i < 100; i++) {  //calcs the targets 10 times 
			targetCell = computer.pickLocation(targets);
			if (targetCell == board.getCellAt(4,10)) counter1++;
			if (targetCell == board.getCellAt(5,10)) counter2++;
			if (targetCell == board.getCellAt(5,12)) counter3++;
			if (targetCell == board.getCellAt(4,13)) counter4++;
		}
		
		assertTrue(counter1 > 5);
		assertTrue(counter2 > 5);
		assertTrue(counter3 > 5);
		assertTrue(counter4 > 5);
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
		String room = board.roomLegend.get(roomInit);
		
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
		
		ArrayList<Player> players = new ArrayList<Player>();
		players = board.getPlayers();
				
		ArrayList<Card> tempHand = new ArrayList<Card>();
		ArrayList<Card> cards = new ArrayList<Card>(board.getCards()); 
		tempHand.add(cards.get(14)); //Mark Watney
		tempHand.add(cards.get(18)); //Poison
		tempHand.add(cards.get(6)); // Observatory
		players.get(0).setHand(new ArrayList<Card>(tempHand));
		
		tempHand.clear();
		tempHand.add(cards.get(13)); //Murph
		tempHand.add(cards.get(19)); //Bullying
		tempHand.add(cards.get(8)); // Space Pool
		players.get(1).setHand(new ArrayList<Card>(tempHand));
		
		tempHand.clear();
		tempHand.add(cards.get(3)); //Galaxy Bar
		tempHand.add(cards.get(0)); //Cryochamber
		tempHand.add(cards.get(9)); // Captain Zapp
		players.get(2).setHand(new ArrayList<Card>(tempHand));
		
		tempHand.clear();
		tempHand.add(cards.get(2)); //Abduction Chamber
		tempHand.add(cards.get(10)); //Wookie Slave
		tempHand.add(cards.get(15)); //Red Light Saber
		players.get(3).setHand(new ArrayList<Card>(tempHand));
		
		tempHand.clear();
		tempHand.add(cards.get(4)); //Captains Quarters 
		tempHand.add(cards.get(11)); //Bruce Willis
		tempHand.add(cards.get(16)); //Anxiety and Depression
		players.get(4).setHand(new ArrayList<Card>(tempHand));
		
		tempHand.clear();
		tempHand.add(cards.get(5)); //Gravity Room 
		tempHand.add(cards.get(7)); //Wookie Slave Chambers
		tempHand.add(cards.get(17)); //High Energy Laser
		players.get(5).setHand(new ArrayList<Card>(tempHand));
		
		Solution suggestion = new Solution();
		suggestion.person = cards.get(12).getCardName(); //Leeloo
		suggestion.room = cards.get(1).getCardName(); //Space Basketball court
		suggestion.weapon = cards.get(20).getCardName(); //Butter Knife
		
		//tests if no one can disprove - returns null
		assertEquals(null, board.handleSuggestion(suggestion, players.get(0)));
		assertEquals(null, board.handleSuggestion(suggestion, players.get(1)));
		assertEquals(null, board.handleSuggestion(suggestion, players.get(2)));
		assertEquals(null, board.handleSuggestion(suggestion, players.get(3)));
		assertEquals(null, board.handleSuggestion(suggestion, players.get(4)));
		assertEquals(null, board.handleSuggestion(suggestion, players.get(5)));

		//Suggestion only accusing player can disprove returns null
		suggestion.room = cards.get(5).getCardName(); //change room to something players[5] has 
		assertEquals(null, board.handleSuggestion(suggestion, players.get(5)));

		suggestion.room = cards.get(1).getCardName(); //Space Basketball court (changed it back) 

		//Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		suggestion.person = cards.get(14).getCardName(); //change person to something human has 
		
		assertEquals(cards.get(14), board.handleSuggestion(suggestion,players.get(1)));
		
		//Suggestion only human can disprove, but human is accuser, returns null
		assertEquals(null, board.handleSuggestion(suggestion,players.get(0)));

		//Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		suggestion.person = cards.get(13).getCardName();  //players[1] has this card
		suggestion.room = cards.get(3).getCardName(); //players[2] has this card 
		
		//should only return room that players[2] has, NOT players[1] cards
		assertEquals(cards.get(3), board.handleSuggestion(suggestion,players.get(0))); 
		
		//Suggestion that human and another player can disprove
		suggestion.room = cards.get(1).getCardName(); //change back so it won't interfere 
		suggestion.person = cards.get(14).getCardName(); //human player has this card
		suggestion.weapon = cards.get(19).getCardName(); //players[1] has this card
		
		//should only return card players[1] has, since he is ahead in query 
		assertEquals(cards.get(19), board.handleSuggestion(suggestion, players.get(4)));
	}	
}
