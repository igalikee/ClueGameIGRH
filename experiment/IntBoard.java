package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	Map<BoardCell, Set<BoardCell>> adjMtx;
	
	public IntBoard() {
		grid = new BoardCell[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				grid[i][j] = new BoardCell(i,j);
			}
		}
	}
	
	public void calcAdjacencies(){
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				Set tempSet = new HashSet<BoardCell>();
				
				if(i != 0){
					tempSet.add(grid[i-1][j])
				}
				
				tempSet.add(grid[i+1][j])
				
				adjMtx.put(grid[i][j], tempSet);
			}
		}
	}

	public void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}

	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
}
