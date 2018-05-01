import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

//Responsible for I/O of individual player cell
public class ServerHelper implements Runnable{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public Player player;
	public int massToGain;
	private boolean dead;
	private static final int SLEEP_TIME = 10;
	private int ticks;
	private long start;
	private long end;
	private boolean doStart;
	public int ticksPerSecond;
	ServerHelper(Socket socket) {
		//Send new player object (spawn)
		try {
			doStart = true;
			ticks = 0;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

			System.out.println("[ServerHelper] Waiting for client username");
			String username = (String)ois.readObject();
			System.out.println("[ServerHelper] Creating new player object");
			player = new Player(Orb.randomColor(), 12, username);

			//Spawn position
			player.cells.get(0).mapPosX = 400;
			player.cells.get(0).mapPosY = 300;

			System.out.println("[ServerHelper] Sending client player info");
			oos.writeObject(Server.getPlayers(player));
			System.out.println("[ServerHelper] Sending this player");
			oos.writeObject(player);
			System.out.println("[ServerHelper] Sending food array");
			oos.writeObject(Server.food);
			massToGain = 0;
			dead = false;
			System.out.println("[ServerHelper] Starting helper thread...");
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void killPlayer() {
		dead = true;
	}


	@Override
	public void run() {
		Random rand = new Random();
		while (true) {
			try {
				if (doStart) {
					start = System.currentTimeMillis();
					doStart = false;
				}
				Thread.sleep(SLEEP_TIME);

				//Send is dead
				oos.writeBoolean(dead);
				oos.flush();
				//If we are dead, remove this helper from array
				if (dead) Server.helpers.remove(this);
				//Receive player
				player = (Player)ois.readObject();
				
				//Check for food eaten
				Server.food.acquire();
				for (int i = 0; i < Server.food.size(); i++) {
					for (Cell c : player.cells) {
						if (c.isColliding(Server.food.get(i))) {
							System.out.println("[ServerHelper] Ate food at "+Server.food.get(i).getCenter().toString());
							Server.food.remove(i);
							massToGain += 4;
							Food spawn = new Food(Orb.randomColor());
							spawn.mapPosX = rand.nextInt(Client.WIDTH);
							spawn.mapPosY = rand.nextInt(Client.HEIGHT);
							Server.food.add(spawn);
						}
					}
				}
				Server.food.release();
				//Send players
				oos.writeObject(Server.getPlayers(player));
				//Send food	
				Server.food.acquire();
				oos.writeObject(Server.food);
				Server.food.release();
				oos.reset();
				//Send int of mass to add
				oos.writeInt(massToGain);
				massToGain = 0;
				oos.flush();
				//Send performance info
				ticks++;
				end = System.currentTimeMillis();
				if (end - start >= 1000) {
					ticksPerSecond = ticks;
					ticks = 0;
					doStart = true;
				}
				oos.writeInt(ticksPerSecond);
				oos.flush();
			} catch (IOException e) {
				System.out.println("[ServerHelper] I/O Error");
			} catch (Exception e) {
				System.out.println("[ServerHelper] Internal error");
				e.printStackTrace();
			}


		}

	}
}
