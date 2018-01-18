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
	private final Action action = new SwingAction();

	public Update(String name) {
		this.name = name;
		
		Connection connection;
        PreparedStatement ps;
        try {
			Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
            ps = connection.prepareStatement("SELECT * FROM `user` WHERE `name` = ?");
            ps.setString(1, userName);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
            name = result.getString("name");
           

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
			ps = connection.prepareStatement("SELECT `day` FROM `avaiable` WHERE `user_name` = `name`");
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
		
		JButton btnUpdate = new JButton("Update Table");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnUpdate.setBounds(25, 170, 120, 25);
		btnUpdate.setBackground(SystemColor.activeCaption);
		btnUpdate.setForeground(SystemColor.desktop);
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
			}
		});
		frmUpdate.getContentPane().add(btnGeneral);
		
		JButton btnPersonal = new JButton("Personal Table");
		btnPersonal.setBounds(25, 205, 120, 25);
		btnPersonal.setBackground(UIManager.getColor("Button.disabledShadow"));
		frmUpdate.getContentPane().add(btnPersonal);
				
		
		JButton btnSave = new JButton("Save and go to profile page");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
            	ProfilePage info = new ProfilePage(lblName.getText(), name);             
            	info.frmProfile.setVisible(true);
                frmUpdate.setVisible(false);
                dispose();

			}
		});
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
		text9_10.setText("9.30 - 10.30");
		text9_10.setBackground(SystemColor.inactiveCaptionBorder);
		Hours.add(text9_10);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(0, 50, 100, 25);
		textPane_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_2.setText("10.30 - 11.30");
		textPane_2.setBackground(SystemColor.control);
		Hours.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(0, 75, 100, 25);
		textPane_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_3.setText("11.30  12.30 ");
		textPane_3.setBackground(SystemColor.inactiveCaptionBorder);
		Hours.add(textPane_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(0, 100, 100, 25);
		textPane_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_4.setText("12.30 - 13.30");
		textPane_4.setBackground(SystemColor.control);
		Hours.add(textPane_4);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setBounds(0, 125, 100, 25);
		textPane_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_5.setText("13.30 - 14.30 ");
		textPane_5.setBackground(SystemColor.inactiveCaptionBorder);
		Hours.add(textPane_5);
		
		JTextPane textPane_6 = new JTextPane();
		textPane_6.setBounds(0, 150, 100, 25);
		textPane_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_6.setText("14.30 - 15.30 ");
		textPane_6.setBackground(SystemColor.control);
		Hours.add(textPane_6);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(0, 175, 100, 25);
		textPane_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane_1.setText("15.30 - 16.30 ");
		textPane_1.setBackground(SystemColor.inactiveCaptionBorder);
		Hours.add(textPane_1);
				
		JPanel Monday = new JPanel();
		Monday.setBounds(300, 75, 80, 200);
		frmUpdate.getContentPane().add(Monday);
		Monday.setLayout(null);		
		
		JTextPane textMonday = new JTextPane();
		textMonday.setBounds(0, 0, 80, 25);
		textMonday.setText("Monday");
		textMonday.setBackground(SystemColor.controlHighlight);
		Monday.add(textMonday);
		
		Connection connection;
        PreparedStatement ps;
        
		JCheckBox M9_10 = new JCheckBox("");
		M9_10.setAction(action);
		M9_10.setBounds(24, 30, 21, 21);
		M9_10.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M9_10);
		  if (M9_10.isSelected()) {
		        try {
					Class.forName("com.mysql.jdbc.Driver");			
					connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
					ps = connection.prepareStatement("INSERT INTO `avaible`(M9_10) VALUES ('?') WHERE `user_name` = ?");
					ps.setInt(1, 1);
					ps.setString(2, lblName.getText());
					ps.executeQuery();
							
				} catch (ClassNotFoundException e) {			
					e.printStackTrace();
				} catch (SQLException e) {	
					e.printStackTrace();
				}
	        			
		  }
		  else {
		        try {
					Class.forName("com.mysql.jdbc.Driver");			
					connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
					ps = connection.prepareStatement("INSERT INTO `avaible`(M9_10) VALUES ('?') WHERE `user_name` = ?");
					ps.setInt(1, 0);
					ps.setString(2, lblName.getText());
					ps.executeQuery();
							
				} catch (ClassNotFoundException e) {			
					e.printStackTrace();
				} catch (SQLException e) {	
					e.printStackTrace();
				}

		        
		  }

           
		JCheckBox M10_11 = new JCheckBox("");
		M10_11.setBounds(24, 55, 21, 21);
		M10_11.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M10_11);

				
		JCheckBox M11_12 = new JCheckBox("");
		M11_12.setBounds(24, 80, 21, 21);
		M11_12.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M11_12);

		
		JCheckBox M12_13 = new JCheckBox("");
		M12_13.setBounds(24, 105, 21, 21);
		M12_13.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M12_13);

		
		JCheckBox M13_14 = new JCheckBox("");
		M13_14.setBounds(24, 130, 21, 21);
		M13_14.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M13_14);

		
		JCheckBox M14_15 = new JCheckBox("");
		M14_15.setBounds(24, 155, 21, 21);
		M14_15.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M14_15);

		
		JCheckBox M15_16 = new JCheckBox("");
		M15_16.setBounds(24, 180, 21, 21);
		M15_16.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M15_16);		

		JPanel Tuesday = new JPanel();
		Tuesday.setLayout(null);
		Tuesday.setBounds(380, 75, 80, 200);
		frmUpdate.getContentPane().add(Tuesday);
				
		JTextPane textTuesday = new JTextPane();
		textTuesday.setText("Tuesday");
		textTuesday.setBackground(SystemColor.controlHighlight);
		textTuesday.setBounds(0, 0, 80, 25);
		Tuesday.add(textTuesday);
		
		JCheckBox Tue9_10 = new JCheckBox("");
		Tue9_10.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue9_10.setBounds(24, 30, 21, 21);
		Tuesday.add(Tue9_10);

		
		JCheckBox Tue10_11 = new JCheckBox("");
		Tue10_11.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue10_11.setBounds(24, 55, 21, 21);
		Tuesday.add(Tue10_11);

		
		JCheckBox Tue11_12 = new JCheckBox("");
		Tue11_12.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue11_12.setBounds(24, 80, 21, 21);
		Tuesday.add(Tue11_12);

		
		JCheckBox Tue12_13 = new JCheckBox("");
		Tue12_13.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue12_13.setBounds(24, 105, 21, 21);
		Tuesday.add(Tue12_13);

		
		JCheckBox Tue13_14 = new JCheckBox("");
		Tue13_14.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue13_14.setBounds(24, 130, 21, 21);
		Tuesday.add(Tue13_14);

		JCheckBox Tue14_15 = new JCheckBox("");
		Tue14_15.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue14_15.setBounds(24, 155, 21, 21);
		Tuesday.add(Tue14_15);
		
		JCheckBox Tue15_16 = new JCheckBox("");
		Tue15_16.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue15_16.setBounds(24, 180, 21, 21);
		Tuesday.add(Tue15_16);
		
		JPanel Wednesday = new JPanel();
		Wednesday.setLayout(null);
		Wednesday.setBounds(460, 75, 80, 200);
		frmUpdate.getContentPane().add(Wednesday);
		
		JTextPane textWednesday = new JTextPane();
		textWednesday.setText("Wednesday");
		textWednesday.setBackground(SystemColor.controlHighlight);
		textWednesday.setBounds(0, 0, 80, 25);
		Wednesday.add(textWednesday);

		JCheckBox W9_10 = new JCheckBox("");
		W9_10.setVerticalAlignment(SwingConstants.BOTTOM);
		W9_10.setBounds(24, 30, 21, 21);
		Wednesday.add(W9_10);
		
		JCheckBox W10_11 = new JCheckBox("");
		W10_11.setVerticalAlignment(SwingConstants.BOTTOM);
		W10_11.setBounds(24, 55, 21, 21);
		Wednesday.add(W10_11);
		
		JCheckBox W11_12 = new JCheckBox("");
		W11_12.setVerticalAlignment(SwingConstants.BOTTOM);
		W11_12.setBounds(24, 80, 21, 21);
		Wednesday.add(W11_12);
		
		JCheckBox W12_13 = new JCheckBox("");
		W12_13.setVerticalAlignment(SwingConstants.BOTTOM);
		W12_13.setBounds(24, 105, 21, 21);
		Wednesday.add(W12_13);
		
		JCheckBox W13_14 = new JCheckBox("");
		W13_14.setVerticalAlignment(SwingConstants.BOTTOM);
		W13_14.setBounds(24, 130, 21, 21);
		Wednesday.add(W13_14);

		JCheckBox W14_15 = new JCheckBox("");
		W14_15.setVerticalAlignment(SwingConstants.BOTTOM);
		W14_15.setBounds(24, 155, 21, 21);
		Wednesday.add(W14_15);

		
		JCheckBox W15_16 = new JCheckBox("");
		W15_16.setVerticalAlignment(SwingConstants.BOTTOM);
		W15_16.setBounds(24, 180, 21, 21);
		Wednesday.add(W15_16);
		
		JPanel Thursday = new JPanel();
		Thursday.setLayout(null);
		Thursday.setBounds(540, 75, 80, 200);
		frmUpdate.getContentPane().add(Thursday);
		
		JTextPane textThursday = new JTextPane();
		textThursday.setText("Thursday");
		textThursday.setBackground(SystemColor.controlHighlight);
		textThursday.setBounds(0, 0, 80, 25);
		Thursday.add(textThursday);
	
		JCheckBox T9_10 = new JCheckBox("");
		T9_10.setVerticalAlignment(SwingConstants.BOTTOM);
		T9_10.setBounds(24, 30, 21, 21);
		Thursday.add(T9_10);		
		
		JCheckBox T10_11 = new JCheckBox("");
		T10_11.setVerticalAlignment(SwingConstants.BOTTOM);
		T10_11.setBounds(24, 55, 21, 21);
		Thursday.add(T10_11);
		
		JCheckBox T11_12 = new JCheckBox("");
		T11_12.setVerticalAlignment(SwingConstants.BOTTOM);
		T11_12.setBounds(24, 80, 21, 21);
		Thursday.add(T11_12);
		
		JCheckBox T12_13 = new JCheckBox("");
		T12_13.setVerticalAlignment(SwingConstants.BOTTOM);
		T12_13.setBounds(24, 105, 21, 21);
		Thursday.add(T12_13);
		
		JCheckBox T13_14 = new JCheckBox("");
		T13_14.setVerticalAlignment(SwingConstants.BOTTOM);
		T13_14.setBounds(24, 130, 21, 21);
		Thursday.add(T13_14);
		
		JCheckBox T14_15 = new JCheckBox("");
		T14_15.setVerticalAlignment(SwingConstants.BOTTOM);
		T14_15.setBounds(24, 155, 21, 21);
		Thursday.add(T14_15);

		JCheckBox T15_16 = new JCheckBox("");
		T15_16.setVerticalAlignment(SwingConstants.BOTTOM);
		T15_16.setBounds(24, 180, 21, 21);
		Thursday.add(T15_16);

		JPanel Friday = new JPanel();
		Friday.setLayout(null);
		Friday.setBounds(620, 75, 80, 200);
		frmUpdate.getContentPane().add(Friday);
		
		JTextPane textFriday = new JTextPane();
		textFriday.setText("Friday");
		textFriday.setBackground(SystemColor.controlHighlight);
		textFriday.setBounds(0, 0, 80, 25);
		Friday.add(textFriday);
	
		JCheckBox F9_10 = new JCheckBox("");
		F9_10.setVerticalAlignment(SwingConstants.BOTTOM);
		F9_10.setBounds(24, 30, 21, 21);
		Friday.add(F9_10);
		
		JCheckBox F10_11 = new JCheckBox("");
		F10_11.setVerticalAlignment(SwingConstants.BOTTOM);
		F10_11.setBounds(24, 55, 21, 21);
		Friday.add(F10_11);
		
		JCheckBox F11_12 = new JCheckBox("");
		F11_12.setVerticalAlignment(SwingConstants.BOTTOM);
		F11_12.setBounds(24, 80, 21, 21);
		Friday.add(F11_12);
		
		JCheckBox F12_13 = new JCheckBox("");
		F12_13.setVerticalAlignment(SwingConstants.BOTTOM);
		F12_13.setBounds(24, 105, 21, 21);
		Friday.add(F12_13);
		
		JCheckBox F13_14 = new JCheckBox("");
		F13_14.setVerticalAlignment(SwingConstants.BOTTOM);
		F13_14.setBounds(24, 130, 21, 21);
		Friday.add(F13_14);
		
		JCheckBox F14_15 = new JCheckBox("");
		F14_15.setVerticalAlignment(SwingConstants.BOTTOM);
		F14_15.setBounds(24, 155, 21, 21);
		Friday.add(F14_15);
		
		JCheckBox F15_16 = new JCheckBox("");
		F15_16.setVerticalAlignment(SwingConstants.BOTTOM);
		F15_16.setBounds(24, 180, 21, 21);
		Friday.add(F15_16);
	
	
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
