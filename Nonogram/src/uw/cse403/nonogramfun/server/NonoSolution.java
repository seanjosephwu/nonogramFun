import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NonoSolution {
  
	private static Color backgroundColor;
	private static NonoPuzzle puzzle;
	private static int solutionNum;
	/**
	 * 
	 * @param puzzle
	 * @return true if there is only one unique 
	 * solution for the puzzle
	 */
	public static boolean isSolvable(NonoPuzzle puz) {
		solutionNum = 0; // number of solutions
		puzzle = puz;
		backgroundColor = puzzle.getBackgroundColor();
		if(!testCols()) {
			return false;
		}
		ArrayList<ArrayList<Color[]>> rowsSatisfied = foundRows();
		if (rowsSatisfied == null) {
			return false;
		}
		fitCols(rowsSatisfied);
		return solutionNum == 1;
	}
	
	/**
	 * 
	 * @return an ArrayList that contains all possibilities of rows,
	 * null if certain this is a bad pic (solutions not exist)
	 */
	private static ArrayList<ArrayList<Color[]>> foundRows() {
		ArrayList<ArrayList<Color[]>> list = new ArrayList<ArrayList<Color[]>>();
		int picRowSize = puzzle.getNonoPicRowSize();
		for (int i = 0; i < puzzle.getNonoPicRowSize(); i++) {
			ArrayList<Color[]> row = getRowPos(puzzle.getRowNonoNumItrator(i), picRowSize);
			if (row == null) {
				return null;
			} else {
				list.add(row);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param it
	 * @return an ArrayList contains all possibilities of a row
	 * null if certain this is a bad row (solution not exists)
	 */
	private static ArrayList<Color[]> getRowPos(Iterator<NonoPuzzle.NonoNum> it, int size) {
		ArrayList<Color[]> list = new ArrayList<Color[]>();
		ArrayList<Integer> rowNum = new ArrayList<Integer>();
		int numSum = 0;
		while (it.hasNext()) {
			int num = it.next().number;
			rowNum.add(num);
			numSum += num;
		}
		int freeSpace = size - (numSum + rowNum.size() - 1);
		// impossible to create such row
		if (freeSpace < 0) {
			return null;
		}
		
		// TODO: find all possibilities of a row and add into list
		
		return list;
	}

	/**
	 * 
	 * @param rowSatisfied
	 * @effect update solutionNum
	 */
	private static void fitCols(ArrayList<ArrayList<Color[]>> rows) {
		// TODO: find all solutions fit, update solutionNum
	}
	
	/**
	 * 
	 * @return true if all columns are posible to be create by itself
	 */
	private static boolean testCols() {
		for (int i = 0; i < puzzle.getNonoPicColSize(); i++) {
			Iterator<NonoPuzzle.NonoNum> it = puzzle.getcolNonoNumItrator(i);
			int numSum = -1;
			while (it.hasNext()) {
				numSum += it.next().number;
			}
			if (numSum > puzzle.getNonoPicColSize()) {
				return false;
			}
		}
		return true;
	}
}
