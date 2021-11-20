package baena.skyline;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	
	List<Building> buildingList;
	List<Solution> solutionList;
	
	public Problem() {
		buildingList = new ArrayList<>();
		solutionList = new ArrayList<>();
	}
	
	public void removeBuilding(Building building) {
		buildingList.remove(building);
	}
	
	public void addBuilding(Building building) {
		buildingList.add(building);
	}
	
	public int getBuildingListSize() {
		return buildingList.size();
	}
	
	public Building getBuilding() {
		assert(buildingList.size() == 1);
		return buildingList.get(0);
	}
	
	public Building getBuildingAt(int index) {
		return buildingList.get(index);
	}
	
	public List<Solution> getSolution(){
		return solutionList;
	}
	
	public void addSolution(Solution solution) {
		this.solutionList.add(solution);
	}
}
