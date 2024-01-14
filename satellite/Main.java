package satellite;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		KingOfSat satelite = new KingOfSat();
		List<String[]> satellitesList = satelite.getSatellitesData();
		List<String[]> transpondersList = satelite.geTransponderstData();
		/*
		 * This is how to access each parameter from satellitesList:
		 * satellitesList.get(i)[0] -> satelliteName
		 * satellitesList.get(i)[1] -> orbitalPosition
		 * satellitesList.get(i)[2] -> channels
		 * satellitesList.get(i)[3] -> longitude
		 * satellitesList.get(i)[4] -> maxDeclination
		 * This is how to access each parameter from transpondersList:
		 * transpondersList.get(i)[0] -> satelliteName
		 * transpondersList.get(i)[1] -> orbitalPosition
		 * transpondersList.get(i)[2] -> frequency
		 * transpondersList.get(i)[3] -> polarization
		 * transpondersList.get(i)[4] -> txp
		 * transpondersList.get(i)[5] -> beam
		 * transpondersList.get(i)[6] -> standard
		 * transpondersList.get(i)[7] -> modulation
		 * transpondersList.get(i)[8] -> srFec
		 * transpondersList.get(i)[9] -> networkBitrate
		 */
		
		if (satellitesList.isEmpty()) {
		    System.out.println("No data retrieved.");
		} else {
			System.out.println("satelliteName orbitalPosition channels longitude maxDeclination ");
		    for (String[] array : satellitesList) {
		        for (String element : array) {
		            System.out.print(element + "  ");
		        }
		        System.out.println();
		    }
		}
		
		if (transpondersList.isEmpty()) {
		    System.out.println("No data retrieved.");
		} else {
			System.out.println("satelliteName orbitalPosition "
					+ "frequence polarization txp beam standard modulation srFec networkBitrate");
		    for (String[] array : transpondersList) {
		        for (String element : array) {
		            System.out.print(element + "  ");
		        }
		        System.out.println();
		    }
		}
		
		//System.out.println(satellitesList.get(0)[0]);
		//satelite.displayTranspondersData();
	}
}
