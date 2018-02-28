package Tables;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import java.awt.Graphics;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.jdbc.CallableStatement;
import java.sql.*;  

import Login.LoginPage;
import Profile.ProfilePage;
import geneticAlgorithm.TimetableGA;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;



public class Update extends JFrame{
	

	public JFrame frmUpdate;
	private String userName = null;
	private String name;
	private String password = null;
	private int professorId;
	private final Action action = new SwingAction();

	public Update(String userName, String password) {
		this.userName = userName;
		this.password = password;
		Connection connection;
        PreparedStatement ps;
        try {
			Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
            ps = connection.prepareStatement("SELECT * FROM `professor` WHERE `professorUserName` = ?");
            ps.setString(1, userName);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
            name = result.getString("professorName");
            professorId = result.getInt("professorId");

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

		
		initialize();
	}

	

	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update window = new Update();
					window.frmUpdate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Update() {
		initialize();
	}
	
	
	public boolean isSelected(JCheckBox day, boolean i) {
		
		Connection connection;
		PreparedStatement ps;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			ps = connection.prepareStatement("SELECT `day` FROM `avaiable` WHERE `user_name` = ?");
			ps.setString(1, userName);
			i = ps.execute();
			
			ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
    		day.setSelected(i); 
		
		
		return true;
	} 
    
    
	private void initialize() {
		frmUpdate = new JFrame();
		frmUpdate.setTitle("UPDATE TABLE");
		frmUpdate.setBounds(100, 100, 750, 375);
		frmUpdate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUpdate.setLocationRelativeTo(null);
		JLabel lblImageLabel = new JLabel("");
		lblImageLabel.setBounds(25, 25, 120, 120);
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image scaledImage = img.getScaledInstance(120, 120, Image.SCALE_FAST);
		frmUpdate.getContentPane().setLayout(null);
		lblImageLabel.setIcon(new ImageIcon(scaledImage));
		frmUpdate.getContentPane().add(lblImageLabel);
		
		JButton btnUpdate = new JButton("Profile Page");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfilePage info = new ProfilePage(userName, password);             
            	info.frmProfile.setVisible(true);
                frmUpdate.setVisible(false);
                dispose();
			}
		});
		btnUpdate.setBounds(25, 170, 120, 25);
		btnUpdate.setBackground(UIManager.getColor("Button.disabledShadow"));
		//btnUpdate.setForeground(SystemColor.desktop);
		frmUpdate.getContentPane().add(btnUpdate);
		
		JLabel lblName = new JLabel("Name : " + name);
		lblName.setForeground(Color.BLACK);
		lblName.setBounds(200, 25, 250, 20);
		frmUpdate.getContentPane().add(lblName);
		
		JButton btnGeneral = new JButton("General Table");
		btnGeneral.setBounds(25, 240, 120, 25);
		btnGeneral.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnGeneral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CalendarProgram.generalTable();
			}
		});
		frmUpdate.getContentPane().add(btnGeneral);
		
		JButton btnPersonal = new JButton("Personal Table");
		btnPersonal.setBounds(25, 205, 120, 25);
		btnPersonal.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CalendarProgram.personalTable(professorId);
			}
		});
		frmUpdate.getContentPane().add(btnPersonal);
				
		
		JButton btnSave = new JButton("Save and go to profile page");
		btnSave.setHorizontalAlignment(SwingConstants.LEFT);
		btnSave.setBounds(490, 300, 210, 25);
		frmUpdate.getContentPane().add(btnSave);
		
		JPanel Hours = new JPanel();
		Hours.setBounds(200, 75, 100, 200);
		frmUpdate.getContentPane().add(Hours);
		Hours.setLayout(null);
		
		JTextPane txtpnFdbdb = new JTextPane();
		txtpnFdbdb.setBounds(0, 0, 100, 25);
		txtpnFdbdb.setBackground(SystemColor.controlHighlight);
		Hours.add(txtpnFdbdb);
		
		JTextPane text9_10 = new JTextPane();
		text9_10.setBounds(0, 25, 100, 25);
		text9_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
		text9_10.setEditable(false);
		text9_10.setText("09.00 - 11.00");
		text9_10.setBackground(SystemColor.inactiveCaptionBorder);
		Hours.add(text9_10);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(0, 50, 100, 25);
		textPane_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_2.setText("13.00 - 15.00");
		textPane_2.setBackground(SystemColor.control);
		Hours.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(0, 75, 100, 25);
		textPane_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_3.setText("15.00  17.00 ");
		textPane_3.setBackground(SystemColor.inactiveCaptionBorder);
		Hours.add(textPane_3);
				
		JPanel Monday = new JPanel();
		Monday.setBounds(300, 75, 80, 200);
		frmUpdate.getContentPane().add(Monday);
		Monday.setLayout(null);		
		
		JTextPane textMonday = new JTextPane();
		textMonday.setBounds(0, 0, 80, 25);
		textMonday.setText("Monday");
		textMonday.setBackground(SystemColor.controlHighlight);
		Monday.add(textMonday);
        
		HashMap<String, JCheckBox> checkBoxes = new  HashMap<String, JCheckBox>();
		
		Connection connection;
		PreparedStatement ps;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			ps = connection.prepareStatement("SELECT `timeslot` FROM `timeslot` WHERE `timeslotId` < 4");
			ResultSet result = ps.executeQuery();
			int i = 0;
			while(result.next()) {
				JCheckBox tmp = new JCheckBox("");
				tmp.setBounds(24, 30+(i*25), 21, 21);
				tmp.setVerticalAlignment(SwingConstants.BOTTOM);
				checkBoxes.put(result.getString("timeslot"), tmp);
				Monday.add(checkBoxes.get(result.getString("timeslot")));
				i++;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel Tuesday = new JPanel();
		Tuesday.setLayout(null);
		Tuesday.setBounds(380, 75, 80, 200);
		frmUpdate.getContentPane().add(Tuesday);
				
		JTextPane textTuesday = new JTextPane();
		textTuesday.setText("Tuesday");
		textTuesday.setBackground(SystemColor.controlHighlight);
		textTuesday.setBounds(0, 0, 80, 25);
		Tuesday.add(textTuesday);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			ps = connection.prepareStatement("SELECT `timeslot` FROM `timeslot` WHERE `timeslotId` < 7 AND `timeslotId` > 3");
			ResultSet result = ps.executeQuery();
			int i = 0;
			while(result.next()) {
				JCheckBox tmp = new JCheckBox("");
				if(i == 0)
					tmp.setAction(action);
				tmp.setBounds(24, 30+(i*25), 21, 21);
				tmp.setVerticalAlignment(SwingConstants.BOTTOM);
				checkBoxes.put(result.getString("timeslot"), tmp);
				Tuesday.add(checkBoxes.get(result.getString("timeslot")));
				i++;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JPanel Wednesday = new JPanel();
		Wednesday.setLayout(null);
		Wednesday.setBounds(460, 75, 80, 200);
		frmUpdate.getContentPane().add(Wednesday);
		
		JTextPane textWednesday = new JTextPane();
		textWednesday.setText("Wednesday");
		textWednesday.setBackground(SystemColor.controlHighlight);
		textWednesday.setBounds(0, 0, 80, 25);
		Wednesday.add(textWednesday);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			ps = connection.prepareStatement("SELECT `timeslot` FROM `timeslot` WHERE `timeslotId` < 10 AND `timeslotId` > 6");
			ResultSet result = ps.executeQuery();
			int i = 0;
			while(result.next()) {
				JCheckBox tmp = new JCheckBox("");
				tmp.setBounds(24, 30+(i*25), 21, 21);
				tmp.setVerticalAlignment(SwingConstants.BOTTOM);
				checkBoxes.put(result.getString("timeslot"), tmp);
				Wednesday.add(checkBoxes.get(result.getString("timeslot")));
				i++;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel Thursday = new JPanel();
		Thursday.setLayout(null);
		Thursday.setBounds(540, 75, 80, 200);
		frmUpdate.getContentPane().add(Thursday);
		
		JTextPane textThursday = new JTextPane();
		textThursday.setText("Thursday");
		textThursday.setBackground(SystemColor.controlHighlight);
		textThursday.setBounds(0, 0, 80, 25);
		Thursday.add(textThursday);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			ps = connection.prepareStatement("SELECT `timeslot` FROM `timeslot` WHERE `timeslotId` < 13 AND `timeslotId` > 9");
			ResultSet result = ps.executeQuery();
			int i = 0;
			while(result.next()) {
				JCheckBox tmp = new JCheckBox("");
				tmp.setBounds(24, 30+(i*25), 21, 21);
				tmp.setVerticalAlignment(SwingConstants.BOTTOM);
				checkBoxes.put(result.getString("timeslot"), tmp);
				Thursday.add(checkBoxes.get(result.getString("timeslot")));
				i++;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JPanel Friday = new JPanel();
		Friday.setLayout(null);
		Friday.setBounds(620, 75, 80, 200);
		frmUpdate.getContentPane().add(Friday);
		
		JTextPane textFriday = new JTextPane();
		textFriday.setText("Friday");
		textFriday.setBackground(SystemColor.controlHighlight);
		textFriday.setBounds(0, 0, 80, 25);
		Friday.add(textFriday);
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			ps = connection.prepareStatement("SELECT `timeslot` FROM `timeslot` WHERE `timeslotId` < 16 AND `timeslotId` > 12");
			ResultSet result = ps.executeQuery();
			int i = 0;
			while(result.next()) {
				JCheckBox tmp = new JCheckBox("");
				tmp.setBounds(24, 30+(i*25), 21, 21);
				tmp.setVerticalAlignment(SwingConstants.BOTTOM);
				checkBoxes.put(result.getString("timeslot"), tmp);
				Friday.add(checkBoxes.get(result.getString("timeslot")));
				i++;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Connection connection;
		        PreparedStatement ps;
		        try {
					Class.forName("com.mysql.jdbc.Driver");			
					connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
					ps = connection.prepareStatement("SELECT `timeslot` FROM `timeslot`");
					ResultSet result = ps.executeQuery();
					while(result.next()) {
						if(checkBoxes.get(result.getString("timeslot")).isSelected()) {
							try {
								Class.forName("com.mysql.jdbc.Driver");			
								connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
								String pss = "UPDATE `available` SET " + result.getString("timeslot") + " = ? WHERE `professorName` = ?";
								ps = connection.prepareStatement(pss);
								ps.setString(1, "1");
								ps.setString(2, name);
								ps.executeUpdate();
										
							} catch (ClassNotFoundException e) {			
								e.printStackTrace();
							} catch (SQLException e) {	
								e.printStackTrace();
							}
				        			
						} else {
							 try {
								Class.forName("com.mysql.jdbc.Driver");			
								connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
								String pss = "UPDATE `available` SET " + result.getString("timeslot") + " = ? WHERE `professorName` = ?";
								ps = connection.prepareStatement(pss);
								ps.setString(1, "0");
								ps.setString(2, name);
								ps.executeUpdate();
										
							} catch (ClassNotFoundException e) {			
								e.printStackTrace();
							} catch (SQLException e) {	
								e.printStackTrace();
							}
						}
					}
							
				} catch (ClassNotFoundException e) {			
					e.printStackTrace();
				} catch (SQLException e) {	
					e.printStackTrace();
				}
		      
				TimetableGA.executeGA();
            	ProfilePage info = new ProfilePage(userName, password);             
            	info.frmProfile.setVisible(true);
                frmUpdate.setVisible(false);
                dispose();

			}
		});
	}
	
	
	class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
