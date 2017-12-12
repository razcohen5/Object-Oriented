package Location;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/*
 * The class role is to estimate a wifi's location
 * by the 3 strongest samples of him using weighted average
 * on lat, lon, alt, when weight is calculated based on the signal.
 */

public class WifiLocation {

	private File ArrangedCSV;
	private String mac;
	private Vector<Wifi> samples;

	public WifiLocation(String mac,String path)
	{
		this.mac = mac;
		ArrangedCSV = new File(path);
		samples = new Vector<Wifi>();
	}

	public double[] FindLocation()
	{
		FillSamples();
		//	PrintSamples();

		if(samples.size()>3)
			FindTop3Signals();

		//	System.out.println("Top 3 Samples: ");
		//	PrintSamples();
		
		return WeightedAverage();	
	}

	//Prints the vector's samples
	private void PrintSamples()
	{
		for(int i=0;i<samples.size();i++)
			System.out.println(samples.elementAt(i).getMac()+ " " + samples.elementAt(i).getLat() + " " + samples.elementAt(i).getLon()+ " " + samples.elementAt(i).getAlt()+ " " + samples.elementAt(i).getSignal()+ " " + samples.elementAt(i).getPower());
	}

	private double[] WeightedAverage()
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

	/*
	 * Keeps only the 3 strongest signals if there are more than 10.
	 */
	private void FindTop3Signals()
	{
		//Find the signal limit of the top 3 signals
		int SignalLimit;
		int[] signals = new int[samples.size()];
		for(int i=0;i<samples.size();i++)
			signals[i] = samples.elementAt(i).getSignal();
		Arrays.sort(signals);
		SignalLimit = signals[signals.length-3];

		//Find top 3 signals above the signal limit
		Vector<Wifi> Top3Samples = new Vector<Wifi>();
		for(int i=0;i<samples.size()&&Top3Samples.size()<3;i++)
			if(samples.elementAt(i).getSignal()>SignalLimit)
				Top3Samples.add(samples.elementAt(i));

		for(int i=0;i<samples.size()&&Top3Samples.size()<3;i++)
			if(samples.elementAt(i).getSignal()==SignalLimit)
				Top3Samples.add(samples.elementAt(i));

		samples = Top3Samples;
	}

	private void FillSamples()
	{
		try {
			Scanner sc = new Scanner(ArrangedCSV);
			String CurrentLine;
			String[] SplitLine;
			sc.nextLine();
			while(sc.hasNext())
			{		
				CurrentLine = sc.nextLine();
				SplitLine = CurrentLine.split(",");
				int NumofWifiesinLine = Integer.parseInt(SplitLine[5]);
				for(int i=0;i<NumofWifiesinLine;i++)
				{
					if(mac.equals(SplitLine[7+4*i]))
						samples.addElement(new Wifi(mac,SplitLine[2],SplitLine[3],SplitLine[4],SplitLine[9+4*i]));
				}
			}
			sc.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}

	public Vector<Wifi> getSamples() {
		return samples;
	}

	public void setSamples(Vector<Wifi> samples) {
		this.samples = samples;
	}

}
