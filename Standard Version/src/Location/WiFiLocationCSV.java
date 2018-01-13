package Location;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import KML.ArrangeWiFis;

/*
 * The class role is to estimate the location of all the WiFis in the database
 * and write the results to CSV file (Using WiFiLocation on every WiFi in the database).
 */

public class WiFiLocationCSV {

	private ArrangeWiFis ArrangedDatabase;

	public WiFiLocationCSV(ArrangeWiFis ArrangedDatabase)
	{
		this.ArrangedDatabase = ArrangedDatabase;
	}

	public void WifiLocationToCSV(String OutputPath)
	{
		try
		{
		File OutputFile = new File(OutputPath);
		PrintWriter outs = new PrintWriter(OutputFile);
		String Headlines = "Time, ID, Lat, Lon, Alt, #Samples, "
				+ "SSID, MAC, Frequncy, Signal";
		outs.println(Headlines);
		for(int i=0;i<ArrangedDatabase.getArrangedSamples().size();i++)
			for(int j=0;j<ArrangedDatabase.getArrangedSamples().elementAt(i).size();j++)
			{	
				String CurrentMac = ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getMac();
				WiFiLocation CurrentWifi = new WiFiLocation(CurrentMac, ArrangedDatabase);
				double WifiLocation[] = CurrentWifi.FindLocation();
				outs.println(ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getTime()
						+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getId()
								+ "," + WifiLocation[0]
										+ "," + WifiLocation[1]
												+ "," + WifiLocation[2]
														+ "," + CurrentWifi.getSamples().size()
														+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getSsid()
																+ "," + CurrentMac
																+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getFrequency()
																		+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getSignal());
			}
		outs.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Wifi Location CSV Was Created At " + OutputPath);
	}	

}


