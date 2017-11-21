package Package;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import com.sun.xml.txw2.Document;

import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;

/* 
 * The class's role is to filter the wifi in the united csv file
 * received from the FolderReader class by time location and id 
 * and change it into a kml file that can be presented on google earth.
*/

public class Wigle {

	private File file;
	private int lines;

	public Wigle(String path)
	{
		file = new File(path);
		lines = FindNumberofLines();

		System.out.println("File path: " + path);
		System.out.println("Number of lines: " + lines);	
	}

	//Finds the number of lines in the file.
	private int FindNumberofLines()
	{
		int NumofLines=0;
		try{
			Scanner sc = new Scanner(file);	
			sc.nextLine();
			while(sc.hasNext())
			{
				sc.nextLine();
				NumofLines++;
			}	
			sc.close();
		}

		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}

		return NumofLines;
	}

	//Keeps only the 10 strongest signals if there are more than 10.
	private void Top10Signals(LinkedList<String[]> lls)
	{
		//find the signal limit of the top 10 signals
		int SignalLimit;
		int counter = 0;
		int[] signals = new int[lls.size()];
		for(int i=0;i<lls.size();i++)
			signals[i] = Integer.parseInt(lls.get(i)[5]);	
		Arrays.sort(signals);
		SignalLimit = signals[signals.length-9];

		//find top 10 signals above the signal limit
		for(int i=0;i<lls.size();i++)
		{
			if(Integer.parseInt(lls.get(i)[5])<SignalLimit||counter>=10)
			{
				lls.remove(i);
				i--;
			}	
		}		
		for(int i=0;i<lls.size()&&lls.size()>10;i++)
			if(Integer.parseInt(lls.get(i)[5])==SignalLimit)
				lls.remove(i);	
	}

	/*
	 * Arranges the CSV so that in each line there will be max 10 wifi
	 * that have the same Time and Location.
	 */
	public void ArrangedCSV(String OutputPath)
	{
		try{
			LinkedList<String[]> allLines = new LinkedList<String[]>();
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc3 = new Scanner(file);
			String CurrentLine;
			//print headlines
			String Headlines = "Time, ID, Lat, Lon, Alt, #WiFi networks, "
					+ "SSID1, MAC1, Frequncy1, Signal1, "
					+ "SSID2, MAC2, Frequncy2, Signal2, "
					+ "SSID3, MAC3, Frequncy3, Signal3, "
					+ "SSID4, MAC4, Frequncy4, Signal4, "
					+ "SSID5, MAC5, Frequncy5, Signal5, "
					+ "SSID6, MAC6, Frequncy6, Signal6, "
					+ "SSID7, MAC7, Frequncy7, Signal7, "
					+ "SSID8, MAC8, Frequncy8, Signal8, "
					+ "SSID9, MAC9, Frequncy9, Signal9, "
					+ "SSID10, MAC10, Frequncy10, Signal10, ";		
			outs.println(Headlines);

			//filling the linked list of strings[] in the splitted lines
			sc3.nextLine();
			while(sc3.hasNext())
			{			
				CurrentLine = sc3.nextLine();
				allLines.add(CurrentLine.split(","));
			}	

			//Arranging the Wifies by time id lat lon alt
			LinkedList<String[]> newLines = new LinkedList<String[]>();
			while(allLines.size()>0)
			{
				newLines.add(allLines.getFirst());
				allLines.removeFirst();
				for(int i=0;i<allLines.size();i++)
				{
					if((newLines.getFirst()[3].equals(allLines.get(i)[3]))//times
							&&(newLines.getFirst()[6].equals(allLines.get(i)[6]))//lat
							&&(newLines.getFirst()[7].equals(allLines.get(i)[7]))//lon
							&&(newLines.getFirst()[8].equals(allLines.get(i)[8])))//alt
					{
						newLines.add(allLines.get(i));
						allLines.remove(i);
						i--;
					}
				}	

				//find top ten signals
				if(newLines.size()>10)
					Top10Signals(newLines);

				//writing to the new csv
				int WifiNetworks = newLines.size();
				String tilla = newLines.getFirst()[3] + ","//time
						+ newLines.getFirst()[11] + ","//id
						+ newLines.getFirst()[6] + ","//lat
						+ newLines.getFirst()[7] + ","//lon
						+ newLines.getFirst()[8] + ","//alt
						+ WifiNetworks + ",";
				outs.print(tilla);
				while(newLines.size()>0)
				{
					outs.print(newLines.getFirst()[1] + ","//ssid
							+ newLines.getFirst()[0] + ","//mac
							+ newLines.getFirst()[4] + ","//freq
							+ newLines.getFirst()[5] + ",");//signal
					newLines.removeFirst();
				}
				outs.println();
			}

			outs.close();
			sc3.close();		
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Arranged CSV Was Created At " + OutputPath);
	}

	/*
	 * Turns the arranged CSV file into a KML
	 * that can be displayed on Google Earth 
	 * Using JAK(Java API for KML).
	 */
	public void ApiArrangedCSVtoKML(String OutputPath)
	{
		try {
			Kml kml = new Kml();
			de.micromata.opengis.kml.v_2_2_0.Document document = kml.createAndSetDocument().withName("Wifi Networks");
			de.micromata.opengis.kml.v_2_2_0.Folder folder = document.createAndAddFolder().withName("Wifi Networks");
			Scanner sc2 = new Scanner(file);
			String CurrentLine;
			String[] SplitLine;
			sc2.nextLine();
			while(sc2.hasNext())
			{			
				CurrentLine = sc2.nextLine();
				SplitLine = CurrentLine.split(",");
				int NumofWifiesinLine = Integer.parseInt(SplitLine[5]);
				for(int i=0;i<NumofWifiesinLine;i++)
				{
					de.micromata.opengis.kml.v_2_2_0.TimeStamp ts = new de.micromata.opengis.kml.v_2_2_0.TimeStamp();
					ts.setWhen(SplitLine[0].replace(' ','T') + 'Z'); 
					folder.createAndAddPlacemark().withTimePrimitive(ts)
					.withName(SplitLine[6+4*i]).withDescription("SSID: " + SplitLine[6+4*i]  + "\n" +  "BSSID: " + SplitLine[7+4*i] + "\n" + "ID: " + SplitLine[1]  + "\n" +  "Time: " + SplitLine[0]  + "\n" +  "Frequency: " + SplitLine[8+4*i]  + "\n" +  "Signal: " + SplitLine[9+4*i]).withOpen(Boolean.TRUE)
					.createAndSetPoint().addToCoordinates(Double.parseDouble(SplitLine[3]),Double.parseDouble(SplitLine[2]));
					/*	"<Placemark>\n" + "<name><![CDATA[" + SplitLine[6+4*i] + "]]></name>\n" +
							"<description><![CDATA[SSID: <b>" + SplitLine[6+4*i] + "</b><br/>BSSID: <b>" + SplitLine[7+4*i] + "</b><br/>ID: <b>" + SplitLine[1] + "</b><br/>Time: <b>" + SplitLine[0] + "</b><br/>Frequency: <b>" + SplitLine[8+4*i] + "</b><br/>Signal: <b>" + SplitLine[9+4*i] + "</b>]]></description><styleUrl>#green</styleUrl>\n" +
							"<Point>\n" + "<coordinates>" + SplitLine[3] + "," + SplitLine[2] + "</coordinates></Point>\n" +
							"</Placemark>\n";*/
					
				}
			}
			//kml.marshal();
			kml.marshal(new File(OutputPath));
			sc2.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("KML Was Created At " + OutputPath);	
	}

	/*
	 * Turns the arranged CSV file into a KML
	 * that can be displayed on Google Earth. 
	 */
	public void ArrangedCSVtoKML(String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc3 = new Scanner(file);
			String CurrentLine;
			String[] SplitLine;
			sc3.nextLine();
			String kmlelement = "";

			while(sc3.hasNext())
			{			
				CurrentLine = sc3.nextLine();
				SplitLine = CurrentLine.split(",");
				int NumofWifiesinLine = Integer.parseInt(SplitLine[5]);
				for(int i=0;i<NumofWifiesinLine;i++)
				{
					kmlelement +="<Placemark>\n" + "<name><![CDATA[" + SplitLine[6+4*i] + "]]></name>\n" +
							"<description><![CDATA[SSID: <b>" + SplitLine[6+4*i] + "</b><br/>BSSID: <b>" + SplitLine[7+4*i] + "</b><br/>ID: <b>" + SplitLine[1] + "</b><br/>Time: <b>" + SplitLine[0] + "</b><br/>Frequency: <b>" + SplitLine[8+4*i] + "</b><br/>Signal: <b>" + SplitLine[9+4*i] + "</b>]]></description><styleUrl>#green</styleUrl>\n" +
							"<Point>\n" + "<coordinates>" + SplitLine[3] + "," + SplitLine[2] + "</coordinates></Point>\n" +
							"</Placemark>\n";
				}
			}
			String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n";

			String kmlend = "</Folder>\n" + "</Document></kml>";

			ArrayList<String> content = new ArrayList<String>();
			String kmltest;

			content.add(0,kmlstart);
			content.add(1,kmlelement);
			content.add(2,kmlend);
			kmltest = content.get(0) + content.get(1) + content.get(2);
			outs.write(kmltest);
			outs.close();
			sc3.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("KML Was Created At " + OutputPath);
	}

	//Filters the arranged CSV file by specific Time.
	public void FilterByTimeArrangedCSV(String time,String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc3 = new Scanner(file);
			String CurrentLine;
			String[] SplitLine;
			outs.println(sc3.nextLine());
			while(sc3.hasNext())
			{			
				CurrentLine = sc3.nextLine();
				SplitLine = CurrentLine.split(",");
				if(time.equals(SplitLine[0]))
					outs.println(CurrentLine);
			}
			outs.close();
			sc3.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Arranged CSV Filtered By Time Was Created At " + OutputPath);
	}

	//Filters the arranged CSV file by specific Location.
	public void FilterByLocationArrangedCSV(String lat,String lon,String alt,String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc3 = new Scanner(file);
			String CurrentLine;
			String[] SplitLine;
			outs.println(sc3.nextLine());
			while(sc3.hasNext())
			{			
				CurrentLine = sc3.nextLine();
				SplitLine = CurrentLine.split(",");
				if(lat.equals(SplitLine[2])&&lon.equals(SplitLine[3])&&alt.equals(SplitLine[4]))
					outs.println(CurrentLine);
			}
			outs.close();
			sc3.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Arranged CSV Filtered By Location Was Created At " + OutputPath);
	}

	//Filters the arranged CSV file by specific ID.
	public void FilterByIDArrangedCSV(String id,String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc3 = new Scanner(file);
			String CurrentLine;
			String[] SplitLine;
			outs.println(sc3.nextLine());
			while(sc3.hasNext())
			{			
				CurrentLine = sc3.nextLine();
				SplitLine = CurrentLine.split(",");
				if(id.equals(SplitLine[1]))
					outs.println(CurrentLine);
			}
			outs.close();
			sc3.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Arranged CSV Filtered By ID Was Created At " + OutputPath);
	}

	/*	public void TopTenSignals(String OutputPath)
	{
		try {
			Scanner sc2 = new Scanner(file);
			int[] arr = new int[lines];
			String[] s = new String[11];
			int rxl;
			int i=0;
			String str;
			sc2.nextLine();
			while (sc2.hasNext())
			{
				str = sc2.nextLine();
				s = str.split(";");
				rxl = Integer.parseInt(s[8]);
				arr[i] = rxl;
				i++;
			}
			Arrays.sort(arr);
			for(int k=0;k<lines;k++)
				System.out.print(arr[k] + " ");
			System.out.println();
			int limit = arr[43-9];  //limit of the top 10 signals
			System.out.println("limit = " + limit);

			//write to a new file the 10 highest signals
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			String[] g = new String[11];
			Scanner sc3 = new Scanner(file);	
			int rxl2=0;
			String str2;
			outs.println(sc3.nextLine());
			while(sc3.hasNext())
			{			
				str2 = sc3.nextLine();
				g = str2.split(";");
				rxl2 = Integer.parseInt(g[8]);
				if(rxl2 >= limit)
					outs.println(str2);				
			}

			outs.close();
			sc2.close();
			sc3.close();
		}

		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}

	}*/

	//	public void CSVtoKML(String OutputPath)
	//	{
	//		/*	try{
	//			//String BSSID,LAT,LON,SSID,FirstCoordinate,SecondCoordinate,
	//			File OutputFile = new File(OutputPath);
	//			PrintWriter outs = new PrintWriter(OutputFile);
	//			String[] g = new String[11];
	//			Scanner sc3 = new Scanner(file);	
	//			String str2;
	//			sc3.nextLine();
	//			while(sc3.hasNext())
	//			{			
	//			str2 = sc3.nextLine();
	//			g = str2.split(";");
	//			outs.print("");
	//			}
	//			outs.close();
	//			sc3.close();	
	//		}
	//
	//		catch(IOException ex)
	//		{
	//			System.out.print("Error reading file\n" + ex);
	//			System.exit(2);
	//		}*/
	//
	//		//офд жд одайришрииииииииииииииииииииииииииииииииии@@@@@@
	//		try {
	//			File OutputFile = new File(OutputPath);
	//			PrintWriter outs = new PrintWriter(OutputFile);
	//			Scanner sc3 = new Scanner(file);
	//			String CurrentLine;
	//			String[] g = new String[11];
	//			sc3.nextLine();
	//			String kmlelement = "";
	//
	//			while(sc3.hasNext())
	//			{			
	//				CurrentLine = sc3.nextLine();
	//				g = CurrentLine.split(";");
	//				kmlelement +="\t<Placemark>\n" +
	//						"\t\t<description>"+g[3]+"\n"+g[0]+"</description>\n" +
	//						"\t\t<styleUrl>#" + g[4] + "</styleUrl>\n" + "\t\t<visibility>0</visibility>\n" 
	//						+ "\t\t<Point>\n" +"\t\t<extrude>1</extrude>\n" +"\t\t<altitudeMode>relativeToGround</altitudeMode>\n"
	//						+ "\t\t<coordinates>"+g[2]+","+g[1]+"</coordinates>\n" +
	//						"\t\t</Point>\n" +
	//						"\t</Placemark>\n";
	//				//outs.print("");
	//			}
	//			String kmlstart = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
	//					"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n<Document>\n";
	//
	//			String kmlend = "</Document>\n"+"</kml>";
	//
	//			ArrayList<String> content = new ArrayList<String>();
	//			//content.add(0,kmlstart);
	//			//content.add(1,kmlelement);
	//			//content.add(2,kmlend);
	//
	//			String kmltest;
	//
	//
	//			//Zum Einsetzen eines Substrings (weitere Placemark)
	//			//String test = "</kml>";
	//			//int index = kml.lastIndexOf(test);
	//
	//
	//			content.add(0,kmlstart);
	//			content.add(1,kmlelement);
	//			content.add(2,kmlend);
	//			kmltest = content.get(0) + content.get(1) + content.get(2);
	//			outs.write(kmltest);
	//			outs.close();
	//			sc3.close();
	//		}
	//		catch(IOException ex)
	//		{
	//			System.out.print("Error reading file\n" + ex);
	//			System.exit(2);
	//		}
	//	}

}
