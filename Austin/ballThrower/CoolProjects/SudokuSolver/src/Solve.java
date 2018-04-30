import java.awt.FileDialog;
import java.awt.Graphics;
import javax.swing.JFrame;


public class Solve extends JFrame{

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT=930;
	public static final int WIDTH=900;
	Board main;
	
	public Solve(){
		
		FileDialog fd = new FileDialog(this);
		fd.setFile(".");
		fd.setVisible(true);
		main = new Board(fd.getFile());
		
	    // 1: reduce main
		main.reduce();
		boolean solved = false;
		Board temp = null;
		int count = 0;
		while(!solved){
			// 2: create a clone of main "temp"
			temp = new Board(main);
			boolean valid = true;
			count++;
			while(valid && !solved ){
				// 3: make a random guess on one of the unsolved squares in temp
				temp.randomGuess();
				// 4: reduce temp as much as we can
				temp.reduce();				
				// 5: if temp is solved we're done
				solved = temp.isSolved();
				// 6: if temp is invalid goto step 2
				valid = temp.isValid();
				// 7: goto step 3
			}
			if(count> 100000 ){
				solved = true;
				temp = null;
				System.out.println("no solution");
			}
		}
		if(temp != null)main = temp;
	}
	
	public static void main(String[] args) {
		Solve s = new Solve();
		s.setSize(WIDTH,HEIGHT);
		s.setResizable(false);
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.setVisible(true);
	}
	
	public void paint(Graphics g){
		main.paint(g);
		
	}

}
