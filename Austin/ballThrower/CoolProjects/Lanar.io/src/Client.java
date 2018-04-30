import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


//Responsible for receiving info from server then sending it to game
public class Client implements Runnable {
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public UserPlayer userPlayer;
	private ArrayList<Player> players;
	private InetAddress host;
	public boolean kill;
	private int ticksPerSecond;
	public static ArrayList<Food> food;
	public static int WIDTH = Menu.getMapDimensions().width;
	public static int HEIGHT = Menu.getMapDimensions().height;
	private int fps;
	private long start;
	private long end;
	private int frames;
	private boolean doStart;
	private GraphicsDevice gd;
	Client(InetAddress host){
		try {
			gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			fps = 0;
			doStart = true;
			kill = false;
			this.host = host;
			System.out.println("[Client] Starting client");
			System.out.println("[Client] Connecting to "+host.getHostAddress()+":"+Server.PORT);
			s = new Socket(host, Server.PORT);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			new Thread(this).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g){
		if (doStart) {
			start = System.currentTimeMillis();
			doStart = false;
		}
		if (Menu.antiAlias.isSelected()){
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setColor(Color.white);
		g.fillRect(0, 0, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
		
		try {
			if (food != null) {
				for (Food f : food) {
					f.draw(g);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(userPlayer != null) {
			userPlayer.draw(g);
		} 
		if (players != null) {
			for (Player p : players) {
				p.draw(g);
			}
		}
		g.setColor(Color.black);
		g.drawString(fps+" FPS", 0, 35);
		if (kill) {
			g.setColor(Color.black);
			g.drawString("Game Over!", WIDTH/2, HEIGHT/2);
		}
		try {
			if (host.equals(InetAddress.getLocalHost())) {
				//Show server refresh rate
				g.setColor(Color.black);
				g.drawString("Server ticks/second: "+ticksPerSecond, 0, 600);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frames++;
		end = System.currentTimeMillis();
		if (end - start >= 1000) {
			fps = frames;
			frames = 0;
			doStart = true;
		}
	}
	
	@Override
	public void run() {
		try {
			//Send username
			System.out.println("[Client] Sending username");
			if (Menu.getUsername() != null || Menu.getUsername().equals("")){
				oos.writeObject(Menu.getUsername());
			} else {
				oos.writeObject(new String("Player"));
			}
			//Receive player (and other objects) from server
			System.out.println("[Client] Waiting for players array");
			players = (ArrayList<Player>)ois.readObject();
			System.out.println("[Client] Players array: "+players);
			System.out.println("[Client] Waiting for this player object");
			Player playerToConvert = (Player)ois.readObject();
			userPlayer = new UserPlayer(playerToConvert);
			System.out.println("[Client] Received player object");
			System.out.println("[Client] Waiting for food array");
			food = (ArrayList<Food>)ois.readObject();
			System.out.println("[Client] Received food objects, "+food.size()+" total");
			
			while (!kill) {
				try {
					//Read if dead
					kill = ois.readBoolean();
					//Send player
					oos.writeObject(userPlayer);
					oos.reset();
					//Read players
					players = (ArrayList<Player>)ois.readObject();
					//Read food
					food = (ArrayList<Food>)ois.readObject();
					
					//Send food eaten
					//oos.writeObject(eatenFood);
					//eatenFood.clear();
					//Read int of mass to add
					int massToGain = ois.readInt();
					int massPerCell = massToGain/userPlayer.cells.size();
					for (Cell c : userPlayer.cells) {
						c.mass += massPerCell;
					}
					//Read ticks/second server side
					ticksPerSecond = ois.readInt();
					userPlayer.update();
				
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
