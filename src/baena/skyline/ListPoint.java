package baena.skyline;

import java.util.ArrayList;

public class ListPoint extends ArrayList<Point> {
	
	private static final long serialVersionUID = 570111141163777365L;
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("[");
		
		for(int i = 0; i < size(); i++) {
			strBuilder.append("[X: " + get(i).getX() + ", H: " + get(i).getHeight() + "]" + ",");
		}
		
		if(size() > 0) strBuilder.deleteCharAt(strBuilder.length() - 1);
		strBuilder.append("]");
		
		return strBuilder.toString();
	}
	
	public String getInOutputDataFormat() {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("(");
		
		for(int i = 0; i < size(); i++) {
			strBuilder.append(get(i).getX() + ", " + get(i).getHeight() + ", ");
		}
		
		if(size() > 0) {
			strBuilder.deleteCharAt(strBuilder.length() - 1);
			strBuilder.deleteCharAt(strBuilder.length() - 1);
		}
		strBuilder.append(")");
		
		return strBuilder.toString();
	}
}
