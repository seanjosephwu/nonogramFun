/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim (co-author Sean Wu)
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.nonogram;
import java.sql.*;
import java.util.*;
import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.utility.NonoUtil;


/**
 * NonoDatabase manages all database related functionalities, such as
 * inserting data into table and getting data from table, using PostGres SQL.
 */
public class NonoDatabase {
	
	/* Connection related string constants */
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String USER_ID = "fcwlxdlohwgjtp";
	private static final String PASSWORD = "0xpGsFrLQgSJfp-4l9GE8HXdH-";
	private static final String SSL = "true";
	private static final String SSL_FACTORY = "org.postgresql.ssl.NonValidatingFactory";
	private static final String DB_URL = "jdbc:postgresql://ec2-54-225-101-126.compute-1.amazonaws.com:5432/d64in60h3dpvf2";
	private static final String URL = DB_URL + "?" + "user=" + USER_ID + "&" + "password=" + PASSWORD + "&" + 
			                         "ssl=" + SSL + "&" + "sslfactory=" + SSL_FACTORY;
	
	/* Database table related string constants */
	private static final String PUZZLE_TABLE = "puzzles";
	private static final String PUZZLE_ID = "id";
	private static final String PUZZLE_DIFFICULTY = "difficulty";
	private static final String PUZZLE_OBJECT = "puzzle";
	private static final String SCORE_TABLE = "scores";
	private static final String SCORE = "score";
	
	// Private constructor
	private NonoDatabase() {}
	
	
	// Creates and returns a connection to the database
	private static Connection getConnection() throws Exception {
        Class.forName(DRIVER).newInstance();
        Properties props = new Properties();
        props.setProperty("user", USER_ID);
        props.setProperty("password", PASSWORD);
        props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(URL, props);
      	return conn;
	}
	
	
	/**
	 * Returns a NonoPuzzle object with the given ID. Returns null if no such puzzle exists.
	 * @param puzzleID id of a NonoPuzzle object
	 * @return A NonoPuzzle object
	 * @throws Exception if connection or database problem occurs
	 */
	public static NonoPuzzle getPuzzle(int puzzleID) throws Exception { 
		Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
		
		String sql = " SELECT " + PUZZLE_OBJECT + 
				     " FROM   " + PUZZLE_TABLE  + 
				     " WHERE  " + PUZZLE_ID     + " = ?";
		
		try {
			// 1. Get connection & set up SQL statement
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, puzzleID);
			
			// 2. Execute statement & get result
			rs = ps.executeQuery();  rs.next();
			if (!rs.isFirst()) { return null; }
			NonoPuzzle puzzle = (NonoPuzzle) NonoUtil.byteToObject(rs.getBytes(PUZZLE_OBJECT)); 
			return puzzle;
		} catch (Exception e) {
			throw e;
		} finally {
			closeResources(conn, ps, rs);
		}
	}
	
	
	/**
	 * Accepts a NonoPuzzle object and saves it into the database.
	 * @param puzzle A NonoPuzzle object to be stored in database
	 * @throws Exception if connection or database problem occurs
	 */
	public static void savePuzzle(NonoPuzzle puzzle) throws Exception {
		Connection conn = null; PreparedStatement ps = null;
		
		String sql = " INSERT INTO " + PUZZLE_TABLE +
				     " VALUES (?, ?, ?)";
		
		try {
			// 1. Get connection & set up SQL statement
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, puzzle.getPuzzleID());
			ps.setString(2, puzzle.getDifficulty().toString());
			ps.setObject(3, NonoUtil.objecToByte(puzzle));
			
			// 2. Execute statement 
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeResources(conn, ps, null);
		}
	}
	
	
	/**
	 * Accepts Difficulty of a NonoPuzzle and returns List of puzzleIDs of 
	 * all NonoPuzzles in database with given difficulty.
	 * @param diff Difficulty of a NonoPuzzle
	 * @return List of puzzleIDs of all NonoPuzzles in database with given difficulty
	 * @throws Exception if connection or database problem occurs
	 */
	public static List<Integer> getPuzzleIDList(Difficulty diff) throws Exception {
		Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
		
		String sql = " SELECT " + PUZZLE_ID         +
				     " FROM   " + PUZZLE_TABLE      +
				     " WHERE  " + PUZZLE_DIFFICULTY + " = ?";
		
		try {
			// 1. Get connection & set up SQL statement
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, diff.toString());
			
			// 2. Execute statement & get result
			rs = ps.executeQuery();
			List<Integer> idList = new ArrayList<Integer>();
			while(rs.next()) { idList.add(rs.getInt(1)); }
			return idList;
		} catch (Exception e) {
			throw e;
		} finally {	
			closeResources(conn, ps, rs);
		}
	}

	
	/**
	 * Returns a list that represents score board of the games with given difficulty.
	 * @param difficulty Difficulty of the games this score board will present.
	 * @return A list that represents score board of the given difficulty.
	 * @throws Exception if connection or database problem occurs.
	 */
	public static NonoScoreBoard getScoreBoard(Difficulty diff) throws Exception {
		Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
		
		String sql = " SELECT   " + "*"               +
				     " FROM     " + SCORE_TABLE       +
				     " WHERE    " + PUZZLE_DIFFICULTY + " = ?" +
				     " ORDER BY " + SCORE;
		
		try {
			// 1. Get connection & set up SQL statement
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, diff.toString());
			
			// 2. Execute statement & get result
			rs = ps.executeQuery();
			NonoScoreBoard scoreBoard = new NonoScoreBoard();
			while(rs.next()) { scoreBoard.add(new NonoScore(rs.getString(1), rs.getString(2), rs.getInt(3))); }
			return scoreBoard;
		} catch (Exception e) {
			throw e;
		} finally {	
			closeResources(conn, ps, rs);
		}
	}
	
	
	/**
	 * Saves the score for the given player.
	 * @param playerName Name of the player whose score is being saved. Can be null.
	 * @param difficulty Difficulty of the game.
	 * @param score Score of the game.
	 * @throws Exception if connection or database problem occurs
	 */
	public static void saveScore(String playerName, Difficulty difficulty, int score) throws Exception {
		Connection conn = null; PreparedStatement ps = null;
		
		String sql = " INSERT INTO " + SCORE_TABLE +
				     " VALUES (?, ?, ?)";
		
		try {
			// 1. Get connection & set up SQL statement
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, playerName);
			ps.setString(2, difficulty.toString());
			ps.setInt(3, score);
			
			// 2. Execute statement 
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeResources(conn, ps, null);
		}
	}
	
	
	/**
	 * Returns the maximum value of puzzle ID that is currently stored in database, 
	 * such that no puzzle with duplicate ID will be stored in the database.
	 * @return The maximum value of puzzle ID that is currently stored in database.
	 */
	public static int getStartPuzzleID() {
		Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
		
		String sql = " SELECT MAX(" + PUZZLE_ID     + ")" +
			         " FROM   "     + PUZZLE_TABLE;    

		try {
			// 1. Get connection & set up SQL statement
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			
			// 2. Execute statement & get result
			rs = ps.executeQuery();  rs.next(); 
			if (!rs.isFirst()) { return 0; }
			return rs.getInt(1) + 1;
		} catch (Exception e) {
			return 0;
		} finally {
			closeResources(conn, ps, null);
		}
	}
	
	
	// Closes all the resources used for database operations.
	private static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)   { rs.close();   }
			if (conn != null) { conn.close(); }
			if (ps != null)   { ps.close();   }
		}catch(SQLException e ) {
			System.out.println("Error: Problem in closing rsources! \n");
			e.printStackTrace();
		}
	}
	
	
	
	
	// TODO: Remove later. For connection testing
	public static void main(String[] args) throws Exception {
		System.out.println("MySQL Connect Example.");
		NonoPuzzle puzzle = getPuzzle(1);
		System.out.println(puzzle);
		System.out.println("Connected to the database");
	}
}

