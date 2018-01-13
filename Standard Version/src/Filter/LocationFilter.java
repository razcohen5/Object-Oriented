package Filter;

import KML.WiFi;
/*
 * The class role is to determine whether or not a WiFi is in a specific lat,lon,alt range.
 */
public class LocationFilter implements Filter{
	private double lat1, lon1, alt1;
	private double lat2, lon2, alt2;
	
	public LocationFilter(double lat1, double lon1, double alt1, double lat2, double lon2, double alt2)
	{
		this.lat1 = lat1;
		this.lon1 = lon1;
		this.alt1 = alt1;
		this.lat2 = lat2;
		this.lon2 = lon2;
		this.alt2 = alt2;
	}

	public LocationFilter(String lat1, String lon1, String alt1, String lat2, String lon2, String alt2)
	{
		this.lat1 = Double.parseDouble(lat1);
		this.lon1 = Double.parseDouble(lon1);
		this.alt1 = Double.parseDouble(alt1);
		this.lat2 = Double.parseDouble(lat2);
		this.lon2 = Double.parseDouble(lon2);
		this.alt2 = Double.parseDouble(alt2);
	}

	public boolean test(WiFi w) 
	{
		boolean LatTest = lat1<=w.getLat() && w.getLat()<=lat2;
		boolean LonTest = lon1<=w.getLon() && w.getLon()<=lon2;
		boolean AltTest = alt1<=w.getAlt() && w.getAlt()<=alt2;
		return LatTest && LonTest && AltTest;
	}
	
	public String toString()
	{
		return "LocationFilter" + "(" + lat1 + " " + lon1 + " " + alt1 + "," + lat2 + " " + lon2 + " " + alt2 + ")";
	}
	
}
