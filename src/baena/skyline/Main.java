package baena.skyline;

import java.io.IOException;
import java.util.List;

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
		List<Solution> solution;
		try {
			solution = skyline.beginExecution();
			solution.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
