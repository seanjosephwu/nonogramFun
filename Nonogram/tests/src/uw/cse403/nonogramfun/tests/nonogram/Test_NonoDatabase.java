/**
 * CSE 403 AA
 * Project Nonogram: Backend Test
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

package uw.cse403.nonogramfun.tests.nonogram;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.nonogram.NonoDatabase;
import uw.cse403.nonogramfun.nonogram.NonoPuzzle;

/**
 * This class tests NonoPuzzle object
 */
public class Test_NonoDatabase extends TestCase {
	private int ZERO = 0;
	

	//--Test getPuzzleIDList ------------------------------------------------------------------------

	@Test
	public void test_getPuzzleIDList() {
		List<Integer> idList = null;
		try {
			idList = NonoDatabase.getPuzzleIDList(Difficulty.EASY);
			assert(idList != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_getPuzzle() {
		List<Integer> idList = null;
		try {
			idList = NonoDatabase.getPuzzleIDList(Difficulty.EASY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (idList != null && idList.size() > ZERO) {
			try {
				int id = idList.get(ZERO);
				NonoPuzzle puzzle = NonoDatabase.getPuzzle(id);
				assert(puzzle != null);
				assert(puzzle.getPuzzleID() == id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//--Test getPuzzleIDList ------------------------------------------------------------------------
}
