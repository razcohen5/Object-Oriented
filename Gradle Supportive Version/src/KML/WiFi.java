package KML;
/*
 * The class defines a object named "WiFi" with WiFi properties.
 */
public class WiFi {
	
	private String mac, ssid, time, id, type, auth;
	private double lat, lon, alt;
	private double signal;
	private double frequency;
	private double accuracy;
	private double power;
	private double wlat, wlon, walt;
	private double diff;
	private double w;
	
	public WiFi()
	{
		signal=-120;
		diff=100;
	}
	
	public WiFi(String line)
	{
		String[] SplitLine = line.split(",");
		mac = SplitLine[0];
		ssid = SplitLine[1];
		auth = SplitLine[2];
		time = SplitLine[3];
		frequency = Double.parseDouble(SplitLine[4]);
		signal = Double.parseDouble(SplitLine[5]);
		lat = Double.parseDouble(SplitLine[6]);
		lon = Double.parseDouble(SplitLine[7]);
		alt = Double.parseDouble(SplitLine[8]);
		accuracy = Double.parseDouble(SplitLine[9]);
		type = SplitLine[10];
		id = SplitLine[11];
		this.power = 1/Math.pow(this.signal, 2);
		this.wlat = power*this.lat;
		this.wlon = power*this.lon;
		this.walt = power*this.alt;
	}
	
	public WiFi(String line, String id)
	{
		String[] SplitLine = line.split(",");
		mac = SplitLine[0];
		ssid = SplitLine[1];
		auth = SplitLine[2];
		time = SplitLine[3];
		frequency = Double.parseDouble(SplitLine[4]);
		signal = Double.parseDouble(SplitLine[5]);
		lat = Double.parseDouble(SplitLine[6]);
		lon = Double.parseDouble(SplitLine[7]);
		alt = Double.parseDouble(SplitLine[8]);
		accuracy = Double.parseDouble(SplitLine[9]);
		type = SplitLine[10];
		this.id = id;
		this.power = 1/Math.pow(this.signal, 2);
		this.wlat = power*this.lat;
		this.wlon = power*this.lon;
		this.walt = power*this.alt;
	}
	
	public WiFi(WiFi other)
	{
		mac = other.getMac();
		ssid = other.getSsid();
		time = other.getTime();
		id = other.getId();
		type = other.getType();
		auth = other.getAuth();
		lat = other.getLat();
		lon = other.getLon();
		alt = other.getAlt();
		signal = other.getSignal();
		frequency = other.getFrequency();
		accuracy = other.getAccuracy();
	}

	public WiFi(String mac, String lat, String lon, String alt, String signal)
	{
		this.mac = mac;
		this.lat = Double.parseDouble(lat);
		this.lon = Double.parseDouble(lon);
		this.alt = Double.parseDouble(alt);
		this.signal = Double.parseDouble(signal);
		
		this.power = 1/Math.pow(this.signal, 2);
		this.wlat = power*this.lat;
		this.wlon = power*this.lon;
		this.walt = power*this.alt;
	}

	public WiFi(String mac, double lat, double lon, double alt, double signal)
	{
		this.mac = mac;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.signal = signal;

		this.power = 1/Math.pow(this.signal, 2);
		this.wlat = power*this.lat;
		this.wlon = power*this.lon;
		this.walt = power*this.alt;
	}
	
	public WiFi(String time, String device, double lat, double lon, double alt, String mac, int rssi)//sql builder
	{
		this.time = time;
		this.id = device;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.mac = mac;
		this.signal = (double)rssi;
		
		this.power = 1/Math.pow(this.signal, 2);
		this.wlat = power*this.lat;
		this.wlon = power*this.lon;
		this.walt = power*this.alt;
	}
	
	public String PrintArrangedFormat1()
	{
		return time + "," + id + "," + lat + "," + lon + "," + alt;
	}
	
	public String PrintArrangedFormat2()
	{
		return "," + ssid + "," + mac + "," + frequency + "," + signal;
	}
	
	public String toString() {
		return  mac + "," + ssid + "," + auth + "," + time + "," + frequency + ","
				+ signal + "," + lat + "," + lon + "," + alt + "," + accuracy + ","
				+ type + "," + id;
	}
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
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
	public double getSignal() {
		return signal;
	}
	public void setSignal(double signal) {
		this.signal = signal;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
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

	public double getDiff() {
		return diff;
	}

	public void setDiff(double diff) {
		this.diff = diff;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}
	
	
}
