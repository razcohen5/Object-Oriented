package Location;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import KML.ArrangeWiFis;

/*
 * The class role is to estimate the user location for every group of WiFis in the arranged database
 * and write the results to CSV file (Using UserLocation on every WiFi in the database).
 */

public class UserLocationCSV {

	private ArrangeWiFis ArrangedDatabase;

	public UserLocationCSV(ArrangeWiFis ArrangedDatabase)
	{
		this.ArrangedDatabase = ArrangedDatabase;
	}

	public void UserLocationToCSV(String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			String Headlines = "Time, ID, Lat, Lon, Alt, #MACS, "
					+ "SSID1, MAC1, SSID2, MAC2, SSID3, MAC3";
			outs.println(Headlines);
			for(int i=0;i<ArrangedDatabase.getArrangedSamples().size();i++)	
				if(ArrangedDatabase.getArrangedSamples().elementAt(i).size()>=3)
				{
					String CurrentMacs[] = {ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(0).getMac(),ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(1).getMac(),ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(2).getMac()};			
					UserLocation CurrentLocation = new UserLocation(CurrentMacs[0], CurrentMacs[1], CurrentMacs[2], ArrangedDatabase);
					double UserLocation[] = CurrentLocation.FindLocation();
					outs.println(ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(0).getTime()
							+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(0).getId()
							+ "," + UserLocation[0]
									+ "," + UserLocation[1]
											+ "," + UserLocation[2]
													+ "," + CurrentMacs.length
													+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(0).getSsid()
													+ "," + CurrentMacs[0]
															+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(1).getSsid()
															+ "," + CurrentMacs[1]
																	+ "," + ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(2).getSsid()
																	+ "," + CurrentMacs[2]);
				}
			outs.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}


		System.out.println("User Location CSV Was Created At " + OutputPath);
	}	

}


