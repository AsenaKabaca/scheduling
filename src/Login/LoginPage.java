package Login;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.Connection;

import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPasswordField;

import javax.swing.UIManager;

import Profile.ProfilePage;

import javax.swing.Box;

public class LoginPage extends JFrame{
	private JFrame frmLogin;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLogin.setTitle("Login");
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setVisible(true);
		frmLogin.setBackground(UIManager.getColor("Button.light"));
		frmLogin.setBounds(200, 200, 360, 360);
		frmLogin.getContentPane().setLayout(null);
		frmLogin.setLocationRelativeTo(null);
		
		JLabel lblUsername = new JLabel("User Name");
		lblUsername.setBounds(37, 163, 81, 30);
		frmLogin.getContentPane().add(lblUsername);
		
		JLabel IbIPassword = new JLabel("Password");
		IbIPassword.setBounds(37, 204, 81, 14);
		frmLogin.getContentPane().add(IbIPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(151, 168, 124, 20);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		btnLogin = new JButton("Login");
		
		//enter
		frmLogin.getRootPane().setDefaultButton(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		        Connection connection;
		        PreparedStatement ps;
		        try {
					Class.forName("com.mysql.jdbc.Driver");

		            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
		            ps = connection.prepareStatement("SELECT `professorUserName`, `password` FROM `professor` WHERE `professorUserName` = ? AND `password` = ?");
		            ps.setString(1, txtUsername.getText());
		            ps.setString(2, String.valueOf(txtPassword.getPassword()));
		            ResultSet result = ps.executeQuery();
		            if(result.next()){
		            	JOptionPane.showMessageDialog(null, "Login Successfull");	
		            	
		            	ProfilePage info = new ProfilePage(txtUsername.getText(), txtPassword.getText());             
		            	info.frmProfile.setVisible(true);
		                frmLogin.setVisible(false);
		                dispose();
		            }
		            else{
		            	JOptionPane.showMessageDialog(null, "Invalid User name or Password","Login Error", JOptionPane.ERROR_MESSAGE);
						txtPassword.setText(null);
						txtUsername.setText(null); 
		                
		            }
		        } catch (SQLException | ClassNotFoundException ex) {
		            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
		        }
		    }}        
		);				
			
		btnLogin.setBounds(46, 253, 89, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frmLoginSystem = new JFrame("Exit");				
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit", "Login Systems",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		}
							);
		btnExit.setBounds(221, 253, 89, 23);
		frmLogin.getContentPane().add(btnExit);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(39, 240, 271, 2);
		frmLogin.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(39, 152, 271, 2);
		frmLogin.getContentPane().add(separator_1);
		
				
		txtPassword = new JPasswordField();
		txtPassword.setBounds(151, 201, 124, 20);
		frmLogin.getContentPane().add(txtPassword);
		
		JLabel lblImageLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image scaledImage = img.getScaledInstance(120, 120, Image.SCALE_FAST);
		lblImageLabel.setIcon(new ImageIcon(scaledImage));
		lblImageLabel.setBounds(108, 11, 120, 120);
		frmLogin.getContentPane().add(lblImageLabel);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(154, 79, 1, 1);
		frmLogin.getContentPane().add(horizontalBox);
	}

}
