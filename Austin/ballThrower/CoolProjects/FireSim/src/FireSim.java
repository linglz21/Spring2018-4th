
import java.awt.*;

import javax.swing.*;
import java.util.*;
import java.awt.image.*;

public class FireSim extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
    private BufferedImage offscreen;
    private BufferedImage offscreen2;
    public Graphics bufferGraphics;
    private ArrayList<Particle> myParticles;
    private GaussianFilter myFilter;
    private double wind;
    private int quantity;

    public FireSim()
    {
        quantity = 300;
        wind = 0.0;
        myFilter = new GaussianFilter(3);
        offscreen = new BufferedImage(WIDTH,HEIGHT, BufferedImage. TYPE_INT_RGB);
        offscreen2 = new BufferedImage(WIDTH,HEIGHT, BufferedImage. TYPE_INT_RGB);
        bufferGraphics = offscreen.getGraphics();
		Graphics2D g2 = (Graphics2D)bufferGraphics;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
        myParticles = new ArrayList<Particle>();
    }
    
    public static void main(String[] args) {
		FireSim f = new FireSim();
		f.setSize(WIDTH, HEIGHT);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(f).start();
		f.setVisible(true);
	}
    
    
    
    public void run(){
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	while(true){
    		/*
    		try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
	        wind += Math.random()*0.4-0.2;
	        wind *= 0.99;
	        if(quantity<300) quantity +=1;
	        if(quantity>300) quantity -= 1;
	        quantity += (int)(Math.random()*50)-25;
	        for(int i=0 ; i<quantity ; i++){
	            
	            double speedX = Math.random()*1.5-1+wind;
	            double speedY = Math.random()*4-4;
	            if(speedX*speedX+speedY*speedY < 2){
	                Particle p = new Particle(200,300,speedX*2,speedY);
	                myParticles.add(p);
	            }
	        }
	        for(Particle p : myParticles)p.update();
	        for(int i=0 ; i<myParticles.size() ; i++){
	            Particle p  = myParticles.get(i);
	            if(p.getAge() > 4){
	                myParticles.remove(p);
	                i--;
	            }
	        }
	        paint(getGraphics());
	        blur();
	        shift();
    	}
        
    }
    
    public void blur(){
        myFilter.filter(offscreen,offscreen2);
        bufferGraphics.drawImage(offscreen2,0,0,this);
    }
    
    public void shift(){
        for(int y=0 ; y<HEIGHT-3 ; y++){
            for(int x=0 ; x<WIDTH-1 ; x++){
                offscreen.setRGB(x,y,offscreen.getRGB(x+1,y+3));
            }
        }
    }

    public void paint(Graphics g)
    {  
        g.drawImage(offscreen,0,0,this);
        bufferGraphics.setColor(new Color(1,1,1,10));
        bufferGraphics.fillRect(0,0,WIDTH,HEIGHT);
        for(Particle p : myParticles) p.draw(bufferGraphics);
    }

}
