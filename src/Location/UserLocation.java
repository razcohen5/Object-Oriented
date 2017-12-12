package Location;

/*
 * The class role is to estimate the user location by 3 different wifi
 * with the 3 strongest samples of each wifi using weighted average
 * on lat, lon, alt, when weight is calculated based on input and constants.
 */

public class UserLocation {
	String mac1,mac2,mac3; //3 mac addresses that are used to find the user location
	Wifi[] wifi1; //mac1's samples
	Wifi[] wifi2; //mac2's samples
	Wifi[] wifi3; //mac3's samples
	double wifi1Location[]; //mac1's location after using findlocation function on him
	double wifi2Location[]; //mac2's location after using findlocation function on him
	double wifi3Location[]; //mac3's location after using findlocation function on him
	
	//constants
	int[] input = {-50,-70,-90}; 
	double norm = 10000;
	double sigdiff = 0.4;
	double power = 2;
	int mindiff = 3;
	
	public UserLocation(String mac1, String mac2, String mac3, String path)
	{
		WifiLocation wl1 = new WifiLocation(mac1,path);
		WifiLocation wl2 = new WifiLocation(mac2,path);
		WifiLocation wl3 = new WifiLocation(mac3,path);

		wifi1Location = wl1.FindLocation();
		wifi2Location = wl2.FindLocation();
		wifi3Location = wl3.FindLocation();

		wifi1 = new Wifi[3];
		wifi2 = new Wifi[3];
		wifi3 = new Wifi[3];
		
		int i,j,k;

		for(i=0;i<wl1.getSamples().size();i++)
		{
			wifi1[i] = wl1.getSamples().elementAt(i);
			wifi1[i].setDiff(Math.max(mindiff, Math.abs(input[i]-wifi1[i].getSignal())));
			wifi1[i].setW(norm/(f1(wifi1[i].getDiff())*f2(input[i])));
		}
		for(;i<3;i++)
		{
			wifi1[i] = new Wifi();
			wifi1[i].setW(norm/(f1(wifi1[i].getDiff())*f2(input[i])));
		}

		for(j=0;j<wl2.getSamples().size();j++)
		{
			wifi2[j] = wl2.getSamples().elementAt(j);
			wifi2[j].setDiff(Math.max(mindiff, Math.abs(input[j]-wifi2[j].getSignal())));
			wifi2[j].setW(norm/(f1(wifi2[j].getDiff())*f2(input[j])));
		}
		for(;j<3;j++)
		{
			wifi2[j] = new Wifi();
			wifi2[j].setW(norm/(f1(wifi2[j].getDiff())*f2(input[j])));
		}

		for(k=0;k<wl3.getSamples().size();k++)
		{
			wifi3[k] = wl3.getSamples().elementAt(k);
			wifi3[k].setDiff(Math.max(mindiff, Math.abs(input[k]-wifi3[k].getSignal())));
			wifi3[k].setW(norm/(f1(wifi3[k].getDiff())*f2(input[k])));
		}
		for(;k<3;k++)
		{
			wifi3[k] = new Wifi();
			wifi3[k].setW(norm/(f1(wifi3[k].getDiff())*f2(input[k])));
		}
	}

	public double[] FindLocation()
	{
		Wifi final1 = new Wifi(mac1,wifi1Location[0],wifi1Location[1],wifi1Location[2],0);
		final1.setW(wifi1[0].getW()*wifi1[1].getW()*wifi1[2].getW());
		final1.setWlat(final1.getW()*final1.getLat());
		final1.setWlon(final1.getW()*final1.getLon());
		final1.setWalt(final1.getW()*final1.getAlt());

		Wifi final2 = new Wifi(mac2,wifi2Location[0],wifi2Location[1],wifi2Location[2],0);
		final2.setW(wifi2[0].getW()*wifi2[1].getW()*wifi2[2].getW());
		final2.setWlat(final2.getW()*final2.getLat());
		final2.setWlon(final2.getW()*final2.getLon());
		final2.setWalt(final2.getW()*final2.getAlt());

		Wifi final3 = new Wifi(mac3,wifi3Location[0],wifi3Location[1],wifi3Location[2],0);
		final3.setW(wifi3[0].getW()*wifi3[1].getW()*wifi3[2].getW());
		final3.setWlat(final3.getW()*final3.getLat());
		final3.setWlon(final3.getW()*final3.getLon());
		final3.setWalt(final3.getW()*final3.getAlt());

		double wSum = final1.getW() + final2.getW() + final3.getW();
		double wlatSum = final1.getWlat() + final2.getWlat() + final3.getWlat();
		double wlonSum = final1.getWlon() + final2.getWlon() + final3.getWlon();
		double waltSum = final1.getWalt() + final2.getWalt() + final3.getWalt();

		double FinalLat = wlatSum/wSum;
		double FinalLon = wlonSum/wSum;
		double FinalAlt = waltSum/wSum;	
		double FinalLocation[] = {FinalLat,FinalLon,FinalAlt};
		
//		System.out.println("User location is at: ");
//		System.out.println("Lat =  " + FinalLat);
//		System.out.println("Lon =  " + FinalLon);
//		System.out.println("Alt =  " + FinalAlt);
		
		return FinalLocation;
	}

	private double f1(double x)
	{
		return Math.pow(x, sigdiff);
	}

	private double f2(double x)
	{
		return Math.pow(x, power);
	}

}
