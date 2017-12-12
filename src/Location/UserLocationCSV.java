package Location;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/*
 * 
 */

public class UserLocationCSV {

	private File ArrangedCSV;
	private String path;

	public UserLocationCSV(String path)
	{
		ArrangedCSV = new File(path);
		this.path = path;
	}

	public void UserLocationToCSV(String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc = new Scanner(ArrangedCSV);
			String CurrentLine;
			String[] SplitLine;
			sc.nextLine();
			String Headlines = "Time, ID, Lat, Lon, Alt, #MACS, "
					+ "SSID1, MAC1, SSID2, MAC2, SSID3, MAC3";
			outs.println(Headlines);
			while(sc.hasNext())
			{	
				CurrentLine = sc.nextLine();
				SplitLine = CurrentLine.split(",");
				int NumofWifiesinLine = Integer.parseInt(SplitLine[5]);
				if(NumofWifiesinLine>=3)
				{
					String CurrentMacs[] = {SplitLine[7],SplitLine[11],SplitLine[15]};			
					UserLocation CurrentLocation = new UserLocation(CurrentMacs[0], CurrentMacs[1], CurrentMacs[2], path);
					double UserLocation[] = CurrentLocation.FindLocation();
					outs.println(			SplitLine[0]
									+ "," + SplitLine[1]
									+ "," + UserLocation[0]
									+ "," + UserLocation[1]
									+ "," + UserLocation[2]
									+ "," + CurrentMacs.length
									+ "," + SplitLine[6]
									+ "," + CurrentMacs[0]
									+ "," + SplitLine[10]
									+ "," + CurrentMacs[1]
									+ "," + SplitLine[14]
									+ "," + CurrentMacs[2]);		
				}
			}		
			outs.close();
			sc.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}

		System.out.println("User Location CSV Was Created At " + OutputPath);
	}	

}


