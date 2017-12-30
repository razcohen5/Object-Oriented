package KML;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;
/*
 * The class contains a vector of WiFi objects that is used as a database,
 * the class has different methods to perform on this database.
 */
public class WiFis {

	private Vector<WiFi> samples;
	private String DatabaseFile;
	private String DatabaseFolder;
	private int NumberofFiles;
	private long FileLastModified;
	private long FilesLastModified[];
	private volatile String Source;

	public WiFis()
	{
		samples = new Vector<WiFi>(); 
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
						samples.addElement(new WiFi(CurrentLine,id));
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
					samples.addElement(new WiFi(CurrentLine,id));
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

	//Prints Vector's WiFis to United CSV File
	public void toCSV(String OutputPath)
	{
		try
		{
			File UnitedFile = new File(OutputPath);
			PrintWriter outs = new PrintWriter(UnitedFile);
			String headers = "MAC,SSID,AuthMode,FirstSeen,Channel,RSSI,CurrentLatitude,CurrentLongitude,AltitudeMeters,AccuracyMeters,Type,ID";
			outs.println(headers);
			for(int i=0;i<samples.size();i++)
				outs.println(samples.elementAt(i));
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
		samples.removeAllElements();
	}

	public int NumberofMacs()
	{
		return samples.size();
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

	public Vector<WiFi> getSamples() {
		return samples;
	}
	public void setSamples(Vector<WiFi> samples) {
		this.samples = samples;
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

}
