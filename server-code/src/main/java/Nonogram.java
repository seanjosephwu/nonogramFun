/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import android.graphics.Color;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

import org.json.*;

import enums.*;

/**
 * 
 *
 */
public class Nonogram {
	
	//
	private Nonogram() {}
	
	public static void createPuzzle(JSONObject requestJSON) throws SQLException, IOException, JSONException {
		Integer[][] cArray = NonoUtil.getColorArray(requestJSON);
		Integer bgColor = NonoUtil.getColor(requestJSON);
		String name = NonoUtil.getString(requestJSON);
		System.out.println(name);
		NonoPuzzle puzzle = NonoPuzzle.createNonoPuzzle(cArray, bgColor, name);
		//TODO: NonoPuzzleSolver check for uniqueness of solution, if fail, throw exception with message saying it is invalid puzzle
		System.out.println("Saving...");
		NonoDatabase.savePuzzle(puzzle);
	}
	
	public static NonoPuzzle getPuzzle(JSONObject requestJSON) throws JSONException, SQLException, ClassNotFoundException, IOException {
		Difficulty difficulty = NonoUtil.getDifficulty(requestJSON);
		List<Integer> puzzleIDList = NonoDatabase.getPuzzleIDList(difficulty);
		int puzzleID = puzzleIDList.get(0); //TODO: randomly chose ID
		return NonoDatabase.getPuzzle(puzzleID);
	}

}
