/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.network;
import java.net.Socket;
import java.util.List;

import org.json.JSONObject;
import uw.cse403.nonogramfun.enums.ClientRequest;
import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.enums.ServerResponse;
import uw.cse403.nonogramfun.nonogram.NonoPuzzle;
import uw.cse403.nonogramfun.nonogram.NonoScore;
import uw.cse403.nonogramfun.utility.NonoUtil;
import uw.cse403.nonogramfun.utility.ParameterPolice;
import android.graphics.Color;


/**
 * NonoClient is a client side of Networking code. It allows android client to communicate 
 * with server without knowing messy networking details, by providing simple interface.
 */
public class NonoClient {
	private static final int MAX_PORT = 10;
	private static NonoNetwork mockNet = null;
	
	// Private constructor
	private NonoClient() {}
	//TODO: Move mockNet into NonoConfig?
	
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
		
		int port = 0;
		boolean success = false;
		
		while(!success && port < MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = null;
				if (mockNet != null) {
					network = mockNet;
				} else {
					network = NonoConfig.getNonoNetwork(port);
				}
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
				if(!success && port < MAX_PORT) {
					port ++;
				}else{
					throw e;
				}
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
		
		int port = 0;
		boolean success = false;
		
		while(!success && port < MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = null;
				if (mockNet != null) {
					network = mockNet;
				} else {
					network = NonoConfig.getNonoNetwork(port);
				}
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
				if(!success && port < MAX_PORT) {
					port ++;
				}else{
					throw e;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param playerName
	 * @param difficulty
	 * @param score
	 * @throws Exception
	 */
	public static void saveScore(String playerName, Difficulty difficulty, int score) throws Exception {
		ParameterPolice.checkIfNull(difficulty, "Difficulty");
		
		int port = 0;
		boolean success = false;
		
		while(!success && port < MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = null;
				if (mockNet != null) {
					network = mockNet;
				} else {
					network = NonoConfig.getNonoNetwork(port);
				}
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
				if(!success && port < MAX_PORT) {
					port ++;
				}else{
					throw e;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param difficulty
	 * @return
	 * @throws Exception
	 */
	public static List<NonoScore> getScoreBoard(Difficulty difficulty) throws Exception {
		ParameterPolice.checkIfNull(difficulty, "Difficulty");
		
		int port = 0;
		boolean success = false;
		
		while(!success && port < MAX_PORT) {
			try {
				// 1. Send request: Create & Save puzzle in database
				NonoNetwork network = null;
				if (mockNet != null) {
					network = mockNet;
				} else {
					network = NonoConfig.getNonoNetwork(port);
				}
				JSONObject requestJSON = new JSONObject();
				NonoUtil.putClientRequest(requestJSON, ClientRequest.GET_SCORE_BOARD);
				NonoUtil.putDifficulty(requestJSON, difficulty);
				network.sendMessage(requestJSON);
				success = true;
				
				// 2. Get server response and check if it is success or error
				JSONObject responseJSON = network.readMessageJSON();
				checkResponseError(responseJSON);
				List<NonoScore> ret = NonoUtil.getScoreBoard(responseJSON); 
				network.close();
				return ret;
			}catch(Exception e) {
				if(!success && port < MAX_PORT) {
					port ++;
				}else{
					throw e;
				}
			}
		}
		return null;
	}

	public static void setNetwork(NonoNetwork network) {
		mockNet = network;
	}
	
	
	// Throws IOException if server responds with error message.
	private static void checkResponseError(JSONObject responseJSON) throws Exception {
		if(NonoUtil.getServerResponse(responseJSON) != ServerResponse.SUCCESS) {
			//throw new IOException("Error: " + NonoUtil.getErrorMsg(responseJSON));
		}
	}

	// For testing  TODO: delete later
	@SuppressWarnings("unused")
	private static void testClient() throws Exception {
		while (true) {
			NonoNetwork tcpHandler = new NonoNetwork(new Socket(NonoConfig.getServerIP(), 1027));
			//tcpHandler.sendMessage("Hi, Server. I'm Client!ooooooooooooooooooo");
			//String response = tcpHandler.readMessageString();
			//System.out.println("Server said: " + response);
			
			Integer[][] arr = {{ Color.BLACK, Color.BLACK }, { Color.BLUE, Color.BLUE } };
			//tcpHandler.sendMessage(arr);
			//NonoPuzzle puzzle = (NonoPuzzle) tcpHandler.readMessageObject();
			System.out.println("I got a response puzzle from server!!");
			//System.out.println(puzzle);
			tcpHandler.close();
		}
	}
}
	
