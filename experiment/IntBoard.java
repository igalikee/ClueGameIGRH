package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	Map<BoardCell, Set<BoardCell>> adjMtx;
	
	public IntBoard() {
		// TODO Auto-generated constructor stub
	}
	
	public void calcAdjacencies(){
		
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
