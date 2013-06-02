package uw.cse403.nonogramfun.tests.network;
/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import java.net.Socket;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import uw.cse403.nonogramfun.network.NonoConfig;
import uw.cse403.nonogramfun.network.NonoServer;

/**
 * Tests NonoServer using a stub network. Before testing, change the SERVER_NAME
 * in NonoConfig to localhost. (I thought about adding a setter in NonoConfig, but since
 * server name is public field, I think it should remain as a final field)
 */
public class Test_NonoServer_Stub extends TestCase {

	@Before
	public void setStub() {
		NonoServer.setStub(true);
	}
	
	/**
	 * Test passes if any one of the ports succeeded to connect to server. 
	 */
	@Test
	public void test() throws Exception {
		NonoServer.main(null);
		boolean[] success = { false };
		for(int i=0; i<NonoConfig.MAX_PORT; i++) {
			testHelper(i, success);
		}
		assertTrue(success[0]);
	}
	
	// Creates new thread that tries to connect to server
	private void testHelper(final int i, final boolean[] success) throws InterruptedException {
		Thread tcpThread = new Thread(){
			@Override
			public void run() {
				Socket sock = null;
				try {
					sock = new Socket(NonoConfig.getServerIP(), NonoConfig.BASE_PORT+i);
					sock.getOutputStream().write(new byte[10]);
					System.out.println("For Port " + (NonoConfig.BASE_PORT+i) + " Success!");
					success[0] = true;
				} catch (Exception e) {
					System.out.println("**For Port " + (NonoConfig.BASE_PORT+i) + " Fail!");
					e.printStackTrace();
				}finally{
					if(sock != null) { try{ sock.close(); }catch(Exception e){} }
				}
			}
		};
		tcpThread.start();
		tcpThread.join();
	}
	
}
