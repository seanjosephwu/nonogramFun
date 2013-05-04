

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Console implementation of a class that tries to determine "the IP address" of the machine
 * we're running on.  The problem is that "the IP address" isn't a well defined notion - there
 * are likely to be many of them.
 * <p>
 * This class deals with IPv4 addresses only.
 * 
 * @author zahorjan
 *
 */
public class IPFinder {
	static private InetAddress mInetAddress = null;
	static private ArrayList<InetAddress> mPreferredAddresses = new ArrayList<InetAddress>();
	
	//-------------------------------------------------------------------------------
	// Interface enumeration helper classes
	//-------------------------------------------------------------------------------
	
	/**
	 * Objects that can field callbacks from the NetworkInterfaceIterator method.
	 * @author zahorjan
	 *
	 */
	private interface NetworkInterfaceCallbackInterface {
		/**
		 * Called for each NetworkInterface encountered by NetworkInterfaceIterator().
		 * @param f A NetworkInterface on the machine.
		 * @return true to continue processing this interface; false to discontinue processing this interface
		 */
		public boolean newNetworkInterface(NetworkInterface f);
		/**
		 * Called for each InetAddress associated with a NetworkInterface whose processing wasn't terminated
		 * by a newNetworkingInterface() call.
		 * @param a The InetAddress.
		 */
		public void newInetAddress(InetAddress a);
	}
	
	
	/**
	 * A class that prints out the networking environment of the machine.
	 * We have to use Log.x() for portability across console and Android environments.
	 * @author zahorjan
	 *
	 */
	static private class NetworkInterfaceDumper implements NetworkInterfaceCallbackInterface {
		
		private StringBuilder sb = new StringBuilder();

		public boolean newNetworkInterface(NetworkInterface iface) {
			sb.append("\n")
			  .append("Interface: ").append(iface.getName()).append("\n")
			  .append("\tisVirtual = ").append(iface.isVirtual()?"yes":"no").append("\n");
			try {
				sb.append("\tisLoopback: ").append(iface.isLoopback()?"yes":"no").append("\n")
				  .append("\tisUp: ").append(iface.isUp()?"yes":"no").append("\n");
			} catch (SocketException e) {
				// keep trying -- any info is useful
				sb.append("Caught SocketException: ")
				  .append(e.getMessage())
				  .append("\n");
			}
			return true;  // print everything
		}
	
		public void newInetAddress(InetAddress address) {
			sb.append("\tIP: ").append(address.toString()).append("\n")
			  .append("\t\tIPV4: ").append(address instanceof java.net.Inet4Address ? "Yes" : "No").append("\n")
			  .append("\t\tisSiteLocalAddress: ").append(address.isSiteLocalAddress()?"yes":"no").append("\n");
//			Log.e(TAG, "\ttoString() = " + address.toString());
//			Log.e(TAG, "\tgetHostAddress() = " + address.getHostAddress());
//			Log.e(TAG, "\n");
		}
		
		public String toString() {
			return sb.toString();
		}
	}
	
	/**
	 * This class uses the interface iterator method below to apply a heuristic
	 * that tries to identify "the IP" for the machine we're running on.
	 * The rules for that are:
	 * <ul>
	 * <li>We consider only the available IPv4 addresses as candidates.
	 * <li>We prefer globally routable addresses to non-routable addresses.
	 * </ul>
	 * If this results in a single IP address, we select it as "the IP."  If we end 
	 * up with more than one, some external code must select one before we can 
	 * answer calls to return "the IP."  (In our projects, the user is asked.)

	 * @author zahorjan
	 *
	 */
	static private class IPChooser implements NetworkInterfaceCallbackInterface {
		
		public boolean newNetworkInterface(NetworkInterface iface) {
			try {
				if ( iface.isLoopback() ) return false;
				if ( !iface.isUp() ) return false;
			} catch (SocketException e) {
				// If we can't examine the interace, we probably don't want to try
				// to use an IP that corresponds to it.
				return false;
			}
			return true;
		}
		
		public void newInetAddress(InetAddress address) {
			// must be an IPv4 address
			if ( ! (address instanceof java.net.Inet4Address) ) return;
			// we prefer routable to non-routable
			if ( !mPreferredAddresses.isEmpty() && mPreferredAddresses.get(0).isSiteLocalAddress() && !address.isSiteLocalAddress() ) mPreferredAddresses.clear();
			if ( mPreferredAddresses.isEmpty() || (address.isSiteLocalAddress() == mPreferredAddresses.get(0).isSiteLocalAddress())) mPreferredAddresses.add(address);
		}
	}
	
	//-------------------------------------------------------------------------------
	// End of interface enumeration helper classes
	//-------------------------------------------------------------------------------

	
	/**
	 * Providing an IP address using this method by-passes any subsequent application
	 * of the heuristics used to identify the local IP, and instead returns whatever
	 * was passed in.
	 * @param preferredIP
	 * @throws UnknownHostException
	 */
	public static void setIP(String preferredIP) throws UnknownHostException {
		if ( preferredIP == null ) return;
		mInetAddress = InetAddress.getByName(preferredIP);
	}
	
	/**
	 * Returns the current best guess at the local IP address.
	 * @return
	 */
	public static String localIP() {
		if ( mInetAddress == null ) {
			_enumerateInterfaces(new IPChooser());
			if ( mPreferredAddresses.size() == 1 ) mInetAddress = mPreferredAddresses.get(0);
		}
		if ( mInetAddress == null ) return null;
		return mInetAddress.getHostAddress();
	}
	
	public static ArrayList<InetAddress> getPreferredAddressList() {
		return mPreferredAddresses;
	}
	
	public static String logInterfaces() {
		NetworkInterfaceDumper dumper = new NetworkInterfaceDumper(); 
		_enumerateInterfaces( dumper );
		return dumper.toString();
	}
	
	/**
	 * This method is the basic control flow to enumerate all network interfaces
	 * and then all IP addresses associated with each.  Think of it as an iterator
	 * over the basic networking configuration data.  It issues a callback to
	 * some object, provided as an argument, for each NetworkInterface and InetAddress.
	 * <p>
	 * It's used for two things: to print out this information, and to apply
	 * a heuristic to try to identify which IP is "the IP" of the machine we're running on.
	 * <p>
	 * @param callback Either a printing or an IP address selection object.
	 */
	private static void _enumerateInterfaces(NetworkInterfaceCallbackInterface callback)  {
		if ( callback == null ) return;
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while ( interfaces.hasMoreElements() ) {
				NetworkInterface iface = interfaces.nextElement();
				if ( callback != null && !callback.newNetworkInterface(iface) ) continue;

				Enumeration<InetAddress>ipVec =  iface.getInetAddresses();
				while ( ipVec.hasMoreElements() ) {
					InetAddress address = ipVec.nextElement();
					if ( callback != null ) callback.newInetAddress(address);
				}

				//Enumeration<InterfaceAddress> ifAddrVec = iface.getInterfaceAddresses();
			}
		}	
		catch (SocketException e) {

		}
	}
}
