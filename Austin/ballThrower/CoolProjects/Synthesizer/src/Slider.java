import java.awt.*;
public class Slider
{
    int x,y;  float location, length;   int boxX, boxY;
    boolean selected, isHorizontal;    
    public Slider(int x, int y, float length, boolean horizontal){
        this.length = length;     isHorizontal = horizontal;   this.x = x;  boxX = x;
        location = (float)0.0;           selected = false;            this.y = y;  boxY = y;
    }
    public void select(int x, int y){
        int dx = boxX - x;          int dy = boxY - y;
        double distance = Math.sqrt(dx*dx + dy*dy);
        System.out.println(distance);
        selected = distance < 15;
    }
    public void move(int x, int y){
        if(selected){
          if(isHorizontal){                boxX = x;
            if(boxX < this.x)            boxX = this.x;
            if(boxX > this.x+length)     boxX = (int)(this.x+length);
            int dX = boxX - this.x;      location = dX/length;
          }else{                           boxY = y;
            if(boxY < this.y)            boxY = this.y;
            if(boxY > this.y+length)     boxY = (int)(this.y+length);
            int dY = boxY - this.y;      location = dY/length;
          }
        }
    }
    public void paint(Graphics g){
       g.setColor(Color.black);
       if(isHorizontal)            g.fillRect(x,y-1,(int)length, 2);
       else                       g.fillRect(x-1,y,2,(int)length);
       g.fillRect(boxX-5,boxY-5, 10,10);
    }
}
