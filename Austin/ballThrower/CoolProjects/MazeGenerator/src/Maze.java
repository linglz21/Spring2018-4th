import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Maze extends JPanel{

	private static final long serialVersionUID = 1L;
	private ArrayList<Location> possibleLocations;
	private int[][] theMaze;
	private int rows;
	private int cols;
	private int width;
	private int height;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(g==null)return;
		if(theMaze == null) return;
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		double dX = (double)width/cols;
		double dY = (double)height/rows;
		for(int row = 0 ; row<rows ; row++){
			for(int col=0 ; col<cols ; col++){
				if(theMaze[row][col] == 1){
					g.setColor(Color.white);
					g.fillRect((int)(col*dX), (int)(row*dY), (int)dX+1, (int)dY+1);
				}
			}
		}
	}

	public void makeMaze(int rows, int cols) {
		possibleLocations = new ArrayList<Location>();
		possibleLocations.add(new Location(1,1));
		this.rows = rows;
		this.cols = cols;
		Dimension dim = this.getSize();
		width = dim.width;
		height = dim.height;
		theMaze = new int[rows][cols];
		
		while(possibleLocations.size()>0){
			int i = (int)(Math.random()*possibleLocations.size());
			Location loc = possibleLocations.remove(i);
			if(checkLocation(loc))addLocation(loc);
		}
	}
	
	public boolean checkLocation(Location loc){
		int x = loc.x;
		int y = loc.y;
		ArrayList<Location> taken = new ArrayList<Location>(); // store all the neighbors that are already in the maze
		if(checkMaze(x-1,y-1))taken.add(new Location(x-1,y-1));
		if(checkMaze(x-1,y))taken.add(new Location(x-1,y));
		if(checkMaze(x-1,y+1))taken.add(new Location(x-1,y+1));
		if(checkMaze(x+1,y+1))taken.add(new Location(x+1,y+1));
		if(checkMaze(x+1,y))taken.add(new Location(x+1,y));
		if(checkMaze(x+1,y-1))taken.add(new Location(x+1,y-1));
		if(checkMaze(x,y-1))taken.add(new Location(x,y-1));
		if(checkMaze(x,y+1))taken.add(new Location(x,y+1));
	
		if(taken.size()<2)return true; // If only one neighbor is in the maze then ok to add this location
		if(taken.size()==2)return areAdjacent(taken); // If only two and they are adjacent then still ok
		if(taken.size()==3)return areOnSameSide(taken); // If only three and all are in same row or collumn still ok
		return false;	// Otherwise don't add this location to the maze
	}
	
	public boolean areAdjacent(ArrayList<Location> list){
		if(list.size()!= 2)return false;
		Location a = list.get(0);
		Location b = list.get(1);
		int dx = a.x - b.x;
		int dy = a.y - b.y;
		return (dx*dx + dy*dy)==1; // Two locations are adjacent if distance between them == one
	}
	
	public boolean areOnSameSide(ArrayList<Location> list){
		if(list.size()!=3)return false;
		Location a = list.get(0);
		Location b = list.get(1);
		Location c = list.get(2);
		return (a.x==b.x && b.x==c.x) || (a.y==b.y && b.y==c.y); // Three locations are in same row or coll iff all x or all y are same
	}
	
	public boolean checkMaze(int x, int y){
		if(x<0 || y<0 || x>=cols || y>= rows) return true; // Any spot outside the Maze is "taken"
		return theMaze[y][x] == 1; // A spot is taken if it's location in the array == 1
	}
	
	public void addLocation(Location loc){
		int x = loc.x;
		int y = loc.y;
		theMaze[y][x] = 1; // Mark the location as taken
		if(!checkMaze(x-1,y))possibleLocations.add(new Location(x-1,y)); // Add vertical and horizontal neighbors 
		if(!checkMaze(x,y+1))possibleLocations.add(new Location(x,y+1));
		if(!checkMaze(x+1,y))possibleLocations.add(new Location(x+1,y));
		if(!checkMaze(x,y-1))possibleLocations.add(new Location(x,y-1));
	}
}
