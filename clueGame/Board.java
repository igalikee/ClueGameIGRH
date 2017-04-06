package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;


public class Board {

	//========================================================================================
	// VARIABLES
	//========================================================================================

	//constants
	public static final int MAX_ROWS = 50;
	public static final int MAX_COLUMNS = 50;
	public static final int NUM_PLAYERS = 6;

	//data structures
	private static BoardCell[][] grid; //holds the board layout  
	private static Set<BoardCell> targets;  //holds possible moves 
	private static Map<BoardCell, Set<BoardCell>> adjMtx;
	public static Map<Character, String> roomLegend ; 
	public static ArrayList<Card> cards; //cards of rooms, players, weapons 
	public static Solution solution; //holds the solution

	private static ArrayList<Player> players;
	private static ArrayList<String> weapons;

	private static int rows;
	private static int columns;

	// Text File Names
	private static String legendString;
	private static String layoutString;
	private static String PlayerString;
	private static String WeaponString;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	//=========================================================================================
	//INITIALIZATION AND LOADING DATA
	//=========================================================================================
	public static void initialize() {
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[MAX_ROWS][MAX_COLUMNS];
		players = new ArrayList<Player>();
		weapons = new ArrayList<String>();
		cards = new ArrayList<Card>();

		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();	
		} catch (BadConfigFormatException e) {
			System.err.println(e.getMessage());
		}
		catch (FileNotFoundException e) {
			System.err.println("Error - File Not Found!");;
		}
		calcAdjacencies();
		setSolution_dealCards();
	}


	public static void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException{

		FileReader reader = null;

		reader = new FileReader(layoutString);

		try(Scanner in = new Scanner(reader)) {
			int counter = 0;
			int c = 0;

			while (in.hasNextLine()){
				String[] temp = in.nextLine().split(",");
				if(counter == 0) c = temp.length;
				for(int i = 0; i < temp.length; i++){
					grid[counter][i] = new BoardCell(counter,i,temp[i]);
					if (!roomLegend.containsKey(grid[counter][i].getInitial()))  throw new BadConfigFormatException("Wrong legend in board");
				}
				counter++;
				if (temp.length != c) throw new BadConfigFormatException("Number of columns is not consistent");
				columns = temp.length;
			}
			rows = counter;
		}
	}


	public static void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		roomLegend = new HashMap<Character, String>();

		FileReader reader = new FileReader(legendString);

		try(Scanner in = new Scanner(reader)) {
			String symbol;
			String room;
			String type;

			while (in.hasNextLine()){

				String[] temp = in.nextLine().split(", ");

				symbol = temp[0];
				room = temp[1];
				type = temp[2];
				//add room cards
				if (type.equals("Card")) {
					cards.add(new Card(temp[1], CardType.ROOM));
				}
				roomLegend.put(symbol.charAt(0), room);
				if((!type.equals("Card") && !type.equals("Other"))) throw new BadConfigFormatException("Not of type 'Card' or 'Other'");
			}
		}

	}

	public static void loadPlayerConfig() throws FileNotFoundException {

		try(Scanner in = new Scanner(new File(PlayerString))) {
			while (in.hasNextLine()) {
				String[] temp = in.nextLine().split(", ");
				cards.add(new Card(temp[0], CardType.PLAYER));
				if (temp[2].equals("P")) players.add(new HumanPlayer(temp[0], temp[1], Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
				else players.add(new ComputerPlayer(temp[0], temp[1], Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
			}
		}
	}

	public static void loadWeaponConfig() throws FileNotFoundException {
		String weaponName;
		try(Scanner in = new Scanner(new File(WeaponString))) {
			while (in.hasNextLine()) {
				weaponName = in.nextLine();
				weapons.add(weaponName);
				cards.add(new Card(weaponName, CardType.WEAPON));
			}
		}
	}

	//=========================================================================================
	// MOVEMENT: ADJECENCY AND TARGET CALCULATIONS
	//=========================================================================================
	public static void calcAdjacencies(){

		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();

		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				Set<BoardCell> tempSet = new HashSet<BoardCell>();

				if(grid[i][j].getInitial() != 'W'){
					switch (grid[i][j].getDoorDirection()) {
					case UP:
						tempSet.add(grid[i-1][j]); break;
					case DOWN:
						tempSet.add(grid[i+1][j]); break;
					case LEFT:
						tempSet.add(grid[i][j-1]); break;
					case RIGHT:
						tempSet.add(grid[i][j+1]); break;
					default:
						break;
					}				
				}

				else {
					if(i > 0){	
						if(checkAdjacency(grid[i-1][j], DoorDirection.DOWN)) tempSet.add(grid[i-1][j]);
						if (j > 0){
							if(checkAdjacency(grid[i][j-1], DoorDirection.RIGHT)) tempSet.add(grid[i][j-1]);
						}
						if (j < columns - 1){
							if(checkAdjacency(grid[i][j+1], DoorDirection.LEFT)) tempSet.add(grid[i][j+1]);
						}
					}

					if(i < rows - 1){
						if(checkAdjacency(grid[i+1][j], DoorDirection.UP)) tempSet.add(grid[i+1][j]);
						if (j > 0){
							if(checkAdjacency(grid[i][j-1], DoorDirection.RIGHT)) tempSet.add(grid[i][j-1]);
						}
						if (j < columns - 1){
							if(checkAdjacency(grid[i][j+1], DoorDirection.LEFT)) tempSet.add(grid[i][j + 1]);
						}
					}
				}
				adjMtx.put(grid[i][j], tempSet);
			}
		}
	}

	private static boolean checkAdjacency(BoardCell cell, DoorDirection direction) {
		return (cell.getInitial() == 'W' || cell.getDoorDirection().equals(direction));
	}

	public void calcTargets(int i, int j, int numSteps){
		targets.clear();
		Set<BoardCell> visited = new HashSet<BoardCell>();

		BoardCell startCell = null;
		startCell = grid[i][j];
		calculateTargets(startCell, numSteps, visited);
	}

	private void calculateTargets(BoardCell startCell, int numSteps, Set<BoardCell> visited){

		visited.add(startCell);

		for(BoardCell adjCell: adjMtx.get(startCell)){
			if(visited.contains(adjCell)) continue;
			visited.add(adjCell);
			if(adjCell.isDoorway()) targets.add(adjCell);
			if(numSteps == 1) targets.add(adjCell);
			else{
				calculateTargets(adjCell, numSteps-1, visited);
			}
			visited.remove(adjCell);
		}
	}

	private static void setSolution_dealCards() {
		ArrayList<Card> deck = new ArrayList<Card>(cards); //deck to shuffle

		Random rand = new Random();
		solution = new Solution();

		int n = rand.nextInt(9);
		solution.room = cards.get(n).getCardName();
		Collections.swap(deck, 0, n); //moving to front of deck for removal

		n = rand.nextInt(6) + 9;
		solution.person = cards.get(n).getCardName();
		Collections.swap(deck, 1, n); //moving to first index for removal

		n = rand.nextInt(6) + 15;
		solution.weapon = cards.get(n).getCardName();
		Collections.swap(deck, 2, n); //same as above 

		for (int i = 0; i < 3; i++) { //removing them from deck
			deck.remove(i);
		}

		Collections.shuffle(deck); //shuffles the deck randomly

		int playerNum = 0;

		for (Card c: deck) { //dealing the cards
			Card t = new Card(c.getCardName(), c.getCardType());
			if (playerNum >= players.size()) playerNum = 0; 

			players.get(playerNum).addCard(t);
			playerNum++;
		}
	}


	public static Card handleSuggestion(Solution suggestion, Player player) {		
		Card tempCard = null;
		int index = players.indexOf(player) + 1; //counter to ensure correct queuing 

		for (int i = 0; i < players.size() - 1; i++) { //iterate through players.size() - 1 b/c don't want to disprove the accuser
			if (index > players.size() - 1) index = 0;
			if (players.get(index).disproveSuggestion(suggestion) != null) {
				tempCard = players.get(index).disproveSuggestion(suggestion);
			}
			index++;
		}

		return tempCard;
	}

	public static boolean checkAccusation(Solution accusation) {
		if (solution.person == accusation.person && solution.room == accusation.room && solution.weapon == accusation.weapon){
			return true;
		}
		return false;
	}

	//========================================================================================
	// GETTERS & SETTERS 
	//========================================================================================
	public void setConfigFiles(String string, String string2, String string3, String string4) {
		layoutString = string;
		legendString = string2;
		PlayerString = string3;
		WeaponString = string4;
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public static Set<BoardCell> getAdjList(BoardCell cell){
		return adjMtx.get(cell);
	}

	public Map<Character, String> getLegend() {
		return roomLegend;
	}

	public int getNumRows() {
		return rows;
	}

	public int getNumColumns() {
		return columns;
	}

	public static BoardCell getCellAt(int i, int j) {
		return grid[i][j];
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMtx.get(grid[i][j]);
	}

	public static ArrayList<Player> getPlayers() {
		return players;
	}

	public static ArrayList<String> getWeapons() {
		return weapons;
	}
	public static ArrayList<Card> getCards() {
		return cards;
	}
}
