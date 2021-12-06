package baena.skyline;

public class Skyline {
	
	private int[] data;
	
	public Skyline(int size) {
		data = new int[size];
	}
	
	public int[] getData() {
		return data;
	}
	
	public int getSize() {
		return data.length;
	}
	
	public int[] getAbcissas() {
		int[] ordinates = new int[data.length/2];
		int j = 0;
		
		for(int i = 0; i < data.length; i = i + 2) {
			ordinates[j] = data[i];
			j++;
		}
		
		return ordinates;
	}
	
	public int[] getHeights() {
		int[] heights = new int[data.length/2];
		int j = 0;
		
		for(int i = 1; i < data.length; i = i + 2) {
			heights[j] = data[i];
			j++;
		}
		
		return heights;
	}
	
	public int addData(int x, int height, int index) {
		data[index] = x;
		data[index + 1] = height;
		
		return index + 2;
	}
}
