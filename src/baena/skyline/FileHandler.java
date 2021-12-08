package baena.skyline;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
	
	/**
	 * Reads the entry file and transforms it into the main problem.
	 * 
	 * Cost: Since there is a while loop that iterates over each line to create a Building object with the data in that line,
 * 			 the cost is the number of buildings in the entry data file. This is, the cost is N.
	 * 
	 * @throws IOException 
	 */
	public Building[] readData(String dataEntryPath) throws IOException {
		
		List<Building> buildingList = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(dataEntryPath))){
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
	
	public void saveData(ListPoint skyline, String saveDataPath) throws FileNotFoundException {
		
		String result = skyline.getInOutputDataFormat();
		
		try(PrintWriter out = new PrintWriter(saveDataPath)){
			out.print(result);
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException("Error guardando datos en archivo de salida...");
		}
		
	}
}
