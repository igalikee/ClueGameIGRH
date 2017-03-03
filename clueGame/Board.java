package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	private static BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	public static Map<Character, String> legend = null;
	public static int rows;
	public static int columns;
	
	Map<BoardCell, Set<BoardCell>> adjMtx;
	
	private static String legendString;
	private static String layoutString;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		grid = new BoardCell[25][25];
		//calcAdjacencies();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void calcAdjacencies(){
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		for(int i = 0; i < 25; i++){
			for(int j = 0; j < 25; j++){
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				
				if(i > 0){
					tempSet.add(grid[i-1][j]);
					if (j > 0){
						tempSet.add(grid[i][j - 1]);
					}
					
				}
				if(i > 0){
					tempSet.add(grid[i-1][j]);
					if (j < 25){
						tempSet.add(grid[i][j + 1]);
					}
					
				}
				if(i < 3){
					tempSet.add(grid[i+1][j]);
					if (j < 25){
						tempSet.add(grid[i][j + 1]);
					}
					
				}
				if(i < 3){
					tempSet.add(grid[i+1][j]);
					if (j > 25){
						tempSet.add(grid[i][j - 1]);
					}
					
				}
								
				adjMtx.put(grid[i][j], tempSet);
			}
		}
	}

	public void calcTargets(BoardCell startCell, int numSteps){
		
		visited.add(startCell);
		
		for(BoardCell adjCell: adjMtx.get(startCell)){
			if(visited.contains(adjCell)) continue;
			visited.add(adjCell);
			if(numSteps == 1) targets.add(adjCell);
			else{
				calcTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
		
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjMtx.get(cell);
	}

	public BoardCell getCell(int i, int j) {
		
		return grid[i][j];
	}

	public static void setConfigFiles(String string, String string2) {
		layoutString = string;
		legendString = string2;
		
	}

	public static void initialize() {
		createLayout();
		createLegend();
	}
	
	private static void createLayout() {
		FileReader reader = null;
		Scanner in = null;
		
		try {
			reader = new FileReader(layoutString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in = new Scanner(reader);
		
		int counter = 0;
		
		while (in.hasNextLine()){
			String[] temp = in.nextLine().split(",");
			for(int i = 0; i < temp.length; i++){
				BoardCell tempCell = new BoardCell(counter,i,temp[i]);
				grid[counter][i] = tempCell;
			}
			counter++;
			rows = temp.length;
		}
		columns = counter;
		
		in.close();
	}
	
	private static void createLegend() {
		legend = new HashMap<Character, String>();
		
		FileReader reader = null;
		Scanner in = null;
		
		try {
			reader = new FileReader(legendString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in = new Scanner(reader);
		
		String symbol;
		String room;
		String type;
		
		while (in.hasNextLine()){
			
			String[] temp = in.nextLine().split(", ");
			
			for (int i = 0; i < temp.length; i++){
				System.out.println(temp[i]);
			}
			
			symbol = temp[0];
			room = temp[1];
			type = temp[2];
			
			legend.put(symbol.charAt(0), room);
			
			
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
		// TODO Auto-generated method stub
		return null;
	}
}
