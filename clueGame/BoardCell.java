package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;

	public BoardCell(){
		
	}
	
	public BoardCell(int r, int c, String i) {
		row = r;
		column = c;
		initial = i.charAt(0);
		if(i.length() > 1){
			switch(i.charAt(1)){
				case 'U':
					direction = DoorDirection.UP;
					break;
				case 'D':
					direction = DoorDirection.DOWN;
					break;
				case 'L':
					direction = DoorDirection.LEFT;
					break;
				case 'R':
					direction = DoorDirection.RIGHT;
					break;
				default:
					direction = DoorDirection.NONE;
					break;
			}
		}
		else direction = DoorDirection.NONE;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", direction=" + direction
				+ "]";
	}

	public boolean isDoorway() {
		if (direction == DoorDirection.NONE) return false;
		return true;
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}


}
