package Tables;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GeneralTable {

	private JFrame frmGeneral;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneralTable window = new GeneralTable();
					window.frmGeneral.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GeneralTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGeneral = new JFrame();
		frmGeneral.setBounds(100, 100, 450, 300);
		frmGeneral.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGeneral.getContentPane().setLayout(null);
		frmGeneral.setLocationRelativeTo(null);
	}



}
