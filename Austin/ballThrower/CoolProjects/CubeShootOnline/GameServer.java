import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;

class GameServer implements Runnable
{
	public static final int platformCount = 20;
	private ArrayList<Platform> allPlatforms;
	private ArrayList<ServerPlayer> allPlayers;
	private Semaphore lock;
	private boolean done;

	public GameServer(){
		lock = new Semaphore(1);
		done = false;
		allPlatforms = generatePlatforms();
		allPlayers = new ArrayList<ServerPlayer>();
		new Thread(this).start();
		new Thread(new Runnable(){public void run(){communicate();}}).start();
	}

	public void communicate(){
		ServerSocket mySocket = null;
		try{
			mySocket = new ServerSocket(6066);
		}catch (Exception e){
			System.out.println("Crash creating server socket");
			e.printStackTrace();
		}
		
		System.out.println("getting ip address");
		String ip;
	    try {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp())
	                continue;

	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                ip = addr.getHostAddress();
	                System.out.println(iface.getDisplayName() + " " + ip);
	            }
	        }
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }
	    System.out.println("got ip address");
		
		
		while(!done){
			try{
				System.out.println("server accepting connections");
				Socket newClient = mySocket.accept();
				System.out.println("server just got a connection");
				String outMessage = "";
				for(Platform p : allPlatforms) outMessage += p + ":";
				BufferedWriter out = new BufferedWriter(
					  new OutputStreamWriter(
					  new DataOutputStream(
					  newClient.getOutputStream())));

				ServerPlayer sPlayer = new ServerPlayer(newClient);
				outMessage = outMessage + "224.2.2.3:" + sPlayer.IDtoString() + "\n";
				System.out.println("Server sent:   " + outMessage);
				out.write(outMessage);
				out.flush();
				lock.acquire();
				allPlayers.add(sPlayer);
				lock.release();
			}catch(Exception e){
				System.out.println("Server failed to accept connection.");
				e.printStackTrace();
			}
		}
	}


	// takes care of updates and broadcast
	public void run(){
		// create broadcast socket
		DatagramSocket socket = null;
		InetAddress address = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;
		final int PORT = 8888;
		try{
			socket = new DatagramSocket();
			address = InetAddress.getByName("224.2.2.3");
		}catch(Exception e){
			System.out.println("Trouble creating broadcast socket.");
			e.printStackTrace();
		}

		while(!done){
			try{
				//update stuff
				String outMessage = "";
				lock.acquire();
				for(int i=0 ; i<allPlayers.size() ; i++){
					ServerPlayer it = allPlayers.get(i);
					if(it.isGone()){
						allPlayers.remove(it);
						i--;
					}else{
						it.update();
						for(Platform p : allPlatforms) it.hitTest(p);
						outMessage += it + ":";
					}
				}
				if(outMessage.length()>0)
					outMessage = outMessage.substring(0,outMessage.length()-1);
				//update all players
				lock.release();
				//sends broadcast message
				outBuf = outMessage.getBytes();
				outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
				socket.send(outPacket);
				//System.out.println("Server broadcast: " + outMessage);
				Thread.sleep(15);
			}catch(Exception e){
				System.out.println("Crash in server update loop");
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Platform> generatePlatforms(){
		ArrayList<Platform> temp = new ArrayList<Platform>();
		int rowCount = (int)(Math.sqrt(platformCount))+1;
		int colCount = (platformCount/rowCount)+1;
		int xOffset =  (GameClient.width-100) / (colCount);
		int yOffset =  (GameClient.height-70) / (rowCount);
		for (int r = 0; r < rowCount; r++){
			for(int c=0 ; c< colCount ; c++){
				double x = c*xOffset + xOffset * Math.random();
				double y = r*yOffset + yOffset/2 * Math.random()+50;
				temp.add(new Platform(x,y));
			}
		}
		temp.add(new Platform(0,GameClient.height-10 ,GameClient.width,10));
		return temp;
	}

}
