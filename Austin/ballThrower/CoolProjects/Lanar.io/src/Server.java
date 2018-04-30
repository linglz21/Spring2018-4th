import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

//Creates new helper for new connections
public class Server implements Runnable {
	private String name;
	private int maxPlayers, connectedPlayers;
	private ServerSocket server;
	public static final int PORT = 2998;
	public static ArrayList<ServerHelper> helpers;
	private static ServerBroadcast broadcaster;
	public static LockableList<Food> food;
	public static int originalFoodSize;
    Server() {
    	try {
	    	System.out.println("[Server] Starting server...");
	    	server = new ServerSocket(PORT);
			helpers = new ArrayList<ServerHelper>();
			
			maxPlayers = Menu.getMaxPlayers();
			name = Menu.getGameName();
			connectedPlayers = helpers.size();
			broadcaster = new ServerBroadcast(name+" | Players: "+connectedPlayers+"/"+maxPlayers);
			updateStats();
			//Generate food/viruses
			food = new LockableList<Food>();
			generateFood();
			new Thread(this).start();
			new ServerRunner();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
    
    private void generateFood() {
    	//Calculate total map size
    	food.acquire();
    	System.out.println("[Server] Generating food");
    	int mapSize = Menu.getMapSize();
    	Random rand = new Random();
    	for (int i = 0; i < mapSize/250; i++) {
    		food.add(new Food(Orb.randomColor()));
    		food.get(i).mapPosX = rand.nextInt(Client.WIDTH);
    		food.get(i).mapPosY = rand.nextInt(Client.HEIGHT);
    	}
    	food.release();
    	originalFoodSize = food.size();
    	System.out.println("[Server] Generated "+originalFoodSize+" food particles");
    }
    private void updateStats() {
    	maxPlayers = Menu.getMaxPlayers();
		name = Menu.getGameName();
		connectedPlayers = helpers.size();
		String servStats;
		servStats = name+" | Players: "+connectedPlayers+"/"+maxPlayers;
		System.out.println("[Server] Updating server stats: "+servStats);
		broadcaster.refresh(servStats);
    }
    
    public static ArrayList<Player> getAllPlayers() {
    	ArrayList<Player> ret = new ArrayList<Player>();
    	for (int i = 0; i < helpers.size(); i++) {
    		ret.add(helpers.get(i).player);
    	}
    	return ret;
    }
    public static ArrayList<Player> getPlayers(Player thisPlayer) {
    	//Gets all players in game except 'this one'
    	//Used so ServerHelper can send all players, then send the one the user will control w/o duplicate
    	ArrayList<Player> ret = new ArrayList<Player>();
    	for (int i = 0; i < helpers.size(); i++) {
    		if (helpers.get(i).player != thisPlayer)ret.add(helpers.get(i).player);
    	}
    	return ret;
    }
	@Override
	public void run() {
		while (true) {
			try {
				Socket socket = server.accept();
				System.out.println("[Server] Accepted new client to server");
				helpers.add(new ServerHelper(socket));
				updateStats();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
