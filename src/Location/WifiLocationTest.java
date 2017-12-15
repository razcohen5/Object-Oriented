package Location;

/*
 * Tests the WifiLocation class.
 */

public class WifiLocationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String ArrangedUnitedCSV = "C:\\Users\\Raz\\Desktop\\WigleOutput\\ArrangedUnitedCSV.csv";
		String WifiLocationCSV = "C:\\Users\\Raz\\Desktop\\LocationOutput\\WifiLocationCSV.csv";
		//C:\\Users\\Raz\\Desktop\\ex2\\data\\BM1\\comb\\_comb_all_.csv
		WifiLocationCSV wl = new WifiLocationCSV(ArrangedUnitedCSV);
		wl.WifiLocationToCSV(WifiLocationCSV);
	}

}
