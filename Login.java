package appl;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frmEmployeeManagementSystem;
	private JTextField tf1;
	private JPasswordField pf1;
	private JLabel lblNewLabel;
	Connection conn = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmEmployeeManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		conn = MyConnection.dbConnection();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmployeeManagementSystem = new JFrame();
		frmEmployeeManagementSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("ENTER_ICON_IMAGE_LOCATION"));
		frmEmployeeManagementSystem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		frmEmployeeManagementSystem.setTitle("Employee Management System - Login");
		frmEmployeeManagementSystem.setBounds(100, 100, 684, 380);
		frmEmployeeManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmployeeManagementSystem.getContentPane().setLayout(null);
		
		tf1 = new JTextField();
		tf1.setForeground(Color.DARK_GRAY);
		tf1.setBackground(Color.CYAN);
		tf1.setBounds(430, 129, 180, 32);
		frmEmployeeManagementSystem.getContentPane().add(tf1);
		tf1.setColumns(10);
		
		pf1 = new JPasswordField();
		pf1.setBackground(Color.CYAN);
		pf1.setForeground(Color.DARK_GRAY);
		pf1.setEchoChar('*');
		pf1.setBounds(430, 198, 180, 32);
		frmEmployeeManagementSystem.getContentPane().add(pf1);
		
		JLabel lblEnterUsername = new JLabel("Enter Username");
		lblEnterUsername.setForeground(Color.WHITE);
		lblEnterUsername.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblEnterUsername.setBounds(430, 107, 96, 23);
		frmEmployeeManagementSystem.getContentPane().add(lblEnterUsername);
		
		JLabel lblEnterPassword = new JLabel("Enter Password");
		lblEnterPassword.setForeground(Color.WHITE);
		lblEnterPassword.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblEnterPassword.setBounds(430, 172, 96, 27);
		frmEmployeeManagementSystem.getContentPane().add(lblEnterPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "SELECT * from TABLE_NAME1 where Username = ? and pass = ?";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1 , tf1.getText());
					ps.setString(2 , String.valueOf(pf1.getPassword()));
					ResultSet rs = ps.executeQuery();
					int count = 0;
					while(rs.next()) {
						count++;
					}
					if(count == 1) {
						EmployeeManagement frame = new EmployeeManagement();
						frame.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null , "Username or Password is Invalid!\nPlease enter again...");
						tf1.setText(null);
						pf1.setText(null);
					}
				}
				catch(SQLException e1) {					
				}
			}
		});
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.CYAN);
		btnLogin.setFont(new Font("Calibri", Font.BOLD, 12));
		btnLogin.setBounds(476, 251, 90, 23);
		frmEmployeeManagementSystem.getContentPane().add(btnLogin);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("ENTER_BACKGROUND_IMAGE_LOCATION"));
		lblNewLabel.setBounds(0, 0, 668, 341);
		frmEmployeeManagementSystem.getContentPane().add(lblNewLabel);
		
		
	}
}
