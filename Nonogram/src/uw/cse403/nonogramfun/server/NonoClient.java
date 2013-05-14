package uw.cse403.nonogramfun.server;
/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim, Sean Wu
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import android.graphics.Color;
import java.io.*;
import java.net.*;
import org.json.*;
import uw.cse403.nonogramfun.enums.*;

/**
 * 
 *
 */
public class NonoClient {
	private static final int BASE_PORT = 1024;
	//TODO: where should I check parameter validity? before sending? at server? or both?
	//TODO: what is the best way of getting port & serverIP in remote machine?
	//TODO: closing the network
	
	//
	private NonoClient() {}
	
	public static void createPuzzle(Integer[][] cArray, Integer bgColor, String name) throws UnknownHostException, IOException, JSONException {
		InetAddress inetAddress = InetAddress.getByName(NonoServer.NAME);
		System.out.println(inetAddress.getHostAddress());
		NonoNetwork network = new NonoNetwork(new Socket(inetAddress.getHostAddress(), BASE_PORT));
		JSONObject requestJSON = new JSONObject();
		NonoUtil.putClientRequest(requestJSON, ClientRequest.CREATE_PUZZLE);
		NonoUtil.putColorArray(requestJSON, cArray);
		NonoUtil.putColor(requestJSON, bgColor);
		NonoUtil.putString(requestJSON, name);
		network.sendMessage(requestJSON);
		
		JSONObject responseJSON = network.readMessageJSON();
		checkResponseError(responseJSON);
	}
	
	public static NonoPuzzle getPuzzle(Difficulty difficulty) throws UnknownHostException, IOException, JSONException {
		InetAddress inetAddress = InetAddress.getByName(NonoServer.NAME);
		System.out.println(inetAddress.getHostAddress());
		NonoNetwork network = new NonoNetwork(new Socket(inetAddress.getHostAddress(), BASE_PORT));
		JSONObject requestJSON = new JSONObject();
		NonoUtil.putClientRequest(requestJSON, ClientRequest.GET_PUZZLE);
		NonoUtil.putDifficulty(requestJSON, difficulty);
		network.sendMessage(requestJSON);
		
		JSONObject responseJSON = network.readMessageJSON();
		checkResponseError(responseJSON);
		return NonoUtil.getNonoPuzzle(responseJSON);
	}
	
	private static void checkResponseError(JSONObject responseJSON) throws IOException, JSONException {
		if(NonoUtil.getServerResponse(responseJSON) != ServerResponse.SUCCESS) {
			throw new IOException("Error: " + NonoUtil.getString(responseJSON));
		}
	}
	
	
	
	
	// For testing  TODO: delete later
	@SuppressWarnings("unused")
	private static void testClient() throws Exception {
		while (true) {
			NonoNetwork tcpHandler = new NonoNetwork(new Socket(NonoServer.SERVER_IP, 1027));
			//tcpHandler.sendMessage("Hi, Server. I'm Client!ooooooooooooooooooo");
			//String response = tcpHandler.readMessageString();
			//System.out.println("Server said: " + response);
			
			Integer[][] arr = {{ Color.BLACK, Color.BLACK }, { Color.WHITE, Color.WHITE } };
			tcpHandler.sendMessage(arr);
			NonoPuzzle puzzle = (NonoPuzzle) tcpHandler.readMessageObject();
			System.out.println("I got a response puzzle from server!!");
			System.out.println(puzzle);
			tcpHandler.close();
		}
	}
	// For testing  TODO: delete later
	private static void testJSON() throws Exception {
		System.out.println("I'm sending somthing to server!!");
		Integer[][] cArray = {{ Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE },
							{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE}, 
							{ Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE}, 
							{ Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE}, 
							{ Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE},
							{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE},
							{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE},
							{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE},
							{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE},
							{Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE , Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE}};
		Integer bgColor = Color.BLACK;
		createPuzzle(cArray, bgColor, "NOmmmmmmmmmmmmmmmmmmmmm");
		InetAddress inetAddress = InetAddress.getByName(NonoServer.NAME);
		System.out.println(inetAddress.getHostAddress());
		NonoNetwork network = new NonoNetwork(new Socket(inetAddress.getHostAddress(), BASE_PORT));
		JSONObject requestJSON = new JSONObject();
		NonoUtil.putClientRequest(requestJSON, ClientRequest.CREATE_PUZZLE);
		NonoUtil.putColorArray(requestJSON, cArray);
		NonoUtil.putColor(requestJSON, bgColor);
		NonoUtil.putString(requestJSON, "NONONONONONONONONONONO");
		System.out.println(requestJSON.getString(NonoUtil.JSON_STRING_TAG));
		network.sendMessage(requestJSON);
		
		JSONObject responseJSON = network.readMessageJSON();
		checkResponseError(responseJSON);
		System.out.println(responseJSON);
		String success = responseJSON.getString("JSON_Header");
		//ServerResponse puzzle = NonoUtil.getServerResponse(responseJSON);
		if (!success.equals("\"SUCCESS\"")) {
			System.out.println(success);
			System.out.println("Transaction failed");
		}
		System.out.println("I got a response puzzle from server!!");
		//System.out.println(puzzle);
		System.out.println("\n\n\n");
	}
	
	private static void testGetPuzzles() throws UnknownHostException, IOException, JSONException {
		System.out.println("I'm trying to get from the server!!");
		NonoPuzzle puzzle = getPuzzle(Difficulty.MEDIUM);
		System.out.println("Got Puzzle!");
		System.out.println(puzzle);
	}
	// For testing  TODO: delete later
	
	public static void main(String[] args) throws Exception {
		System.out.println("Running NonoClient");
		testJSON();
		testGetPuzzles();
		//testClient();
	}
	
}
