package Package;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

/*
 * JUnit testing of the methods in the FolderReader class.
 */

public class FolderReaderJUnitTest {

	@Test
	public void UniteFilesTest()
	{
		try
		{
		String Folder = "C:\\Users\\Raz\\Desktop\\foldereader";
		String ExpectedPath = "C:\\Users\\Raz\\Desktop\\WigleOutput\\UnitedFile.csv";
		String ActualPath = "C:\\Users\\Raz\\Desktop\\WigleOutput\\JUnitOutput\\UnitedFileTest.csv";
		
		FolderReader fr = new FolderReader(Folder);
		fr.UniteFiles(ActualPath);
		
		File ExpectedFile = new File(ExpectedPath);
		File ActualFile = new File(ActualPath);
		
		Scanner scExpected = new Scanner(ExpectedFile);
		Scanner scActual = new Scanner(ActualFile);
		
		while(scActual.hasNext())
		{
			String ExpectedLine = scExpected.nextLine();
			String ActualLine = scActual.nextLine();
			assertEquals(ExpectedLine, ActualLine);
		}
		scExpected.close();
		scActual.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}
}
