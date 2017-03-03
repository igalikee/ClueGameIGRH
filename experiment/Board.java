package experiment;

import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	public static Map<Character, String> legend = null;
	public static int rows;
	public static int columns;
	
	Map<BoardCell, Set<BoardCell>> adjMtx;
	
	private static String legendString;
	private static String layoutString;
	

	
	public Board() {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		grid = new BoardCell[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				grid[i][j] = new BoardCell(i,j);
			}
		}
		calcAdjacencies();
		
		
	}
	
	public void calcAdjacencies(){
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				
				if(i > 0){
					tempSet.add(grid[i-1][j]);
					if (j > 0){
						tempSet.add(grid[i][j - 1]);
					}
					
				}
				if(i > 0){
					tempSet.add(grid[i-1][j]);
					if (j < 3){
						tempSet.add(grid[i][j + 1]);
					}
					
				}
				if(i < 3){
					tempSet.add(grid[i+1][j]);
					if (j < 3){
						tempSet.add(grid[i][j + 1]);
					}
					
				}
				if(i < 3){
					tempSet.add(grid[i+1][j]);
					if (j > 0){
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

	
}
