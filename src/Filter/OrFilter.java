package Filter;

import KML.WiFi;
/*
 * The class role is to perform OR Operation between 2 Filters.
 */
public class OrFilter implements Filter{
	private Filter filter1, filter2;
	
	public OrFilter(Filter filter1, Filter filter2)
	{
		this.filter1 = filter1;
		this.filter2 = filter2;
	}
	
	public boolean test(WiFi w) 
	{	
		return filter1.test(w)||filter2.test(w);
	}

	public String toString()
	{
		return filter1 + "||" + filter2;
	}
}
