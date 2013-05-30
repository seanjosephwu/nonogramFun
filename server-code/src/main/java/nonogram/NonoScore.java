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
 * 
 *
 */
public class NonoScore implements Serializable, Comparable<NonoScore> {
	private static final long serialVersionUID = 1L;
	public String playerName;
	public String difficulty;
	public int score;
	
	public NonoScore(String playerName, String difficulty, int score) {
		this.playerName = playerName;
		this.difficulty = difficulty;
		this.score = score;
	}
	
	public int compareTo(NonoScore other){
		if (other.score > this.score)
			return -1;
		else if (other.score < this. score)
			return 1;
		else 
			return 0;
	}
}
