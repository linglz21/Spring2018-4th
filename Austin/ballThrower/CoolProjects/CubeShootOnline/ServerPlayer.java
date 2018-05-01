import java.io.*;
import java.net.*;
import java.awt.event.*;

public class ServerPlayer extends Player implements Runnable
{
	private final int LEFT  = KeyEvent.VK_LEFT;
	private final int A_KEY  = KeyEvent.VK_A;
	private final int RIGHT = KeyEvent.VK_RIGHT;
	private final int D_KEY  = KeyEvent.VK_D;
	private final int UP    = KeyEvent.VK_UP;
	private final int W_KEY  = KeyEvent.VK_W;
	private final int DOWN  = KeyEvent.VK_DOWN;
	private final int S_KEY  = KeyEvent.VK_S;
	private final int X_KEY  = KeyEvent.VK_X;

	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private BufferedReader in;
	private static int nextID = 0;
	private boolean done;

	public boolean isGone(){
		return done;
	}

	public ServerPlayer(Socket s){
		super(nextID++);
		done = false;
		leftPressed = rightPressed = upPressed = downPressed = false;
		try{
			in = new BufferedReader(new InputStreamReader(new DataInputStream(s.getInputStream())));
		}catch(IOException e){
			System.out.println("Server can't create inputStream from socket.");
			e.printStackTrace();
		}
		// create a buffered reader from s
		new Thread(this).start();
	}

	public String IDtoString(){
		return myID + "";
	}

	public void run(){
		while(!done){
			String message = "";
			try{
				message = in.readLine();
				String [] parts = null;
				if(message!=null) parts = message.split(":");
				boolean flag = true;
				if(parts != null && parts.length==2){
					if(parts[0].equals("R")) flag = false;
					int keyCode = new Integer(parts[1]);
					switch (keyCode){
						case UP    :
						case W_KEY : upPressed    = flag; break;
						case DOWN  :
						case S_KEY :
						case X_KEY : downPressed  = flag; break;
						case LEFT  :
						case A_KEY : leftPressed  = flag; break;
						case RIGHT :
						case D_KEY : rightPressed = flag; break;
					}
				}
			}catch(IOException e){
				System.out.println("Server can't read from socket");
				done = true;
			}
		}
	}

	public void update(){
		if(upPressed) if(motion.y==0)motion.y -= 8;
		if(downPressed) position.y += 0.3;
		if(leftPressed) motion.x -= 0.3;
		if(rightPressed) motion.x += 0.3;
		super.update();
	}

}
