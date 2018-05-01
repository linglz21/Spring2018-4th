import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
//Like player cell, but is controlled by mouse movement
public class UserCell extends Cell {
	private static final long serialVersionUID = 1L;
	UserCell(Color color, int mass) {
		super(color, mass);
	}
	UserCell(Cell cell) {
		super(cell.color, (int)cell.mass);
		this.mapPosX = cell.mapPosX;
		this.mapPosY = cell.mapPosY;
		this.screenX = cell.screenX;
		this.screenY = cell.screenY;
		this.velocityX = cell.velocityX;
		this.velocityY = cell.velocityY;
	}

	public void update() {
		super.update();
		if(Game.self == null)return;
		Point mouse = Game.self.getMousePosition();
		if (mouse == null)return;
		//Point mouse = MouseInfo.getPointerInfo().getLocation();
		//System.out.println("[UserCell] Mouse coordinates: "+mouse.getX()+", "+mouse.getY());
		//System.out.println("[UserCell] Cell coordinates: "+mapPosX+", "+mapPosY);
		double deltaX = mouse.getX() - this.getCenter().x;
		double deltaY = mouse.getY() - this.getCenter().y;
		double length = getDistanceTo(mouse);
		if(length != 0){
			deltaX /= length;
			deltaY /= length;
			//System.out.println("[UserCell] Delta X: "+deltaX+" Delta Y: "+deltaY);
			this.velocityX = deltaX*(10000-mass)/10000;
			this.velocityY = deltaY*(10000-mass)/10000;
			//System.out.println("[UserCell] Velocity X: "+velocityX+" Velocity Y: "+velocityY);
		} 
	}

}
