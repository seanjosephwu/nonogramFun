/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim (Edited by Sean Wu)
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.nonogram;
import java.util.List;

import org.json.JSONObject;

import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.utility.NonoUtil;
import uw.cse403.nonogramfun.utility.ParameterPolice;


/**
 * Nonogram is the main back end class that provides important functionalities
 * related for playing Nonogram: creating puzzle, getting puzzle, saving score etc.
 */
public class Nonogram {
	
	
	// Private constructor
	private Nonogram() {}
	
	
	/**
	 * Accepts a JSON Object containing client request for creating puzzle. Creates
	 * the puzzle using parameters stored in JSON object, and stores it in the database.
	 * @param requestJSON A JSON Object containing client request for creating puzzle
	 * @throws Exception if given JSON object is not valid or any error in saving puzzle.
	 */
	public static void createPuzzle(JSONObject requestJSON) throws Exception {
		ParameterPolice.checkIfNull(requestJSON, "JSON Object");
		
		Integer[][] cArray = NonoUtil.getColorArray(requestJSON);
		Integer bgColor = NonoUtil.getColor(requestJSON);
		String name = NonoUtil.getString(requestJSON);
		NonoPuzzle puzzle = NonoPuzzle.createNonoPuzzle(cArray, bgColor, name);
		//TODO: NonoPuzzleSolver check for uniqueness of solution, if fail, throw exception with message saying it is invalid puzzle
		System.out.println("Saving...");
		NonoDatabase.savePuzzle(puzzle);
	}
	
	/**
	 * Accepts a JSON Object containing client request for getting a puzzle with given difficulty.
	 * @param requestJSON A JSON Object containing client request for getting a puzzle
	 * @return A randomly chosen NonoPuzzle that has requested difficulty 
	 * @throws Exception if given JSON object is not valid or any error in getting puzzle.
	 */
	public static NonoPuzzle getPuzzle(JSONObject requestJSON) throws Exception {
		ParameterPolice.checkIfNull(requestJSON, "JSON Object");
		
		Difficulty difficulty = NonoUtil.getDifficulty(requestJSON);
		List<Integer> puzzleIDList = NonoDatabase.getPuzzleIDList(difficulty);
		int index = (int) Math.floor(Math.random()*puzzleIDList.size());
		int puzzleID = puzzleIDList.get(index);
		return NonoDatabase.getPuzzle(puzzleID);
	}

}
