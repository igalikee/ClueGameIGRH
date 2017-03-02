package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;

	public BoardCell(){
		
	}
	
	public BoardCell(int r, int c) {
		row = r;
		column = c;
	}

	public boolean isDoorway() {
		
		return direction != DoorDirection.NONE;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return direction;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}


}
