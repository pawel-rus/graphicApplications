package satellite;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Satellite Data Comparison");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

            
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> System.exit(0));
            frame.getContentPane().add(closeButton, BorderLayout.SOUTH);

            
            KingOfSat satelliteKing = new KingOfSat();
            SatBeams satelliteBeam = new SatBeams();
            List<String[]> satellitesKing = satelliteKing.getSatellitesData();
            List<String[]> satellitesBeam = satelliteBeam.getSatellitesData();
            List<String[]> transpondersKing = satelliteKing.geTransponderstData();

            if (satellitesKing.isEmpty() || satellitesBeam.isEmpty()) {
                textArea.setText("No data retrieved.");
            } else {
                StringBuilder resultText = new StringBuilder();
                resultText.append(" -------------------- Comparison of Satellite and Transponders Data -------------------- \n");
                resultText.append(" *********************************************************************************************************************************************************************************************************** \n");
                resultText.append("SATELITES: satelliteName | orbitalPosition | norad | "
                        + "status | cospar | model | "
                        + "channels | longitude | maxDeclination "
                        + "operator | launchSite | launchMass | launchDate\n");
                resultText.append("TRANSPONDERS: satelliteName | orbitalPosition | frequence | polarization | txp "
                		+ "| beam | standard | modulation | srFec | networkBitrate | status |  norad | cospar |"
                		+ " model | operator | launchSite | launchMass | launchDate\n");
                resultText.append(" *********************************************************************************************************************************************************************************************************** \n");

                int unmatchedSatellitesCount = 0;
                int matchedSatellitesCount = 0;
                int transpondersCount = 0;
                int satelitesPositionDifference = 0;
                int transPositionDifference = 0;
                Double SatelitesDifferenceSum = 0.0;
                Double transDifferenceSum = 0.0;

                for (String[] arrayKing : satellitesKing) {
                    boolean matchFound = false;
                    for (String[] arrayBeam : satellitesBeam) {
                        if (arrayKing[2].equals(arrayBeam[3])) { // Compare NORAD numbers
                            if (!arrayKing[1].equals(arrayBeam[0])) {
                                Double pos1 = extractDoubleFromString(arrayKing[1]);
                                Double pos2 = extractDoubleFromString(arrayBeam[0]);
                                double difference = Math.abs(pos1 - pos2);
                                if(difference !=0) {
    	                        	SatelitesDifferenceSum += difference;
    	                        	satelitesPositionDifference++;	
                            	}
                            }
                            //resultText.setForeground(Color.BLUE);
                            resultText.append("Satelite: ");
                            resultText.append(arrayKing[0]).append(" | ").append(arrayKing[1]).append(" | ").append(arrayKing[2]).append(" | ")
                                    .append(arrayBeam[1]).append(" | ").append(arrayBeam[4]).append(" | ").append(arrayBeam[5]).append(" | ")
                                    .append(arrayKing[3]).append(" | ").append(arrayKing[4]).append(" | ").append(arrayKing[5]).append(" | ")
                                    .append(arrayBeam[6]).append(" | ").append(arrayBeam[7]).append(" | ").append(arrayBeam[8]).append(" | ").append(arrayBeam[9]);
                            
                            matchFound = true;
                            resultText.append("\n");
                            break; 
                        } else {
                            ABC:
                            for (String[] arrayTransponder : transpondersKing) {
                                if (arrayTransponder[0].equals(arrayBeam[2])) { //match names
                                    if (!arrayTransponder[1].equals(arrayBeam[0])) {
                                        Double pos1 = extractDoubleFromString(arrayTransponder[1]);
                                        Double pos2 = extractDoubleFromString(arrayBeam[0]);
                                        double difference = Math.abs(pos1 - pos2);
                                        if(difference !=0) {
    	                                	transDifferenceSum += difference;
    	                                	transPositionDifference++;	
                                    	}
                                    }
                                    // Dodanie danych do wyników
                                    resultText.append("Transponder: ");
                                    		
                                    resultText.append(arrayTransponder[0]).append(" | ").append(arrayTransponder[1]).append(" | ").append(arrayTransponder[2]).append(" | ")
                                            .append(arrayTransponder[3]).append(" | ").append(arrayTransponder[4]).append(" | ").append(arrayTransponder[5]).append(" | ")
                                            .append(arrayTransponder[6]).append(" | ").append(arrayTransponder[7]).append(" | ").append(arrayTransponder[8]).append(" | ")
                                            .append(arrayTransponder[9]).append(" | ")
                                            .append(arrayBeam[1]).append(" | ").append(arrayBeam[3]).append(" | ").append(arrayBeam[4]).append(" | ").append(arrayBeam[5]).append(" | ")
                                            .append(arrayBeam[6]).append(" | ").append(arrayBeam[7]).append(" | ").append(arrayBeam[8]).append(" | ").append(arrayBeam[9]);
                                    resultText.append("\n");
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
                //Double avg = SatelitesDifferenceSum / satelitesPositionDifference;
                Double avg = SatelitesDifferenceSum / matchedSatellitesCount;
                //Double avg2 = transDifferenceSum / transPositionDifference;
                Double avg2 = transDifferenceSum / transpondersCount;

                /*
                resultText.append("******************************************************************************").append("\n");
                resultText.append("Number of matched satellites by NORAD number: ").append(matchedSatellitesCount).append("\n");
                resultText.append("Number of transponders: ").append(transpondersCount).append("\n");
                resultText.append("Number of differences in satellites Orbital Position: ").append(satelitesPositionDifference).append("\n");
                //Double avg = SatelitesDifferenceSum / satelitesPositionDifference;
                resultText.append("Average difference in satellites orbital position: ").append(avg).append("\n");
                resultText.append("Number of differences in transponders Orbital Position: ").append(transPositionDifference).append("\n");
                //Double avg2 = transDifferenceSum / transPositionDifference;
                resultText.append("Average difference in transponders orbital position: ").append(avg2).append("\n");
                resultText.append("******************************************************************************").append("\n");
                */
                // Ustawienie tekstu w JTextArea
                textArea.setText(resultText.toString());
                JTextPane additionalTextPane = new JTextPane();
                additionalTextPane.setEditable(true);
                StyledDocument styledDoc = additionalTextPane.getStyledDocument();

                SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                StyleConstants.setForeground(attributeSet, Color.RED);  

                try {
                    styledDoc.insertString(styledDoc.getLength(),
                            "******************************************************************************\n" +
                            "Number of matched satellites by NORAD number: " + matchedSatellitesCount + "\n" +
                            //"Number of transponders: " + transpondersCount + "\n" +
                            "Number of differences in satellites Orbital Position: " + satelitesPositionDifference + "\n" +
                            "Average difference in satellites orbital position: " + avg + "\n" +
                            //"Number of differences in transponders Orbital Position: " + transPositionDifference + "\n" +
                            "Average difference in transponders orbital position: " + avg2 + "\n" +
                            "******************************************************************************\n", attributeSet);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

                JScrollPane additionalScrollPane = new JScrollPane(additionalTextPane);
                frame.getContentPane().add(additionalScrollPane, BorderLayout.SOUTH);
            }

            frame.setVisible(true);
        });
    }

    private static double extractDoubleFromString(String input) {
		try {
	        // Extract numerical values from the input string
	        String numericalValue = input.replaceAll("[^\\d.-]", "");
	        Double number = Double.parseDouble(numericalValue);
	        if(number>90) {
	        	number-=90;
	        }
	        return number;
	    } catch (NumberFormatException e) {
	        System.err.println("Error parsing double value from: " + input);
	        return 0.0;  
	    }
    }
}
