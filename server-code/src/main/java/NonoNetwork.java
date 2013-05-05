/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim, Sean Wu
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import java.io.*;
import java.net.*;
import org.json.*;

/**
 * Sends/receives a message over an established TCP connection.
 */
public class NonoNetwork {
	public static final int SOCKET_TIMEOUT = 5000;
	private static final int LENGTH_BYTE = 4;
	
	private Socket tcpSocket;
	private OutputStream outStrm;
	private InputStream inStrm;
	private int maxReadLength = 5000;
	
	
	/**
	 * Constructor, associating this TCPMessageHandler with a connected socket.
	 */
	public NonoNetwork(Socket sock) throws IOException {
		tcpSocket = sock;
		outStrm = tcpSocket.getOutputStream();
		inStrm = tcpSocket.getInputStream();
		tcpSocket.setSoTimeout(SOCKET_TIMEOUT);
	}
	
	/**
	 * Closes the underlying socket and renders this TCPMessageHandler useless.
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
	 */
	public void setTimeout(int timeout) throws SocketException {
		tcpSocket.setSoTimeout(timeout);
	}
	
	/**
	 * Returns the read timeout on the underlying socket.
	 */
	public int getTimeOut() throws SocketException {
		return tcpSocket.getSoTimeout();
	}
	
	/**
	 * Sets the maximum allowed size for which decoding of a message will be attempted.
	 */
	public void setMaxReadLength(int maxLen) {
		maxReadLength = maxLen;
	}

	/**
	 * Returns the current setting for the maximum read length
	 */
	public int getMaxReadLength() {
		return maxReadLength;
	}
	
	//--------------------------------------------------------------------------------------
	// send routines
	//--------------------------------------------------------------------------------------
	
	public void sendMessage(byte[] buf) throws IOException {
		if(buf.length >= getMaxReadLength()) {
			throw new IOException("Attempts to send message of size " + buf.length + ", longer than max length " + getMaxReadLength());
		}
		outStrm.write(NonoUtil.intToByte(buf.length));
		outStrm.write(buf);
	}
	
	public void sendMessage(String str) throws IOException {
		sendMessage(str.getBytes());
	}

	public void sendMessage(int value) throws IOException{
		sendMessage(NonoUtil.intToByte(value));
	}
	
	public void sendMessage(Object obj) throws IOException {
		sendMessage(NonoUtil.objecToByte(obj));
	}
	
	public void sendMessage(JSONObject jsObject) throws IOException {
		sendMessage(jsObject.toString());
	}
	
	
	//--------------------------------------------------------------------------------------
	// read routines
	//--------------------------------------------------------------------------------------
	
	public byte[] readMessageBytes() throws IOException {
	
		// 1. Read the length field
		byte[] lenBuf = new byte[LENGTH_BYTE];
		int lenRead = inStrm.read(lenBuf, 0, LENGTH_BYTE);
		if (lenRead < LENGTH_BYTE) {
			throw new IOException("Length field (" + lenRead + ") is less than " + LENGTH_BYTE);
		}
		
		// 2. Convert the length to integer
		int expectedLen = NonoUtil.byteToInt(lenBuf);
		if (expectedLen < 0 || expectedLen > getMaxReadLength()) {
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
	
	public String readMessageString() throws IOException {
		return new String(readMessageBytes());
	}

	public int readMessageInt() throws IOException {
		return NonoUtil.byteToInt(readMessageBytes());
	}
	
	public Object readMessageObject() throws IOException, ClassNotFoundException {
		return NonoUtil.byteToObject(readMessageBytes());
	}
	
	public JSONObject readMessageJSON() throws IOException, JSONException {
		return new JSONObject(readMessageString());
	}
}
