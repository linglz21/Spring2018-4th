import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Client extends JFrame implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	private boolean done;
	private BufferedReader in;
	private BufferedWriter out;
	private JTextField txtField;
	private TextArea messages;
	private String username;
	private JLabel user;
	public Client(Socket s, String name) {
		try {
			username = name;
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			done = false;

			this.setLayout(null);
			this.setSize(300, 400);
			this.setResizable(false);
			this.getContentPane().setBackground(Color.black);
			this.getContentPane().setForeground(Color.green);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			txtField = new JTextField();
			txtField.setBounds(25, 50, 250, 25);
			txtField.setBackground(Color.black);
			txtField.setForeground(Color.green);
			txtField.setCaretColor(Color.green);
			txtField.addActionListener(this);
			this.add(txtField);
			
			messages = new TextArea();

			messages.setBounds(25, 100, 250, 250);
			messages.setBackground(Color.black);
			messages.setForeground(Color.green);
			messages.setFocusable(false);
			messages.setEditable(false);
			this.add(messages);
			
			user = new JLabel(username);
			user.setForeground(Color.green);
			user.setBounds(25, 10, 100, 25);
			this.add(user);
			
			new Thread(this).start();
		} catch (Exception e) {
			System.out.println("It appeareth ye journey hath come to the most unfortunate of endings.");
			e.printStackTrace();
		}
	}

	public void hasServer() {
		user.setText("[Server] "+username);
	}
	@Override
	public void run() {
		while(!done) {
			try {
				String line = in.readLine();
				if (line == null) {
					JOptionPane.showMessageDialog(null, "Server is down", "Death is near", JOptionPane.ERROR_MESSAGE, null);
					System.exit(0);
				}
				messages.setText(line+'\n'+messages.getText());
			} catch (IOException e) {
				System.out.println("It appeareth ye journey hath come to the most unfortunate of endings.");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			String line = username + ": "+txtField.getText()+'\n';
			out.write(line);
			out.flush();
			messages.setText(line + messages.getText());
			messages.setCaretPosition(0);
			txtField.setText("");	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
