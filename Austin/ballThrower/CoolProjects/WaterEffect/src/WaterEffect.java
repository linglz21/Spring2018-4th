import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class WaterEffect extends JFrame implements MouseMotionListener, Runnable
{
	private static final long serialVersionUID = 1L;
	private double heightMap[][][];
	private int flip, width, height;
	private BufferedImage background;
	private BufferedImage rendered;
	private int outWidth, outHeight;

	public WaterEffect() {
		outWidth=800;
		outHeight=600;
		flip = 0;
		try {
			background = ImageIO.read(getClass().getResource("/resources/spiderman_6.jpg"));
		    width = background.getWidth();
		    height = background.getHeight();
		    rendered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		} catch (IOException e) {
		}
		heightMap = new double [2][width][height];
		addMouseMotionListener(this);
	}
	
	public static void main(String[] args) {
		WaterEffect we = new WaterEffect();
		we.setSize(we.width, we.height);
		we.setResizable(false);
		we.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(we).start();
		we.requestFocus();
		we.setVisible(true);
		
	}

	public void render(){

		for(int y=1 ; y<height-1 ; y++)
		for(int x=1 ; x<width-1 ; x++){
			double srcX = x+(7.0*heightMap[flip][x-1][y]-heightMap[flip][x+1][y]);
			double srcY = y+(7.0*heightMap[flip][x][y-1]-heightMap[flip][x][y+1]);
			while(srcX<0)srcX+=width;
			while(srcX>=width)srcX-=width;
			while(srcY<0)srcY+=height;
			while(srcY>=height)srcY-=height;
			rendered.setRGB(x,y,background.getRGB((int)srcX,(int)srcY));
		}
	}
		
	private void update(){
		int source = flip;
		if (flip ==1)flip = 0;
		else flip = 1;
		int dest = flip;

		for(int y=1 ; y<height-1 ; y++)
		for(int x=1 ; x<width-1 ; x++){
			heightMap[dest][x][y] = (double)(0.98 *(
				        ( heightMap[source][x+1][y] +
				         heightMap[source][x-1][y] +
				         heightMap[source][x][y+1] +
				         heightMap[source][x][y-1])*.5 -
				         heightMap[dest][x][y]));
		}
	}

	public void run(){
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			
			}
			update();
			update();
			repaint();
		}

	}


	public void paint(Graphics g){
		render();
		// display rendered image
		g.drawImage(rendered,0,0,outWidth,outHeight,0,0,width,height,Color.GREEN,null);
	}

	public void mouseMoved (MouseEvent me) {
		int x = (int) me.getPoint().getX();
		int y = (int) me.getPoint().getY();
		x *= (double)width/outWidth;
		y *= (double)height/outHeight;


		if(x>0 && y>0 && x<width-5 && y<height-5){
			for(int dx=0 ; dx<9 ; dx++)
			for(int dy=0 ; dy<9 ; dy++){
				double bump = 0.5*((5-Math.abs(dx-5))*(5-Math.abs(dy-5)));
				heightMap[flip][x+dx][y+dy] += (double)bump;
			}
		}

	}

	public void mouseDragged (MouseEvent me) {
		mouseMoved(me);
	}

}