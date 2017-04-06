package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class GVIG_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/GVIG_ClueLayout.csv", "data/GVIG_ClueLegend.txt", "data/GVIG_Players.txt", "data/GVIG_Weapons.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(1, 9);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(3, 18);	
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(10, 5);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(12, 20);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(13, 8);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// GREEN tests
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(2, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 2)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(12, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 16)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(4, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		//TEST DOORWAY UP
		testList = board.getAdjList(18, 20);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 20)));
		//TEST DOORWAY LEFT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(10, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 11)));		
	}
	
	// Test adjacency at entrance to rooms
	//PURPLE tests
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(4, 11);
		assertTrue(testList.contains(board.getCellAt(4, 10)));
		assertTrue(testList.contains(board.getCellAt(4, 12)));
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(13, 12);
		assertTrue(testList.contains(board.getCellAt(12, 12)));
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		assertTrue(testList.contains(board.getCellAt(14, 12)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(20, 11);
		assertTrue(testList.contains(board.getCellAt(20, 10)));
		assertTrue(testList.contains(board.getCellAt(20, 12)));
		assertEquals(2, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(16, 2);
		assertTrue(testList.contains(board.getCellAt(16, 1)));
		assertTrue(testList.contains(board.getCellAt(16, 3)));
		assertTrue(testList.contains(board.getCellAt(17, 2)));
		assertTrue(testList.contains(board.getCellAt(15, 2)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 13);
		assertTrue(testList.contains(board.getCellAt(1, 13)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(6, 13);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 13)));
		assertTrue(testList.contains(board.getCellAt(6, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 13)));

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(15, 20);
		assertTrue(testList.contains(board.getCellAt(15, 19)));
		assertTrue(testList.contains(board.getCellAt(16, 20)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(11,0);
		assertTrue(testList.contains(board.getCellAt(10, 0)));
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertEquals(2, testList.size());
		
		// next to 3 room piece
		testList = board.getAdjList(11, 8);
		assertTrue(testList.contains(board.getCellAt(11, 7)));
		assertTrue(testList.contains(board.getCellAt(12, 8)));
		assertTrue(testList.contains(board.getCellAt(11, 9)));
		assertEquals(3, testList.size());
		
		//next to 4 walkwauys
		testList = board.getAdjList(5, 6);
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(6, 6)));
		assertTrue(testList.contains(board.getCellAt(5, 7)));
		assertEquals(4, testList.size());

		//on bottom edge with room next to it 2 adj 
		testList = board.getAdjList(20, 9);
		assertTrue(testList.contains(board.getCellAt(19, 9)));
		assertTrue(testList.contains(board.getCellAt(20, 10)));
		assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(7, 16, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));	
		assertTrue(targets.contains(board.getCellAt(7, 17)));
		assertTrue(targets.contains(board.getCellAt(8, 16)));
		
		board.calcTargets(8, 6, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(8, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));	
		assertTrue(targets.contains(board.getCellAt(7, 6)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(14, 10, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 9)));
		assertTrue(targets.contains(board.getCellAt(14, 9)));
		assertTrue(targets.contains(board.getCellAt(16, 9)));
		assertTrue(targets.contains(board.getCellAt(17, 10)));
		assertTrue(targets.contains(board.getCellAt(15, 10)));
		assertTrue(targets.contains(board.getCellAt(13, 10)));
		assertTrue(targets.contains(board.getCellAt(11, 10)));
		assertTrue(targets.contains(board.getCellAt(12, 11)));
		assertTrue(targets.contains(board.getCellAt(14, 11)));
		assertTrue(targets.contains(board.getCellAt(15, 12)));
		assertTrue(targets.contains(board.getCellAt(14, 13)));					
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(4, 20, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 16)));
		assertTrue(targets.contains(board.getCellAt(5, 17)));
			
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(17, 18, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		// directly left and right
		assertTrue(targets.contains(board.getCellAt(17, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 20)));
		//directly up
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(16, 19)));
		//doorway left/down
		assertTrue(targets.contains(board.getCellAt(18, 19)));
	}
	
	// Test getting into room,
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(15, 1, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 0)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));
		assertTrue(targets.contains(board.getCellAt(17, 0)));
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		assertTrue(targets.contains(board.getCellAt(16, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));
		assertTrue(targets.contains(board.getCellAt(17, 2)));
		assertTrue(targets.contains(board.getCellAt(14, 3)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		
		
		
		board.calcTargets(6, 2, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 1)));
		assertTrue(targets.contains(board.getCellAt(5, 2)));
		assertTrue(targets.contains(board.getCellAt(6, 3)));
		assertTrue(targets.contains(board.getCellAt(7, 2)));
	}

	// Test getting out of a room
	@Test
	public void testRoomExit()
	{
		board.calcTargets(12, 17, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		assertTrue(targets.contains(board.getCellAt(11, 16)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));

		// Take two steps
		board.calcTargets(4, 9, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 10)));
	}

}
