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

public class WifiLocationCSV {

	private File ArrangedCSV;
	private String path;

	public WifiLocationCSV(String path)
	{
		ArrangedCSV = new File(path);
		this.path = path;
	}

	public void WifiLocationToCSV(String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc = new Scanner(ArrangedCSV);
			String CurrentLine;
			String[] SplitLine;
			sc.nextLine();
			String Headlines = "Time, ID, Lat, Lon, Alt, #Samples, "
					+ "SSID, MAC, Frequncy, Signal";
			outs.println(Headlines);
			while(sc.hasNext())
			{	
				CurrentLine = sc.nextLine();
				SplitLine = CurrentLine.split(",");
				int NumofWifiesinLine = Integer.parseInt(SplitLine[5]);
				for(int i=0;i<NumofWifiesinLine;i++)
				{
					String CurrentMac = SplitLine[7+4*i];
					WifiLocation CurrentWifi = new WifiLocation(CurrentMac, path);
					double WifiLocation[] = CurrentWifi.FindLocation();
					outs.println(			SplitLine[0]
									+ "," + SplitLine[1]
									+ "," + WifiLocation[0]
									+ "," + WifiLocation[1]
									+ "," + WifiLocation[2]
									+ "," + CurrentWifi.getSamples().size()
									+ "," + SplitLine[6+4*i]
									+ "," + CurrentMac
									+ "," + SplitLine[8+4*i]
									+ "," + SplitLine[9+4*i]	);
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

		System.out.println("Wifi Location CSV Was Created At " + OutputPath);
	}	

}


