/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.network;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONException;
import org.json.JSONObject;

import uw.cse403.nonogramfun.utility.NonoUtil;
import uw.cse403.nonogramfun.utility.ParameterPolice;



/**
 * NonoNetwork is a wrapper class over a socket with established TCP connection.
 * It sends and receives a message over an established TCP connection, and provides
 * methods for easy client - server communication.
 */
public class NonoNetwork {
	private static final int LENGTH_BYTE = 4;
	private Socket tcpSocket;
	private OutputStream outStrm;
	private InputStream inStrm;
	
	
	/**
	 * Constructor creates a NonoNetwork object
	 * @param sock A socket to be associated with this TCP network.
	 * @throws IOException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws IllegalArgumentException if the given socket is null.
	 */
	public NonoNetwork(Socket sock) throws IOException {
		ParameterPolice.checkIfNull(sock, "Socket");
		
		tcpSocket = sock;
		outStrm = tcpSocket.getOutputStream();
		inStrm = tcpSocket.getInputStream();
		tcpSocket.setSoTimeout(NonoConfig.SOCKET_TIMEOUT);
	}
	
	
	/**
	 * Closes the underlying socket of this NonoNetwork.
	 */
	public void close() {
		if(tcpSocket != null) {
			try { 
				outStrm.close();
				inStrm.close();
				tcpSocket.close();
				tcpSocket = null;
			} catch (Exception e) { }
		}
	}
	
	/**
	 * Set the read timeout on the underlying socket.
	 * The timeout must be non-negative. A timeout of zero is interpreted as an infinite timeout.
	 * @param timeout socket read timeout in milliseconds.
	 * @throws SocketException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws IllegalArgumentException if the given timeout is negative.
	 */
	public void setTimeout(int timeout) throws SocketException {
		ParameterPolice.checkIfNegative(timeout);
		tcpSocket.setSoTimeout(timeout);
	}
	
	/**
	 * Returns the read timeout on the underlying socket.
	 */
	public int getTimeOut() throws SocketException {
		return tcpSocket.getSoTimeout();
	}
	
	
	
	//--Send routines----------------------------------------------------------
	
	/**
	 * Accepts a JSON Object and sends it over network.
	 * @param jsObject A JSON Object to be sent over this NonoNetwork
	 * @throws IOException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws IllegalArgumentException if the given JSON Object is null
	 */
	public void sendMessage(JSONObject jsObject) throws IOException {
		ParameterPolice.checkIfNull(jsObject, "JSON Object");
		sendMessage(jsObject.toString().getBytes());
	}
	
	
	// Sends a byte buffer over the network
	private void sendMessage(byte[] buf) throws IOException {
		if(NonoConfig.MAX_READ_LENGTH < buf.length) {
			throw new IOException("Error: Message length " + buf.length + ", is longer than max length " + NonoConfig.MAX_READ_LENGTH);
		}
		outStrm.write(NonoUtil.intToByte(buf.length));
		outStrm.write(buf);
	}
	
	
	
	//--Read routines------------------------------------------------------------
	
	/**
	 * Reads and returns a JSON Object from network.
	 * @return A JSON Object read from the network.
	 * @throws IOException if there is an error in the underlying protocol, such as a TCP error.
	 * @throws JSONException if the accepted message cannot be converted to JSON Object
	 */
	public JSONObject readMessageJSON() throws IOException, JSONException {
		return new JSONObject(new String(readMessageBytes()));
	}
	
	
	// Reads incoming message into a byte array
	public byte[] readMessageBytes() throws IOException {
	
		// 1. Read the length field
		byte[] lenBuf = new byte[LENGTH_BYTE];
		int lenRead = inStrm.read(lenBuf, 0, LENGTH_BYTE);
		if (lenRead < LENGTH_BYTE) {
			throw new IOException("Length field (" + lenRead + ") is less than " + LENGTH_BYTE);
		}
		
		// 2. Convert the length to integer
		int expectedLen = NonoUtil.byteToInt(lenBuf);
		if (expectedLen < 0 || NonoConfig.MAX_READ_LENGTH < expectedLen) {
			throw new IOException("Data to be received is less than zero or exceeding max read length");
		}
		
		// 3. Read the actual payload given its size
		byte[] buf = new byte[expectedLen];
		int actualLen = 0;
		while(actualLen < expectedLen){
			lenRead = inStrm.read(buf, actualLen, expectedLen - actualLen);
			if(lenRead == -1) { break; }
			actualLen += lenRead;
		} 
		
		// 4. Check if read correct number of bytes
		if (actualLen != expectedLen) {
			throw new IOException("Received data has a size of " + actualLen + " bytes while expected " + expectedLen + " bytes");
		}
		
		return buf;
	}
	
}
