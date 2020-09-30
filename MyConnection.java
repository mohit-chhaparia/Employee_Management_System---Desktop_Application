package appl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class MyConnection {
	
	static Connection dbConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:PORT_NUMBER/FILE_NAME"
				+ "" , "USERNAME" , "PASSWORD");
			JOptionPane.showMessageDialog(null , "Connection Successful");
			return conn;
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null , "Connection Failed");
			e.printStackTrace();
			return null;
		}
	}
}
