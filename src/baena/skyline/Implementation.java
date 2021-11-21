package baena.skyline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Implementation {
	
	private boolean showTrace;
	private boolean showHelp;
	private String entryDataPath;
	
	public Implementation(boolean showTrace, boolean showHelp, String entryDataPath) {
		this.showTrace = showTrace;
		this.showHelp = showHelp;
		this.entryDataPath = entryDataPath;
	}
	
	public Skyline beginExecution() throws IOException {
		
		if(showHelp) printHelp();
		
		Building[] vectorBuildings = readData(entryDataPath);
		
		// TODO: Sort buildings in problem by ascending ordinates
		
		return buildings(vectorBuildings, 0, vectorBuildings.length - 1);
	}
	
	/**
	 * Reads the entry file and transforms it into the main problem.
	 * @throws IOException 
	 */
	private Building[] readData(String entryDataFilePath) throws IOException {
		
		List<Building> buildingList = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(entryDataFilePath).getPath()))){
			String line = br.readLine();
			
			while(line != null) {
				String[] data = line.split(",");
				Building newBuilding = new Building(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
				
				buildingList.add(newBuilding);
				
				line = br.readLine();
			}
		}
		
		return buildingList.toArray(new Building[buildingList.size()]);
	}
	
	private void printHelp() {
		System.out.println(
				"SINTAXIS: skyline [-t][-h] [fichero entrada] [fichero salida]\n" +
						"\t-t\t\t\tTraza cada llamada recursiva y sus parámetros\n" +
						"\t-h\t\t\tMuestra esta ayuda\n" +
						"\t[fichero entrada]\tConjunto de edificios de la ciudad\n" +
						"\t[fichero salida]\tSecuencia que representan el skyline de la ciudad\n"
				);
	}
	
	/**
	 * Method where the divide and conquer algorithm takes place.
	 * 
	 * @param problem
	 * @return
	 */
	private Skyline buildings(Building[] buildingVector, int i, int j) {
		
		int m = (i + j-1)/2;
		int n = j-i+1;
		
		Skyline s1;
		Skyline s2;
		
		if(n == 1) {
			return convertBuildingIntoSkyline(buildingVector[i]);	// trivial solution
		} else {
			s1 = buildings(buildingVector, i, m);
			s2 = buildings(buildingVector, m+1, j);
			return combine(s1, s2);
		}
	}
	
	// This is the trivial solution
	private Skyline convertBuildingIntoSkyline(Building building) {
		
		int x1 = building.getX1();
		int x2 = building.getX2();
		int h = building.getH();
		Skyline skyline = new Skyline(4);
		int i = 0;
		
		skyline.addData(x1, h, i);
		i += 2;
		skyline.addData(x2, 0, i);
		
		return skyline;
	}
	
	private Skyline combine(Skyline s1, Skyline s2) {
		
		int n = s1.getSize();	// size or size / 2 ??
		int m = s2.getSize();
		int i = 1;
		int j = 1;
		int k = 1;
		int[] s1x = s1.getAbcissas();
		int[] s2x = s2.getAbcissas();
		int[] s1h = s1.getHeights();
		int[] s2h = s2.getHeights();
		Skyline s = new Skyline(n);
		
		while(i <= n || j <= m) {
			
			int x = Math.min(s1x[i], s2x[j]);
			int max;
			
			if(s1x[i] < s2x[j]) {
				max = Math.max(s1h[i], s2h[j-1]);
				j++;
			} else {
				max = Math.max(s1h[i], s2h[j]);
				i++;
				j++;
			}
			
			s.addData(x, max, k);
			k++;
		}
		
		return s;
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.println(message);
	}
}
