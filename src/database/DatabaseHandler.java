package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Login.LoginPage;

public class DatabaseHandler {
	public static ResultSet executeQ(String query) {
		Connection connection;
        PreparedStatement ps;
        ResultSet result = null;
        try {
			Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
            ps = connection.prepareStatement(query);
            result = ps.executeQuery();
            return result;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
		return result;
	}
	public static void updateQ(String query) {
		Connection connection;
        PreparedStatement ps;
        try {
			Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
            ps = connection.prepareStatement(query);
			ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
