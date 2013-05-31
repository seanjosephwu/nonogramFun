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


/**
 * NetworkStub is a stub class that is used for testing.
 */
public class NetworkStub implements Network {
	private int timeout = 0;
	
	public NetworkStub() {}

	@Override
	public void close() {}

	@Override
	public void setTimeout(int timeout) throws SocketException {
		this.timeout = timeout;
	}

	@Override
	public int getTimeOut() throws SocketException {
		return timeout;
	}

	@Override
	public void sendMessage(JSONObject jsObject) throws IOException {
		System.out.println("Sending message: " + jsObject);
	}

	@Override
	public JSONObject readMessageJSON() throws IOException, JSONException {
		return new JSONObject();
	}
}
