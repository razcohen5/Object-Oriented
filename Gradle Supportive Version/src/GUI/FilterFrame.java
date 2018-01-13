package GUI;
/*
 * Frame used for filtering the database by time, location and id.
 * filters can be save in a CSV file with or without applying the filter on the actual database.
 */
import java.awt.EventQueue;

import javax.swing.JFrame;

import KML.WiFi;
import KML.WiFis;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Filter.*;
public class FilterFrame {

	private JFrame frame;
	private WiFis Database;
	private WiFis FilteredDatabase;
	private JTextField Filter1textField;
	private JTextField Filter2textField;
	private JTextField OutputPathtextField;

	/**
	 * Create the application.
	 */
	public FilterFrame(WiFis Database) {
		initialize();
		this.Database = Database;
		FilteredDatabase = new WiFis();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 1050, 650);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFilter = new JLabel("Filter");
		lblFilter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setBounds(480, 11, 65, 22);
		frame.getContentPane().add(lblFilter);

		JComboBox Filter1comboBox = new JComboBox();
		Filter1comboBox.setModel(new DefaultComboBoxModel(new String[] {"TimeFilter", "Location Filter", "ID Filter"}));
		Filter1comboBox.setBounds(285, 312, 112, 32);
		frame.getContentPane().add(Filter1comboBox);

		Filter1textField = new JTextField();
		Filter1textField.setBounds(88, 312, 199, 32);
		frame.getContentPane().add(Filter1textField);
		Filter1textField.setColumns(10);

		Filter2textField = new JTextField();
		Filter2textField.setColumns(10);
		Filter2textField.setBounds(709, 312, 199, 32);
		frame.getContentPane().add(Filter2textField);

		JComboBox Filter2comboBox = new JComboBox();
		Filter2comboBox.setModel(new DefaultComboBoxModel(new String[] {"Time Filter", "Location Filter", "ID Filter"}));
		Filter2comboBox.setBounds(907, 312, 107, 32);
		frame.getContentPane().add(Filter2comboBox);

		JTextArea textAreaForm = new JTextArea();
		textAreaForm.setFont(new Font("Courier New", Font.PLAIN, 15));
		textAreaForm.setText("Write in both filters if you want to use 2 filters, \r\nif u want to use only 1 filter write in Filter1 and keep Filter2 empty.\r\n\r\nTime Filter Form: StartTime,EndTime\r\n\r\nLocation Filter Form: Lat1,Lon1,Alt1,Lat2,Lon2,Alt2\r\n\r\nID Filter Form: ID");
		textAreaForm.setBounds(181, 362, 656, 148);
		frame.getContentPane().add(textAreaForm);

		JTextArea FiltertextArea = new JTextArea();
		FiltertextArea.setEditable(false);
		FiltertextArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		FiltertextArea.setBounds(108, 72, 891, 39);
		frame.getContentPane().add(FiltertextArea);

		JLabel lblFilterDisplay = new JLabel("Filter Display");
		lblFilterDisplay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFilterDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterDisplay.setBounds(10, 72, 107, 39);
		frame.getContentPane().add(lblFilterDisplay);

		JLabel lblFilter1 = new JLabel("Filter 1");
		lblFilter1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFilter1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter1.setBounds(140, 266, 82, 35);
		frame.getContentPane().add(lblFilter1);

		JLabel lblFilter2 = new JLabel("Filter 2");
		lblFilter2.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFilter2.setBounds(815, 266, 82, 35);
		frame.getContentPane().add(lblFilter2);

		JComboBox OperationcomboBox = new JComboBox();
		OperationcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		OperationcomboBox.setModel(new DefaultComboBoxModel(new String[] {"AND", "OR"}));
		OperationcomboBox.setBounds(486, 312, 65, 32);
		frame.getContentPane().add(OperationcomboBox);

		JLabel lblOperation = new JLabel("Operation");
		lblOperation.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOperation.setBounds(479, 266, 82, 35);
		frame.getContentPane().add(lblOperation);

		JRadioButton rdbtnNot2 = new JRadioButton("NOT");
		rdbtnNot2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnNot2.setBounds(655, 311, 53, 32);
		frame.getContentPane().add(rdbtnNot2);

		JRadioButton rdbtnNot1 = new JRadioButton("NOT");
		rdbtnNot1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnNot1.setBounds(35, 312, 53, 32);
		frame.getContentPane().add(rdbtnNot1);

		JButton btnApplyFilter = new JButton("Apply Filter");
		btnApplyFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database.DeleteAllWiFis();
				for(int i=0;i<FilteredDatabase.getDatabase().size();i++)
					Database.getDatabase().add(new WiFi(FilteredDatabase.getDatabase().elementAt(i)));
				JOptionPane.showMessageDialog(null, "Database Was Successfully Filtered");
			}
		});
		btnApplyFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnApplyFilter.setBounds(10, 122, 157, 39);
		frame.getContentPane().add(btnApplyFilter);

		JButton btnSaveFilter = new JButton("Save Filter");
		btnSaveFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					FilteredDatabase.toCSV(OutputPathtextField.getText());
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Valid Path");
				}
				JOptionPane.showMessageDialog(null, "Filter Was Successfully Saved At" + OutputPathtextField.getText());
			}
		});
		btnSaveFilter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveFilter.setBounds(10, 163, 157, 37);
		frame.getContentPane().add(btnSaveFilter);

		OutputPathtextField = new JTextField();
		OutputPathtextField.setBounds(180, 163, 260, 37);
		frame.getContentPane().add(OutputPathtextField);
		OutputPathtextField.setColumns(10);

		JLabel label = new JLabel("Enter Output Path");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(166, 139, 133, 32);
		frame.getContentPane().add(label);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					FilteredDatabase.DeleteAllWiFis();
					String S1 = Filter1textField.getText();
					String SplitS1[] = S1.split(",");
					String S2 = Filter2textField.getText();
					String SplitS2[] = S2.split(",");
					if(!S1.isEmpty()&&!S2.isEmpty())
					{
						Filter filter1;
						Filter filter2;
						Filter operation;
						Filter not1;
						Filter not2;


						if(Filter1comboBox.getSelectedIndex()==0)
							filter1=new TimeFilter(SplitS1[0],SplitS1[1]);
						else if(Filter1comboBox.getSelectedIndex()==1)
							filter1=new LocationFilter(SplitS1[0],SplitS1[1],SplitS1[2],SplitS1[3],SplitS1[4],SplitS1[5]);
						else
							filter1=new IDFilter(SplitS1[0]);

						if(Filter2comboBox.getSelectedIndex()==0)
							filter2=new TimeFilter(SplitS2[0],SplitS2[1]);
						else if(Filter2comboBox.getSelectedIndex()==1)
							filter2=new LocationFilter(SplitS2[0],SplitS2[1],SplitS2[2],SplitS2[3],SplitS2[4],SplitS2[5]);
						else
							filter2=new IDFilter(SplitS2[0]);


						if(rdbtnNot1.isSelected())
						{
							not1 = new NotFilter(filter1);
							filter1=not1;
						}
						if(rdbtnNot2.isSelected())
						{
							not2 = new NotFilter(filter2);
							filter2=not2;
						}

						if(OperationcomboBox.getSelectedIndex()==0)
							operation = new AndFilter(filter1,filter2);
						else
							operation = new OrFilter(filter1,filter2);

						String filterdisplay = operation.toString();
						FiltertextArea.setText(filterdisplay);
						UseFilter(operation);
					}
					else
					{
						Filter filter1;
						Filter not1;
						if(Filter1comboBox.getSelectedIndex()==0)
							filter1=new TimeFilter(SplitS1[0],SplitS1[1]);
						else if(Filter1comboBox.getSelectedIndex()==1)
							filter1=new LocationFilter(SplitS1[0],SplitS1[1],SplitS1[2],SplitS1[3],SplitS1[4],SplitS1[5]);
						else
							filter1=new IDFilter(SplitS1[0]);
						
						if(rdbtnNot1.isSelected())
						{
							not1 = new NotFilter(filter1);
							filter1=not1;
						}
						
						String filterdisplay = filter1.toString();
						FiltertextArea.setText(filterdisplay);
						UseFilter(filter1);
					}
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Valid Filter");
				}

			}
		});
		btnDone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDone.setBounds(665, 217, 112, 32);
		frame.getContentPane().add(btnDone);

		JLabel lblChooseFilter = new JLabel("Choose Your Filter And Press The Done Button When Done");
		lblChooseFilter.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChooseFilter.setBounds(216, 219, 439, 32);
		frame.getContentPane().add(lblChooseFilter);
		frame.setLocationRelativeTo(null);

	}

	private void UseFilter(Filter f)
	{
		for(int i=0;i<Database.getDatabase().size();i++)
			if(f.test(Database.getDatabase().elementAt(i)))
				FilteredDatabase.getDatabase().add(new WiFi(Database.getDatabase().elementAt(i)));
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
