package Location;

/*
 * The class defines a object named "Wifi" with the wifi's properties.
 */

public class Wifi {

	private String mac;
	private double lat, lon, alt;
	private int signal;
	private double power;
	private double wlat, wlon, walt;
	private int diff;
	private double w;
	
	public Wifi()
	{
		signal=-120;
		diff=100;
	}

	public Wifi(String mac, String lat, String lon, String alt, String signal)
	{
		this.mac = mac;
		this.lat = Double.parseDouble(lat);
		this.lon = Double.parseDouble(lon);
		this.alt = Double.parseDouble(alt);
		this.signal = Integer.parseInt(signal);
		
		this.power = 1/Math.pow(this.signal, 2);
		this.wlat = power*this.lat;
		this.wlon = power*this.lon;
		this.walt = power*this.alt;
	}

	public Wifi(String mac, double lat, double lon, double alt, int signal)
	{
		this.mac = mac;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.signal = signal;
		this.power = 1/Math.pow(this.signal, 2);
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getAlt() {
		return alt;
	}

	public void setAlt(double alt) {
		this.alt = alt;
	}

	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}
	
	public double getWlat() {
		return wlat;
	}

	public void setWlat(double wlat) {
		this.wlat = wlat;
	}

	public double getWlon() {
		return wlon;
	}

	public void setWlon(double wlon) {
		this.wlon = wlon;
	}

	public double getWalt() {
		return walt;
	}

	public void setWalt(double walt) {
		this.walt = walt;
	}

	public int getDiff() {
		return diff;
	}
	
	public void setDiff(int diff) {
		this.diff = diff;
	}
	
	public double getW() {
		return w;
	}
	
	public void setW(double w) {
		this.w = w;
	}
	
}
