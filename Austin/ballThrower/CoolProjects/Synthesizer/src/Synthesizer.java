
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Synthesizer extends JApplet implements MouseMotionListener, MouseListener
{
    Button myButton;
    Note myNote; 
    Button myButton2;
    Note myNote2; 
    Slider mySlider;    
    Dimension dim; 
    SoundMaker myMaker;
    public void init() {
        dim = getSize(); 
        myButton = new Button(100,100,50,20,"Button");    
        mySlider = new Slider(25,25,100,false);
        myNote = new Note(18.35*32, 1.0, 0.5);  
        myMaker = new SoundMaker(); 
        myMaker.start();
        addMouseListener(this);    
        addMouseMotionListener(this);
    }
    public void paint(Graphics g){
        g.clearRect(0,0,dim.width,dim.height);   
        myButton.paint(g);    
        mySlider.paint(g);
    }
    public void mouseMoved(MouseEvent e){
        System.out.println("mouseMoved");
        int x = e.getX();     
        int y = e.getY();     
        myButton.checkOver(x,y);    
        mySlider.move(x,y); 
        repaint();
    }
    public void mouseReleased(MouseEvent e){
        myNote = new Note(1500*mySlider.location+50, 2.0, 0.5);
        int x = e.getX();     
        int y = e.getY();    
        myButton.isPressed = false;     
        mySlider.selected = false; 
        repaint();
    }
    public void mousePressed(MouseEvent e){
        int x = e.getX();     
        int y = e.getY();    
        myButton.checkPress(x,y);
        if(myButton.isPressed) myMaker.play(myNote.samples);
        mySlider.select(x,y); 
        repaint();
    }
    public void mouseDragged(MouseEvent e){
        int x = e.getX();     
        int y = e.getY();    
        mySlider.move(x,y);  
        repaint();
    }
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}

}
