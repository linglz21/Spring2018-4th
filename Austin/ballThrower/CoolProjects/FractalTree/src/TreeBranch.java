import java.awt.Graphics;
import java.awt.Color;

public class TreeBranch
{
    private Point start,stop;
    private double angle;
    private TreeBranch left,right;
    private Double length;
    private Color red;
    private Color brown;
    private Color lightGreen;
    private double fade;
    private double fadeRate;
    private Color fallLeaf;
    private boolean leavesGone;
    public static FractalTree parent;
    
    public TreeBranch(Point start, Double angle)
    {
        fadeRate = Math.random()*0.01+0.01;
        leavesGone = false;
        red = new Color(130,3,34);
        brown = new Color(92,66,6);
        lightGreen = new Color(72,227,0);
        int r = (int)(Math.random()*64)+191;
        int g = (int)(Math.random()*128)+128;
        int b = (int)(Math.random()*128)+64;
        fallLeaf = new Color(r,g,b);
        fade = 0.0;
        
        this.start = start;
        this.stop = new Point(start.x,start.y);
        this.angle = angle;
        left = null;
        right = null;

        length = 3.0;
    }
    
    public void update(Point start){
        if(length>43){
            fade();
            return;
        }
        this.start = start;
        length += 0.04;
        double dx = length * Math.sin(angle);
        double dy = length * Math.cos(angle);
        stop.x = start.x + (int)dx;
        stop.y = start.y + (int)dy;
        if(stop.y < 600 || stop.y < start.y){
            if(left==null && Math.random() < length/550) left = new TreeBranch(stop, angle +0.3+Math.random()*0.25);
            if(right==null&& Math.random() < length/550) right = new TreeBranch(stop, angle -0.3-Math.random()*0.25);
        }else{
            left = null;
            right = null;
            stop.y +=1;
            length -=0.04;
        }
        if(left!=null)left.update(stop);
        if(right!=null)right.update(stop);
        
    }
    
    private void fade(){
        fade += fadeRate;
        if(!leavesGone && fade>3.0 && Math.random()<0.001){
            parent.myLeaves.add(new Leaf(stop.x,stop.y,fallLeaf,6));
            leavesGone = true;
        }
        if(Math.random()<0.97){
            if(left!=null)left.fade();
            if(right!=null)right.fade();
        }
    }
    
    public void paint(Graphics g){
        Color leaf;
        if(fade<0.99)leaf = interpolate(fallLeaf,lightGreen,fade);
        else leaf = fallLeaf;
        Color stem = interpolate(brown,red,length/40.0);
        int d = (int)(length/2);
        if(left!=null){
            left.paint(g);
        }else if(!leavesGone){
            g.setColor(leaf);
            g.fillOval(stop.x-2-d/2,stop.y-2-d/2,6,6);
        }
        if(right!=null){
            right.paint(g);
        }else if(!leavesGone){
            g.setColor(leaf);
            g.fillOval(stop.x-2-d/2,stop.y-2-d/2,6,6);
        }
        if(length<5){
            g.setColor(stem);
            g.drawLine(start.x,start.y,stop.x,stop.y);
        }else{
            drawThickLine(g,start.x,start.y,stop.x,stop.y, (int)(length/5)+1, stem);
        }
    }
    
    private Color interpolate(Color c1, Color c2, double amount){
        double red = amount*c1.getRed() + (1-amount)*c2.getRed();
        double green = amount*c1.getGreen() + (1-amount)*c2.getGreen();
        double blue = amount*c1.getBlue() + (1-amount)*c2.getBlue();
        return new Color((int)red,(int)green, (int)blue);
    }
    
    
    public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) {
      // The thick line is in fact a filled polygon
      g.setColor(c);
      int dX = x2 - x1;
      int dY = y2 - y1;
      // line length
      double lineLength = Math.sqrt(dX * dX + dY * dY);
      double scale = (double)(thickness) / (2 * lineLength);

      // The x,y increments from an endpoint needed to create a rectangle...
      double ddx = -scale * (double)dY;
      double ddy = scale * (double)dX;
      ddx += (ddx > 0) ? 0.5 : -0.5;
      ddy += (ddy > 0) ? 0.5 : -0.5;
      int dx = (int)ddx;
      int dy = (int)ddy;

      // Now we can compute the corner points...
      int xPoints[] = new int[4];
      int yPoints[] = new int[4];

      xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
      xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
      xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
      xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

      g.fillPolygon(xPoints, yPoints, 4);
    }


}
