package satellite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KingOfSat {
	final String kingOfSatUrl = "https://en.kingofsat.net/satellites.php";
	//List containing all satellite data
	List<String[]> satellitesList = new ArrayList<>();
	List<String[]> transpondersList = new ArrayList<>();

	
	//data from the main page
	public String satelliteName;
	public String orbitalPosition;
	public String norad;
	public String channels;
	public String longitude;
	public String maxDeclination;
	//specific data from subpages
	public String frequence;
	public String polarization;
	public String txp;
	public String beam;
	public String standard;
	public String modulation;
	public String srFec;
	public String networkBitrate;
	
	/**
	 * Constructor for the KingOfSat class. Initiates the web scraping process 
	 */
    KingOfSat(){
    	
    	webScrapper();
    	//displayData();
    }
	
    /**
     * Method for initiating the web scraping process from https://en.kingofsat.net/satellites.php.
     */
	void webScrapper() {
		try {
			final Document document = Jsoup.connect(kingOfSatUrl).get();
	        System.out.println("Main Page Scraped Successfully");
			scrapMainPage(document);
		} catch (IOException e){
	        System.err.println("An error occurred while connecting to URL: " + e.getMessage());
			e.printStackTrace();
			//System.exit(1);
		}
	}
	
	/**
     * Method for scrapping the main page and collecting satellite data.
     *
     * @param document HTML document of the main page.
	*/
	private void scrapMainPage(Document document) {
	    Elements rows = document.select("table.footable tr");

	    for (Element row : rows) {
	        Elements cells = row.select("td");

	        if (cells.size() >= 10) {  // Ensure there are enough cells
	            // Extract data from the main page 
	            satelliteName = cells.get(1).select("a").text();
	            orbitalPosition = cells.get(0).text();
	            norad = cells.get(2).text();
	            channels = cells.get(5).text();
	            longitude = cells.get(7).text();
	            maxDeclination = cells.get(9).text();
	            
	            if(!satelliteName.contains("Moving")) {
		            satellitesList.add(new String[]{satelliteName, orbitalPosition, norad, channels, longitude, maxDeclination});
		            // Extract link for subpage
		            String subpageUrl ="https://en.kingofsat.net/"+ cells.get(1).select("a").attr("href");
		            // Call method to scrape specific data from subpages
		            scrapSubpage(subpageUrl);
	            }
	            
	        }
	    }
	}
	
	/**
	 * Method for scraping data from a subpage.
	 *
	 * @param subpageUrl URL of the subpage.
	 */
	
	private void scrapSubpage(String subpageUrl) {
	    try {
	        final Document subpageDocument = Jsoup.connect(subpageUrl).get();
	        System.out.println("Subpage Scraped Successfully: " + subpageUrl);
	        Elements tables = subpageDocument.select("table.frq");
	        
	        //check if there is second table.frq
	        if (tables.size() >= 2) {
	        	
	        	for(int i=1; i< tables.size(); i++) {
	        		Element frqTable = tables.get(i);
	        		Elements rows = frqTable.select("tr");
	        		if (!rows.isEmpty()) {
		                Element dataRow = rows.first();  // Scrape first row
	
		                frequence = dataRow.select("td").get(2).text();
		                polarization = dataRow.select("td").get(3).text();
		                txp = dataRow.select("td").get(4).text();
		                beam = dataRow.select("td").get(5).text();
		                standard = dataRow.select("td").get(6).text();
		                modulation = dataRow.select("td").get(7).text();
		                srFec = dataRow.select("td").get(8).text();
		                networkBitrate = dataRow.select("td").get(9).text();  
	
		                // add scraped data to satellitesList
		                transpondersList.add(new String[]{satelliteName, orbitalPosition, frequence, polarization, txp, beam, standard, modulation, srFec, networkBitrate});
	        		} else {
	                System.out.println("No rows in the second 'frq' table to extract data.");
	        		}
	        	}
	        } else {
	            System.out.println("Second 'frq' table not found on the subpage."+ subpageUrl);
	        }
	        		

	    } catch (IOException e) {
	        System.err.println("An error occurred while scraping the subpage: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	
	/**
     * Method returning a list of satellite data.
     *
     * @return List of satellite data.
     */
	List<String[]> getSatellitesData() {
		//method that returns list of satelites
		return satellitesList;
	}
	
	List<String[]> geTransponderstData() {
		//method that returns list of satelites
		return transpondersList;
	}
	
	/**
     * Method for displaying information about satellites.
     */
	void displaySatellitesData() {
		for(String[] array : satellitesList) {
			for(String element : array) {
				System.out.print(element + " ");
			}
			System.out.println();
		}
	}
	
	void displayTranspondersData() {
		for(String[] array : transpondersList) {
			for(String element : array) {
				System.out.print(element + " ");
			}
			System.out.println();
		}
	}
}
