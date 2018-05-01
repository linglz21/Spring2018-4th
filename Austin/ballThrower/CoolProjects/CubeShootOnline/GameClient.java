
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;

class GameClient extends JFrame implements Runnable
{
	public static final int width = 800, height = 600;
	private ArrayList<Platform> allPlatforms;
	private SelfPlayer me;
	private HashMap<Integer,Player> allPlayers;
	private boolean done;

	private boolean showButtons;
	private ArrayList<Button> allButtons;
	private TextBox serverSelect;

	private MulticastSocket socket = null;
	// must listen on MultiCast socket and update all players

    public BufferedImage offscreen;
    public Graphics bufferGraphics;

	public GameClient(){
		allPlayers = new HashMap<Integer,Player>();
        offscreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );
        bufferGraphics = offscreen.getGraphics();
		showButtons = true;
		serverSelect = new TextBox(new Vector2(50,50), "type in me",
								   new TextBoxRunnable(){
		                           		public void run(String p){
		                           	     	if(!showButtons)return;
		                           		 	System.out.println("Connecting to: " +p);
											try{
												Socket client = new Socket(p, 6066);
										    	generate(client);
										    	showButtons = false;
											}catch(Exception e){
												System.out.println("Client can't connect to local server.");
												e.printStackTrace();
											}
		                           		}
								   });
		addKeyListener(serverSelect);
		allButtons = new ArrayList<Button>();
		allButtons.add(new Button(new Vector2(200,200),"Start Server",
		                           new Runnable(){
		                           	     public void run(){
		                           	     	if(!showButtons)return;
		                           	     	System.out.println("Creating Server");
		                           	     	new GameServer();
											// connect to the local host
											try{
												Socket client = new Socket("localhost", 6066);
										    	generate(client);
											}catch(Exception e){
												System.out.println("Client can't connect to local server.");
												e.printStackTrace();
											}
										    showButtons = false;
		                           		 }
		                           }));
		for(Button b : allButtons) addMouseListener(b);
		done = false;

	}

	private void generate(Socket s){
		allPlatforms = new ArrayList<Platform>();
		String inMessage = "";
		try{
			inMessage = new BufferedReader(new InputStreamReader(new DataInputStream(s.getInputStream()))).readLine();
			String[] split = inMessage.split(":");
			for(int i=0 ; i< split.length-2 ; i++){
				allPlatforms.add(new Platform(split[i]));
			}
			System.out.println("Creating multicast socket");
			socket = new MulticastSocket(8888);
			System.out.println("Creating address: " + split[split.length-2]);
			System.out.println("using ip:" + split[split.length-2]);
			InetAddress address = InetAddress.getByName(split[split.length-2]);
			System.out.println("Joining group");
			socket.joinGroup(address);
			me = new SelfPlayer(new Integer(split[split.length-1]),s);
			addKeyListener(me);

		}catch(IOException e){
			System.out.println("Client can't generate platforms.");
			e.printStackTrace();
		}
	}

	public void run(){
		byte[] inBuf = new byte[256];
		DatagramPacket inPacket = null;
		while(!done){
			if(showButtons){
				try{
					Thread.sleep(30);
					//System.out.println("Showing buttons");
				}catch (Exception e){
					System.out.println("Trouble sleeping?!");
					e.printStackTrace();
				}
			}else{
				try{
					inPacket = new DatagramPacket(inBuf, inBuf.length);
					socket.receive(inPacket);
					String msg = new String(inBuf, 0, inPacket.getLength());
					String [] split = msg.split(":");

					HashMap<Integer,Player> updatedPlayers = new HashMap<Integer,Player>();

					for(String piece : split){
						String [] pieces = piece.split(" ");
						int id = new Integer(pieces[0]);
						double health = new Double(pieces[1]);
						Vector2 position = new Vector2(pieces[2]);
						if(me.idMatch(id)) me.update(health,position);
						else{
							Player p = allPlayers.get(id);
							if(p == null)p = new Player(id);
							p.update(health,position);
							updatedPlayers.put(id,p);
						}
					}
					allPlayers = updatedPlayers;
					// update all players.
				}catch (Exception e){
					System.out.println("Trouble recieving broadcast packet");
					e.printStackTrace();
				}
			}
			repaint();
		}
	}

	public void paint(Graphics g){
		bufferGraphics.setColor(Color.black);
		bufferGraphics.fillRect(0,0,width,height);
		if(showButtons){
			for(Button b : allButtons)b.draw(bufferGraphics);
			serverSelect.draw(bufferGraphics);
		}else{
			for(Platform p : allPlatforms)p.draw(bufferGraphics);
			me.draw(bufferGraphics);
			for(Player p : allPlayers.values())p.draw(bufferGraphics);
		}
		g.drawImage(offscreen,0,0,this);
	}

	public static void main(String [] args){
		GameClient g = new GameClient();
		g.setSize(width,height);
		g.setResizable(false);
		new Thread(g).start();
		g.setVisible(true);
	}


}
