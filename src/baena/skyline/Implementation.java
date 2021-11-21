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
			
			List<Point> sl = new ArrayList<>();
			
			sl.add(new Point(buildingVector[0].getX1(), buildingVector[0].getH()));
			sl.add(new Point(buildingVector[0].getX2(), 0));
			
			return sl;
		}
		
		List<Point> sl1 = buildings(Arrays.copyOfRange(buildingVector, 0, n/2));
		List<Point> sl2 = buildings(Arrays.copyOfRange(buildingVector, (n/2), n));
		
		return combine(sl1, sl2);
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
	
	private List<Point> combine(List<Point> sl1, List<Point> sl2) {

		List<Point> skyline = new ArrayList<Point>();
		int curH1=0, curH2=0, curX=0;

		while(!sl1.isEmpty() && !sl2.isEmpty()){

			if( sl1.get(0).getX() < sl2.get(0).getX() ){
				curX = sl1.get(0).getX();
				curH1 = sl1.get(0).getH();
				sl1.remove(0);
				skyline.add(new Point(curX, Math.max(curH1, curH2)));

			}else{
				curX = sl2.get(0).getX();
				curH2 = sl2.get(0).getH();
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

			boolean heightEquality = rightPoint.getH() == leftPoint.getH();
			boolean leftEquality = rightPoint.getX() == leftPoint.getX();

			if (leftEquality && !heightEquality)
				leftPoint.setH(Math.max(rightPoint.getH(), leftPoint.getH()));

			if (leftEquality || heightEquality)
				points.remove(i);
		}
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.println(message);
	}
}
