package KML;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * The class role is to read a folder with csv files
 * received from the application and unite them into a one big csv file 
 * with all the information from all files.
 */

public class FolderReader {
	
	private File folder;
	private File[] listOfFiles;
	
	public FolderReader(String path)
	{
		folder = new File(path);
		listOfFiles = folder.listFiles();
	}
	
	//Unites the CSV files from the folder into one big CSV file.
	public void UniteFiles(String OutputPath)
	{
		try
		{
			File UnitedFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(UnitedFile);
			String headers = "MAC,SSID,AuthMode,FirstSeen,Channel,RSSI,CurrentLatitude,CurrentLongitude,AltitudeMeters,AccuracyMeters,Type,ID";
			outs.println(headers);
			for(int i=0;i<listOfFiles.length;i++)
			{
				Scanner sc = new Scanner(listOfFiles[i]);
				String CurrentLine = sc.nextLine();
				String[] SplitLine = CurrentLine.split(",");
				String id = SplitLine[2];
				sc.nextLine();
				while(sc.hasNext())
				{
					CurrentLine = sc.nextLine();
					outs.println(CurrentLine + "," + id);
				}
				sc.close();
			}
			outs.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("United File Was Created At " + OutputPath);
	}
}
