package baena.skyline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	public List<Point> beginExecution() throws IOException {
		
		if(showHelp) printHelp();
		
		Building[] vectorBuildings = readData(entryDataPath);
		
		// TODO: Sort buildings in problem by ascending ordinates
		
		return buildings(vectorBuildings);
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
	private List<Point> buildings(Building[] buildingVector) {
		
		int n = buildingVector.length;
		
		if(n == 1){
			
			return processTrivialCase(buildingVector[0]);
		}
		
		List<Point> sl1 = buildings(Arrays.copyOfRange(buildingVector, 0, n/2));
		List<Point> sl2 = buildings(Arrays.copyOfRange(buildingVector, (n/2), n));
		
		return combine(sl1, sl2);
	}
	
	// This is the trivial solution
	private List<Point> processTrivialCase(Building building) {
		
		List<Point> points = new ArrayList<>();
		
		points.add(new Point(building.getX1(), building.getHeight()));
		points.add(new Point(building.getX2(), 0));
		
		return points;
	}
	
	private List<Point> combine(List<Point> sl1, List<Point> sl2) {

		List<Point> skyline = new ArrayList<>();
		int curH1=0;
		int curH2=0;
		int curX=0;

		while(!sl1.isEmpty() && !sl2.isEmpty()){

			if(sl1.get(0).getX() < sl2.get(0).getX()){
				curX = sl1.get(0).getX();
				curH1 = sl1.get(0).getHeight();
				sl1.remove(0);
				skyline.add(new Point(curX, Math.max(curH1, curH2)));

			}else{
				curX = sl2.get(0).getX();
				curH2 = sl2.get(0).getHeight();
				sl2.remove(0);
				skyline.add(new Point(curX, Math.max(curH1, curH2)));
			}

		}
		if(sl1.isEmpty()){
			skyline.addAll(sl2);
		}else if(sl2.isEmpty()){
			skyline.addAll(sl1);
		}
		
		removeRedundant(skyline);

		return skyline;
	}
	
	private static void removeRedundant(List<Point> points) {
		for (int i = points.size() - 1; i > 0; i--) {
			Point rightPoint = points.get(i);
			Point leftPoint = points.get(i - 1);

			boolean heightEquality = rightPoint.getHeight() == leftPoint.getHeight();
			boolean leftEquality = rightPoint.getX() == leftPoint.getX();

			if (leftEquality && !heightEquality)
				leftPoint.setHeight(Math.max(rightPoint.getHeight(), leftPoint.getHeight()));

			if (leftEquality || heightEquality)
				points.remove(i);
		}
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.println(message);
	}
}
