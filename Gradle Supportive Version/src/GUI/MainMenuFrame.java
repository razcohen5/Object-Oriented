package GUI;
/*
 * The main frame of the GUI system, responsible for reading the Wigle files,
 * either from folder or from file into the database, also provides access to all other frames.
 */
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import KML.ArrangeWiFis;
import KML.WiFis;

import java.awt.Font;
import javax.swing.JTextField;

public class MainMenuFrame {

	private JFrame frame;
	private WiFis Database;
	private JTextField FolderPathtextField;
	private JTextField FilePathtextField;
	private JTextField SQLIPtextField;
	private JTextField SQLPorttextField;
	private JTextField SQLDatabasetextField;
	private JTextField SQLUsernametextField;
	private JTextField SQLTabletextField;
	private JTextField SQLPasswordtextField;

	/**
	 * Create the application.
	 */
	public MainMenuFrame() {
		initialize();
		Database = new WiFis();
		Thread CheckModification = new Thread(new Runnable() {
			public void run() {
				while(true)
				{
					try 
					{
						if(Database.getSource()!=null)
						{
							Thread.sleep(3000);
							if(Database.getSource().equals("folder"))
							{
								if(Database.CheckFolderModification())
								{
									JOptionPane.showMessageDialog(null, "Database Folder Has Been Modified And Therefore Reread");
									Database.ReadFolder(Database.getDatabaseFolder());
								}
							}
							else if(Database.getSource().equals("file"))
							{
								if(Database.CheckFileModification())
								{
									JOptionPane.showMessageDialog(null, "Database File Has Been Modified And Therefore Reread");
									Database.ReadFile(Database.getDatabaseFile());
								}
							}
							else if(Database.getSource().equals("sql"))
							{
								if(Database.CheckSQLModification())
								{
									JOptionPane.showMessageDialog(null, "SQL Database Has Been Modified And Therefore Reread");
									Database.ReadArrangedSQL(Database.getSQLip(),Database.getSQLport(),Database.getSQLdatabase(),Database.getSQLtable(),Database.getSQLusername(),Database.getSQLpassword());
								}
							}
								
						}
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}       
			}
		});
		CheckModification.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 1050, 650);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		//frame.setPreferredSize(new Dimension(500, 350));
		//frame.pack();

		JButton btnReadFolder = new JButton("Read Folder");
		btnReadFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							String FolderPath = FolderPathtextField.getText();
							Database.ReadFolder(FolderPath);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Folder Was Successfully Added to Database");
					}
				});
			}
		});
		btnReadFolder.setBounds(10, 168, 150, 36);
		getFrame().getContentPane().add(btnReadFolder);

		JLabel lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 23));
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenu.setBounds(390, 11, 184, 50);
		getFrame().getContentPane().add(lblMainMenu);

		JButton btnDeleteDatabase = new JButton("Delete Database");
		btnDeleteDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.DeleteAllWiFis();
				JOptionPane.showMessageDialog(null, "Database Was Successfully Deleted");
			}
		});
		btnDeleteDatabase.setBounds(164, 537, 150, 36);
		frame.getContentPane().add(btnDeleteDatabase);

		JButton btnDatabaseInfo = new JButton("Database Info");
		btnDatabaseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Database Contains " + Database.NumberofMacs() + " WiFis");
			}
		});
		btnDatabaseInfo.setBounds(164, 470, 150, 36);
		frame.getContentPane().add(btnDatabaseInfo);

		JButton btnUnitedCSV = new JButton("United CSV");
		btnUnitedCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							UnitedCSVFrame window = new UnitedCSVFrame(Database);
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnUnitedCSV.setBounds(595, 234, 150, 36);
		frame.getContentPane().add(btnUnitedCSV);

		JButton btnKML = new JButton("KML");
		btnKML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							KMLFrame window = new KMLFrame(Database);
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnKML.setBounds(595, 374, 150, 36);
		frame.getContentPane().add(btnKML);

		JButton btnAlgo1 = new JButton("Algo1");
		btnAlgo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Algo1Frame window = new Algo1Frame(Database);
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnAlgo1.setBounds(840, 168, 150, 36);
		frame.getContentPane().add(btnAlgo1);

		JButton btnAlgo2 = new JButton("Algo2");
		btnAlgo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Algo2Frame window = new Algo2Frame(Database);
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnAlgo2.setBounds(840, 234, 150, 36);
		frame.getContentPane().add(btnAlgo2);

		JButton btnArrangedCSV = new JButton("Arranged CSV");
		btnArrangedCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ArrangedCSVFrame window = new ArrangedCSVFrame(Database);
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnArrangedCSV.setBounds(595, 302, 150, 36);
		frame.getContentPane().add(btnArrangedCSV);

		JLabel lblAlgorithms = new JLabel("Algorithms");
		lblAlgorithms.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAlgorithms.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlgorithms.setBounds(834, 75, 156, 36);
		frame.getContentPane().add(lblAlgorithms);

		JLabel lblActions = new JLabel("Actions");
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);
		lblActions.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblActions.setBounds(589, 75, 156, 36);
		frame.getContentPane().add(lblActions);

		JLabel lblDatabase = new JLabel("Database");
		lblDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatabase.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDatabase.setBounds(158, 75, 156, 36);
		frame.getContentPane().add(lblDatabase);

		FolderPathtextField = new JTextField();
		FolderPathtextField.setBounds(198, 168, 286, 36);
		frame.getContentPane().add(FolderPathtextField);
		FolderPathtextField.setColumns(10);

		JButton btnReadFile = new JButton("Read File");
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String FilePath = FilePathtextField.getText();
				Database.ReadFile(FilePath);
				JOptionPane.showMessageDialog(null, "File Was Successfully Added to Database");
			}
		});
		btnReadFile.setBounds(10, 234, 150, 36);
		frame.getContentPane().add(btnReadFile);

		FilePathtextField = new JTextField();
		FilePathtextField.setColumns(10);
		FilePathtextField.setBounds(198, 235, 286, 36);
		frame.getContentPane().add(FilePathtextField);

		JLabel lblEnterFolderPath = new JLabel("Enter Folder Path:");
		lblEnterFolderPath.setBounds(198, 138, 150, 36);
		frame.getContentPane().add(lblEnterFolderPath);

		JLabel lblEnterFilePath = new JLabel("Enter File Path:");
		lblEnterFilePath.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterFilePath.setBounds(198, 207, 150, 36);
		frame.getContentPane().add(lblEnterFilePath);
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FilterFrame window = new FilterFrame(Database);
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnFilter.setBounds(595, 168, 150, 36);
		frame.getContentPane().add(btnFilter);
		
		JButton btnReadSQL = new JButton("Read SQL");
		btnReadSQL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database.ReadArrangedSQL(SQLIPtextField.getText(),SQLPorttextField.getText(),SQLDatabasetextField.getText(),SQLTabletextField.getText(),SQLUsernametextField.getText(),SQLPasswordtextField.getText());
				JOptionPane.showMessageDialog(null, "SQL Database Was Successfully Added to Database");
			}
		});
		btnReadSQL.setBounds(10, 354, 150, 36);
		frame.getContentPane().add(btnReadSQL);
		
		SQLIPtextField = new JTextField();
		SQLIPtextField.setBounds(198, 302, 116, 28);
		frame.getContentPane().add(SQLIPtextField);
		SQLIPtextField.setColumns(10);
		
		SQLPorttextField = new JTextField();
		SQLPorttextField.setColumns(10);
		SQLPorttextField.setBounds(390, 302, 116, 28);
		frame.getContentPane().add(SQLPorttextField);
		
		SQLDatabasetextField = new JTextField();
		SQLDatabasetextField.setColumns(10);
		SQLDatabasetextField.setBounds(198, 358, 116, 28);
		frame.getContentPane().add(SQLDatabasetextField);
		
		SQLUsernametextField = new JTextField();
		SQLUsernametextField.setColumns(10);
		SQLUsernametextField.setBounds(390, 358, 116, 28);
		frame.getContentPane().add(SQLUsernametextField);
		
		SQLTabletextField = new JTextField();
		SQLTabletextField.setColumns(10);
		SQLTabletextField.setBounds(198, 414, 116, 28);
		frame.getContentPane().add(SQLTabletextField);
		
		SQLPasswordtextField = new JTextField();
		SQLPasswordtextField.setColumns(10);
		SQLPasswordtextField.setBounds(390, 414, 116, 28);
		frame.getContentPane().add(SQLPasswordtextField);
		
		JLabel lblSQLIP = new JLabel("Enter SQL Server IP:");
		lblSQLIP.setHorizontalAlignment(SwingConstants.LEFT);
		lblSQLIP.setBounds(198, 277, 184, 36);
		frame.getContentPane().add(lblSQLIP);
		
		JLabel lblSQLPort = new JLabel("Enter SQL Server Port:");
		lblSQLPort.setHorizontalAlignment(SwingConstants.LEFT);
		lblSQLPort.setBounds(390, 277, 182, 36);
		frame.getContentPane().add(lblSQLPort);
		
		JLabel lblSQLUsername = new JLabel("Enter SQL Server Username:");
		lblSQLUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblSQLUsername.setBounds(390, 332, 182, 36);
		frame.getContentPane().add(lblSQLUsername);
		
		JLabel lblSQLDatabase = new JLabel("Enter SQL Server Database:");
		lblSQLDatabase.setHorizontalAlignment(SwingConstants.LEFT);
		lblSQLDatabase.setBounds(198, 332, 201, 36);
		frame.getContentPane().add(lblSQLDatabase);
		
		JLabel lblSQLTable = new JLabel("Enter SQL Server Table:");
		lblSQLTable.setHorizontalAlignment(SwingConstants.LEFT);
		lblSQLTable.setBounds(198, 385, 201, 36);
		frame.getContentPane().add(lblSQLTable);
		
		JLabel lblSQLPassword = new JLabel("Enter SQL Server Password:");
		lblSQLPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblSQLPassword.setBounds(390, 385, 182, 36);
		frame.getContentPane().add(lblSQLPassword);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
