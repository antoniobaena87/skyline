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
		
		if(i < j) {
			int mid = i + (j - i) / 2;
			return combine(buildings(buildingVector, i, mid), buildings(buildingVector, mid + 1, j));
		
		} else {
			return convertBuildingIntoSkyline(buildingVector[i]);	
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
		
		int n = s1.getSize() / 2;
		int m = s2.getSize() / 2;
		int i = 0;
		int j = 0;
		int k = 0;
		
		int[] data1 = s1.getData();
		int[] data2 = s2.getData();
		
		int h1 = 0;
		int h2 = 0;
		
		Skyline s = new Skyline(n*2);
		
		while(i < n && j < m) {
			int x = 0;
			int h = 0;
			
			if(data1[i] < data2[j]) {
				x = data1[i];
				h1 = data1[i+1];
				h = Math.max(h1, h2);
				i += 2;
				
			} else if(data1[i] > data2[j]) {
				x = data2[j];
				h2 = data2[j+1];
				h = Math.max(h1,  h2);
				j += 2;
				
			} else {
				x = data1[i];
				h1 = data1[i+1];
				h2 = data2[j+1];
				h = Math.max(h1, h2);
				i += 2;
				j += 2;
			}
			
			if(s.getData().length == 0 || h != s.getData()[s.getData().length - 1]) {
				k = s.addData(x, h, k);
				
			}
		}
		
		return s;
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.println(message);
	}
}
