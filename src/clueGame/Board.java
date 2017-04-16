package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSeparatorUI;

import clueGame.BoardCell;

public class Board extends JPanel {

	// ========================================================================================
	// VARIABLES
	// ========================================================================================


	// constants
	public static final int MAX_ROWS = 50;
	public static final int MAX_COLUMNS = 50;
	public static final int NUM_PLAYERS = 6;

	// data structures
	private BoardCell[][] grid; // holds the board layout
	private Set<BoardCell> targets; // holds possible moves
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	public  Map<Character, String> roomLegend;
	public  ArrayList<Card> cards; // cards of rooms, players, weapons
	public  Solution solution; // holds the solution
	
	private Control_GUI control;
	private boolean playerTurnDone;

	private ArrayList<Player> players;
	private  int currentPlayer;

	private ArrayList<String> weapons;

	private int rows;
	private int columns;

	// Text File Names
	private  String legendString;
	private String layoutString;
	private  String PlayerString;
	private String WeaponString;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// ctor is private to ensure only one can be created
	private Board() {
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	// =========================================================================================
	// INITIALIZATION AND LOADING DATA
	// =========================================================================================
	public void initialize() {
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[MAX_ROWS][MAX_COLUMNS];
		players = new ArrayList<Player>();
		weapons = new ArrayList<String>();
		cards = new ArrayList<Card>();
		currentPlayer = 0;
		playerTurnDone = true;
		addMouseListener(new PlayerInputListener());


		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();
		} catch (BadConfigFormatException e) {
			System.err.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.err.println("Error - File Not Found!");
			;
		}
		calcAdjacencies();
		setSolution_dealCards();
	}

	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {

		FileReader reader = null;

		reader = new FileReader(layoutString);

		try (Scanner in = new Scanner(reader)) {
			int counter = 0;
			int c = 0;

			while (in.hasNextLine()) {
				String[] temp = in.nextLine().split(",");
				if (counter == 0)
					c = temp.length;
				for (int i = 0; i < temp.length; i++) {
					grid[counter][i] = new BoardCell(counter, i, temp[i]);
					if (!roomLegend.containsKey(grid[counter][i].getInitial()))
						throw new BadConfigFormatException("Wrong legend in board");
				}
				counter++;
				if (temp.length != c)
					throw new BadConfigFormatException("Number of columns is not consistent");
				columns = temp.length;
			}
			rows = counter;
		}
	}

	public  void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		roomLegend = new HashMap<Character, String>();

		FileReader reader = new FileReader(legendString);

		try (Scanner in = new Scanner(reader)) {
			String symbol;
			String room;
			String type;

			while (in.hasNextLine()) {

				String[] temp = in.nextLine().split(", ");

				symbol = temp[0];
				room = temp[1];
				type = temp[2];
				// add room cards
				if (type.equals("Card")) {
					cards.add(new Card(temp[1], CardType.ROOM));
				}
				roomLegend.put(symbol.charAt(0), room);
				if ((!type.equals("Card") && !type.equals("Other")))
					throw new BadConfigFormatException("Not of type 'Card' or 'Other'");
			}
		}
	}

	public void loadPlayerConfig() throws FileNotFoundException {

		try (Scanner in = new Scanner(new File(PlayerString))) {
			while (in.hasNextLine()) {
				String[] temp = in.nextLine().split(", ");
				cards.add(new Card(temp[0], CardType.PLAYER));
				if (temp[2].equals("P"))
					players.add(
							new HumanPlayer(temp[0], temp[1], Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
				else
					players.add(
							new ComputerPlayer(temp[0], temp[1], Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
			}
		}
	}

	public void loadWeaponConfig() throws FileNotFoundException {
		String weaponName;
		try (Scanner in = new Scanner(new File(WeaponString))) {
			while (in.hasNextLine()) {
				weaponName = in.nextLine();
				weapons.add(weaponName);
				cards.add(new Card(weaponName, CardType.WEAPON));
			}
		}
	}

	// =========================================================================================
	// MOVEMENT: ADJECENCY AND TARGET CALCULATIONS
	// =========================================================================================
	public void calcAdjacencies() {

		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();

				if (grid[i][j].getInitial() != 'W') {
					switch (grid[i][j].getDoorDirection()) {
					case UP:
						tempSet.add(grid[i - 1][j]);
						break;
					case DOWN:
						tempSet.add(grid[i + 1][j]);
						break;
					case LEFT:
						tempSet.add(grid[i][j - 1]);
						break;
					case RIGHT:
						tempSet.add(grid[i][j + 1]);
						break;
					default:
						break;
					}
				}

				else {
					if (i > 0) {
						if (checkAdjacency(grid[i - 1][j], DoorDirection.DOWN))
							tempSet.add(grid[i - 1][j]);
						if (j > 0) {
							if (checkAdjacency(grid[i][j - 1], DoorDirection.RIGHT))
								tempSet.add(grid[i][j - 1]);
						}
						if (j < columns - 1) {
							if (checkAdjacency(grid[i][j + 1], DoorDirection.LEFT))
								tempSet.add(grid[i][j + 1]);
						}
					}

					if (i < rows - 1) {
						if (checkAdjacency(grid[i + 1][j], DoorDirection.UP))
							tempSet.add(grid[i + 1][j]);
						if (j > 0) {
							if (checkAdjacency(grid[i][j - 1], DoorDirection.RIGHT))
								tempSet.add(grid[i][j - 1]);
						}
						if (j < columns - 1) {
							if (checkAdjacency(grid[i][j + 1], DoorDirection.LEFT))
								tempSet.add(grid[i][j + 1]);
						}
					}
				}
				adjMtx.put(grid[i][j], tempSet);
			}
		}
	}

	private  boolean checkAdjacency(BoardCell cell, DoorDirection direction) {
		return (cell.getInitial() == 'W' || cell.getDoorDirection().equals(direction));
	}

	public void calcTargets(int i, int j, int numSteps) {
		targets.clear();
		Set<BoardCell> visited = new HashSet<BoardCell>();

		BoardCell startCell = grid[i][j];
		calculateTargets(startCell, numSteps, visited);
	}

	private void calculateTargets(BoardCell startCell, int numSteps, Set<BoardCell> visited) {

		visited.add(startCell);

		for (BoardCell adjCell : adjMtx.get(startCell)) {
			if (visited.contains(adjCell))
				continue;
			visited.add(adjCell);
			if (adjCell.isDoorway())
				targets.add(adjCell);
			if (numSteps == 1)
				targets.add(adjCell);
			else {
				calculateTargets(adjCell, numSteps - 1, visited);
			}
			visited.remove(adjCell);
		}
	}

	public void makeMove() {

		Random rand = new Random();
		int numSteps = rand.nextInt(6) + 1;
		control.updateRoll(Integer.toString(numSteps));
		control.updateTurnName(players.get(currentPlayer).getPlayerName());

		calcTargets(players.get(currentPlayer).getRow(), players.get(currentPlayer).getCol(), numSteps);

		if (currentPlayer == 0) {
			for (BoardCell b : targets) {
				b.setPlayerHighlight(true);
				
			}
			playerTurnDone = false;
		}

		else {
			ComputerPlayer p = (ComputerPlayer) players.get(currentPlayer);

			BoardCell b;
			do {	//TODO change this
				b = p.pickLocation(targets);	
			} while (b == p.getVisited());

			players.get(currentPlayer).setRow(b.getRow());
			players.get(currentPlayer).setCol(b.getColumn());
		}

		super.repaint();
		currentPlayer++;
		if (currentPlayer == 6) {
			currentPlayer = 0;
		}

	}

	// =========================================================================================
	// GAMEPLAY: DEALING CARDS, SUGGESTIONS, ACCUSATIONS
	// =========================================================================================
	private  void setSolution_dealCards() {
		ArrayList<Card> deck = new ArrayList<Card>(cards); // deck to shuffle

		Random rand = new Random();
		solution = new Solution();

		int n = rand.nextInt(9);
		solution.room = cards.get(n).getCardName();
		Collections.swap(deck, 0, n); // moving to front of deck for removal

		n = rand.nextInt(6) + 9;
		solution.person = cards.get(n).getCardName();
		Collections.swap(deck, 1, n); // moving to first index for removal

		n = rand.nextInt(6) + 15;
		solution.weapon = cards.get(n).getCardName();
		Collections.swap(deck, 2, n); // same as above

		for (int i = 0; i < 3; i++) { // removing them from deck
			deck.remove(i);
		}

		Collections.shuffle(deck); // shuffles the deck randomly

		int playerNum = 0;

		for (Card c : deck) { // dealing the cards
			Card t = new Card(c.getCardName(), c.getCardType());
			if (playerNum >= players.size())
				playerNum = 0;

			players.get(playerNum).addCard(t);
			playerNum++;
		}
	}

	public  Card handleSuggestion(Solution suggestion, Player player) {
		Card tempCard = null;
		int index = players.indexOf(player) + 1; // counter to ensure correct
		// queuing

		for (int i = 0; i < players.size() - 1; i++) { // iterate through
			// players.size() - 1
			// b/c don't want to
			// disprove the accuser
			if (index > players.size() - 1)
				index = 0;
			if (players.get(index).disproveSuggestion(suggestion) != null) {
				tempCard = players.get(index).disproveSuggestion(suggestion);
			}
			index++;
		}

		return tempCard;
	}

	public  boolean checkAccusation(Solution accusation) {
		if (solution.person == accusation.person && solution.room == accusation.room
				&& solution.weapon == accusation.weapon) {
			return true;
		}
		return false;
	}
	// =========================================================================================
	// GUI
	// =========================================================================================
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		super.setBackground(Color.LIGHT_GRAY);

		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				grid[i][j].draw(g);			//draws the board 
			}
		}


		for (Player p: players) {
			g.setColor(p.getColor());
			g.fillOval(p.getCol()*BoardCell.CELL_SIZE, p.getRow()*BoardCell.CELL_SIZE, BoardCell.CELL_SIZE, BoardCell.CELL_SIZE);

		}

		//RoomNames TODO fix these so they don't go to next screen 
		g.setColor(Color.BLACK);

		g.drawString("Cryo", 5, 80);
		g.drawString("Chamber", 2, 100);

		g.drawString("SpaceBasketball Court", 10, 400);

		g.drawString("Abduction Chamber", 330, 50);
		g.drawString("Galaxy Bar", 320, 400);
		g.drawString("Captains Quarters", 140, 30);
		g.drawString("Gravity Room", 350, 250);
		g.drawString("Observatory", 240, 235);

		g.drawString("Wookie", 370, 120);
		g.drawString("Slave", 370, 135);
		g.drawString("Chambers", 370, 150);

		g.drawString("Space Pool", 40, 200);

	}

	// ========================================================================================
	// GETTERS & SETTERS
	// ========================================================================================
	public void setConfigFiles(String string, String string2, String string3, String string4) {
		layoutString = string;
		legendString = string2;
		PlayerString = string3;
		WeaponString = string4;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public  Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}

	public  Map<Character, String> getLegend() {
		return roomLegend;
	}

	public int getNumRows() {
		return rows;
	}

	public int getNumColumns() {
		return columns;
	}

	public BoardCell getCellAt(int i, int j) {
		return grid[i][j];
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMtx.get(grid[i][j]);
	}

	public  ArrayList<Player> getPlayers() {
		return players;
	}

	public  ArrayList<String> getWeapons() {
		return weapons;
	}

	public  ArrayList<Card> getCards() {
		return cards;
	}

	public void setGameControl(Control_GUI control) {
		this.control = control;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void movePlayer(int x, int y) {
		players.get(0).setCol(x/BoardCell.CELL_SIZE);
		players.get(0).setRow(y/BoardCell.CELL_SIZE);
		repaint();
	}

	public boolean checkMouseClick(int x, int y) {

		if (targets.contains(Board.getInstance().getCellAt(y/BoardCell.CELL_SIZE, x/BoardCell.CELL_SIZE))) return true;

		return false;
	}

	public void setPlayerTurnDone(boolean b) {
		playerTurnDone = b;		
	}

	public boolean getPlayerTurnDone() {
		return playerTurnDone;
	}
}


//============================================================
// MOUSE LISTENER
//============================================================
class PlayerInputListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (Board.getInstance().getCurrentPlayer() == 1 && Board.getInstance().checkMouseClick(e.getX(), e.getY())) {


			Board.getInstance().movePlayer(e.getX(), e.getY());
			Board.getInstance().setPlayerTurnDone(true);
		}

		else  {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame,"Not a Valid Square", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

