package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.Board;

public class IntBoardTests {

	Board board;
	
	@Before
	public void setup(){
		board = new Board();
	}
	
	/*
	 * Test adjacencies for top left corner
	 */
	@Test
	public void testAdjacency0() {
		clueGame.BoardCell cell = board.getCell(0,0);
		Set<clueGame.BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}

	/*
	 * Test adjacencies for bottom right corner
	 */
	@Test
	public void testAdjacency1() {
		clueGame.BoardCell cell = board.getCell(3,3);
		Set<clueGame.BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for right edge
	 */
	@Test
	public void testAdjacency2() {
		clueGame.BoardCell cell = board.getCell(1,3);
		Set<clueGame.BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for left edge
	 */
	@Test
	public void testAdjacency3() {
		clueGame.BoardCell cell = board.getCell(3,0);
		Set<clueGame.BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for second column middle of grid
	 */
	@Test
	public void testAdjacency4() {
		clueGame.BoardCell cell = board.getCell(1,1);
		Set<clueGame.BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test adjacencies for middle of grid
	 */
	@Test
	public void testAdjacency5() {
		clueGame.BoardCell cell = board.getCell(2,2);
		Set<clueGame.BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test targets for roll of 1 starting from 0,0
	 */
	@Test
	public void testTargets0_1()
	{
		clueGame.BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	/*
	 * Test targets for roll of 2 starting from 0,0
	 */
	@Test
	public void testTargets0_2()
	{
		clueGame.BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	/*
	 * Test targets for roll of 3 starting from 0,0
	 */
	@Test
	public void testTargets0_3()
	{
		clueGame.BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	/*
	 * Test targets for roll of 4 starting from 3,3
	 */
	@Test
	public void testTargets3_4()
	{
		clueGame.BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 4);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
	}
	
	/*
	 * Test targets for roll of 5 starting from 3,3
	 */
	@Test
	public void testTargets3_5()
	{
		clueGame.BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 5);
		Set targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	/*
	 * Test targets for roll of 6 starting from 3,3
	 */
	@Test
	public void testTargets3_6()
	{
		clueGame.BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 6);
		Set targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
	}
}
