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
		getFrame().setBounds(100, 100, 1000, 600);
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
		btnDeleteDatabase.setBounds(164, 374, 150, 36);
		frame.getContentPane().add(btnDeleteDatabase);

		JButton btnDatabaseInfo = new JButton("Database Info");
		btnDatabaseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Database Contains " + Database.NumberofMacs() + " WiFis");
			}
		});
		btnDatabaseInfo.setBounds(164, 302, 150, 36);
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
		btnUnitedCSV.setBounds(498, 234, 150, 36);
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
		btnKML.setBounds(498, 374, 150, 36);
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
		btnAlgo1.setBounds(743, 168, 150, 36);
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
		btnAlgo2.setBounds(743, 234, 150, 36);
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
		btnArrangedCSV.setBounds(498, 302, 150, 36);
		frame.getContentPane().add(btnArrangedCSV);

		JLabel lblAlgorithms = new JLabel("Algorithms");
		lblAlgorithms.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAlgorithms.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlgorithms.setBounds(737, 75, 156, 36);
		frame.getContentPane().add(lblAlgorithms);

		JLabel lblActions = new JLabel("Actions");
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);
		lblActions.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblActions.setBounds(492, 75, 156, 36);
		frame.getContentPane().add(lblActions);

		JLabel lblDatabase = new JLabel("Database");
		lblDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatabase.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDatabase.setBounds(158, 75, 156, 36);
		frame.getContentPane().add(lblDatabase);

		FolderPathtextField = new JTextField();
		FolderPathtextField.setBounds(198, 168, 245, 36);
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
		FilePathtextField.setBounds(198, 235, 245, 36);
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
		btnFilter.setBounds(498, 168, 150, 36);
		frame.getContentPane().add(btnFilter);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
