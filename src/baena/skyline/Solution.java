package baena.skyline;

public class Solution {
	
	private int x1;
	private int x2;
	private int h;
	
	public Solution(int x1, int x2, int h) {
		this.x1 = x1;
		this.x2 = x2;
		this.h = h;
	}
	
	public String getTrace() {
		return "";
	}
	
	public int getOrdinate() {
		return x1;
	}
	
	public int getEndLine() {
		return x2;
	}
	
	public void setEndLine(int endPoint) {
		this.x2 = endPoint;
	}
	
	public int getHeight() {
		return h;
	}
	
	public void setHeight(int h) {
		this.h = h;
	}
	
	@Override
	public String toString() {
		return x1 + "," + h;
	}
}
