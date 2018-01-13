package KML;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

import de.micromata.opengis.kml.v_2_2_0.Kml;
/*
 * The class contains an arranged version of WiFis' database.
 * it storages the data in vector of (Vectors of WiFis) such that
 * every element in the vector is a group of WiFis(vector of WiFis)
 * that has the same Time,ID and Location.
 */
public class ArrangeWiFis {

	private Vector<WiFi> samples;
	private Vector<Vector<WiFi>> ArrangedSamples;

	public ArrangeWiFis(WiFis w)
	{
		this.samples = new Vector<WiFi>();
		for(int i=0;i<w.getDatabase().size();i++)
			this.samples.addElement(w.getDatabase().elementAt(i));

		ArrangedSamples = new Vector<Vector<WiFi>>();
	}

	/*
	 * Arranges the database in CSV file so that in each line there will be max 10 wifi
	 * that have the same Time, Location and ID.
	 */
	public void ArrangeSamples()
	{	
		//Arranging the Wifies by time id lat lon alt
		int index=0;
		System.out.println(samples.size());
		while(samples.size()>0)//dont forget to increase index
		{
			ArrangedSamples.add(index, new Vector<WiFi>());
			ArrangedSamples.elementAt(index).add(samples.firstElement());
			samples.remove(0);
			for(int i=0;i<samples.size();i++)
			{
				if((ArrangedSamples.elementAt(index).firstElement().getTime().equals(samples.elementAt(i).getTime()))//times
						&&(ArrangedSamples.elementAt(index).firstElement().getLat()==(samples.elementAt(i).getLat()))//lat
						&&(ArrangedSamples.elementAt(index).firstElement().getLon()==(samples.elementAt(i).getLon()))//lon
						&&(ArrangedSamples.elementAt(index).firstElement().getAlt()==(samples.elementAt(i).getAlt())))//alt
				{
					ArrangedSamples.elementAt(index).add(samples.elementAt(i));
					samples.remove(i);
					i--;
				}				
			}	

			if(ArrangedSamples.elementAt(index).size()>10)
				Top10Signals(ArrangedSamples.elementAt(index));

			index++;	
		}
	}

	/*
	 * Keeps only the 10 strongest signals if there are more than 10.
	 */
	private void Top10Signals(Vector<WiFi> v)
	{
		//Find the signal limit of the top 10 signals
		double SignalLimit;
		double[] signals = new double[v.size()];
		for(int i=0;i<v.size();i++)
			signals[i] = v.elementAt(i).getSignal();	
		Arrays.sort(signals);
		SignalLimit = signals[signals.length-10];

		//Find top 10 signals above the signal limit
		for(int i=0;i<v.size()&&v.size()>10;i++)
			if(v.elementAt(i).getSignal()<=SignalLimit)
			{
				v.remove(i);
				i--;
			}			
	}

	public void toArrangedCSV(String OutputPath)
	{
		try{
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			//Print headlines
			String Headlines = "Time, ID, Lat, Lon, Alt, #WiFi networks, "
					+ "SSID1, MAC1, Frequency1, Signal1, "
					+ "SSID2, MAC2, Frequency2, Signal2, "
					+ "SSID3, MAC3, Frequency3, Signal3, "
					+ "SSID4, MAC4, Frequency4, Signal4, "
					+ "SSID5, MAC5, Frequency5, Signal5, "
					+ "SSID6, MAC6, Frequency6, Signal6, "
					+ "SSID7, MAC7, Frequency7, Signal7, "
					+ "SSID8, MAC8, Frequency8, Signal8, "
					+ "SSID9, MAC9, Frequency9, Signal9, "
					+ "SSID10, MAC10, Frequency10, Signal10, ";		
			outs.println(Headlines);

			//Writing to the new csv
			for(int i=0;i<ArrangedSamples.size();i++)
				outs.println(this.PrintArrangedFormat(ArrangedSamples.elementAt(i)));

			outs.close();	
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Arranged CSV Was Created At " + OutputPath);
	}

	private String PrintArrangedFormat(Vector<WiFi> v)
	{
		String s = v.firstElement().PrintArrangedFormat1() + "," + v.size();
		for(int i=0;i<v.size();i++)
			s = s + v.elementAt(i).PrintArrangedFormat2();
		return s;
	}
	
	public void toKML(String OutputPath)
	{
		try {
			Kml kml = new Kml();
			de.micromata.opengis.kml.v_2_2_0.Document document = kml.createAndSetDocument().withName("Wifi Networks");
			de.micromata.opengis.kml.v_2_2_0.Folder folder = document.createAndAddFolder().withName("Wifi Networks");
			for(int i=0;i<ArrangedSamples.size();i++)	
			{
				de.micromata.opengis.kml.v_2_2_0.TimeStamp ts = new de.micromata.opengis.kml.v_2_2_0.TimeStamp();
				ts.setWhen(ArrangedSamples.elementAt(i).firstElement().getTime().replace(' ','T') + 'Z'); 
				for(int j=0;j<ArrangedSamples.elementAt(i).size();j++)
				{

					folder.createAndAddPlacemark().withTimePrimitive(ts)
					.withName(ArrangedSamples.elementAt(i).elementAt(j).getSsid())
					.withDescription("SSID: " + ArrangedSamples.elementAt(i).elementAt(j).getSsid()  + "\n" +  "BSSID: " + ArrangedSamples.elementAt(i).elementAt(j).getMac() + "\n" + "ID: " + ArrangedSamples.elementAt(i).elementAt(j).getId()  + "\n" +  "Time: " + ArrangedSamples.elementAt(i).elementAt(j).getTime()  + "\n" +  "Frequency: " + ArrangedSamples.elementAt(i).elementAt(j).getFrequency()  + "\n" +  "Signal: " + ArrangedSamples.elementAt(i).elementAt(j).getSignal())
					.withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(ArrangedSamples.elementAt(i).elementAt(j).getLon(), ArrangedSamples.elementAt(i).elementAt(j).getLat(), ArrangedSamples.elementAt(i).elementAt(j).getAlt());
					/*	"<Placemark>\n" + "<name><![CDATA[" + SplitLine[6+4*i] + "]]></name>\n" +
							"<description><![CDATA[SSID: <b>" + SplitLine[6+4*i] + "</b><br/>BSSID: <b>" + SplitLine[7+4*i] + "</b><br/>ID: <b>" + SplitLine[1] + "</b><br/>Time: <b>" + SplitLine[0] + "</b><br/>Frequency: <b>" + SplitLine[8+4*i] + "</b><br/>Signal: <b>" + SplitLine[9+4*i] + "</b>]]></description><styleUrl>#green</styleUrl>\n" +
							"<Point>\n" + "<coordinates>" + SplitLine[3] + "," + SplitLine[2] + "</coordinates></Point>\n" +
							"</Placemark>\n";*/
					
				}
			}
			//kml.marshal();
			kml.marshal(new File(OutputPath));
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("KML Was Created At " + OutputPath);
	}

	public Vector<Vector<WiFi>> getArrangedSamples() {
		return ArrangedSamples;
	}

	public void setArrangedSamples(Vector<Vector<WiFi>> arrangedSamples) {
		ArrangedSamples = arrangedSamples;
	}
	
}
