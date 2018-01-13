package KML;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;
/*
 * The class contains a vector of WiFi objects that is used as a database,
 * the class has different methods to perform on this database.
 */
public class WiFis {

	private Vector<WiFi> Database;
	private String DatabaseFile;
	private String DatabaseFolder;
	private int NumberofFiles;
	private long FileLastModified;
	private long FilesLastModified[];
	private volatile String Source;
	private String SQLip;
	private String SQLport;
	private String SQLdatabase;
	private String SQLtable;
	private String SQLusername;
	private String SQLpassword;
	private String SQLurl;
	private String SQLlastmodified;

	public WiFis()
	{
		Database = new Vector<WiFi>(); 
	}
	/*
	 * The function role is to read a folder with csv files
	 * received from the application and unite them into a one big database  
	 * with all the information from all files.
	 */
	public void ReadFolder(String folder)
	{
		Source = "folder";
		this.DeleteAllWiFis();
		File FolderFile = new File(folder);
		File ListofFiles[] = FolderFile.listFiles();
		String names[] = FolderFile.list();
		DatabaseFolder = folder;
		NumberofFiles = ListofFiles.length;
		FilesLastModified = new long[ListofFiles.length];
		for(int i=0;i<ListofFiles.length;i++)
			if(names[i].startsWith("WigleWifi") && names[i].endsWith(".csv"))
			{
				try
				{
					FilesLastModified[i] = ListofFiles[i].lastModified();
					Scanner sc = new Scanner(ListofFiles[i]);
					String CurrentLine = sc.nextLine();
					String[] SplitLine = CurrentLine.split(",");
					String id = SplitLine[2];
					sc.nextLine();
					while(sc.hasNext())
					{
						CurrentLine = sc.nextLine();
						Database.addElement(new WiFi(CurrentLine,id));
					}
					sc.close();
				}
				catch(IOException ex)
				{
					System.out.print("Error reading file\n" + ex);
					System.exit(2);
				}
			}
	}

	public void ReadFile(String path)
	{
		Source = "file";
		this.DeleteAllWiFis();
		File file = new File(path);
		if(file.getName().startsWith("WigleWifi") && file.getName().endsWith(".csv"))
		{
			try
			{
				DatabaseFile = path;
				NumberofFiles = 1;
				FileLastModified = file.lastModified();
				Scanner sc = new Scanner(file);
				String CurrentLine = sc.nextLine();
				String[] SplitLine = CurrentLine.split(",");
				String id = SplitLine[2];
				sc.nextLine();
				while(sc.hasNext())
				{
					CurrentLine = sc.nextLine();
					Database.addElement(new WiFi(CurrentLine,id));
				}
				sc.close();
			}
			catch(IOException ex)
			{
				System.out.print("Error reading file\n" + ex);
				System.exit(2);
			}
		}
	}

	public void ReadSQL(String ip, String port, String database, String table, String username, String password)
	{
		Source = "sql";
		SQLip=ip;
		SQLport=port;
		SQLdatabase=database;
		SQLtable=table;
		SQLusername=username;
		SQLpassword=password;
		this.DeleteAllWiFis();
		SQLurl = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
		try
		{
			Connection con = DriverManager.getConnection(SQLurl, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from " + database + "." + table);
			Connection con2 = DriverManager.getConnection(SQLurl, username, password);
			Statement st2 = con2.createStatement();
			ResultSet lm = st2.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = '" + SQLdatabase + "' AND TABLE_NAME = '" + SQLtable + "'");
			lm.next();
			SQLlastmodified = lm.getString(1);
			//SELECT CHECKSUM_AGG(BINARY_CHECKSUM(*)) FROM sample_table WITH (NOLOCK);
			String line = "";
			while(rs.next())
			{
				for(int i=1;i<=12;i++)
					line = line + rs.getString(i) + ",";
				Database.addElement(new WiFi(line));
				line = "";
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ReadArrangedSQL(String ip, String port, String database, String table, String username, String password)
	{
		Source = "sql";
		SQLip=ip;
		SQLport=port;
		SQLdatabase=database;
		SQLtable=table;
		SQLusername=username;
		SQLpassword=password;
		this.DeleteAllWiFis();
		SQLurl = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
		try
		{
			Connection con = DriverManager.getConnection(SQLurl, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from " + database + "." + table);
			Connection con2 = DriverManager.getConnection(SQLurl, username, password);
			Statement st2 = con2.createStatement();
			ResultSet lm = st2.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = '" + SQLdatabase + "' AND TABLE_NAME = '" + SQLtable + "'");
			lm.next();
			SQLlastmodified = lm.getString(1);
			//SELECT CHECKSUM_AGG(BINARY_CHECKSUM(*)) FROM sample_table WITH (NOLOCK);

			while(rs.next())
			{
				int size = rs.getInt(7);
				for(int i=0;i<size;i++)
					//	line = line + rs.getString(i) + ",";
					Database.addElement(new WiFi(rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getString(8+2*i),rs.getInt(9+2*i)));
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Prints Vector's WiFis to United CSV File
	public void toCSV(String OutputPath)
	{
		try
		{
			File UnitedFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(UnitedFile);
			String headers = "MAC,SSID,AuthMode,FirstSeen,Channel,RSSI,CurrentLatitude,CurrentLongitude,AltitudeMeters,AccuracyMeters,Type,ID";
			outs.println(headers);
			for(int i=0;i<Database.size();i++)
				outs.println(Database.elementAt(i));
			outs.close();
		}
		catch(IOException ex)
		{
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
		System.out.println("United File Was Created At " + OutputPath);
	}

	public void DeleteAllWiFis()
	{
		Database.removeAllElements();
	}

	public int NumberofMacs()
	{
		return Database.size();
	}

	public boolean CheckFileModification()
	{
		boolean modified = false;
		File file = new File(this.getDatabaseFile());
		if(file.lastModified()!=this.getFileLastModified())
			modified = true;
		return modified;
	}

	public boolean CheckFolderModification()
	{
		boolean modified = false;
		File FolderFile = new File(this.getDatabaseFolder());
		File ListofFiles[] = FolderFile.listFiles();
		String names[] = FolderFile.list();
		if(ListofFiles.length!=this.getNumberofFiles())
			modified = true;
		if(!modified)
			for(int i=0;i<ListofFiles.length;i++)
				if(names[i].startsWith("WigleWifi") && names[i].endsWith(".csv"))
					if(ListofFiles[i].lastModified()!=this.getFilesLastModified()[i])
						modified = true;
		return modified;
	}

	public boolean CheckSQLModification()
	{
		boolean modified = false;
		try 
		{
			if(SQLlastmodified!=null)
			{
				Connection con = DriverManager.getConnection(SQLurl, SQLusername, SQLpassword);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = '" + SQLdatabase + "' AND TABLE_NAME = '" + SQLtable + "'");
				rs.next();
				if(!rs.getString(1).equals(SQLlastmodified))
					modified = true;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modified;
	}

	public Vector<WiFi> getDatabase() {
		return Database;
	}
	public void setDatabase(Vector<WiFi> Database) {
		this.Database = Database;
	}
	public String getDatabaseFile() {
		return DatabaseFile;
	}
	public void setDatabaseFile(String databaseFile) {
		DatabaseFile = databaseFile;
	}
	public String getDatabaseFolder() {
		return DatabaseFolder;
	}
	public void setDatabaseFolder(String databaseFolder) {
		DatabaseFolder = databaseFolder;
	}
	public int getNumberofFiles() {
		return NumberofFiles;
	}
	public void setNumberofFiles(int numberofFiles) {
		NumberofFiles = numberofFiles;
	}
	public long getFileLastModified() {
		return FileLastModified;
	}
	public void setFileLastModified(long fileLastModified) {
		FileLastModified = fileLastModified;
	}
	public long[] getFilesLastModified() {
		return FilesLastModified;
	}
	public void setFilesLastModified(long[] filesLastModified) {
		FilesLastModified = filesLastModified;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getSQLip() {
		return SQLip;
	}
	public void setSQLip(String sQLip) {
		SQLip = sQLip;
	}
	public String getSQLport() {
		return SQLport;
	}
	public void setSQLport(String sQLport) {
		SQLport = sQLport;
	}
	public String getSQLdatabase() {
		return SQLdatabase;
	}
	public void setSQLdatabase(String sQLdatabase) {
		SQLdatabase = sQLdatabase;
	}
	public String getSQLtable() {
		return SQLtable;
	}
	public void setSQLtable(String sQLtable) {
		SQLtable = sQLtable;
	}
	public String getSQLusername() {
		return SQLusername;
	}
	public void setSQLusername(String sQLusername) {
		SQLusername = sQLusername;
	}
	public String getSQLpassword() {
		return SQLpassword;
	}
	public void setSQLpassword(String sQLpassword) {
		SQLpassword = sQLpassword;
	}

}
