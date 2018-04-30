import java.awt.*;

public class Face
{
    private int x,y;
    private int lEyeX,lEyeY;
    private int rEyeX,rEyeY;
    private int mouth;
    private Color eyeColor;
    
    private boolean mouthOpen;
    
    public Face(int theX, int theY)
    {
        mouthOpen = false;
        x = theX;
        y = theY;
        lEyeX = x+15;
        lEyeY = y+15;
        rEyeX = x+35;
        rEyeY = y+15;
        mouth = (int)(Math.random()*10);
        eyeColor = randColor();
    }
    
    public void move(int dX, int dY){
        x += dX;
        y += dY;
    }
    
    public boolean overlaps(Face other){
        double dX = this.x - other.x;
        double dY = this.y - other.y;
        return Math.sqrt(dX*dX + dY*dY) < 70;
    }
    
   
    
    public boolean checkMouth(int eventX, int eventY){
        double dX = eventX - (x+30);
        double dY = eventY - (y+30);
        double distance = Math.sqrt(dX*dX + dY*dY);
        mouthOpen = distance<30;
        return mouthOpen;
    }
    
    public void lookAt(int eventX, int eventY){
        int lCenterX = x+20;
        int rCenterX = x+40;
        int CenterY = y+20;
        int dX = eventX - lCenterX;
        int dY = eventY - CenterY;        
        double lAngle = Math.atan2(dY,dX);
        dX = eventX - rCenterX;
        double rAngle = Math.atan2(dY,dX);
        dX = (int)(5 * Math.cos(lAngle));
        dY = (int)(5 * Math.sin(lAngle));
        lEyeX = lCenterX + dX - 5;
        lEyeY = CenterY + dY - 5;
        
        dX = (int)(5 * Math.cos(rAngle));
        dY = (int)(5 * Math.sin(rAngle));
        rEyeX = rCenterX + dX - 5;
        rEyeY = CenterY + dY - 5;
    }
    
    private Color randColor(){
        int r = (int)(Math.random()*255);
        int g = (int)(Math.random()*255);
        int b = (int)(Math.random()*255);
        return new Color(r,g,b);
    }
    
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillOval(x-2,y-2,64,64);
        g.setColor(Color.yellow);
        g.fillOval(x,y-5,60,60);
        g.setColor(new Color(232,213,169));
        g.fillOval(x,y,60,60);
        g.setColor(Color.black);
        g.fillOval(x+11,y+8,18,18);
        g.fillOval(x+31,y+8,18,18);        
        g.setColor(Color.white);
        g.fillOval(x+10,y+10,20,20);
        g.fillOval(x+30,y+10,20,20);
        g.setColor(eyeColor);
        g.fillOval(lEyeX,lEyeY,10,10);
        g.fillOval(rEyeX,rEyeY,10,10);
        g.setColor(Color.black);
        g.fillOval(lEyeX+3,lEyeY+3,4,4);
        g.fillOval(rEyeX+3,rEyeY+3,4,4);
        g.setColor(Color.red);
        if(mouthOpen){
            g.fillOval(x+15,y+42,30,10+mouth);
        }else{
            g.fillOval(x+15,y+42,30,2+mouth);
        }
    }

}
