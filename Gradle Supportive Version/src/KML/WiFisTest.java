package KML;
/*
 * Tests the WiFi classes.
 */
public class WiFisTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String folder = "C:\\Users\\Raz\\Desktop\\Data";
		String csv = "C:\\Users\\Raz\\Desktop\\Output\\KMLOutput\\UnitedFile.csv";
		WiFis w = new WiFis();
		w.ReadFolder(folder);
		w.toCSV(csv);
		
		
		w.ReadSQL("localhost", "3306", "sqlwifis", "wifis", "root", "sqlpassword");
		System.out.println(w.NumberofMacs());
	}

}
