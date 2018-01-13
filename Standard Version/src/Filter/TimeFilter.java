package Filter;

import KML.WiFi;
/*
 * The class role is to determine whether or not a WiFi is in a specific time range.
 */
public class TimeFilter implements Filter{	
	private String start, end;
	
	public TimeFilter(String start, String end)
	{
		this.start = start;
		this.end = end;
	}
	
	public boolean test(WiFi w) 
	{
		return start.compareTo(w.getTime())<=0 && end.compareTo(w.getTime())>=0;
	}
	
	public String toString()
	{
		return "TimeFilter" + "(" + start + "," + end + ")";
	}
}
