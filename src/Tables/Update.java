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
	private String password = null;
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
        
		JCheckBox M9_11 = new JCheckBox("");
		M9_11.setAction(action);
		M9_11.setBounds(24, 30, 21, 21);
		M9_11.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M9_11);

		JCheckBox M13_15 = new JCheckBox("");
		M13_15.setBounds(24, 55, 21, 21);
		M13_15.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M13_15);

				
		JCheckBox M15_17 = new JCheckBox("");
		M15_17.setBounds(24, 80, 21, 21);
		M15_17.setVerticalAlignment(SwingConstants.BOTTOM);
		Monday.add(M15_17);	

		JPanel Tuesday = new JPanel();
		Tuesday.setLayout(null);
		Tuesday.setBounds(380, 75, 80, 200);
		frmUpdate.getContentPane().add(Tuesday);
				
		JTextPane textTuesday = new JTextPane();
		textTuesday.setText("Tuesday");
		textTuesday.setBackground(SystemColor.controlHighlight);
		textTuesday.setBounds(0, 0, 80, 25);
		Tuesday.add(textTuesday);
		
		JCheckBox Tue9_11 = new JCheckBox("");
		Tue9_11.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue9_11.setBounds(24, 30, 21, 21);
		Tuesday.add(Tue9_11);

		
		JCheckBox Tue13_15 = new JCheckBox("");
		Tue13_15.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue13_15.setBounds(24, 55, 21, 21);
		Tuesday.add(Tue13_15);

		
		JCheckBox Tue15_17 = new JCheckBox("");
		Tue15_17.setVerticalAlignment(SwingConstants.BOTTOM);
		Tue15_17.setBounds(24, 80, 21, 21);
		Tuesday.add(Tue15_17);
		
		JPanel Wednesday = new JPanel();
		Wednesday.setLayout(null);
		Wednesday.setBounds(460, 75, 80, 200);
		frmUpdate.getContentPane().add(Wednesday);
		
		JTextPane textWednesday = new JTextPane();
		textWednesday.setText("Wednesday");
		textWednesday.setBackground(SystemColor.controlHighlight);
		textWednesday.setBounds(0, 0, 80, 25);
		Wednesday.add(textWednesday);

		JCheckBox W9_11 = new JCheckBox("");
		W9_11.setVerticalAlignment(SwingConstants.BOTTOM);
		W9_11.setBounds(24, 30, 21, 21);
		Wednesday.add(W9_11);
		
		JCheckBox W13_15 = new JCheckBox("");
		W13_15.setVerticalAlignment(SwingConstants.BOTTOM);
		W13_15.setBounds(24, 55, 21, 21);
		Wednesday.add(W13_15);
		
		JCheckBox W15_17 = new JCheckBox("");
		W15_17.setVerticalAlignment(SwingConstants.BOTTOM);
		W15_17.setBounds(24, 80, 21, 21);
		Wednesday.add(W15_17);
		
		JPanel Thursday = new JPanel();
		Thursday.setLayout(null);
		Thursday.setBounds(540, 75, 80, 200);
		frmUpdate.getContentPane().add(Thursday);
		
		JTextPane textThursday = new JTextPane();
		textThursday.setText("Thursday");
		textThursday.setBackground(SystemColor.controlHighlight);
		textThursday.setBounds(0, 0, 80, 25);
		Thursday.add(textThursday);
	
		JCheckBox T9_11 = new JCheckBox("");
		T9_11.setVerticalAlignment(SwingConstants.BOTTOM);
		T9_11.setBounds(24, 30, 21, 21);
		Thursday.add(T9_11);		
		
		JCheckBox T13_15 = new JCheckBox("");
		T13_15.setVerticalAlignment(SwingConstants.BOTTOM);
		T13_15.setBounds(24, 55, 21, 21);
		Thursday.add(T13_15);
		
		JCheckBox T15_17 = new JCheckBox("");
		T15_17.setVerticalAlignment(SwingConstants.BOTTOM);
		T15_17.setBounds(24, 80, 21, 21);
		Thursday.add(T15_17);

		JPanel Friday = new JPanel();
		Friday.setLayout(null);
		Friday.setBounds(620, 75, 80, 200);
		frmUpdate.getContentPane().add(Friday);
		
		JTextPane textFriday = new JTextPane();
		textFriday.setText("Friday");
		textFriday.setBackground(SystemColor.controlHighlight);
		textFriday.setBounds(0, 0, 80, 25);
		Friday.add(textFriday);
	
		JCheckBox F9_11 = new JCheckBox("");
		F9_11.setVerticalAlignment(SwingConstants.BOTTOM);
		F9_11.setBounds(24, 30, 21, 21);
		Friday.add(F9_11);
		
		JCheckBox F13_15 = new JCheckBox("");
		F13_15.setVerticalAlignment(SwingConstants.BOTTOM);
		F13_15.setBounds(24, 55, 21, 21);
		Friday.add(F13_15);
		
		JCheckBox F15_17 = new JCheckBox("");
		F15_17.setVerticalAlignment(SwingConstants.BOTTOM);
		F15_17.setBounds(24, 80, 21, 21);
		Friday.add(F15_17);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Connection connection;
		        PreparedStatement ps;
		        if (M9_11.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET M9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET M9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (M13_15.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET M13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET M13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (M15_17.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET M15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET M15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (Tue9_11.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET Tue9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET Tue9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (Tue13_15.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET Tue13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET Tue13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (Tue15_17.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET Tue15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET Tue15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (W9_11.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET W9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET W9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (W13_15.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET W13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET W13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (W15_17.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET W15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET W15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (T9_11.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET T9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET T9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (T13_15.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET T13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET T13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (T15_17.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET T15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET T15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (F9_11.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET F9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET F9_11 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (F13_15.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET F13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET F13_15 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}

			        
			  }
		        if (F15_17.isSelected()) {
			        try {
						Class.forName("com.mysql.jdbc.Driver");			
						connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
						ps = connection.prepareStatement("UPDATE available SET F15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "1");
						ps.setString(2, name);
						ps.executeUpdate();
								
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
						ps = connection.prepareStatement("UPDATE available SET F15_17 = ? WHERE `professorName` = ?");
						ps.setString(1, "0");
						ps.setString(2, name);
						ps.executeUpdate();
								
					} catch (ClassNotFoundException e) {			
						e.printStackTrace();
					} catch (SQLException e) {	
						e.printStackTrace();
					}
			  }
				
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
