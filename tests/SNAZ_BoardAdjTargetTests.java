package tests;

import static org.junit.Assert.*;

import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class SNAZ_BoardAdjTargetTests {

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/SNAZ_ClueLayout.csv", "data/SNAZ_ClueLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test center of room
		Set<BoardCell> testList = board.getAdjList(9, 3);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(2, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		//TEST DOORWAY LEFT
		testList = board.getAdjList(12, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 15)));

	}

	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(17, 4);
		assertTrue(testList.contains(board.getCellAt(16, 4)));
		assertTrue(testList.contains(board.getCellAt(18, 4)));
		assertTrue(testList.contains(board.getCellAt(17, 3)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 9);
		assertTrue(testList.contains(board.getCellAt(5, 8)));
		assertTrue(testList.contains(board.getCellAt(5, 10)));
		assertTrue(testList.contains(board.getCellAt(6, 9)));
		assertTrue(testList.contains(board.getCellAt(4, 9)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(4, 14);
		assertTrue(testList.contains(board.getCellAt(4, 13)));
		assertTrue(testList.contains(board.getCellAt(4, 15)));
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(7, 16);
		assertTrue(testList.contains(board.getCellAt(7, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 17)));
		assertTrue(testList.contains(board.getCellAt(6, 16)));
		assertTrue(testList.contains(board.getCellAt(8, 16)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 12);
		assertTrue(testList.contains(board.getCellAt(1, 12)));
		assertEquals(1, testList.size());

		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertEquals(3, testList.size());

		// Test on bottom edge of board, three walkway pieces
		testList = board.getAdjList(20, 15);
		assertTrue(testList.contains(board.getCellAt(20, 14)));
		assertTrue(testList.contains(board.getCellAt(19, 15)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, three walkway pieces
		testList = board.getAdjList(15, 20);
		assertTrue(testList.contains(board.getCellAt(14, 20)));
		assertTrue(testList.contains(board.getCellAt(15, 19)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(13,6);
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		assertTrue(testList.contains(board.getCellAt(13, 5)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertEquals(4, testList.size());

		// Test next to a room on the right
		testList = board.getAdjList(15, 12);
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertTrue(testList.contains(board.getCellAt(14, 12)));
		assertTrue(testList.contains(board.getCellAt(16, 12)));
		assertEquals(3, testList.size());

		// Test next to a room on the left
		testList = board.getAdjList(7, 7);
		assertTrue(testList.contains(board.getCellAt(8, 7)));
		assertTrue(testList.contains(board.getCellAt(6, 7)));
		assertTrue(testList.contains(board.getCellAt(7, 8)));
		assertEquals(3, testList.size());

	}


	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(5, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 3)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(4, 4)));
		assertTrue(targets.contains(board.getCellAt(6, 4)));
	
	}

	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(13, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 12)));
		assertTrue(targets.contains(board.getCellAt(15, 14)));
		
	}

	// Tests of just walkways, 3 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(17, 14, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 14)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 14)));
		assertTrue(targets.contains(board.getCellAt(15, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 14)));

	}	

	// Tests of just walkways plus one door, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(6, 20, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 19)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));	
		assertTrue(targets.contains(board.getCellAt(7, 17)));	
		assertTrue(targets.contains(board.getCellAt(6, 16)));	
	}	

	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(13, 2, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		// directly up
		assertTrue(targets.contains(board.getCellAt(12, 2)));
		// directly right
		assertTrue(targets.contains(board.getCellAt(13, 3)));
		// directly down
		assertTrue(targets.contains(board.getCellAt(14, 2)));
		// directly left
		assertTrue(targets.contains(board.getCellAt(13, 1)));
	}

	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		//Short cut into room of 1
		board.calcTargets(16, 18, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		// directly up
		assertTrue(targets.contains(board.getCellAt(14, 18)));
		// up and left
		assertTrue(targets.contains(board.getCellAt(15, 17)));
		// down and left
		assertTrue(targets.contains(board.getCellAt(17, 17)));
		// up and right
		assertTrue(targets.contains(board.getCellAt(15, 19)));
		// down and right
		assertTrue(targets.contains(board.getCellAt(17, 19)));
		// down shortcut into room
		assertTrue(targets.contains(board.getCellAt(17, 18)));

	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(15, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		
		// Take two steps
		board.calcTargets(2, 11, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 12)));
		assertTrue(targets.contains(board.getCellAt(3, 12)));
	}

}
