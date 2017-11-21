package Package;

/*
 * Tests the FolderReader class.
 */

public class FolderReaderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Folder = "C:\\Users\\Raz\\Desktop\\foldereader";
		String UnitedFile = "C:\\Users\\Raz\\Desktop\\WigleOutput\\UnitedFile.csv";
		
		FolderReader fr = new FolderReader(Folder);
		fr.UniteFiles(UnitedFile);
	}
}
