package baena.skyline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Implementation {
	
	private boolean showTrace;
	private String entryDataPath;
	private FileHandler fileHandler;
	
	public Implementation(boolean showTrace, boolean showHelp, String entryDataPath) {
		this.showTrace = showTrace;
		this.entryDataPath = entryDataPath;
		fileHandler = new FileHandler();
		
		if(showHelp) printHelp();
	}
	
	public ListPoint beginExecution() throws IOException {
		
		Building[] vectorBuildings = fileHandler.readData(entryDataPath); // cost N
		
		// Sort buildings in problem by ascending ordinates
		// BuildingSorter.sortBuildings(vectorBuildings);	// cost of O(n log n)
		
		return buildings(vectorBuildings, "1");
	}
	
	public void saveData(ListPoint skyline, String saveDataPath) throws FileNotFoundException {
		fileHandler.saveData(skyline, saveDataPath);
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
	 * Each recursive call to this method divides the problem by half, and calls it again twice. So our parameters to calculate the cost is:
	 * 
	 * a = 2
	 * b = 2
	 * k = 0
	 * 
	 * the iterative cost is T(n) = O(n * log n)
	 * 
	 * @param problem
	 * @return
	 */
	private ListPoint buildings(Building[] buildingVector, String iterationNumber) {
		
		printTrace("Llamada número " + iterationNumber + " al método buildings.\n");	// constant cost
		printTrace("Vector de edificios: " + Arrays.toString(buildingVector) + "\n");	// cost of Arrays.toString() is the size of the buildingVector
		
		int n = buildingVector.length;
		
		if(n == 1){
			return processTrivialCase(buildingVector[0]);	// constant cost
		}
		
		printTrace("Dividiendo el vector de edificios en dos mitades...\n\n");
		
		// Two recursive calls, each dividing the array by half -> a = b = 2
		ListPoint sl1 = buildings(Arrays.copyOfRange(buildingVector, 0, n/2), iterationNumber + "." + 1);
		ListPoint sl2 = buildings(Arrays.copyOfRange(buildingVector, (n/2), n), iterationNumber + "." + 2);
		
		return combine(sl1, sl2, iterationNumber);	// cost is sl1.size + sl2.size, each one being N/2, being N the length of buildingVector
	}
	
	// This is the trivial solution. The cost is constant
	private ListPoint processTrivialCase(Building building) {
		
		ListPoint points = new ListPoint();
		
		points.add(new Point(building.getX1(), building.getHeight()));
		points.add(new Point(building.getX2(), 0));
		
		printTrace("Añadiendo solución trivial:\n"
				+ "\tPunto " + building.getX1() + ", altura " + building.getHeight() + "\n"
				+ "\tPunto " + building.getX2() + ", altura 0\n\n");
		
		return points;
	}
	
	/**
	 * Combines two skylines into one that serves as solution.
	 * 
	 * Cost is 2 * (sl1.size + sl2.size) => O(n+m)
	 * 
	 * @param sl1
	 * @param sl2
	 * @param iterationNumber
	 * @return
	 */
	private ListPoint combine(ListPoint sl1, ListPoint sl2, String iterationNumber) {

		ListPoint skyline = new ListPoint();
		int curH1=0;
		int curH2=0;
		int curX=0;
		
		printTrace("Comenzando combinación de skylines para iteración " + iterationNumber + "...");
		printTrace("\nSkyline 1: " + sl1.toString());
		printTrace("\nSkyline 2: " + sl2.toString() + "\n");

		while(!sl1.isEmpty() && !sl2.isEmpty()){	// number of iterations is Min(n,m), being n the size of sl1 and m the size of sl2

			if(sl1.get(0).getX() < sl2.get(0).getX()) {
				curX = sl1.get(0).getX();
				curH1 = sl1.get(0).getHeight();
				sl1.remove(0);											// constant cost
				skyline.add(new Point(curX, Math.max(curH1, curH2)));	// constant cost

			} else {
				curX = sl2.get(0).getX();
				curH2 = sl2.get(0).getHeight();
				sl2.remove(0);
				skyline.add(new Point(curX, Math.max(curH1, curH2)));
			}
		}
		
		if(sl1.isEmpty()) {
			skyline.addAll(sl2);	// cost m
		} else if(sl2.isEmpty()) {
			skyline.addAll(sl1);	// cost n
		}
		
		printTrace("Comenzando comprobación de puntos redundantes...");
		ListPoint removedPoints = removeRedundant(skyline);				// cost is n + m
		printTrace(" Puntos eliminados: " + removedPoints + "\n");
		
		printTrace("Skyline resultado de la combinación: " + skyline.toString() + "\n\n");

		return skyline;
	}
	
	/**
	 * Removes redundant points. These points are points that are along a vertical line of the skyline 
	 * or points along a horizontal line of the skyline.
	 * 
	 * @param points
	 * @return A ListPoint object containing the removed points.
	 */
	private static ListPoint removeRedundant(ListPoint points) {
		
		ListPoint removedPoints = new ListPoint();
		
		for (int i = points.size() - 1; i > 0; i--) {
			Point rightPoint = points.get(i);
			Point leftPoint = points.get(i - 1);

			boolean horizontalEquality = (rightPoint.getHeight() == leftPoint.getHeight());
			boolean verticalEquality = (rightPoint.getX() == leftPoint.getX());

			if (verticalEquality && !horizontalEquality)
				// Same ordinate, select highest height and set it for the left point so we can remove the one on the right
				leftPoint.setHeight(Math.max(rightPoint.getHeight(), leftPoint.getHeight()));

			if (verticalEquality || horizontalEquality) {
				removedPoints.add(points.get(i));
				points.remove(i);
			}
		}
		
		return removedPoints;
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.print(message);
	}
}
