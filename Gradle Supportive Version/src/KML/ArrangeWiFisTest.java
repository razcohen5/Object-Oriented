package KML;
/*
 * Tests the ArrangeWiFis class.
 */
public class ArrangeWiFisTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String folder = "C:\\Users\\Raz\\Desktop\\Data";
		String kml = "C:\\Users\\Raz\\Desktop\\Output\\KMLOutput\\KML.kml";
		String arrangedcsv = "C:\\Users\\Raz\\Desktop\\Output\\KMLOutput\\ArrangedCSV.csv";
		WiFis w = new WiFis();
		w.ReadFolder(folder);
		ArrangeWiFis aw = new ArrangeWiFis(w);
		aw.ArrangeSamples();
		aw.toArrangedCSV(arrangedcsv);
		aw.toKML(kml);
	}

}
