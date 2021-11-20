package baena.skyline;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		
		boolean showTrace = false;
		boolean showHelp = false; 
		Skyline skyline;
		
		for(String arg:args) {
			if(arg.equals(Constants.TRACE_PARAM)) {
				showTrace = true;
			}else if(arg.equals(Constants.HELP_PARAM)) {
				showHelp = true;
			}
		}
		
		skyline = new Skyline(showTrace, showHelp, "data.d");
		try {
			Solution solution = skyline.beginExecution();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
