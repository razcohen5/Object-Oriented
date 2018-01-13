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
/*
 * Frame used for creating a KML file from the database,
 * the KML file can be presented in Google Earth.
 */
public class KMLFrame {

	private JFrame frame;
	private JTextField OutputPathtextField;
	private WiFis Database;

	/**
	 * Create the application.
	 */
	public KMLFrame(WiFis Database) {
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
		
		JLabel lblKML = new JLabel("KML");
		lblKML.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKML.setHorizontalAlignment(SwingConstants.CENTER);
		lblKML.setBounds(174, 11, 90, 20);
		frame.getContentPane().add(lblKML);
		
		OutputPathtextField = new JTextField();
		OutputPathtextField.setBounds(196, 64, 199, 36);
		frame.getContentPane().add(OutputPathtextField);
		OutputPathtextField.setColumns(10);

		JLabel lblEnterOutputPath = new JLabel("Enter Output Path");
		lblEnterOutputPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterOutputPath.setBounds(10, 64, 150, 36);
		frame.getContentPane().add(lblEnterOutputPath);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String OutputPath = OutputPathtextField.getText();
				try
				{	
					ArrangeWiFis aw = new ArrangeWiFis(Database);
					aw.ArrangeSamples();
					aw.toKML(OutputPath);
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Valid Path");
				}
				JOptionPane.showMessageDialog(null, "KML Was Successfully Created At " + OutputPath);
				frame.dispose();
			}
		});
		btnSubmit.setBounds(245, 111, 107, 29);
		frame.getContentPane().add(btnSubmit);
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
