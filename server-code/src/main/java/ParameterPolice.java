/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import java.util.NoSuchElementException;


/**
 * ParameterPolice is a police officer that helps keeping state of other object
 * valid by checking validity of parameters passed to their methods 
 * (ParameterPolice class is singleton and all its methods are static).   
 * It arrests(scary!) the execution of program by throwing various exceptions when 
 * parameters are turned out to be invalid! 
 */
public class ParameterPolice {
	private static ParameterPolice police = null;
	
	// An empty private constructor.
		private ParameterPolice() {}
		
	/**
	 * Returns a reference to ParameterPolice object if there is already existing 
	 * one, or creates a ParameterPolice and returns the reference to it.
	 * @return A reference to ParameterPolice object.
	 */
	public static ParameterPolice getInstance() {
		if(police == null) {
			police = new ParameterPolice();
		}
		return police;
	}
	
	
	/**
	 * Accepts an object as a parameter and throws an 
	 * IllegalArgumentException if it is null.
	 * @param o An object to be checked if it is null.
	 * @throws IllegalArgumentException if the passed object is null.
	 */
	public static void checkIfNull(Object o, String objectName) {
		if(o == null){
			throw new IllegalArgumentException("Error: " + objectName + " is null!");
		}
	}
	
	/**
	 * Accepts a string the represent a name as a parameter and throws an 
	 * NoSuchElementException if it is null. Hack around the provide unit tests
	 * catch only NoSuchElementException, not IllegalArgumentException.
	 * @param o An object to be checked if it is null.
	 * @throws NoSuchElementException if the passed object is null.
	 */
	public static void checkIfNullName(String s, String objectName) {
		if(s == null){
			throw new NoSuchElementException("Error: " + objectName + " is null!");
		}
	}
	
	
	/**
	 * Throws NoSuchElementException if given index is out of range, [lowB, upB)
	 * @param i - index to be checked
	 * @param lowB - inclusive lower bound of i
	 * @param upB - exclusive upper bound of i
	 */
	public static void checkIfWithinRange(int i, int lowB, int upB) {
		if(i < lowB || upB <= i) {
	        throw new NoSuchElementException("Error: Given number ("+ i + ") is out of bound [" + lowB + ", " + upB + ")!");
		}
	}
	
	/**
	 * Throws IllegalArgumentException if given number is not positive  
	 * @param n - number to be checked
	 */
	public static void checkIfNotPositive(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Error: Given number \"" + n + "\" is not positive!");
		}
	}
	
	/**
	 * Throws IllegalArgumentException if given number is negative  
	 * @param n - number to be checked
	 */
	public static void checkIfNegative(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Error: Given number \"" + n + "\" is negative!");
		}
	}
}