import java.awt.*;

public class Particle
{
    private double dX;
    private double x,y;
    private double speedX, speedY;
    private double age;
    private double ageRate;
    private int size;
    private Color[] colors;

    public Particle(int x, int y, double speedX, double speedY)
    {
        colors = new Color[5];
        colors[0] = new Color(0,0,0,128);
        colors[1] = new Color(255,128,0,128);
        colors[2] = new Color(255,255,0,128);
        colors[3] = new Color(180,0,0,128);
        colors[4] = new Color(0,0,0,128);
        ageRate = Math.random()*0.1+0.05;
        size = (int)(Math.random()*8)+3;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.dX = this.speedX + Math.random()*0.3-0.15;
        this.speedY = speedY;
        this.age = 0;
    }
    
    public Color blend(Color a, Color b, double c){
        int rA = a.getRed();
        int gA = a.getGreen();
        int bA = a.getBlue();
        int rB = b.getRed();
        int gB = b.getGreen();
        int bB = b.getBlue();
        int red = (int)(c*rA + (1-c)*rB);
        int green = (int)(c*gA + (1-c)*gB);
        int blue = (int)(c*bA + (1-c)*bB);
        return new Color(red,green,blue,128);
    }
    
    public void update(){
        speedX -= dX/40 +0.01;
        speedY -= 0.05;
        x += speedX;
        y += speedY;
        age += ageRate;
    }
    
    public double getAge(){
        return age;
    }
    
    public void draw(Graphics g){
        //if(age<0.25)return;
        int a = (int)age;
        g.setColor(blend(colors[a],colors[a+1],1-(age-a)));
        g.fillOval((int)x,(int)y,(int)(size-age),(int)(size-age));
    }
}
