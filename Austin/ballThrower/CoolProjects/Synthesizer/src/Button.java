import java.awt.*;

public class Button
{
    int x, y, width, height;
    public boolean isPressed, isOver;
    String text;
    public Button(int x, int y, int width, int height, String text) {
        this.x = x;        this.y = y;          this.width = width;   this.height = height;
        this.text = text;  isPressed = false;   isOver = false;
    }
    private boolean check(int x, int y){
        return   x> this.x   &&   y > this.y   &&   x< this.x + width   &&   y < this.y + height;
    }
    public void checkOver(int x, int y){        isOver = check(x,y);  }
    public void checkPress(int x, int y){       isPressed = check(x,y); }
    public boolean checkRelease(int x, int y){  return check(x,y);   }
    public void paint (Graphics g){
        Color btnColor;    int dx=0, dy=0;
        if(isOver)   btnColor = new Color(159,168,212);
        else         btnColor = new Color(109,116,145);   
        if(isPressed){   dx = 2; dy = 2;    }
        g.setColor(Color.BLACK);       g.fillRect(x-1+dx,y-1+dy,width+2,height+2);
        g.setColor(btnColor);     g.fillRect(x+dx,y+dy,width,height);
        g.setColor(Color.WHITE);       g.drawString(text,x+5+dx,y+height-5+dy);
    }
}
