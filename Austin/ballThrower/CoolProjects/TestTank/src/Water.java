import java.awt.Graphics;
import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.*;
import java.awt.image.*;



/*
 *make the distortion of the water
 *get the pixel values from the
 *opposite side of the screen if
 *the distortion is occuring on
 *the edge of the tank.  Right now
 *it looks black.
 *
 *Also, the distortion needs to
 *include the fish, right now
 *it is only distorting the water.
*/


class Water implements Sprite
{
	private BufferedImage background;
	private BufferedImage rendered;
	private double heightMap [][][];
	private int flip;
	private int height, width;
	private int pixelValues [][];

	public Water(int width, int height)
	{
		this.height = height;
		this.width = width;
		flip = 0;
		heightMap = new double [2][width/2][height/2];
		try{
			background = ImageIO.read(getClass().getResource("/resources/water.jpg"));
			rendered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		} catch (IOException e)
		{
		}
		pixelValues = new int[width][height];
		for(int y = 0; y < height; y++)
		for(int x = 0; x < width; x++){
			pixelValues[x][y] = background.getRGB(x, y);
			rendered.setRGB(x, y, background.getRGB(x, y));
		}
	}

	public void render()
	{
		for(int y = 1; y < height - 1; y++)
		{
			for(int x = 1; x < width - 1; x++)
			{
				int srcX = x + (int)(14.0 * heightMap[flip][x/2][y/2]);//(heightMap[flip][(x - 1)/2][y/2] - heightMap[flip][(x + 1)/2][y/2]));
				int srcY = y + (int)(14.0 * heightMap[flip][x/2][y/2]);//(heightMap[flip][x/2][(y - 1)/2] - heightMap[flip][x/2][(y + 1)/2]));
				if(srcX != x || srcY != y)
				{

					while(srcX < 0)
					{
						srcX += width;
					}
					while(srcX >= width)
					{
						srcX -= width;
					}

					while(srcY < 0)
					{
						srcY += height;
					}
					while(srcY >= height)
					{
						srcY -= height;
					}

					/*
					srcX = (width*5+srcX)%(width/2);
					srcY = (height*5+srcY)%(height/2);
					*/
					//System.out.println("srcX=" + srcX + " srcY=" + srcY + " x=" + x + " y=" + y);
					rendered.setRGB(x, y, pixelValues[srcX][srcY]);
				}
			}
		}
	}

	public void update()
	{
		int source = flip;
		if(flip == 1)
		{
			flip = 0;
		}else{
			flip = 1;
		}
		int dest = flip;
		for(int y = 1; y < height/2 - 1; y++)
		{
			for(int x = 1; x < width/2 - 1; x++)
			{
				heightMap[dest][x][y] = (float)(0.96 * ((heightMap[source][x + 1][y] + heightMap[source][x - 1][y] + heightMap[source][x][y + 1] + heightMap[source][x][y - 1]) * 0.5 - heightMap[dest][x][y]));
			}
		}
	}

	public void draw(Graphics g)
	{
		update();
		render();
		g.drawImage(background, 0, 0, null);
	}

	public void draw(Graphics g, ImageObserver ob)
	{
		update();
		update();
		render();
		g.drawImage(rendered, 0, 0, ob);
	}

	public void disturb(int x, int y)
	{
		x = x/2;
		y = y/2;
		if(x > 0 && y > 0 && x < width/2 -5 && y < height/2 - 5)
		{
			for(int dx = 0; dx < 5; dx++)
			{
				for(int dy = 0; dy < 5; dy++)
				{
					heightMap[flip][x + dx][y + dy] += (4 - Math.abs(dx - 2) - Math.abs(dy - 2))*2;
				}
			}
		}
	}
}
