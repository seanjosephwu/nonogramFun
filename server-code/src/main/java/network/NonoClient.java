/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.network;
import java.io.IOException;
import org.json.JSONObject;
import uw.cse403.nonogramfun.enums.*;
import uw.cse403.nonogramfun.nonogram.*;
import uw.cse403.nonogramfun.utility.*;


/**
 * NonoClient is a client side of Networking code. It allows android client to communicate 
 * with server without knowing messy networking details, by providing simple interface.
 * Each method tries MAX_PORT ports to connect, because sometimes certain ports doesn't work.
 */
public class NonoClient {
	private static NonoNetwork mockNet = null;
	
	// Private constructor
	private NonoClient() {}
	
	
	/**
	 * Accepts a 2D array represents a NonoPuzzle, background color and name of NonoPuzzle
	 * as parameters. Creates NonoPuzzle according to the given parameters and saves it in the database.
	 * @param cArray Color array represents a NonoPuzzle
	 * @param bgColor Background color of NonoPuzzle
	 * @param name Name of NonoPuzzle
	 * @throws Exception if any error occurs (Network error, invalid puzzle, database error ect.)
	 */
	public static void createPuzzle(Integer[][] cArray, Integer bgColor, String name) throws Exception {
		ParameterPolice.checkIfValid2DArray(cArray);
		int port = 0; boolean success = false;
		
		while(!success && port < NonoConfig.MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = NonoConfig.getNonoNetwork(port, mockNet);
				JSONObject requestJSON = new JSONObject();
				NonoUtil.putClientRequest(requestJSON, ClientRequest.CREATE_PUZZLE);
				NonoUtil.putColorArray(requestJSON, cArray);
				NonoUtil.putColor(requestJSON, bgColor);
				NonoUtil.putString(requestJSON, name);
				network.sendMessage(requestJSON);
				success = true;
				
				// 2. Get server response and check if it is success or error
				JSONObject responseJSON = network.readMessageJSON();
				checkResponseError(responseJSON);
				network.close();
			}catch(Exception e) {
				if(!success && port < NonoConfig.MAX_PORT) { port ++; }else{ throw e; }
			}
		}
	}
	
	
	/**
	 * Accepts a difficulty and returns a randomly chosen NonoPuzzle of given difficulty.
	 * @param difficulty Difficulty of NonoPuzzle
	 * @return A random NonoPuzzle of given difficulty
	 * @throws Exception if any error occurs (Network error, invalid puzzle, database error ect.)
	 */
	public static NonoPuzzle getPuzzle(Difficulty difficulty) throws Exception {
		ParameterPolice.checkIfNull(difficulty, "Difficulty");
		int port = 0; boolean success = false;
		
		while(!success && port < NonoConfig.MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = NonoConfig.getNonoNetwork(port, mockNet);
				JSONObject requestJSON = new JSONObject();
				NonoUtil.putClientRequest(requestJSON, ClientRequest.GET_PUZZLE);
				NonoUtil.putDifficulty(requestJSON, difficulty);
				network.sendMessage(requestJSON);
				success = true;
				
				// 2. Get server response and check if it is success or error
				JSONObject responseJSON = network.readMessageJSON();
				checkResponseError(responseJSON);
				NonoPuzzle ret = NonoUtil.getNonoPuzzle(responseJSON); 
				network.close();
				return ret;
			}catch(Exception e) {
				if(!success && port < NonoConfig.MAX_PORT) { port ++; }else{ throw e; }
			}
		}
		return null;
	}
	
	
	/**
	 * Saves the score for the given player.
	 * @param playerName Name of the player whose score is being saved. Can be null.
	 * @param difficulty Difficulty of the game.
	 * @param score Score of the game.
	 * @throws Exception if any error occurs (Network error, invalid score, database error etc.)
	 */
	public static void saveScore(String playerName, Difficulty difficulty, int score) throws Exception {
		ParameterPolice.checkIfNull(difficulty, "Difficulty");
		ParameterPolice.checkIfNegative(score);
		int port = 0; boolean success = false;
		
		while(!success && port < NonoConfig.MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = NonoConfig.getNonoNetwork(port, mockNet);
				JSONObject requestJSON = new JSONObject();
				NonoUtil.putClientRequest(requestJSON, ClientRequest.SAVE_SCORE);
				NonoUtil.putString(requestJSON, playerName);
				NonoUtil.putDifficulty(requestJSON, difficulty);
				NonoUtil.putScore(requestJSON, score);
				network.sendMessage(requestJSON);
				success = true;
				
				// 2. Get server response and check if it is success or error
				JSONObject responseJSON = network.readMessageJSON();
				checkResponseError(responseJSON);
				network.close();
			}catch(Exception e) {
				if(!success && port < NonoConfig.MAX_PORT) { port ++; }else{ throw e; }
			}
		}
	}
	
	
	/**
	 * Returns a list that represents score board of the games with given difficulty.
	 * @param difficulty Difficulty of the games this score board will present.
	 * @return A list that represents score board of the given difficulty.
	 * @throws Exception if any error occurs (Network error, invalid difficulty, database error etc.)
	 */
	public static NonoScoreBoard getScoreBoard(Difficulty difficulty) throws Exception {
		ParameterPolice.checkIfNull(difficulty, "Difficulty");
		int port = 0; boolean success = false;
		
		while(!success && port < NonoConfig.MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = NonoConfig.getNonoNetwork(port, mockNet);
				JSONObject requestJSON = new JSONObject();
				NonoUtil.putClientRequest(requestJSON, ClientRequest.GET_SCORE_BOARD);
				NonoUtil.putDifficulty(requestJSON, difficulty);
				network.sendMessage(requestJSON);
				success = true;
				
				// 2. Get server response and check if it is success or error
				JSONObject responseJSON = network.readMessageJSON();
				checkResponseError(responseJSON);
				NonoScoreBoard ret = NonoUtil.getScoreBoard(responseJSON); 
				network.close();
				return ret;
			}catch(Exception e) {
				if(!success && port < NonoConfig.MAX_PORT) { port ++; }else{ throw e; }
			}
		}
		return null;
	}

	
	/**
	 * Method for mock testing
	 */
	public static void setNetwork(NonoNetwork network) {
		mockNet = network;
	}
	
	
	// Throws IOException if server responds with error message.
	private static void checkResponseError(JSONObject responseJSON) throws Exception {
		if(NonoUtil.getServerResponse(responseJSON) != ServerResponse.SUCCESS) {
			throw new IOException("Error: " + NonoUtil.getErrorMsg(responseJSON));
		}
	}

	
	
	
	
	
	// For testing  TODO: delete later
	@SuppressWarnings("unused")
	private static void testClient() throws Exception {
		while (true) {
			//NonoNetwork tcpHandler = new NonoNetwork(new Socket(NonoConfig.getServerIP(), 1027));
			System.out.println("I got a response puzzle from server!!");
			NonoScoreBoard lst = new NonoScoreBoard();
			lst.add(new NonoScore("hi", "EASY", 55));
			System.out.println("ADDING SCORE!!!" + lst);
			JSONObject json = new JSONObject();
			NonoUtil.putScoreBoard(json, lst);
			NonoScoreBoard result = NonoUtil.getScoreBoard(json);
			//NonoScoreBoard board = getScoreBoard(Difficulty.EASY);
			System.out.println("GETTING SCORE!!!" + result);
			//tcpHandler.close();
		}
	}
}
	
