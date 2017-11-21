package Package;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

/*
 * JUnit testing of the methods in the Wigle class.
 */

public class WigleJUnitTest {

	String United = "C:\\Users\\Raz\\Desktop\\WigleOutput\\UnitedFile.csv";
	String ArrangedUnitedCSV = "C:\\Users\\Raz\\Desktop\\WigleOutput\\ArrangedUnitedCSV.csv";
	String ApiKML = "C:\\Users\\Raz\\Desktop\\WigleOutput\\ApiKML.kml";
	
	@Test
	public void ArrangedCSVTest()
	{
		try
		{
		String ExpectedPath = ArrangedUnitedCSV;
		String ActualPath = "C:\\Users\\Raz\\Desktop\\WigleOutput\\JUnitOutput\\ArrangedUnitedCSVTest.csv";
		
		Wigle w = new Wigle(United);
		w.ArrangedCSV(ActualPath);
		
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
	
	@Test
	public void ApiArrangedCSVtoKMLTest()
	{
		try
		{
		String ExpectedPath = ApiKML;
		String ActualPath = "C:\\Users\\Raz\\Desktop\\WigleOutput\\JUnitOutput\\ApiKMLTest.kml";
		
		Wigle w = new Wigle(ArrangedUnitedCSV);
		w.ApiArrangedCSVtoKML(ActualPath);
		
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
