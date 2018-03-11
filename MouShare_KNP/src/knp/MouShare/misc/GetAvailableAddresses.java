package knp.MouShare.misc;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.JOptionPane;

public class GetAvailableAddresses {
	// Source:
	//   https://stackoverflow.com/questions/40912417/java-getting-ipv4-address
	//   https://docs.oracle.com/javase/tutorial/networking/nifs/listing.html
	public static ArrayList<String> get() {
		Enumeration<NetworkInterface> nets = null;
		ArrayList<String> list = null;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			list = new ArrayList<String>();
			for (NetworkInterface netint : Collections.list(nets)) {
		    	Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
		    	if (netint.isLoopback() || !netint.isUp()) continue;
		        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
		        	if (inetAddress instanceof Inet6Address) continue;
		        	list.add(inetAddress.toString().replace('/', '\0'));
		        }
		    }
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null,"Some error occured while scanning Network Interfaces.");
			e.printStackTrace();
		}
		return list;
	}
}
