import java.net.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class HostListener implements Runnable {
	MulticastSocket socket;
	InetAddress addr;
	DatagramPacket packet;
	public static ArrayList<InetAddress> hosts;
	HostListener() throws Exception {
		System.setProperty("java.net.preferIPv4Stack", "true"); //Fix bug on macs
		socket = new MulticastSocket(2455);
		addr = InetAddress.getByName("230.0.0.1");
		socket.joinGroup(addr);
		hosts = new ArrayList<InetAddress>();
		byte[] buf = new byte[256];
		packet = new DatagramPacket(buf, buf.length);
		new Thread(this).start();
	}
	
	public static long ping(InetAddress addr) {
		try {
			long finish = 0;
			long start = new GregorianCalendar().getTimeInMillis();
			
			if (addr.isReachable(5000)) {
				finish = new GregorianCalendar().getTimeInMillis();
				return finish-start;
			} else {
				System.out.println("[HostListener] host not reachable");
				return finish-start;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				socket.receive(packet);
				String message = new String(packet.getData());
				long time = ping(packet.getAddress());
				message += " | " + time + "ms";
				if (hosts.size() == 0 /*&& packet.getAddress().equals(socket.getLocalAddress())*/) {
					Menu.addToList(message);
					hosts.add(packet.getAddress());
				}
				for (int i = 0; i < hosts.size(); i++) {
					if (hosts.get(i).equals(packet.getAddress())) {
						//Existing host with new info
						hosts.set(i, packet.getAddress());
						Menu.gamesModel.set(i, message);
					} else {
						System.out.println("[HostListener] Found new host");
						Menu.addToList(new String(packet.getData()));
						hosts.add(packet.getAddress());
					}
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
