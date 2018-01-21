package Tables;
/*Contents of CalendarProgran.class */

//Import packages
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CalendarProgram{
	static JTable tblCalendar;
	static JFrame frmMain;
	static Container pane;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane
	static JPanel pnlCalendar;
	//static int realYear, realMonth, realDay, currentYear, currentMonth;
	
	public static void generalTable() {

		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		frmMain = new JFrame ("Schedule Table"); //Create frame
		frmMain.setSize(1090, 726); //Set size to 400x400 pixels
		pane = frmMain.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout
		frmMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked

		//Create controls

		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);

		//Set border
		pnlCalendar.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		//Add controls to pane
		pane.add(pnlCalendar);

		pnlCalendar.add(stblCalendar);
		
		//Set bounds
		pnlCalendar.setBounds(0, 0, 1080, 720);

		stblCalendar.setBounds(0, 0, 1080, 720);
		
		//Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		
		//Get real month/year
		/*GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;*/
		
		//Add headers
		String[] headers = {"Hours", "Mon", "Tue", "Wed", "Thu", "Fri"}; //All headers
		for (int i=0; i<6; i++){
			mtblCalendar.addColumn(headers[i]);
		}

		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tblCalendar.setRowHeight(220);
		mtblCalendar.setColumnCount(6);
		//mtblCalendar.setRowCount(3);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblCalendar.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.CENTER);
		leftRenderer.setVerticalAlignment(JLabel.CENTER);
		tblCalendar.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(5).setCellRenderer(leftRenderer);		

		//hücre ici dolumu
		Object[] rowData = new Object[6];
		rowData[0] = "";
		for(int i = 1; i <=3; i++) {
			if(i == 1)
				rowData[0] = "09:00-11:00";
			else if(i == 2)
				rowData[0] = "13:00-15:00";
				else
					rowData[0] = "15:00-17:00";
			rowData[1] = "<html>";
			rowData[2] = "<html>";
			rowData[3] = "<html>";
			rowData[4] = "<html>";
			rowData[5] = "<html>";
			for(int j = 0; j <=4; j++) {
				int timeslotId = i+ (j*3);
				Connection connection;
			    PreparedStatement ps;
			    try {
					java.lang.Class.forName("com.mysql.jdbc.Driver");
					connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			        
			        ps = connection.prepareStatement("SELECT * FROM `schedule` WHERE timeslotId = ?");
			        ps.setInt(1, timeslotId);
			        ResultSet result1 = ps.executeQuery();
			        while(result1.next()){
			        	ps = connection.prepareStatement("SELECT * FROM `module` WHERE moduleId = ?");
			        	ps.setString(1, result1.getString("moduleId"));
			        	ResultSet result2 = ps.executeQuery();
			        	
			        	ps = connection.prepareStatement("SELECT * FROM `room` WHERE roomId = ?");
			        	ps.setString(1, result1.getString("roomId"));
			        	ResultSet result3 = ps.executeQuery();
			        	
			        	ps = connection.prepareStatement("SELECT * FROM `professor` WHERE professorId = ?");
			        	ps.setString(1, result1.getString("professorId"));
			        	ResultSet result4 = ps.executeQuery();
			        	
			        	if(result2.next() && result3.next() && result4.next()) {
			        		rowData[j+1] += result2.getString("moduleCode") + " - " + result3.getString("roomNumber") + "<br/>" + 
			        				result2.getString("moduleName") + "<br/>" + result4.getString("professorName") + "<br/><br/>";
			        	}
			        	
			        }
			    } catch (SQLException | ClassNotFoundException ex) {
			    	ex.printStackTrace();
			    }
			}
			for(int k = 1; k <= 5; k++) {
				rowData[k] += "</html>";
			}
			mtblCalendar.addRow(rowData);
		}
		
		//Populate table

		//Refresh calendar
		//refreshCalendar (realMonth, realYear); //Refresh calendar
	
	}

	public static void personalTable(int professorId) {


		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		frmMain = new JFrame ("Schedule Table"); //Create frame
		frmMain.setSize(1090, 726); //Set size to 400x400 pixels
		pane = frmMain.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout
		frmMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked

		//Create controls

		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);

		//Set border
		pnlCalendar.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		//Add controls to pane
		pane.add(pnlCalendar);

		pnlCalendar.add(stblCalendar);
		
		//Set bounds
		pnlCalendar.setBounds(0, 0, 1080, 720);

		stblCalendar.setBounds(0, 0, 1080, 720);
		
		//Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		
		//Get real month/year
		/*GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;*/
		
		//Add headers
		String[] headers = {"Hours", "Mon", "Tue", "Wed", "Thu", "Fri"}; //All headers
		for (int i=0; i<6; i++){
			mtblCalendar.addColumn(headers[i]);
		}

		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tblCalendar.setRowHeight(220);
		mtblCalendar.setColumnCount(6);
		//mtblCalendar.setRowCount(3);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tblCalendar.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.CENTER);
		leftRenderer.setVerticalAlignment(JLabel.CENTER);
		tblCalendar.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);		
		tblCalendar.getColumnModel().getColumn(5).setCellRenderer(leftRenderer);		

		//hücre ici dolumu
		Object[] rowData = new Object[6];
		rowData[0] = "";
		for(int i = 1; i <=3; i++) {
			if(i == 1)
				rowData[0] = "09:00-11:00";
			else if(i == 2)
				rowData[0] = "13:00-15:00";
				else
					rowData[0] = "15:00-17:00";
			rowData[1] = "<html>";
			rowData[2] = "<html>";
			rowData[3] = "<html>";
			rowData[4] = "<html>";
			rowData[5] = "<html>";
			for(int j = 0; j <=4; j++) {
				int timeslotId = i+ (j*3);
				Connection connection;
			    PreparedStatement ps;
			    try {
					java.lang.Class.forName("com.mysql.jdbc.Driver");
					connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
			        
			        ps = connection.prepareStatement("SELECT * FROM `schedule` WHERE timeslotId = ? and professorId = ?");
			        ps.setInt(1, timeslotId);
			        ps.setInt(2, professorId);
			        ResultSet result1 = ps.executeQuery();
			        while(result1.next()){
			        	ps = connection.prepareStatement("SELECT * FROM `module` WHERE moduleId = ?");
			        	ps.setString(1, result1.getString("moduleId"));
			        	ResultSet result2 = ps.executeQuery();
			        	
			        	ps = connection.prepareStatement("SELECT * FROM `room` WHERE roomId = ?");
			        	ps.setString(1, result1.getString("roomId"));
			        	ResultSet result3 = ps.executeQuery();
			        	
			        	ps = connection.prepareStatement("SELECT * FROM `professor` WHERE professorId = ?");
			        	ps.setString(1, result1.getString("professorId"));
			        	ResultSet result4 = ps.executeQuery();
			        	
			        	if(result2.next() && result3.next() && result4.next()) {
			        		rowData[j+1] += result2.getString("moduleCode") + " - " + result3.getString("roomNumber") + "<br/>" + 
			        				result2.getString("moduleName") + "<br/>" + result4.getString("professorName") + "<br/><br/>";
			        	}
			        	
			        }
			    } catch (SQLException | ClassNotFoundException ex) {
			    	ex.printStackTrace();
			    }
			}
			for(int k = 1; k <= 5; k++) {
				rowData[k] += "</html>";
			}
			mtblCalendar.addRow(rowData);
		}
		
		//Populate table

		//Refresh calendar
		//refreshCalendar (realMonth, realYear); //Refresh calendar
	
	
	}
	
	
	
	/*public static void refreshCalendar(int month, int year){
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}*/
}