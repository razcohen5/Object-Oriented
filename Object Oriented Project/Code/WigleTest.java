package Package;

/*
 * Tests the Wigle class.
 */

public class WigleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String United = "C:\\Users\\Raz\\Desktop\\WigleOutput\\UnitedFile.csv";
		String ArrangedUnitedCSV = "C:\\Users\\Raz\\Desktop\\WigleOutput\\ArrangedUnitedCSV.csv";
		String KML = "C:\\Users\\Raz\\Desktop\\WigleOutput\\KML.kml";
		String ApiKML = "C:\\Users\\Raz\\Desktop\\WigleOutput\\ApiKML.kml";
		
		//Tests ArrangedCSV().
		Wigle UnitedFile = new Wigle(United);
		UnitedFile.ArrangedCSV(ArrangedUnitedCSV);
		
		//Tests ArrangedCSVtoKML.
		Wigle ac = new Wigle(ArrangedUnitedCSV);
		ac.ArrangedCSVtoKML(KML);

		//Tests ApiArrangedCSVtoKML.
		ac.ApiArrangedCSVtoKML(ApiKML);
				
		//Tests FilterByTimeArrangedCSV().
		ac.FilterByTimeArrangedCSV("2017-11-02 08:42:50","C:\\Users\\Raz\\Desktop\\WigleOutput\\FilterByTimeArrangedUnitedCSV.csv");
		
		//Tests FilterByLocationArrangedCSV().
		ac.FilterByLocationArrangedCSV("32.10371076","35.19879422","633","C:\\Users\\Raz\\Desktop\\WigleOutput\\FilterByLocationArrangedUnitedCSV.csv");
		
		//Tests FilterByIDArrangedCSV().
		ac.FilterByIDArrangedCSV("model=LG-D855","C:\\Users\\Raz\\Desktop\\WigleOutput\\FilterByIDArrangedUnitedCSV.csv");
	}

}
