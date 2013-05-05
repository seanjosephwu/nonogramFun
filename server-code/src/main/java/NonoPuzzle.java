/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim, Sean Wu
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import android.graphics.Color;
import java.io.Serializable;
import java.util.*;

import enums.Difficulty;

/**
 * 
 *
 */
public class NonoPuzzle implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int ID_COUNTER = 0;
	private List<NonoNum>[] rowNonoNums;
	private List<NonoNum>[] colNonoNums;
	private Integer[][] nonoPicArr;
	private Integer backgroundColor;
	private PuzzleInfo puzzleInfo;
	
	//
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
	
	public static NonoPuzzle createNonoPuzzle(Integer[][] array, Integer bgColor, String name) {
    PuzzleInfo info = new PuzzleInfo(ID_COUNTER ++, name, findDifficulty(array), array.length, array[0].length, 0, 0);
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
	
	private static Difficulty findDifficulty(Integer[][] array) {
    if ( array.length <= 5 ) {
      return Difficulty.EASY;
    } else if ( array.length <= 10 ) {
      return Difficulty.MEDIUM;
    } else if ( array.length <= 15 ) {
      return Difficulty.HARD;
    } else if ( array.length <= 20) {
      return Difficulty.INSANE;
    } else {
      return Difficulty.UNKNOWN;
    }
		//return null; //TODO: categorize into difficulty given dimention
	}
	
	public Iterator<NonoNum> getRowNonoNumItrator(int row) {
		return rowNonoNums[row].iterator();
	}
	
	public Iterator<NonoNum> getcolNonoNumItrator(int col) {
		return colNonoNums[col].iterator();
	}
	
	public int getPuzzleID() {
		return puzzleInfo.puzzleID;
	}
	
	public String getPuzzleName() {
		return puzzleInfo.puzzleName;
	}

	public Difficulty getDifficulty() {
		return puzzleInfo.difficulty;
	}
	
	public int getNonoPicRowSize() {
		return puzzleInfo.nonoPicRowSize;
	}
	
	public int getNonoPicColSize() {
		return puzzleInfo.nonoPicColSize;
	}
	
	public int getNonoNumRowSize() {
		return puzzleInfo.nonoNumRowSize;
	}
	
	public int getNonoNumColSize() {
		return puzzleInfo.nonoNumColSize;
	}
	
	public Integer getBackgroundColor() {
		return backgroundColor;
	}
	
	public boolean isBackgroundColor(Integer color) {
		return backgroundColor.equals(color);
	}
	
	public Integer getColor(int row, int col) {
		return nonoPicArr[row][col];
	}
	
	public boolean isSameColor(int row, int col, Integer color) {
		return nonoPicArr[row][col].equals(color);
	}
	
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
	
	public static class NonoNum implements Serializable {
		private static final long serialVersionUID = 1L;
		public final int number;
		public final Integer color;
		
		public NonoNum(int number, Integer color) {
			this.number = number;
			this.color = color;
		}
	}
	
	private static class PuzzleInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		private int puzzleID;
		private String puzzleName;
		private Difficulty difficulty;
		private int nonoPicRowSize;
		private int nonoPicColSize;
		private int nonoNumRowSize;
		private int nonoNumColSize;
		
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
