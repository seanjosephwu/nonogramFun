/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim 
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.network;
import java.net.*;
import org.json.JSONObject;
import uw.cse403.nonogramfun.enums.*;
import uw.cse403.nonogramfun.nonogram.Nonogram;
import uw.cse403.nonogramfun.utility.*;


/**
 * NonoServer is a server that accepts and processes client requests.
 * It has a main method that runs the server and a method that tells status of a server socket.
 */
public class NonoServer {
	private static final String SERVER_IP = NonoConfig.getServerIP();
	private static boolean STUB = false;
	
	// Private constructor
	private NonoServer() {}
	
	/**
	 * If true, use stub class for network. If false, use actual network.
	 */
	public static void setStub(boolean stub) { STUB = stub; }

	/**
	 * The main method that runs this server
	 * @throws Exception if any error occurs running server or processing client request.
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Running NonoServer");
		runServer();
	}
	
	/**
	 * Accepts a server socket and returns a string summarizing its status. 
	 * @param serverSock A server socket whose status is going to be summarized.
	 * @return a string summarizing the status of given server socket.
	 */
	public static String dumpServerState(ServerSocket serverSock) {
		ParameterPolice.checkIfNull(serverSock, "Server Socket");
		StringBuilder sb = new StringBuilder();
		if(serverSock != null){
			sb.append("\nListening on: " + serverSock.toString());
		}else{
			sb.append("Not listening");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	// Runs the server, by opening the ports
	private static void runServer() throws Exception {
		if (SERVER_IP == null) { throw new Exception("Error: Server cannot find the IP address!"); }
		for(int i=0; i<NonoConfig.MAX_PORT; i++) {
			openPort(i);
		}
	}
		
	// Opens a port
	private static synchronized void openPort(final int portID) {
		Thread tcpThread = new Thread(){
			@Override
			public void run() {
				ServerSocket serverSock = null;
				try {
					// 1. Set up a server socket for this port
					serverSock = new ServerSocket();
					serverSock.bind(new InetSocketAddress(NonoConfig.SERVER_NAME, NonoConfig.BASE_PORT + portID));
					serverSock.setSoTimeout(NonoConfig.SOCKET_TIMEOUT);
					System.out.println(dumpServerState(serverSock));
					
					// 2. Wait for connection & process request. Retry if times out.
					while(true) {
						Network network = null;
						try {
							network = NonoConfig.getNetwork(serverSock.accept(), STUB);
							JSONObject requestJSON = network.readMessageJSON();
							JSONObject responseJSON = processRequest(requestJSON);
							network.sendMessage(responseJSON);
						}catch(SocketTimeoutException ste) {
							System.out.println("Socket timed out");
						}catch(Exception e) {
							sendErrorJSON(network, e);
						}finally{
							if(network != null) try { network.close(); network = null; }catch(Exception e) {}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(serverSock != null) { try { serverSock.close(); }catch(Exception e) {} }
				}
			}
		};
		tcpThread.start();
	}
	
	
	// Accepts a JSON Object that represents a client request, processes the request and 
	// returns a JSON Object that represents a server response to the client request.
	private static JSONObject processRequest(JSONObject requestJSON) throws Exception {
		ClientRequest request = ClientRequest.TEST;
		if(!STUB) { request = NonoUtil.getClientRequest(requestJSON); }
		JSONObject responseJSON = new JSONObject();
		if(!STUB) { NonoUtil.putServerResponse(responseJSON, ServerResponse.SUCCESS); }
		switch (request) {
			case CREATE_PUZZLE:
				Nonogram.createPuzzle(requestJSON);
				break;
			case GET_PUZZLE:
				NonoUtil.putNonoPuzzle(responseJSON, Nonogram.getPuzzle(requestJSON));
				break;
			case SAVE_SCORE:
				Nonogram.saveScore(requestJSON);
				break;
			case GET_SCORE_BOARD:
				NonoUtil.putScoreBoard(responseJSON, Nonogram.getScoreBoard(requestJSON));
				break;
			case TEST:
				responseJSON.put("TEST", request);
				break;
			default:
				throw new UnsupportedOperationException();
		}
		return responseJSON;
	}
	
	
	// When error occurs while processing client request, sends the error to client
	private static void sendErrorJSON(Network network, Exception e) {
		try {
			e.printStackTrace();
			JSONObject errorJSON = new JSONObject();
			NonoUtil.putServerResponse(errorJSON, ServerResponse.ERROR);
			NonoUtil.putObject(errorJSON, NonoUtil.JSON_ERROR_MSG_TAG, e.getStackTrace());
			network.sendMessage(errorJSON);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
