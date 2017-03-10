package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	private static BoardCell[][] grid;
	private static Set<BoardCell> targets;
	private static Set<BoardCell> visited;

	public static Map<Character, String> legend = null;
	private static int rows;
	private static int columns;

	public static final int MAX_ROWS = 50;
	public static final int MAX_COLUMNS = 50;

	private static Map<BoardCell, Set<BoardCell>> adjMtx;

	private static String legendString;
	private static String layoutString;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		grid = new BoardCell[MAX_ROWS][MAX_COLUMNS];
		legend = new HashMap<Character, String>();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public static void calcAdjacencies(){
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				Set<BoardCell> tempSet = new HashSet<BoardCell>();

				if(grid[i][j].getInitial() != 'W'){
					switch (grid[i][j].getDoorDirection()) {
					case UP:
						tempSet.add(grid[i-1][j]);
						break;
					case DOWN:
						tempSet.add(grid[i+1][j]);
						break;
					case LEFT:
						tempSet.add(grid[i][j-1]);
						break;
					case RIGHT:
						tempSet.add(grid[i][j+1]);
						break;
					default:
						break;
					}				
				}

				else {
					if(i > 0){
						if(grid[i-1][j].getInitial() == 'W' || (grid[i-1][j].isDoorway() && grid[i-1][j].getDoorDirection().equals(DoorDirection.DOWN))) tempSet.add(grid[i-1][j]);
						if (j > 0){
							if(grid[i][j-1].getInitial() == 'W' || (grid[i][j-1].isDoorway() && grid[i][j-1].getDoorDirection().equals(DoorDirection.RIGHT))) tempSet.add(grid[i][j - 1]);
						}

					}
					if(i > 0){
						if(grid[i-1][j].getInitial() == 'W' || (grid[i-1][j].isDoorway() && grid[i-1][j].getDoorDirection().equals(DoorDirection.DOWN))) tempSet.add(grid[i-1][j]);
						if (j < columns - 1){
							if(grid[i][j+1].getInitial() == 'W' || (grid[i][j+1].isDoorway() && grid[i][j+1].getDoorDirection().equals(DoorDirection.LEFT))) tempSet.add(grid[i][j + 1]);
						}

					}
					if(i < rows - 1){
						if(grid[i+1][j].getInitial() == 'W' || (grid[i+1][j].isDoorway() && grid[i+1][j].getDoorDirection().equals(DoorDirection.UP))) tempSet.add(grid[i+1][j]);
						if (j > 0){
							if(grid[i][j-1].getInitial() == 'W' || (grid[i][j-1].isDoorway() && grid[i][j-1].getDoorDirection().equals(DoorDirection.RIGHT))) tempSet.add(grid[i][j - 1]);
						}

					}
					if(i < rows - 1){
						if(grid[i+1][j].getInitial() == 'W' || (grid[i+1][j].isDoorway() && grid[i+1][j].getDoorDirection().equals(DoorDirection.UP))) tempSet.add(grid[i+1][j]);
						if (j < columns - 1){
							if(grid[i][j+1].getInitial() == 'W' || (grid[i][j+1].isDoorway() && grid[i][j+1].getDoorDirection().equals(DoorDirection.LEFT))) tempSet.add(grid[i][j + 1]);
						}

					}
				}

				adjMtx.put(grid[i][j], tempSet);
			}
		}
	}

	public void calcTargets(int i, int j, int numSteps){
		targets.clear();
		BoardCell startCell = new BoardCell();
		startCell = grid[i][j];
		calculateTargets(startCell, numSteps);
	}

	private void calculateTargets(BoardCell startCell, int numSteps){

		visited.add(startCell);

		for(BoardCell adjCell: adjMtx.get(startCell)){
			if(visited.contains(adjCell)) continue;
			visited.add(adjCell);
			if(adjCell.isDoorway()) targets.add(adjCell);
			if(numSteps == 1) targets.add(adjCell);
			else{
				calculateTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}

	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public static Set<BoardCell> getAdjList(BoardCell cell){
		Set<BoardCell> adj = new HashSet<BoardCell>();
		adj = adjMtx.get(cell);
		return adj;
	}

	public BoardCell getCell(int i, int j) {

		return grid[i][j];
	}

	public static void setConfigFiles(String string, String string2) {
		layoutString = string;
		legendString = string2;

	}

	public static void initialize() {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		grid = new BoardCell[MAX_ROWS][MAX_COLUMNS];
		legend = new HashMap<Character, String>();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		catch (FileNotFoundException e) {
			e.getStackTrace();
		}
		calcAdjacencies();


	}

	public static void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException{

		FileReader reader = null;
		Scanner in = null;


		try {
			reader = new FileReader(layoutString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		in = new Scanner(reader);

		int counter = 0;
		int c = 0;

		while (in.hasNextLine()){
			String[] temp = in.nextLine().split(",");
			if(counter == 0) c = temp.length;
			for(int i = 0; i < temp.length; i++){
				grid[counter][i] = new BoardCell(counter,i,temp[i]);
				//System.out.println(legend.size());
				//System.out.println(legend.containsKey(grid[counter][i].getInitial()));
				if (!legend.containsKey(grid[counter][i].getInitial()))  throw new BadConfigFormatException("Wrong legend in board");
			}
			counter++;
			if (temp.length != c) throw new BadConfigFormatException("Number of columns is not consistent");
			columns = temp.length;

		}

		rows = counter;

		in.close();
	}

	public static void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		legend = new HashMap<Character, String>();

		FileReader reader = null;
		Scanner in = null;

		try {
			reader = new FileReader(legendString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		in = new Scanner(reader);

		String symbol;
		String room;
		String type;

		while (in.hasNextLine()){

			String[] temp = in.nextLine().split(", ");

			symbol = temp[0];
			room = temp[1];
			type = temp[2];
			//System.out.println(type + " " + (!type.equals("Card") && !type.equals("Other")));
			//System.out.println("here");
			legend.put(symbol.charAt(0), room);
			//System.out.println(legend.size());
			if((!type.equals("Card") && !type.equals("Other"))) throw new BadConfigFormatException("Not of type 'Card' or 'Other'");
			//if(type.equals("X")) throw new BadConfigFormatException("Not of type 'Card' or 'Other'");
		}

		in.close();
	}

	public Map<Character, String> getLegend() {
		return legend;
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


}
