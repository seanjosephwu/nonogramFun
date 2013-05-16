/**
 * CSE 403 AA
 * Project Nonogram: Backend Test
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

package uw.cse403.nonogramfun.test;
import static org.junit.Assert.*;
import org.junit.Test;

import uw.cse403.nonogramfun.enums.Difficulty;


/**
 * This class tests Difficulty (Enum)
 */
public class Test_Difficulty {
	private static final int START_EASY = 5;
	private static final int START_MED = 15;
	private static final int START_HARD = 25;
	private static final int START_INSANE = 50;
	private static final int START_UNDEF = 100;
	private static final int ADD_SMALL = 3;
	private static final int ADD_MORE = 5;
	
	
	
	//--Test isInRange()------------------------------------------------------------------------
	
	private void test_isInRangeHelper_True(Difficulty d, int startRange, int endRange) {
		assertTrue(d.isInRange(startRange, startRange));
		assertTrue(d.isInRange(endRange-1, startRange+ADD_MORE));
		assertTrue(d.isInRange(startRange+ADD_SMALL, startRange+ADD_SMALL));
	}
	
	private void test_isInRangeHelper_False(Difficulty d, int offBelow, int startRange, int endRange) {
		assertFalse(d.isInRange(offBelow, startRange));
		assertFalse(d.isInRange(startRange+ADD_MORE, endRange+ADD_SMALL));
		assertFalse(d.isInRange(startRange+ADD_MORE, endRange));
	}
	
	@Test
	public void test_isInRange_Easy_1() {
		test_isInRangeHelper_True(Difficulty.EASY, START_EASY, START_MED);
	}
	
	@Test
	public void test_isInRange_Easy_2() {
		test_isInRangeHelper_False(Difficulty.EASY, -5, START_EASY, START_MED);
	}
	
	@Test
	public void test_isInRange_Med_1() {
		test_isInRangeHelper_True(Difficulty.MEDIUM, START_MED, START_HARD);
	}
	
	@Test
	public void test_isInRange_Med_2() {
		test_isInRangeHelper_False(Difficulty.MEDIUM, START_EASY, START_MED, START_HARD);
	}

	@Test
	public void test_isInRange_Hard_1() {
		test_isInRangeHelper_True(Difficulty.HARD, START_HARD, START_INSANE);
	}
	
	@Test
	public void test_isInRange_Hard_2() {
		test_isInRangeHelper_False(Difficulty.HARD, START_MED, START_HARD, START_INSANE);
	}
	
	@Test
	public void test_isInRange_Insane_1() {
		test_isInRangeHelper_True(Difficulty.INSANE, START_INSANE, START_UNDEF);
	}
	
	@Test
	public void test_isInRange_Insane_2() {
		test_isInRangeHelper_False(Difficulty.INSANE, START_HARD, START_INSANE, START_UNDEF);
	}
	
	@Test
	public void test_isInRange_Undef_1() {
		Difficulty d = Difficulty.UNDEFINED;
		assertTrue(d.isInRange(START_UNDEF, 10000));
		assertTrue(d.isInRange(START_HARD+ADD_MORE, START_INSANE+ADD_SMALL));
		assertTrue(d.isInRange(START_EASY-ADD_MORE, START_MED-ADD_SMALL));
	}
	
	@Test
	public void test_isInRange_Undef_2() {
		Difficulty d = Difficulty.UNDEFINED;
		assertFalse(d.isInRange(START_EASY+ADD_SMALL, START_EASY+ADD_SMALL));
		assertFalse(d.isInRange(START_HARD-1, START_MED+ADD_MORE));
		assertFalse(d.isInRange(START_INSANE, START_INSANE));
	}
	
	
	//--Test getDifficulty()------------------------------------------------------------------------
	
	
	private void test_getDifficulty_Helper_True(Difficulty d, int startRange, int endRange) {
		assertEquals(d, Difficulty.getDifficulty(startRange, startRange));
		assertEquals(d, Difficulty.getDifficulty(endRange-1, startRange+ADD_MORE));
		assertEquals(d, Difficulty.getDifficulty(startRange+ADD_SMALL, startRange+ADD_SMALL));
	}
	
	private void test_getDifficulty_Helper_False(Difficulty d, int offBelow, int startRange, int endRange) {
		assertNotSame(d, Difficulty.getDifficulty(offBelow, startRange));
		assertNotSame(d, Difficulty.getDifficulty(startRange+ADD_MORE, endRange+ADD_SMALL));
		assertNotSame(d, Difficulty.getDifficulty(startRange+ADD_MORE, endRange));
	}
	
	@Test
	public void test_getDifficulty_Easy_1() {
		test_getDifficulty_Helper_True(Difficulty.EASY, START_EASY, START_MED);
	}
	
	@Test
	public void test_getDifficulty_Easy_2() {
		test_getDifficulty_Helper_False(Difficulty.EASY, -5, START_EASY, START_MED);
	}
	
	@Test
	public void test_getDifficulty_Med_1() {
		test_getDifficulty_Helper_True(Difficulty.MEDIUM, START_MED, START_HARD);
	}
	
	@Test
	public void test_getDifficulty_Med_2() {
		test_getDifficulty_Helper_False(Difficulty.MEDIUM, START_EASY, START_MED, START_HARD);
	}

	@Test
	public void test_getDifficulty_Hard_1() {
		test_getDifficulty_Helper_True(Difficulty.HARD, START_HARD, START_INSANE);
	}
	
	@Test
	public void test_getDifficulty_Hard_2() {
		test_getDifficulty_Helper_False(Difficulty.HARD, START_MED, START_HARD, START_INSANE);
	}
	
	@Test
	public void test_getDifficulty_Insane_1() {
		test_getDifficulty_Helper_True(Difficulty.INSANE, START_INSANE, START_UNDEF);
	}
	
	@Test
	public void test_getDifficulty_Insane_2() {
		test_getDifficulty_Helper_False(Difficulty.INSANE, START_HARD, START_INSANE, START_UNDEF);
	}
	
	@Test
	public void test_getDifficulty_Undef_1() {
		assertEquals(Difficulty.UNDEFINED, Difficulty.getDifficulty(START_UNDEF, 10000));
		assertEquals(Difficulty.UNDEFINED, Difficulty.getDifficulty(START_HARD+ADD_MORE, START_INSANE+ADD_SMALL));
		assertEquals(Difficulty.UNDEFINED, Difficulty.getDifficulty(START_EASY-ADD_MORE, START_MED-ADD_SMALL));
	}
	
	@Test
	public void test_getDifficulty_Undef_2() {
		assertNotSame(Difficulty.UNDEFINED, Difficulty.getDifficulty(START_EASY+ADD_SMALL, START_EASY+ADD_SMALL));
		assertNotSame(Difficulty.UNDEFINED, Difficulty.getDifficulty(START_HARD-1, START_MED+ADD_MORE));
		assertNotSame(Difficulty.UNDEFINED, Difficulty.getDifficulty(START_INSANE, START_INSANE));
	}
}
