
class Vector2
{
	public double x,y,angle;
	public Vector2(){
	}

	public Vector2(String s){
		String[] split = s.split(",");
		if(split ==null || split.length<3){
			System.out.println("Trouble in Vector2 constructor: " + s);
			return;
		}
		x = new Double(split[0]);
		y = new Double(split[1]);
		angle = new Double(split[2]);
	}

	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Vector2(double x, double y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	public void add(Vector2 other){
		x += other.x;
		y += other.y;
		angle += other.angle;
	}

	public void mult(double d){
		x *= d;
		y *= d;
		angle *= d;
	}

	public Vector2 clone(){
		Vector2 temp = new Vector2();
		temp.add(this);
		return temp;
	}

	public String toString(){
		return x + "," + y + "," + angle;
	}


}
