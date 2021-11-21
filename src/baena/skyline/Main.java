package baena.skyline;

import java.io.IOException;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		boolean showTrace = false;
		boolean showHelp = false; 
		Implementation implementation;
		
		for(String arg:args) {
			if(arg.equals(Constants.TRACE_PARAM)) {
				showTrace = true;
			}else if(arg.equals(Constants.HELP_PARAM)) {
				showHelp = true;
			}
		}
		
		implementation = new Implementation(showTrace, showHelp, "data.d");
		List<Point> skyline;
		try {
			skyline = implementation.beginExecution();
			System.out.println(skyline);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
