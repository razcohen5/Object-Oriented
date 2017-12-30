package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import KML.ArrangeWiFis;
import KML.WiFis;
import Location.UserLocation;
import Location.WiFiLocationCSV;
/*
 * Frame used for performing the second algorithm using the database,
 * given 3 mac addresses or a scan line from the arranged database.
 */
public class Algo2Frame {

	private JFrame frame;
	JTextField Mac1textField;
	JTextField Mac2textField;
	JTextField Mac3textField;
	private WiFis Database;
	private JTextField LineFromScantextField;

	/**
	 * Create the application.
	 */
	public Algo2Frame(WiFis Database) {
		initialize();
		this.Database = Database;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 450, 300);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblAlgo2 = new JLabel("Algo2");
		lblAlgo2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAlgo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlgo2.setBounds(174, 11, 90, 20);
		frame.getContentPane().add(lblAlgo2);
		
		Mac1textField = new JTextField();
		Mac1textField.setBounds(198, 46, 166, 20);
		frame.getContentPane().add(Mac1textField);
		Mac1textField.setColumns(10);
		
		Mac2textField = new JTextField();
		Mac2textField.setBounds(198, 92, 166, 20);
		frame.getContentPane().add(Mac2textField);
		Mac2textField.setColumns(10);
		
		Mac3textField = new JTextField();
		Mac3textField.setBounds(198, 69, 166, 20);
		frame.getContentPane().add(Mac3textField);
		Mac3textField.setColumns(10);

		JLabel lblEnter3Macs = new JLabel("Enter 3 Macs");
		lblEnter3Macs.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnter3Macs.setBounds(10, 42, 150, 36);
		frame.getContentPane().add(lblEnter3Macs);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Mac1 = Mac1textField.getText();
				String Mac2 = Mac2textField.getText();
				String Mac3 = Mac3textField.getText();
				try
				{	
					ArrangeWiFis aw = new ArrangeWiFis(Database);
					aw.ArrangeSamples();
					UserLocation ul = new UserLocation(Mac1,Mac2,Mac3,aw);
					double UserLocation[] = ul.FindLocation();
					JOptionPane.showMessageDialog(null, "User Location is At:\n"
							+ "Lat = " + UserLocation[0] + "\nLon = " + UserLocation[1] + "\nAlt = " + UserLocation[2]);
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Valid Path");
				}
			}
		});
		btnSubmit.setBounds(228, 123, 107, 29);
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblEnterLineFromScan = new JLabel("Enter Line From Scan");
		lblEnterLineFromScan.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterLineFromScan.setBounds(10, 168, 150, 36);
		frame.getContentPane().add(lblEnterLineFromScan);
		
		LineFromScantextField = new JTextField();
		LineFromScantextField.setColumns(10);
		LineFromScantextField.setBounds(198, 172, 166, 28);
		frame.getContentPane().add(LineFromScantextField);
		
		JButton btnSubmit2 = new JButton("Submit");
		btnSubmit2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String LineFromScan = LineFromScantextField.getText();
				try
				{	
					String SplitLine[] = LineFromScan.split(",");
					if(SplitLine.length>=14)
					{
					ArrangeWiFis aw = new ArrangeWiFis(Database);
					aw.ArrangeSamples();
					UserLocation ul = new UserLocation(SplitLine[7],SplitLine[11],SplitLine[15],aw);
					double UserLocation[] = ul.FindLocation();
					JOptionPane.showMessageDialog(null, "User Location is At:\n"
							+ "Lat = " + UserLocation[0] + "\nLon = " + UserLocation[1] + "\nAlt = " + UserLocation[2]);
					}
				}
				catch(Exception e1)
				{
					JLabel label = new JLabel("Not Enough Macs At Line From Scan");
					label.setFont(new Font("Arial", Font.BOLD, 18));
					JOptionPane.showMessageDialog(null,label,"ERROR",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSubmit2.setBounds(228, 211, 107, 29);
		frame.getContentPane().add(btnSubmit2);
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
