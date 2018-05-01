import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ServerHelper implements Runnable{
	private Socket client;
	private LockableList<Socket> clients;
	private boolean done;

	public ServerHelper(Socket s, LockableList<Socket> clients) {
		client = s;
		this.clients = clients;
		done = false;
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			while (!done) {
				String message = in.readLine();
				clients.acquire();
				if(message == null){
					clients.remove(client);
					done = true;
				} else {
					for(Socket s : clients) {
						if (s != client) {
							BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
							out.write(message+'\n');
							out.flush();
						}
					}
				}
				clients.release();
			}
		} catch (IOException e) {
			System.out.println("It appeareth ye journey hath come to the most unfortunate of endings.");
			System.out.println("Here lieth ye prophet's complaints:");
			e.printStackTrace();
		}

	}
}
