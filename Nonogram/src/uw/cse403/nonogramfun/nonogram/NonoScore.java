/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.nonogram;
import java.io.Serializable;


/**
 * NonoScore class represents a score for a Nonogram game.
 * It is a tiny struct like class that holds playerName, difficulty of
 * the game the player accomplished, and the score of the game together.
 */
public class NonoScore implements Serializable, Comparable<NonoScore> {
	private static final long serialVersionUID = 1L;
	public String playerName;
	public String difficulty;
	public int score;
	
	/**
	 * Constructs a new NonoScore object
	 */
	public NonoScore(String playerName, String difficulty, int score) {
		this.playerName = playerName;
		this.difficulty = difficulty;
		this.score = score;
	}
	
	/**
	 * Compare NonoScore y score
	 * return -1 is this NonoScore is less than other
	 * return 0 for equal
	 * return 1 for greater
	 */
	public int compareTo(NonoScore other){
		if (other.score > this.score)
			return -1;
		else if (other.score < this. score)
			return 1;
		else 
			return 0;
	}

	/**
	 * Returns a string representation of NonoScore
	 */
	public String toString() {
		return playerName + "**" + difficulty + "**" + score; 
	}
}
