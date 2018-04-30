import java.awt.Graphics;
import java.awt.Color;

public class Leaf
{
    private double x,y,vX,vY;
    private Color myColor;
    private double myResistance;
    private int size;

    public Leaf(double x, double y, Color c, int size)
    {
        this.size = size;
        this.x = x;
        this.y = y;
        myColor = c;
        myResistance = Math.random()*0.5 + 0.5;
    }
    
    public void update(double fX, double fY){
        vX += fX*myResistance;
        vY += 0.5 + fY*myResistance;
        x += vX;
        y += vY;
        vX *= 0.95;
        vY *= 0.93;
    }
    
    public void paint(Graphics g){
        g.setColor(myColor);
        g.fillOval((int)x,(int)y,size,size);
    }

}
