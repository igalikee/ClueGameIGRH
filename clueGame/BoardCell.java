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
