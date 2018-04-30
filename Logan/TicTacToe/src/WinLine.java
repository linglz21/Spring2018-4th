import java.awt.Color;
import java.awt.Graphics;


public class WinLine {
	int row,col,diag;
	
	public WinLine(int row,int col,int diag){
		this.row = row;
		this.col = col;
		this.diag = diag;
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(1.0f,0f,0f,0.5f));
		if(row!=-1){
			g.fillRect(0, 200*row+115, MainClass.WIDTH, 30);
		}
		if(col!=-1){
			g.fillRect(200*col+95,0, 30,MainClass.HEIGHT);
		}
		if(diag==1){
			int[] x = {0,15,MainClass.WIDTH,MainClass.WIDTH-15};
			int[] y = {45,30,MainClass.HEIGHT-15,MainClass.HEIGHT};
			g.fillPolygon(x, y, 4);;
		}
		if(diag==2){
			int[] x = {0,15,MainClass.WIDTH,MainClass.WIDTH-15};
			int[] y = {MainClass.HEIGHT-15,MainClass.HEIGHT,45,30};
			g.fillPolygon(x, y, 4);;
		}
	}
}
