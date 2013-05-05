package uw.cse403.nonogramfun;

/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */



/**
 * 
 *
 */
public enum Difficulty {
	EASY("Easy"), MEDIUM("Medium"), HARD("Hard"), INSANE("Insane"), UNKNOWN("Unknown");
	private final String name;

	private Difficulty(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}