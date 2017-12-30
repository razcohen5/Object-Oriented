package Location;

import KML.ArrangeWiFis;
import KML.WiFis;

/*
 * Tests the UserLocation classes.
 */

public class UserLocationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String folder = "C:\\Users\\Raz\\Desktop\\Data";
		String userlocationcsv = "C:\\Users\\Raz\\Desktop\\Output\\LocationOutput\\UserLocationCSV.csv";
		WiFis w = new WiFis();
		w.ReadFolder(folder);
		ArrangeWiFis aw = new ArrangeWiFis(w);
		aw.ArrangeSamples();
		UserLocationCSV wl = new UserLocationCSV(aw);
		wl.UserLocationToCSV(userlocationcsv);
	}

}
