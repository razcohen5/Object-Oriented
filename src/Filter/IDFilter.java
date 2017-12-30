package Filter;

import KML.WiFi;
/*
 * The class role is to determine if a WiFi was scanned by a specific ID.
 */
public class IDFilter implements Filter{
	private String id;
	
	public IDFilter(String id)
	{
		this.id = id;
	}

	public boolean test(WiFi w) 
	{
		return w.getId().contains(id);
	}
	
	public String toString()
	{
		return "IDFilter" + "(" + id + ")";
	}
}
