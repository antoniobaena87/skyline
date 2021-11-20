package baena.skyline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Skyline {
	
	private boolean showTrace;
	private boolean showHelp;
	private String entryDataPath;
	
	public Skyline(boolean showTrace, boolean showHelp, String entryDataPath) {
		this.showTrace = showTrace;
		this.showHelp = showHelp;
		this.entryDataPath = entryDataPath;
	}
	
	public Solution beginExecution() throws IOException {
		
		if(showHelp) printHelp();
		
		Problem mainProblem = readData(entryDataPath);
		
		// TODO: Order buildings in problem by ascending x1 ordinate
		
		return solveProblem(mainProblem);
	}
	
	/**
	 * Reads the entry file and transforms it into the main problem.
	 * @throws IOException 
	 */
	private Problem readData(String entryDataFilePath) throws IOException {

		Problem mainProblem = new Problem();
		try (BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(entryDataFilePath).getPath()))){
			String line = br.readLine();
			
			while(line != null) {
				String[] data = line.split(",");
				Building newBuilding = new Building(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
				mainProblem.addBuilding(newBuilding);
				line = br.readLine();
			}
		}
		
		return mainProblem;
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
	private Solution solveProblem(Problem problem) {
		
		List<Problem> listProblems = new ArrayList<>();
		List<Solution> listSolutions = new ArrayList<>();
		
		if(isTrivial(problem)) return trivialSolution(problem);
		
		listProblems = decomposeProblem(problem);
		
		for(Problem p:listProblems) {
			listSolutions.add(solveProblem(p));
		}
		
		return combineSolutions(listSolutions);
	}
	
	/**
	 * 
	 * @param problem
	 * @return true if this problem has a trivial solution.
	 */
	private boolean isTrivial(Problem problem) {
		return problem.getBuildingListSize() == 1;
	}
	
	private Solution trivialSolution(Problem trivialProblem) {
		
		Solution solution = new Solution(trivialProblem.getBuilding());
		
		printTrace(solution.getTrace());
		
		return solution;
	}
	
	private List<Problem> decomposeProblem(Problem problem){
		
		List<Problem> listProblems = new ArrayList<>();
		int problemSize = problem.getBuildingListSize();
		Problem p1 = new Problem();
		Problem p2 = new Problem();
		
		for(int i=0; i < problemSize; i++) {
			
			if(i < problemSize/2) {
				p1.addBuilding(problem.getBuildingAt(i));
			}else {
				p2.addBuilding(problem.getBuildingAt(i));
			}
		}
		
		listProblems.add(p1);
		listProblems.add(p2);
		
		return listProblems;
	}
	
	private Solution combineSolutions(List<Solution> listSolutions) {
		
		Solution solution = null;
		
		printTrace("");
		
		return solution;
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.println(message);
	}
}
