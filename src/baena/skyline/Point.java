package baena.skyline;

public class Point {
	
	private int x;
	private int h;
	
	public Point(int x, int h){
		this.x = x;
		this.h = h;
	}
	
	public int getX() {
		return x;
	}
	
	public int getH() {
		return h;
	}
	
	public void setH(int h) {
		this.h = h;
	}
}
