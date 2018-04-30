import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

//Updates all helpers 
public class ServerRunner implements Runnable {
	private ArrayList<ServerHelper> helpers;
	private Random rand;
	public static final int TARGET_TICKS_PER_SECOND = 30;
	public static final int SLEEP_TIME = 1000/TARGET_TICKS_PER_SECOND;
	public static int ticksPerSecond;
	private boolean doStart;
	private long start;
	private long end;
	private int ticks;
	private int sleepTime; //used to get more accurate ticks/second
	public static HashMap<String, Integer> scoreboard;
	private HashMap<String, Integer> position;
	ServerRunner() {
		position = new HashMap<String, Integer>(10);
		scoreboard = new HashMap<String, Integer>(10);
		helpers = Server.helpers;
		rand = new Random();
		ticksPerSecond = 0;
		start = 0;
		end = 0;
		ticks = 0;
		doStart = true;
		sleepTime = SLEEP_TIME;
		System.out.println("[ServerRunner] Starting game thread...");
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if(doStart) {
					start = System.currentTimeMillis();
					doStart = false;
				}
				Thread.sleep(sleepTime);
				//Leaderboard
				int smallest = Integer.MAX_VALUE;
				String smallestP = new String();
				for (Player p : Server.getAllPlayers()) {
					Iterator it = scoreboard.entrySet().iterator();	
					while (it.hasNext()) {
						//Find smallest player
						String key = (String) it.next();
						Integer value = (Integer) scoreboard.get(key);
						int mass = value.intValue();
						if (mass < smallest) {
							smallest = mass;
							smallestP = key;
						}
					}

					if (p.getMass() > smallest) {
						System.out.println("[ServerRunner] Adding "+p.username+" to leaderboards");
						if (scoreboard.size() > 10) {
							scoreboard.remove(smallestP);
							smallestP = p.username;
							smallest = p.getMass();
						}
						scoreboard.put(p.username, new Integer(p.getMass()));	
						System.out.println("[ServerRunner] Leaderboards: "+scoreboard.toString());
					}
					//For every player on the leaderboard, if they are bigger than the smallest player, minus 1 to position

				}
				position.put(smallestP, 10);

				Iterator it = scoreboard.entrySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					Integer value = (Integer) scoreboard.get(key);
					Iterator it2 = scoreboard.entrySet().iterator();
					while (it2.hasNext()) {
						String key2 = (String) it2.next();
						Integer value2 = (Integer) scoreboard.get(key2);
						if (value.intValue() > value2.intValue()) {
							System.out.println("[ServerRunner] Adding ");
							position.put(key, position.get(key2).intValue()-1);
						}
					}
				}
				//For every player cell, if that cell is colliding w/ any other cell
				for (Player p : Server.getAllPlayers()) {
					for (Cell c : p.cells) {
						for (Player p1 : Server.getPlayers(p)) {
							for (Cell c1 : p1.cells) {
								if (c.isColliding(c1) && c != c1) {
									System.out.println("[ServerRunner] Detected a collision");
									//If c is 50% bigger than c1
									if (c1.mass+(c1.mass*.5) < c.mass) {
										//kill p1

										System.out.println("[ServerRunner] "+p1.username+" been killed by "+p.username);
										System.out.println("[ServerRunner] "+p1.username+" mass: "+c1.mass);
										System.out.println("[ServerRunner] "+p.username+" mass: "+c.mass);
										//Give p p1's mass
										//Find helper associated with player
										for (ServerHelper helper : helpers) {
											if (helper.player == p) {
												System.out.println("[ServerRunner] Adding mass to helper");
												helper.massToGain += p1.getMass();
											} else if (helper.player == p1) {
												helper.killPlayer();
											}
										}

									}
								}

							}
						}
					}
				}
				//Check for colliding etc etc	
				ticks++;
				end = System.currentTimeMillis();
				if (end - start >= 1000) {
					doStart = true;
					ticksPerSecond = ticks;
					ticks = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
