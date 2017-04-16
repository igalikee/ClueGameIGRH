package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {

	

	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	private boolean playerHighlight;

	public final static int CELL_SIZE = 20;
	public final int DOOR_HEIGHT = 3;
	public final int DOOR_OFFSET = CELL_SIZE - DOOR_HEIGHT;

	public BoardCell(int r, int c, String i) {
		row = r;
		column = c;
		initial = i.charAt(0);
		playerHighlight = false;
		
		if (i.length() > 1) {
			switch (i.charAt(1)) {
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
		} else
			direction = DoorDirection.NONE;
	}

	public void draw(Graphics g) {

		if (initial == 'W' && playerHighlight == false) {
			g.setColor(Color.YELLOW);
			g.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			g.setColor(Color.GRAY);
			g.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);	
		}
		
		else if (playerHighlight ==  true) {
			g.setColor(Color.CYAN);
			g.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			g.setColor(Color.GRAY);
			g.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			
			playerHighlight = false;
		}
		
		else {
			g.setColor(Color.GRAY);
			g.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}
		


		g.setColor(Color.RED);
		switch (getDoorDirection()) {
		case DOWN: g.fillRect(column*CELL_SIZE, row*CELL_SIZE  + DOOR_OFFSET, CELL_SIZE, DOOR_HEIGHT);  
			break;
		case LEFT: g.fillRect(column*CELL_SIZE, row*CELL_SIZE, DOOR_HEIGHT, CELL_SIZE); 
			break;
		case RIGHT: g.fillRect(column*CELL_SIZE  + DOOR_OFFSET, row*CELL_SIZE, DOOR_HEIGHT , CELL_SIZE); 
			break;
		case UP: g.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE ,DOOR_HEIGHT); 
			break;
		default:
			break;
		}

	}
	
	public void setPlayerHighlight(boolean playerHighlight) {
		this.playerHighlight = playerHighlight;
	}


	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", direction=" + direction
				+ "]" + "\n";
	}

	public boolean isDoorway() {
		if (direction == DoorDirection.NONE)
			return false;
		return true;
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
