package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BoardCell;
import clueGame.DoorDirection;
import experiment.Board;

public class SNAZ_FileInitTests {

	// Constants that I will use to test whether the file was loaded correctly
		public static final int LEGEND_SIZE = 11;
		public static final int NUM_ROWS = 21;
		public static final int NUM_COLUMNS = 21;

		// NOTE: I made Board static because I only want to set it up one 
		// time (using @BeforeClass), no need to do setup before each test.
		private static Board board;
		
		@BeforeClass
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			//board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
			// Initialize will load BOTH config files 
			//board.initialize();
		}
		
		@Test
		public void testRooms() {
			// Get the map of initial => room 
			Map<Character, String> legend = board.getLegend();
			// Ensure we read the correct number of rooms
			assertEquals(LEGEND_SIZE, legend.size());
			// To ensure data is correctly loaded, test retrieving a few rooms 
			// from the hash, including the first and last in the file and a few others
			assertEquals("Rainbow Road", legend.get('R'));
			assertEquals("Bowser's Castle", legend.get('B'));
			assertEquals("Luigi Raceway", legend.get('L'));
			assertEquals("Kalimari Desert", legend.get('K'));
			assertEquals("Moo Moo Meadoes", legend.get('M'));
			assertEquals("Toad's Factory", legend.get('T'));
			assertEquals("Ghost Valley", legend.get('G'));
			assertEquals("Donut Plains", legend.get('D'));
			assertEquals("Shroom Ridge", legend.get('S'));
			assertEquals("Walkway", legend.get('W'));
			assertEquals("closet", legend.get('X'));
		}
		
		@Test
		public void testBoardDimensions() {
			// Ensure we have the proper number of rows and columns
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());		
		}
		
		// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell room = board.getCellAt(4, 3);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(4, 8);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCellAt(15, 18);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(14, 11);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(14, 14);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(0, 6);
			assertFalse(cell.isDoorway());		

		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(13, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			assertEquals('R', board.getCellAt(0, 0).getInitial());
			assertEquals('D', board.getCellAt(0, 6).getInitial());
			assertEquals('S', board.getCellAt(9, 16).getInitial());
			assertEquals('B', board.getCellAt(16, 16).getInitial());
			assertEquals('L', board.getCellAt(20, 13).getInitial());
			assertEquals('M', board.getCellAt(17, 9).getInitial());
			assertEquals('T', board.getCellAt(14, 0).getInitial());
			assertEquals('G', board.getCellAt(7, 6).getInitial());
			assertEquals('X', board.getCellAt(9, 9).getInitial());
			assertEquals('W', board.getCellAt(13, 13).getInitial());

		}
}
