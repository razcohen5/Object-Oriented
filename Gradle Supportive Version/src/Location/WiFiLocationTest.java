package Location;

import KML.ArrangeWiFis;
import KML.WiFis;

/*
 * Tests the WifiLocations classes.
 */

public class WiFiLocationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		//C:\\Users\\Raz\\Desktop\\ex2\\data\\BM1\\comb\\_comb_all_.csv
		String folder = "C:\\Users\\Raz\\Desktop\\Data";
		String wifilocationcsv = "C:\\Users\\Raz\\Desktop\\Output\\LocationOutput\\WiFiLocationCSV.csv";
		WiFis w = new WiFis();
		w.ReadFolder(folder);
		ArrangeWiFis aw = new ArrangeWiFis(w);
		aw.ArrangeSamples();
		WiFiLocationCSV wl = new WiFiLocationCSV(aw);
		wl.WifiLocationToCSV(wifilocationcsv);
	}

}
