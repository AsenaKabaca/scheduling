package Profile;

import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import java.awt.Color;

import Login.LoginPage;
import Tables.CalendarProgram;
import Tables.Update;




public class ProfilePage extends JFrame{

	public JFrame frmProfile;
	private String userName = null;
	private String password = null;
	private int professorId;
	private String name;
	private String lectures = "";

	public ProfilePage(String username, String password) {
		this.userName = username;
		this.password = password;
		
		Connection connection;
        PreparedStatement ps;
        try {
			Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
            ps = connection.prepareStatement("SELECT * FROM `professor` WHERE `professorUserName` = ? AND `password` = ?");
            ps.setString(1, userName);
            ps.setString(2, String.valueOf(password));
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {
            name = result.getString("professorName");
            professorId = result.getInt("professorId");
            ps = connection.prepareStatement("SELECT * FROM `module` WHERE `professorId` = ?");
            ps.setString(1, result.getString("professorId"));
            
            }
            
            result = ps.executeQuery();
            while(result.next()) {
            	lectures = lectures + result.getString("moduleCode") + " - " + result.getString("moduleName") + "<br/>" + "&nbsp;&nbsp;&nbsp;"+ 
            			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmProfile = new JFrame();
		frmProfile.setBounds(100, 100, 546, 366);
		frmProfile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProfile.getContentPane().setLayout(null);
		frmProfile.setLocationRelativeTo(null);
		
		JLabel lblImageLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		Image scaledImage = img.getScaledInstance(120, 120, Image.SCALE_FAST);
		lblImageLabel.setIcon(new ImageIcon(scaledImage));
		lblImageLabel.setBounds(225, 11, 120, 120);
		frmProfile.getContentPane().add(lblImageLabel);
		
		JButton btnNewButton = new JButton("Personal Table");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
            	CalendarProgram.personalTable(professorId);
				//frmProfile.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
		btnNewButton.setBounds(10, 180, 129, 23);
		frmProfile.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("General Table");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CalendarProgram.generalTable();
				//frmProfile.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

				/*GeneralTable info = new GeneralTable();             
				GeneralTable.main(null);
                frmProfile.setVisible(false);
                dispose();*/
			}
		});
		btnNewButton_1.setBounds(10, 214, 129, 23);
		frmProfile.getContentPane().add(btnNewButton_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 151, 500, 2);
		frmProfile.getContentPane().add(separator);
		
		JLabel lblName = new JLabel("Name :   " + name);
		lblName.setForeground(Color.BLACK);
		lblName.setBounds(172, 180, 250, 19);
		frmProfile.getContentPane().add(lblName);
		
		JLabel lblLectures = new JLabel("<html>Lecture(s) : " + lectures + "</html>");
		lblLectures.setForeground(Color.BLACK);
		lblLectures.setBounds(172, 199, 400, 90);
		frmProfile.getContentPane().add(lblLectures);
		
		/*JTextPane txtName = new JTextPane();
		txtName.setDropMode(DropMode.INSERT);
		txtName.setBounds(286, 181, 175, 20);
		frmProfile.getContentPane().add(txtName);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(286, 212, 175, 20);
		frmProfile.getContentPane().add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(286, 240, 175, 63);
		frmProfile.getContentPane().add(textPane_2);*/
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(149, 165, 361, 151);
		frmProfile.getContentPane().add(progressBar);
		
		JButton btnNewButton_2 = new JButton("Update Table");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Update info = new Update(userName, password);           
            	info.frmUpdate.setVisible(true);
                frmProfile.setVisible(false);
                dispose();
			}
		});

		btnNewButton_2.setBounds(10, 248, 129, 23);
		frmProfile.getContentPane().add(btnNewButton_2);
	}
}
