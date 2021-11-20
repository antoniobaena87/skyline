package baena.skyline;

public class Solution {
	
	int x, h;
	
	public Solution(Building building) {
		this.x = building.getX1();
		this.h = building.getH();
	}
	
	public String getTrace() {
		return "";
	}
	
	public int getOrdinate() {
		return x;
	}
	
	public int getHeight() {
		return h;
	}
}
