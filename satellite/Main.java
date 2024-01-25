package satellite;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		KingOfSat satelliteKing = new KingOfSat();
        SatBeams satelliteBeam = new SatBeams();
        List<String[]> satellitesKing = satelliteKing.getSatellitesData();
        List<String[]> satellitesBeam = satelliteBeam.getSatellitesData();
        List<String[]> transpondersKing = satelliteKing.geTransponderstData();
        if (satellitesKing.isEmpty() || satellitesBeam.isEmpty()) {
            System.out.println("No data retrieved.");
        } else {
            System.out.println("Comparison of Satellite Data:");
            System.out.println("satelliteName orbitalPosition norad "
            		+ "status cospar model"
            		+ "channels longitude maxDeclination "
            		+ "operator launchSite launchMass launchDate");
            
            int unmatchedSatellitesCount = 0;
            int matchedSatellitesCount = 0;
            int transpondersCount = 0;
            int satelitesPositionDiffrence = 0;
            int transPositionDiffrence = 0;
            Double SatelitesDifferenceSum = 0.0;
            Double transDifferenceSum = 0.0;
            
            for (String[] arrayKing : satellitesKing) {
            	boolean matchFound = false;
                for (String[] arrayBeam : satellitesBeam) {
                    if (arrayKing[2].equals(arrayBeam[3])) { // Compare NORAD numbers
                        if(arrayKing[1] != arrayBeam[0]) {
                        	Double pos1 = extractDoubleFromString(arrayKing[1]);
                        	Double pos2 = extractDoubleFromString(arrayBeam[0]);
                        	double difference = Math.abs(pos1 - pos2);
                        	if(difference !=0) {
	                        	SatelitesDifferenceSum += difference;
	                        	satelitesPositionDiffrence++;	
                        	}
                        	
                        	
                        }
                    	// Display data from KingOfSat and SatBeams
                        System.out.print(arrayKing[0] + "|" + arrayKing[1] + "|" + arrayKing[2] + "|" +
                        		arrayBeam[1] + "|" + arrayBeam[4] + "|" + arrayBeam[5] + "|" +
                                arrayKing[3] + "|" + arrayKing[4] + "|" + arrayKing[5] + "|" +
                                arrayBeam[6] + "|" + arrayBeam[7] + "|" + arrayBeam[8] + "|" + arrayBeam[9]);

                        matchFound = true;
                        System.out.println();
                        break; // Break once a match is found
                    }else {
                    ABC:for (String[] arrayTransponder : transpondersKing ) {
                            if (arrayTransponder[0].equals(arrayBeam[2])) { //match names
                            	if(arrayTransponder[1] != arrayBeam[0]) {
                                	Double pos1 = extractDoubleFromString(arrayTransponder[1]);
                                	Double pos2 = extractDoubleFromString(arrayBeam[0]);
                                	double difference = Math.abs(pos1 - pos2);
                                	if(difference !=0) {
	                                	transDifferenceSum += difference;
	                                	transPositionDiffrence++;	
                                	}
                                	
                                	
                                }
                                // Display data from TranspondersList and SatBeams
                            	System.out.print("Transponder: ");
                                System.out.print(arrayTransponder[0] + "|" + arrayTransponder[1] + "|" + arrayTransponder[2]+ "|" +
                                		arrayTransponder[3] + "|" + arrayTransponder[4] + "|" + arrayTransponder[5]+ "|" +
                                		arrayTransponder[6] + "|" + arrayTransponder[7] + "|" + arrayTransponder[8]+ "|" +
                                		arrayTransponder[9] + "|" + arrayTransponder[1] + "|" + arrayTransponder[2]+ "|" +
                                        arrayBeam[1] + "|" + arrayBeam[3] + "|" + arrayBeam[4] + "|" + arrayBeam[5] + "|" +
                                        arrayBeam[6] + "|" + arrayBeam[7] + "|" + arrayBeam[8] + "|" + arrayBeam[9]);
                                System.out.println();
                                //unmatchedSatellitesCount++;
                                matchFound = true;
                                transpondersCount++;
                                break ABC;
                            }
                        }
                    }
                }
                if (!matchFound) {
                    unmatchedSatellitesCount++;
                }else if(matchFound) {
                	matchedSatellitesCount++;
                }
            }
            System.out.println("Number of matched satellites by NORAD number: " + matchedSatellitesCount);
            System.out.println("Number of transponders: " + transpondersCount);
            System.out.println("Number of diffrences  in satelites Orbital Position: " + satelitesPositionDiffrence);
            Double avg = SatelitesDifferenceSum/satelitesPositionDiffrence;
            System.out.println("Average difference in satelites orbital position: " + avg);
            System.out.println("Number of diffrences  in transponders Orbital Position: " + transPositionDiffrence);
            Double avg2 = transDifferenceSum/transPositionDiffrence;
            System.out.println("Average difference in transponders orbital position: " + avg2);
        }
		
		
		
		
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
		
		//System.out.println(satellitesList.get(0)[0]);
		//satelite.displayTranspondersData();
        
	}
	/*
	private static double extractDoubleFromString(String input) {
	    try {
	        if (input.contains("°E")) {
	            // Jeśli dane są w formacie stopni dziesiętnych (np. 75.0°E)
	            return Double.parseDouble(input.replace("°E", ""));
	        } else if (input.contains("W")) {
	            // Jeśli dane są w formacie stopni z zachodniej długości (np. 5 W)
	            return -Double.parseDouble(input.replace(" W", ""));
	        } else {
	            throw new NumberFormatException("Invalid format: " + input);
	        }
	    } catch (NumberFormatException e) {
	        System.err.println("Error parsing double value from: " + input);
	        return 0.0;
	    }
	}
	*/
	
	private static double extractDoubleFromString(String input) {
		try {
	        // Extract numerical values from the input string
	        String numericalValue = input.replaceAll("[^\\d.-]", "");
	        return Double.parseDouble(numericalValue);
	    } catch (NumberFormatException e) {
	        System.err.println("Error parsing double value from: " + input);
	        return 0.0;  
	    }
    }
    
}

