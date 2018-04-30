import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class PhotoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private BufferedImage myImage;
	private int maxSize;
	
	
	public PhotoPanel(int max){
		maxSize = max;
	}
	
	public boolean loadImage(File f){
		if(f==null)return false;
		BufferedImage temp;
		try {
			temp = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(temp == null)return false;
		double aspectRatio = (double)temp.getWidth()/temp.getHeight();
		if(aspectRatio > 1){
			myImage = new BufferedImage(maxSize,(int)(maxSize/aspectRatio),BufferedImage.TYPE_INT_RGB);
		}else{
			myImage = new BufferedImage((int)(maxSize*aspectRatio),maxSize,BufferedImage.TYPE_INT_RGB);
		}
		Graphics g = myImage.getGraphics();
		g.drawImage(temp, 0, 0, myImage.getWidth(), myImage.getHeight(), 0, 0, temp.getWidth(), temp.getHeight(), null);
		this.setSize(maxSize,maxSize);
		return true;
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(myImage,0,0,null);
	}
	
	public int getPixel(){
		Point p = this.getMousePosition();
		if(p==null)return 0;
		if(p.x<0 || p.x>=myImage.getWidth() || p.y<0 || p.y>=myImage.getHeight())return 0;
		return myImage.getRGB((int)p.getX(),(int) p.getY());
	}

	public void invertColors() {
		for(int y=0 ; y<myImage.getHeight() ; y++){
			for(int x=0 ; x<myImage.getWidth() ; x++){
				int pixel = myImage.getRGB(x, y);
				int r = (pixel & 0xFF0000) >> 16;
				int g = (pixel & 0x00FF00) >> 8;
				int b = (pixel & 0x0000FF) >> 0;
				r = 255-r;
				g = 255-g;
				b = 255-b;
				pixel = (r<<16) | (g<<8) | (b<<0);
				myImage.setRGB(x, y, pixel);
			}
		}
	}

	public void swapColors() {
		// TODO Auto-generated method stub
		for(int y=0 ; y<myImage.getHeight() ; y++){
			for(int x=0 ; x<myImage.getWidth() ; x++){
				int pixel = myImage.getRGB(x, y);
				int r = (pixel & 0xFF0000) >> 16;
				int g = (pixel & 0x00FF00) >> 8;
				int b = (pixel & 0x0000FF) >> 0;
				pixel = (g<<16) | (b<<8) | (r<<0);
				myImage.setRGB(x, y, pixel);
			}
		}
		
	}

	public void darken() {
		// TODO Auto-generated method stub
		for(int y=0 ; y<myImage.getHeight() ; y++){
			for(int x=0 ; x<myImage.getWidth() ; x++){
				int pixel = myImage.getRGB(x, y);
				int r = (pixel & 0xFF0000) >> 16;
				int g = (pixel & 0x00FF00) >> 8;
				int b = (pixel & 0x0000FF) >> 0;
				r *= 0.9;
				g *= 0.9;
				b *= 0.9;
				pixel = (r<<16) | (g<<8) | (b<<0);
				myImage.setRGB(x, y, pixel);
			}
		}
		
	}

	public void lighten() {
		// TODO Auto-generated method stub
		invertColors();
		darken();
		invertColors();		
	}

	public void makeGray() {
		// TODO Auto-generated method stub
		for(int y=0 ; y<myImage.getHeight() ; y++){
			for(int x=0 ; x<myImage.getWidth() ; x++){
				int pixel = myImage.getRGB(x, y);
				int r = (pixel & 0xFF0000) >> 16;
				int g = (pixel & 0x00FF00) >> 8;
				int b = (pixel & 0x0000FF) >> 0;
				int n = (r+g+b)/3;
				pixel = (n<<16) | (n<<8) | (n<<0);
				myImage.setRGB(x, y, pixel);
			}
		}
		
	}

	public void rotateClockwise() {
		// TODO Auto-generated method stub
		BufferedImage temp = myImage;
		myImage = new BufferedImage(temp.getHeight(),temp.getWidth(),BufferedImage.TYPE_INT_RGB);
		for(int x=0 ; x<temp.getWidth() ; x++){
			for(int y=0 ; y<temp.getHeight() ; y++){
				int pixel = temp.getRGB(x, y);
				myImage.setRGB(temp.getHeight()-y-1, x, pixel);
			}
		}
		
	}

	public void flipHorizontal() {
		// TODO Auto-generated method stub
		BufferedImage temp = myImage;
		myImage = new BufferedImage(temp.getWidth(),temp.getHeight(),BufferedImage.TYPE_INT_RGB);
		for(int x=0 ; x<temp.getWidth() ; x++){
			for(int y=0 ; y<temp.getHeight() ; y++){
				int pixel = temp.getRGB(x, y);
				myImage.setRGB(temp.getWidth()-1-x, y, pixel);
			}
		}
		
	}

	public int getMouseX() {
		Point p = this.getMousePosition();
		if(p==null)return 0;
		return (int)p.getX();
	}

	public int getMouseY() {
		Point p = this.getMousePosition();
		if(p==null)return 0;
		return (int)p.getY();
	}

}
