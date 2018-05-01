import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class ServerBroadcast implements Runnable {
	private DatagramPacket packet;
	private static DatagramSocket socket;
	private byte[] buf;
	public static InetAddress group;
	ServerBroadcast(String info) {
		try {
			buf = new byte[256];
			buf = info.getBytes();
			group = InetAddress.getByName("230.0.0.1");
			packet = new DatagramPacket(buf, buf.length, group, 2455);
			if (socket == null)socket = new DatagramSocket(2454);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	public void refresh(String str) {
		buf = str.getBytes();
		packet = new DatagramPacket(buf, buf.length, group, 2455);
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				socket.send(packet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
