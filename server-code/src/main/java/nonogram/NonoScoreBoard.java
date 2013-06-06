/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.nonogram;
import java.io.Serializable;
import java.util.*;

import uw.cse403.nonogramfun.utility.ParameterPolice;

/**
 * NonoScoreBoard represents a scoreBoard of the Nonogram game.
 */
public class NonoScoreBoard implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<NonoScore> scoreBoard;
	
	/**
	 * Constructs a new NonoScoreBoard object
	 */
	public NonoScoreBoard() {
		scoreBoard = new LinkedList<NonoScore>();
	}
	
	/**
	 * Adds a NonoScore to this scoreBoard. 
	 * @param score Score to be added to this score board.
	 * @throws IllegalArgumentException if the given score is null.
	 */
	public void add(NonoScore score) {
		ParameterPolice.checkIfNull(score, "NonoScore");
		scoreBoard.add(score);
	}
	
	/**
	 * @return An iterator that returns scores stored in this score board. 
	 */
	public Iterator<NonoScore> getIterator() {
		return scoreBoard.iterator();
	}
	
	/**
	 * Returns a string representation of this score board
	 */
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		for(NonoScore score : scoreBoard) {
			sBuilder.append(score + "\n");
		}
		return sBuilder.toString();
	}
}
