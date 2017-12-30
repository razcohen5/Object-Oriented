package Location;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import KML.ArrangeWiFis;
import KML.WiFis;
/*
 * The class role is to compare file of my results to ex 2 algorithm 1 to test file of results.
 */
public class Ex2TestAlgo1 {

	File MyFile;
	File TestFile;

	public Ex2TestAlgo1(String MyPath, String TestPath)
	{
		MyFile = new File(MyPath);
		TestFile = new File(TestPath);
	}

	public void CompareCSV(String OutputPath)
	{
		try {
			File OutputFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(OutputFile);
			Scanner sc1 = new Scanner(MyFile);
			String CurrentLine1;
			String[] SplitLine1;
			sc1.nextLine();	
			String Headlines = "SSID, MAC, MyLat, MyLon, MyAlt, TestLat, TestLon, TestAlt";
			outs.println(Headlines);
			while(sc1.hasNext())
			{			
				CurrentLine1 = sc1.nextLine();
				SplitLine1 = CurrentLine1.split(",");
				Scanner sc2 = new Scanner(TestFile);
				while(sc2.hasNext())
				{
					String CurrentLine2 = sc2.nextLine();
					String SplitLine2[] = CurrentLine2.split(",");
					if(SplitLine1[7].equals(SplitLine2[1]))
						outs.println(			SplitLine1[6]
										+ "," + SplitLine1[7]
										+ "," + SplitLine1[2]
										+ "," + SplitLine1[3]
										+ "," + SplitLine1[4]
										+ "," + SplitLine2[5]
										+ "," + SplitLine2[6]
										+ "," + SplitLine2[7]);
					sc2.nextLine();
				}
				sc2.close();
			}
			outs.close();
			sc1.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("Compare CSV Was Created At " + OutputPath);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ScannedFolder = "C:\\Users\\Raz\\Desktop\\Output\\LocationOutput\\Ex2TestOutput\\BM2";
		String MyFile = "C:\\Users\\Raz\\Desktop\\Output\\LocationOutput\\Ex2TestOutput\\MyAlgo1.csv";
		String TestFile = "C:\\Users\\Raz\\Desktop\\Output\\LocationOutput\\Ex2TestOutput\\Algo1_4_BM2_comb_all_.csv";
		String CompareFile = "C:\\Users\\Raz\\Desktop\\Output\\LocationOutput\\Ex2TestOutput\\CompareAlgo1.csv";
		
		WiFis w = new WiFis();
		w.ReadFolder(ScannedFolder);
		ArrangeWiFis aw = new ArrangeWiFis(w);
		aw.ArrangeSamples();
		WiFiLocationCSV wl = new WiFiLocationCSV(aw);
		wl.WifiLocationToCSV(MyFile);
		
		Ex2TestAlgo1 t = new Ex2TestAlgo1(MyFile,TestFile);
		t.CompareCSV(CompareFile);
	}

}
