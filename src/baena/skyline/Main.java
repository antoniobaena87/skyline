package baena.skyline;

import java.io.IOException;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		boolean showTrace = false;
		boolean showHelp = false; 
		String entryDataPath = null;
		String exitDataPath = null;
		
		Implementation implementation;
		
		for(String arg:args) {
			if(arg.equals(Constants.TRACE_PARAM)) {
				showTrace = true;
			}else if(arg.equals(Constants.HELP_PARAM)) {
				showHelp = true;
			}else {
				if(entryDataPath == null) entryDataPath = arg;
				else exitDataPath = arg;
			}
		}
		
		implementation = new Implementation(showTrace, showHelp, entryDataPath);
		ListPoint skyline;
		
		try {
			skyline = implementation.beginExecution();
			if(exitDataPath != null) {
				implementation.saveData(skyline, exitDataPath);
				System.out.println("Algoritmo ejecutado y guardado en archivo de salida con éxito.");
			} else {
				System.out.println("Resultado del algoritmo:\n" + skyline);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
