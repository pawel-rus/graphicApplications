package satellite;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SatBeams {
    final String satBeamsUrl = "https://www.satbeams.com/satellites";
    List<String[]> satellitesList = new ArrayList<>();


    public String orbitalPosition;
    public String status;
    public String satelliteName;
    public String norad;
    public String cospar;
    public String model;
    public String operator;
    public String launchSite;
    public String launchMass;
    public String launchDate;

    public SatBeams() {
        webScraper();
        // displayData();
    }

    void webScraper() {
        try {
            final Document document = Jsoup.connect(satBeamsUrl).get();
            System.out.println("Main Page Scraped Successfully");
            scrapMainPage(document);
        } catch (IOException e) {
            System.err.println("An error occurred while connecting to URL: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void scrapMainPage(Document document) {
        Elements rows = document.select(".class_tr");
        int satelliteCount = 0;

        for (Element row : rows) {
        	 //if (satelliteCount >= 5) { 
             //    break; 
             //}
            Elements cells = row.select("td");

            if (cells.size() >= 11) { 
            	orbitalPosition = cells.get(1).text();
            	status = cells.get(2).text();
                satelliteName = cells.get(3).select("a").text();
                norad = cells.get(4).text();
                cospar = cells.get(5).text();
                model = cells.get(6).text();
                operator = cells.get(7).text();
                launchSite = cells.get(8).text();
                launchMass = cells.get(9).text();
                launchDate = cells.get(10).text();

                satellitesList.add(new String[]{orbitalPosition, status, satelliteName, norad, cospar, model,
                        operator, launchSite, launchMass, launchDate});
                
                String hrefValue = cells.get(1).select("a").attr("href");
                String subpageUrl = "https://www.satbeams.com" + hrefValue;
                //scrapSubpage(subpageUrl);
                satelliteCount++;
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
        } catch (IOException e) {
            System.err.println("An error occurred while scraping the subpage: " + e.getMessage());
            e.printStackTrace();
        }
    }

    List<String[]> getSatellitesData() {
        return satellitesList;
    }

    void displaySatellitesData() {
        for (String[] array : satellitesList) {
            for (String element : array) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}