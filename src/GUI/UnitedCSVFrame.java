package GUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import KML.ArrangeWiFis;
import KML.WiFis;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * Frame used for creating a United CSV file from the database.
 */
public class UnitedCSVFrame {

	private JFrame frame;
	private JTextField OutputPathtextField;
	private WiFis Database;

	/**
	 * Create the application.
	 */
	public UnitedCSVFrame(WiFis Database) {
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
		
		JLabel lblUnitedCsv = new JLabel("United CSV");
		lblUnitedCsv.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnitedCsv.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnitedCsv.setBounds(174, 11, 90, 20);
		frame.getContentPane().add(lblUnitedCsv);
		
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
					Database.toCSV(OutputPath);
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Valid Path");
				}
				JOptionPane.showMessageDialog(null, "United File Was Successfully Created At " + OutputPath);
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
