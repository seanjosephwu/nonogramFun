package server;
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
import enums.*;
import java.sql.SQLException;


/**
 * 
 *
 */
public class NonoServer {
	//public static final String SERVER_IP = IPFinder.localIP();
	public static String SERVER_IP;
	private static final int NUM_PORTS = 4;
	private static final int BASE_PORT = 1024;
	public static final String NAME = "cubist.cs.washington.edu";
	//public static final String NAME = "localhost";
	//
	private NonoServer() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(NAME);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		SERVER_IP = inetAddress.getHostAddress();
		
	}
	
	
	public static void runServer() throws Exception {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(NAME);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SERVER_IP = inetAddress.getHostAddress();
		System.out.println(SERVER_IP);
		if (SERVER_IP == null) {
			throw new Exception("IPFinder isn't providing the local IP address. Can't run.");
		}
		for(int i=0; i<NUM_PORTS; i++) {
			openPort(SERVER_IP, i);
		}
	}
	
	private static synchronized void openPort(final String serverIP, final int portID) {
		Thread tcpThread = new Thread(){
			@Override
			public void run() {
				ServerSocket serverSock = null;
				try {
					serverSock = new ServerSocket();
					serverSock.bind(new InetSocketAddress(NonoServer.NAME, BASE_PORT + portID));
					serverSock.setSoTimeout(NonoNetwork.SOCKET_TIMEOUT);
					System.out.println(dumpServerState(serverSock));
					while(true) {
						Socket connectionSock = null;
						NonoNetwork network = null;
						try {
							connectionSock = serverSock.accept();
							network = new NonoNetwork(connectionSock);
							processRequest(network);
							//testSerer(network);
						}catch(SocketTimeoutException ste) {
							System.out.println("Socket timed out");
						}catch(SQLException e) {
							sendErrorJSON(network, e);
            } catch (IOException e1) {
							sendErrorJSON(network, e1);
            } catch (JSONException e2) {
							sendErrorJSON(network, e2);
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
	
	private static void processRequest(NonoNetwork network) throws IOException, JSONException, SQLException, ClassNotFoundException {
		JSONObject requestJSON = network.readMessageJSON();
		ClientRequest request = NonoUtil.getClientRequest(requestJSON);
		JSONObject responseJSON = new JSONObject();
		NonoUtil.putServerResponse(responseJSON, ServerResponse.SUCCESS);
		
		switch (request) {
			case CREATE_PUZZLE:
				//testJSON(network, requestJSON);
				Nonogram.createPuzzle(requestJSON);
				break;
			case GET_PUZZLE:
				NonoUtil.putNonoPuzzle(responseJSON, Nonogram.getPuzzle(requestJSON));
				break;
			case SAVE_RESULT:
				throw new UnsupportedOperationException();
				//break;
			case CREATE_USER:
				throw new UnsupportedOperationException();
				//break;
			case LOG_IN:
				throw new UnsupportedOperationException();
				//break;
			case LOG_OUT:
				throw new UnsupportedOperationException();
				//break;
			default:
				throw new UnsupportedOperationException();
		}
		network.sendMessage(responseJSON);
	}
	
	//
	private static void sendErrorJSON(NonoNetwork network, Exception e) {
		try {
			e.printStackTrace();
			JSONObject errorJSON = new JSONObject();
			NonoUtil.putServerResponse(errorJSON, ServerResponse.ERROR);
			NonoUtil.putString(errorJSON, e.getMessage());
			network.sendMessage(errorJSON);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns string summarizing the status of this server.  
	 */
	public static String dumpServerState(ServerSocket serverSock) {
		StringBuilder sb = new StringBuilder();
		if(serverSock != null){
			sb.append("\nListening on: " + serverSock.toString());
		}else{
			sb.append("Not listening");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Running NonoServer");
		runServer();
	}
	
	
	
	
	// For testing  TODO: delete later
	//@SuppressWarnings("unused") 
	private static void testJSON(NonoNetwork network, JSONObject requestJSON) throws JSONException, IOException {
		System.out.println("Client sent me something!\n");	
		System.out.println(requestJSON);
		Integer[][] cArray = NonoUtil.getColorArray(requestJSON);
		Integer bgColor = NonoUtil.getColor(requestJSON);
		String name = NonoUtil.getString(requestJSON);
		NonoPuzzle puzzle = NonoPuzzle.createNonoPuzzle(cArray, bgColor, name);
		System.out.println("So I made a puzzle out of it!!");
		System.out.println(puzzle);
		System.out.println("\n");
		
		JSONObject responseJSON = new JSONObject();
		NonoUtil.putServerResponse(responseJSON, ServerResponse.SUCCESS);
		NonoUtil.putNonoPuzzle(responseJSON, puzzle);

		network.sendMessage(responseJSON);
		System.out.println("And I sent it to client!\n");
		System.out.println("\n\n\n");
	}
	// For testing  TODO: delete later
	@SuppressWarnings("unused") 
	private static void testSerer(NonoNetwork network) throws IOException, ClassNotFoundException {
		//String request = network.readMessageString();
		//System.out.println("Client said: " + request);
		//network.sendMessage("Hi, Client. I'm server!");
		
		Integer[][] arr = (Integer[][]) network.readMessageObject();
		network.sendMessage(NonoPuzzle.createNonoPuzzle(arr, Color.BLACK, "Test"));
	}
	
	
}
