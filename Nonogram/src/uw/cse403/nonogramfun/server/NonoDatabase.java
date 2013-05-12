package server;
/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim, Sean Wu
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import java.io.*;
import java.sql.*;
import java.util.*;
import enums.*;

/**
 * 
 *
 */
public class NonoDatabase {
	public static final String DB_URL = "jdbc:mysql://82.197.130.17:5432/d64in60h3dpvf2";
	public static final String DRIVER = "org.postgresql.Driver";
	public static final String USER_ID = "fcwlxdlohwgjtp";
	public static final String PASSWORD = "uwcse403nonogram";
	public static final String url = "jdbc:postgresql://ec2-54-225-101-126.compute-1.amazonaws.com:5432/d64in60h3dpvf2?user=fcwlxdlohwgjtp&password=0xpGsFrLQgSJfp-4l9GE8HXdH-&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	
	private static Connection getConnection() {
      try {
        Class.forName(DRIVER).newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e1) {
        e1.printStackTrace();
      } catch (ClassNotFoundException e2) {
        e2.printStackTrace();
      }
      Properties props = new Properties();
      props.setProperty("user","fcwlxdlohwgjtp");
      props.setProperty("password","0xpGsFrLQgSJfp-4l9GE8HXdH-");
      props.setProperty("ssl","true");
      Connection conn = null;
      try {
        conn = DriverManager.getConnection(url, props);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     // Connection conn = DriverManager.getConnection(url);  
     // Connection conn = DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);
      System.out.println("Connected to the database");
    return conn;
	}
	
	public static NonoPuzzle getPuzzle(int puzzleID) throws SQLException, ClassNotFoundException, IOException {
		Connection conn = getConnection();
		String sql = "SELECT puzzle " +
				     "FROM   puzzles " +
				     "WHERE  id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, puzzleID);
		ResultSet rs = ps.executeQuery();
		if (!rs.isAfterLast()) {
			rs.next();
		}
		NonoPuzzle puzzle = (NonoPuzzle) NonoUtil.byteToObject(rs.getBytes(1));
		rs.close();
		conn.close();
		return puzzle;
	}
	
	public static void savePuzzle(NonoPuzzle puzzle) throws SQLException, IOException {
		Connection conn = getConnection();
		String sql = "INSERT INTO Puzzles " +
				     "VALUES (?, ?, ?)";
		System.out.println(puzzle);
		System.out.println(puzzle.getPuzzleID());
		System.out.println(puzzle.getDifficulty());
		System.out.println(NonoUtil.objecToByte(puzzle));
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, puzzle.getPuzzleID());
		
		ps.setString(2, puzzle.getDifficulty().toString());
		ps.setObject(3, NonoUtil.objecToByte(puzzle));
		System.out.println(ps.toString());
		ps.executeUpdate();
		conn.close();
	}
	
	public static List<Integer> getPuzzleIDList(Difficulty diff) throws SQLException {
		Connection conn = getConnection();
		String sql = "SELECT id " +
				     "FROM puzzles " +
				     "WHERE difficulty = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, diff.toString());
		ResultSet rs = ps.executeQuery();
		List<Integer> idList = new ArrayList<Integer>();
		while(rs.next()) {
			idList.add(rs.getInt(1));
		}
		rs.close();
		conn.close();
		return idList;
	}

	// For connection testing
	public static void main(String[] args) throws InstantiationException, SQLException, IllegalAccessException, ClassNotFoundException {
    System.out.println("MySQL Connect Example.");
    Class.forName(DRIVER).newInstance();
    Properties props = new Properties();
    props.setProperty("user","fcwlxdlohwgjtp");
    props.setProperty("password","0xpGsFrLQgSJfp-4l9GE8HXdH-");
    props.setProperty("ssl","true");
    Connection conn = DriverManager.getConnection(url, props);
   // Connection conn = DriverManager.getConnection(url);  
   // Connection conn = DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);
    System.out.println("Connected to the database");
    conn.close();
  }
}
