package Filter;

import KML.WiFi;
/*
 * The class role is to perform NOT Operation on a Filter.
 */
public class NotFilter implements Filter{
	private Filter filter;
	
	public NotFilter(Filter filter)
	{
		this.filter = filter;
	}
	
	public boolean test(WiFi w) 
	{	
		return !filter.test(w);
	}

	public String toString()
	{
		return "!(" + filter + ")";
	}
}
