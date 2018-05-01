import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;


public class Creator {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter ammount of Players:");
		int ammount = Integer.parseInt(in.nextLine());
		String[] names= new String[ammount];
		for(int i=0; i<ammount; i++){
			System.out.println("Enter name of player"+ (i+1));
			names[i] = in.nextLine();
		}
		GameRunner runner = new GameRunner(ammount, names);
		runner.setSize(800, 800);
		runner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		runner.setResizable(false);
		new Thread(runner).start();
		runner.setVisible(true);
		runner.setFocusable(true);
	}
}
