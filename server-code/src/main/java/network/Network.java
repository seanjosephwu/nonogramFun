/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.network;
import java.io.IOException;
import java.net.SocketException;
import org.json.*;
import org.json.JSONObject;


/**
 * Network interface defines operations that allows client and server to talk to each other. 
 */
public interface Network {

	public void close();
	
	/**
	 * Set the read timeout on the underlying socket.
	 * The timeout must be non-negative. A timeout of zero is interpreted as an infinite timeout.
	 * @param timeout socket read timeout in milliseconds.
	 * @throws SocketException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws IllegalArgumentException if the given timeout is negative.
	 */
	public void setTimeout(int timeout) throws SocketException;
	
	/**
	 * Returns the read timeout on the underlying socket.
	 */
	public int getTimeOut() throws SocketException;
	
	/**
	 * Accepts a JSON Object and sends it over network.
	 * @param jsObject A JSON Object to be sent over this NonoNetwork
	 * @throws IOException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws IllegalArgumentException if the given JSON Object is null
	 */
	public void sendMessage(JSONObject jsObject) throws IOException;
	
	/**
	 * Reads and returns a JSON Object from network.
	 * @return A JSON Object read from the network.
	 * @throws IOException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws JSONException if the accepted message cannot be converted to JSON Object
	 */
	public JSONObject readMessageJSON() throws IOException, JSONException;
	
}
