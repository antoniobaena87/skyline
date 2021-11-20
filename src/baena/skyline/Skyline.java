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
	
	public List<Solution> beginExecution() throws IOException {
		
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
	private List<Solution> solveProblem(Problem problem) {
		
		Problem[] listProblems = new Problem[2];
		List<Solution> listSolutions = new ArrayList<>();
		
		if(isTrivial(problem)) return trivialSolution(problem);
		
		listProblems = decomposeProblem(problem);	// divide problem in two halves
		List<Solution> sol1 = solveProblem(listProblems[0]);
		List<Solution> sol2 = solveProblem(listProblems[1]);
		
		listSolutions = combineSolutions(sol1, sol2);
		
		return listSolutions;
	}
	
	/**
	 * 
	 * @param problem
	 * @return true if this problem has a trivial solution.
	 */
	private boolean isTrivial(Problem problem) {
		return problem.getBuildingListSize() == 1;
	}
	
	private List<Solution> trivialSolution(Problem trivialProblem) {
		
		Building trivialBuilding = trivialProblem.getBuilding();
		trivialProblem.addSolution(new Solution(trivialBuilding.getX1(), trivialBuilding.getX2(), trivialBuilding.getH()));
		trivialProblem.addSolution(new Solution(trivialBuilding.getX2(), trivialBuilding.getX2(), 0));
		
		return trivialProblem.getSolution();
	}
	
	private Problem[] decomposeProblem(Problem problem){
		
		Problem[] listProblems = new Problem[2];
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
		
		listProblems[0] = p1;
		listProblems[1] = p2;
		
		return listProblems;
	}
	
	private List<Solution> combineSolutions(List<Solution> solutionList1, List<Solution> solutionList2) {
		
		List<Solution> solution = new ArrayList<>();
		
		int i = 0;	// TODO: use iterators
		int j = 0;
		int maxHeight = 0;
		int maxEndLine = 0;
		int currentmaxEndLineHeight = 0;
		Solution previousSetSolution = new Solution(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		while(i < solutionList1.size() || j < solutionList2.size()) {
			Solution s1 = i < solutionList1.size() ? solutionList1.get(i) : new Solution(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
			Solution s2 = j < solutionList2.size() ? solutionList2.get(j) : new Solution(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
			
			if(s1.getOrdinate() == s2.getOrdinate()) {
				Solution s = s1.getHeight() > s2.getHeight() ? s1 : s2;
				
				if(s.getOrdinate() < maxEndLine && s.getHeight() < maxHeight && s.getHeight() != 0) {
					// do not add this point to solution
				} else {
					
					if(s.getHeight() == 0 && s.getOrdinate() < maxEndLine) {
						s.setHeight(currentmaxEndLineHeight);
						s.setEndLine(maxEndLine);
					}
					
					if(s.getHeight() == previousSetSolution.getHeight()) {
						// do not add point
					} else {
						solution.add(s);
						previousSetSolution = s;
						maxHeight = s.getHeight();
					}
				}
				
				if (s1.getHeight() > s2.getHeight()) i++;
				else j++;
				
				if(s.getEndLine() > maxEndLine) {
					maxEndLine = s.getEndLine();
					currentmaxEndLineHeight = s.getHeight();
				}
				
			} else if(s1.getOrdinate() < s2.getOrdinate()) {
				
				if(s1.getOrdinate() < maxEndLine && s1.getHeight() < maxHeight && s1.getHeight() != 0) {
					// do not add this point to solution
					i++;
				} else {
					// add point
					if(s1.getHeight() == 0 && s1.getOrdinate() < maxEndLine) {
						s1.setHeight(currentmaxEndLineHeight);
						s1.setEndLine(maxEndLine);
					}
					
					if(s1.getHeight() == previousSetSolution.getHeight()) {
						// do not add point
						i++;
					} else {
						solution.add(s1);
						previousSetSolution = s1;
						maxHeight = s1.getHeight();
						i++;
					}
				}
				
				if(s1.getEndLine() > maxEndLine) {
					maxEndLine = s1.getEndLine();
					currentmaxEndLineHeight = s1.getHeight();
				}
				
			} else {
				if(s2.getOrdinate() < maxEndLine && s2.getHeight() < maxHeight && s2.getHeight() != 0) {
					// do not add this point to solution
					j++;
				} else {
					// add point
					if(s2.getHeight() == 0 && s2.getOrdinate() < maxEndLine) {
						s2.setHeight(currentmaxEndLineHeight);
						s2.setEndLine(maxEndLine);
					}
					
					if(s2.getHeight() == previousSetSolution.getHeight()) {
						// do not add point
						j++;
					} else {
					
						solution.add(s2);
						previousSetSolution = s2;
						maxHeight = s2.getHeight();
						j++;
					}
				}
				
				if(s2.getEndLine() > maxEndLine) {
					maxEndLine = s2.getEndLine();
					currentmaxEndLineHeight = s2.getHeight();
				}
			}
		}
		
		return solution;
	}
	
	private void printTrace(String message) {
		if(showTrace) System.out.println(message);
	}
}
