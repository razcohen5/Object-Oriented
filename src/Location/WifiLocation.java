package Location;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import KML.ArrangeWiFis;
import KML.WiFi;
import KML.WiFis;

/*
 * The class role is to estimate a wifi's location
 * by the 3 strongest samples of him using weighted average
 * on lat, lon, alt, when weight is calculated based on the signal.
 */

public class WiFiLocation {

	private String mac;
	private Vector<WiFi> samples;

	public WiFiLocation(String mac,ArrangeWiFis ArrangedDatabase)
	{
		this.mac = mac;
		samples = new Vector<WiFi>();
		FillSamples(ArrangedDatabase);
	}

	public double[] FindLocation()
	{	
		double powerSum = 0;
		double wlatSum = 0;
		double wlonSum = 0;
		double waltSum = 0;

		for(int i=0;i<samples.size();i++)
		{
			powerSum += samples.elementAt(i).getPower();
			wlatSum += samples.elementAt(i).getWlat();
			wlonSum += samples.elementAt(i).getWlon();
			waltSum += samples.elementAt(i).getWalt();
		}

		double FinalLat = wlatSum/powerSum;
		double FinalLon = wlonSum/powerSum;
		double FinalAlt = waltSum/powerSum;	
		double FinalLocation[] = {FinalLat,FinalLon,FinalAlt};

//		System.out.println("Wifi location is at: ");
//		System.out.println("Lat =  " + FinalLat);
//		System.out.println("Lon =  " + FinalLon);
//		System.out.println("Alt =  " + FinalAlt);

		return FinalLocation;	
	}

	//Prints the vector's samples
	private void PrintSamples()
	{
		for(int i=0;i<samples.size();i++)
			System.out.println(samples.elementAt(i).getMac()+ " " + samples.elementAt(i).getLat() + " " + samples.elementAt(i).getLon()+ " " + samples.elementAt(i).getAlt()+ " " + samples.elementAt(i).getSignal()+ " " + samples.elementAt(i).getPower());
	}

	/*
	 * Keeps only the 3 strongest signals if there are more than 10.
	 */
	private void FindTop3Signals()
	{
		//Find the signal limit of the top 3 signals
		double SignalLimit;
		double[] signals = new double[samples.size()];
		for(int i=0;i<samples.size();i++)
			signals[i] = samples.elementAt(i).getSignal();
		Arrays.sort(signals);
		SignalLimit = signals[signals.length-3];

		//Find top 3 signals above the signal limit
		Vector<WiFi> Top3Samples = new Vector<WiFi>();
		for(int i=0;i<samples.size()&&Top3Samples.size()<3;i++)
			if(samples.elementAt(i).getSignal()>SignalLimit)
				Top3Samples.add(samples.elementAt(i));

		for(int i=0;i<samples.size()&&Top3Samples.size()<3;i++)
			if(samples.elementAt(i).getSignal()==SignalLimit)
				Top3Samples.add(samples.elementAt(i));

		samples = Top3Samples;
	}

	private void FillSamples(ArrangeWiFis ArrangedDatabase)
	{
		for(int i=0;i<ArrangedDatabase.getArrangedSamples().size();i++)
			for(int j=0;j<ArrangedDatabase.getArrangedSamples().elementAt(i).size();j++)
					if(mac.equals(ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j).getMac()))
						samples.addElement(ArrangedDatabase.getArrangedSamples().elementAt(i).elementAt(j));
		if(samples.size()>3)
			FindTop3Signals();
	}

	public Vector<WiFi> getSamples() {
		return samples;
	}

	public void setSamples(Vector<WiFi> samples) {
		this.samples = samples;
	}

}
