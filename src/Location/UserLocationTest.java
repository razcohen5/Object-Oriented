package Location;

/*
 * Tests the UserLocation class.
 */

public class UserLocationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ArrangedUnitedCSV = "C:\\Users\\Raz\\Desktop\\WigleOutput\\ArrangedUnitedCSV.csv";
		String UserLocationCSV = "C:\\Users\\Raz\\Desktop\\WigleOutput\\UserLocationCSV.csv";
		UserLocationCSV ul = new UserLocationCSV(ArrangedUnitedCSV);
		ul.UserLocationToCSV(UserLocationCSV);
	}

}
