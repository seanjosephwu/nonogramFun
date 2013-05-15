/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim 
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package nonogram;
import java.io.Serializable;
import java.util.*;
import utility.ParameterPolice;
import enums.Difficulty;


/**
 * A NonoPuzzle object represents a NonoPuzzle game. It stores all information 
 * needed to play a single NonoPuzzle game. Each NonoPuzzle has unique ID and it may have a name.
 */
public class NonoPuzzle implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int ID_COUNTER = 0;
	private List<NonoNum>[] rowNonoNums;
	private List<NonoNum>[] colNonoNums;
	private Integer[][] nonoPicArr;
	private Integer backgroundColor;
	private PuzzleInfo puzzleInfo;
	
	
	// Private constructor 
	@SuppressWarnings("unchecked")
	private NonoPuzzle(Integer[][] array, Integer bgColor, PuzzleInfo info) {
		rowNonoNums = (List<NonoNum>[]) new List[info.nonoPicRowSize];
		colNonoNums = (List<NonoNum>[]) new List[info.nonoPicColSize];
		nonoPicArr = array;
		backgroundColor = bgColor;
		puzzleInfo = info;
		for(int i=0; i<rowNonoNums.length; i++) {
			rowNonoNums[i] = new LinkedList<NonoNum>();
		}
		for(int i=0; i<colNonoNums.length; i++) {
			colNonoNums[i] = new LinkedList<NonoNum>();
		}
	}
	
	
	/**
	 * Creates and returns a NonoPuzzle object using given parameters.
	 * @param array Array that represents the picture portion of NonoPuzzle.
	 * @param bgColor Background color of the NonoPuzzle to be created
	 * @param name Name of the NonoPuzzle. Null if no name specified.
	 * @return A NonoPuzzle created using given parameters.
	 * @throws IllegalArgumentException if given array is null.
	 */
	public static NonoPuzzle createNonoPuzzle(Integer[][] array, Integer bgColor, String name) {
		ParameterPolice.checkIfNull(array, "Color array");
		Difficulty difficulty = Difficulty.getDifficulty(array.length, array[0].length);
		PuzzleInfo info = new PuzzleInfo(ID_COUNTER ++, name, difficulty, array.length, array[0].length, 0, 0);
		NonoPuzzle puzzle = new NonoPuzzle(array, bgColor, info);
		
		int maxNonoNumRowSize = 0;
		for(int i=0; i<puzzle.getNonoPicRowSize(); i++) { //TODO: factor out & clean up redundancy
			int blockLength = 1;
			for(int j=0; j<puzzle.getNonoPicColSize()-1; j++) {
				if(!array[i][j].equals(bgColor)) {
					if(array[i][j].equals(array[i][j+1])) {
						blockLength ++;
					}else{
						puzzle.rowNonoNums[i].add(new NonoNum(blockLength, array[i][j]));
						blockLength = 1;
					}
				}
			}
			maxNonoNumRowSize = Math.max(maxNonoNumRowSize, puzzle.rowNonoNums[i].size());
		}
		
		int maxNonoNumColSize = 0;
		for(int j=0; j<puzzle.getNonoPicColSize(); j++) {
			int blockLength = 1;
			for(int i=0; i<puzzle.getNonoPicRowSize()-1; i++) {
				if(!array[i][j].equals(bgColor)) {
					if(array[i][j].equals(array[i+1][j])) {
						blockLength ++;
					}else{
						puzzle.colNonoNums[j].add(new NonoNum(blockLength, array[i][j]));
						blockLength = 1;
					}
				}
			}
			maxNonoNumColSize = Math.max(maxNonoNumColSize, puzzle.colNonoNums[j].size());
		}
		
		info.nonoNumRowSize = maxNonoNumRowSize;
		info.nonoNumColSize = maxNonoNumColSize;
		
		return puzzle;
	}
	
	
	
	//--Getter Methods------------------------------------------------------------------------
	
	/**
	 * Returns an Iterator that loops through all NonoNums associated with given row.
	 * @param row The row index of NonoPuzzle.
	 * @return Iterator that loops through all NonoNums associated with given row.
	 * @throws IllegalArgumentException if given index is out of range, [0, NonoPicRowSize)
	 */
	public Iterator<NonoNum> getRowNonoNumItrator(int row) {
		ParameterPolice.checkIfWithinRange(row, 0, getNonoPicRowSize());
		return rowNonoNums[row].iterator();
	}
	
	/**
	 * Returns an Iterator that loops through all NonoNums associated with given column.
	 * @param col The column index of NonoPuzzle.
	 * @return Iterator that loops through all NonoNums associated with given column.
	 * @throws IllegalArgumentException if given index is out of range, [0, NonoPicColSize)
	 */
	public Iterator<NonoNum> getcolNonoNumItrator(int col) {
		ParameterPolice.checkIfWithinRange(col, 0, getNonoPicColSize());
		return colNonoNums[col].iterator();
	}
	
	/**
	 * Returns a unique ID of this NonoPuzzle.
	 * @return ID of this NonoPuzzle.
	 */
	public int getPuzzleID() {
		return puzzleInfo.puzzleID;
	}
	
	/**
	 * Returns name of this NonoPuzzle, null if it has no name.
	 * @return Name of this NonoPuzzle
	 */
	public String getPuzzleName() {
		return puzzleInfo.puzzleName;
	}

	/**
	 * Returns the Difficulty of this NonoPuzzle. Difficulty is
	 * classified according to the dimension of NonoPuzzle.
	 * @return Difficulty of this NonoPuzzle.
	 */
	public Difficulty getDifficulty() {
		return puzzleInfo.difficulty;
	}
	
	/**
	 * @return Number of rows in NonoPuzzle array (array representing picture).
	 */
	public int getNonoPicRowSize() {
		return puzzleInfo.nonoPicRowSize;
	}
	
	/**
	 * @return Number of columns in NonoPuzzle array (array representing picture).
	 */
	public int getNonoPicColSize() {
		return puzzleInfo.nonoPicColSize;
	}
	
	/**
	 * @return The maximum number of NonoNums for the row. 
	 */
	public int getNonoNumRowSize() {
		return puzzleInfo.nonoNumRowSize;
	}
	
	/**
	 * @return The maximum number of NonoNums for the column.
	 */
	public int getNonoNumColSize() {
		return puzzleInfo.nonoNumColSize;
	}
	
	/**
	 * Returns an Integer that represents Background color of this NonoPuzzle.
	 * @return Background color of this NonoPuzzle.
	 */
	public Integer getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * @param row The row index of the cell.
	 * @param col The column index of the cell.
	 * @return Color of the cell in given coordinate [row][col].
	 * @throws IllegalArgumentException if given indexes are out of range, 
	 *         [0, NonoPicRowSize) and [0, NonoPicColSize).
	 */
	public Integer getColor(int row, int col) {
		ParameterPolice.checkIfWithinRange(row, 0, getNonoPicRowSize());
		ParameterPolice.checkIfWithinRange(col, 0, getNonoNumColSize());
		return nonoPicArr[row][col];
	}
	
	/**
	 * @param row The row index of the cell.
	 * @param col The column index of the cell.
	 * @param color The color to be compared to the color in the specified cell.
	 * @return Boolean that tells if the color of the cell in given 
	 *         coordinate [row][col] is same as the given color.
	 * @throws IllegalArgumentException if given indexes are out of range, 
	 *         [0, NonoPicRowSize) and [0, NonoPicColSize).
	 */
	public boolean isSameColor(int row, int col, Integer color) {
		ParameterPolice.checkIfWithinRange(row, 0, getNonoPicRowSize());
		ParameterPolice.checkIfWithinRange(col, 0, getNonoNumColSize());
		return nonoPicArr[row][col].equals(color);
	}
	
	/**
	 * @param color A color to be checked if it is background color.
	 * @return A boolean that tells if the given color is background color of the NonoPuzzle.
	 */
	public boolean isBackgroundColor(Integer color) {
		return backgroundColor.equals(color);
	}
	
	/**
	 * Returns string representation of the NonoPuzzle.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("I'm a nonopuzzle!\n");
		for(int i=0; i<getNonoPicRowSize(); i++) {
			for(int j=0; j<getNonoPicColSize(); j++) {
				sb.append(nonoPicArr[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	
	//--Nested classes------------------------------------------------------------------------
	
	/**
	 * NonoNum ties a number and color together. The number tells the length of 
	 * contiguous marked block in NonoPuzzle, and the color is color of contiguous block.
	 */
	public static class NonoNum implements Serializable {
		private static final long serialVersionUID = 1L;
		public final int number;
		public final Integer color;
		
		/**
		 * Constructs a new NonoNum object
		 * @param number A Number that tells length of contiguous marked block in NonoPuzzle
		 * @param color Color of the contiguous block
		 * @throws IllegalArgumentException if given number is not positive
		 */
		public NonoNum(int number, Integer color) {
			ParameterPolice.checkIfNotPositive(number);
			this.number = number;
			this.color = color;
		}
	}
	
	// Stores Information about a NonoPuzzle, so that the NonoPuzzle class won't have million fields.
	private static class PuzzleInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		private int puzzleID;
		private String puzzleName;
		private Difficulty difficulty;
		private int nonoPicRowSize;
		private int nonoPicColSize;
		private int nonoNumRowSize;
		private int nonoNumColSize;
		
		// Constructs a PuzzleInfo object
		public PuzzleInfo(int id, String name, Difficulty difficulty, int picRow, int picCol, int numRow, int numCol) {
			this.puzzleID = id;
			this.puzzleName = name;
			this.difficulty = difficulty;
			this.nonoPicRowSize = picRow;
			this.nonoPicColSize = picCol;
			this.nonoNumRowSize = numRow;
			this.nonoNumColSize = numCol;
		}
	}

}
