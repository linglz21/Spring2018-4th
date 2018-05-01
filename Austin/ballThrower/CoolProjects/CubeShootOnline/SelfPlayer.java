
import java.awt.event.*;
import java.io.*;
import java.net.*;

class SelfPlayer extends Player implements KeyListener
{
	//private CrossHair;

	// listens for key strokes sends messages to server
	private BufferedWriter out;

	public SelfPlayer(int ID, Socket server){
		super(ID);
		try{
			out = new BufferedWriter(
				  new OutputStreamWriter(
				  new DataOutputStream(
				  server.getOutputStream())));
		}catch(Exception e){
			System.out.println("SelfPlayer can't connect to server.");
			e.printStackTrace();
		}
	}

	public boolean idMatch(int id){
		return myID == id;
	}

	public void keyReleased(KeyEvent e){
		try{
			out.write("R:" + e.getKeyCode() + "\n");
			out.flush();
		}catch(Exception ex){
			System.out.println("Trouble sending release message to server");
			ex.printStackTrace();
		}
	}
	public void keyPressed(KeyEvent e){
		try{
			out.write("P:" + e.getKeyCode() + "\n");
			out.flush();
		}catch(Exception ex){
			System.out.println("Trouble sending press message to server");
			ex.printStackTrace();
		}
	}
	public void keyTyped(KeyEvent e){
	}

}
