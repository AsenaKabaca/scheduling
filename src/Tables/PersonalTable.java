package Tables;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class PersonalTable {

	private JFrame frmPersonal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalTable window = new PersonalTable();
					window.frmPersonal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PersonalTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPersonal = new JFrame();
		frmPersonal.setBounds(100, 100, 450, 300);
		frmPersonal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPersonal.getContentPane().setLayout(null);
		frmPersonal.setLocationRelativeTo(null);
	}

}
