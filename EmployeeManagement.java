package appl;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class EmployeeManagement extends JFrame {

	private JPanel contentPane;
	private JTextField tf;
	private JTable table;
	private JTextField tf5;
	private JTextField tf4;
	private JTextField tf3;
	private JTextField tf2;
	private JTextField tf1;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox;
	private final JLabel lblNewLabel = new JLabel("");
	Connection conn = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeManagement frame = new EmployeeManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void comboBoxFill() {
		String query = "SELECT * from TABLE_NAME2";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				comboBox.addItem(rs.getString("Name"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		String query = "SELECT * from TABLE_NAME2";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			tf1.setText(null);
			tf2.setText(null);
			tf3.setText(null);
			tf4.setText(null);
			tf5.setText(null);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null , "Failed to load table");
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public EmployeeManagement() {
		conn = MyConnection.dbConnection();
		setFont(new Font("Times New Roman", Font.BOLD, 14));
		setTitle("Employee Management System - Database");
		setIconImage(Toolkit.getDefaultToolkit().getImage("ENTER_ICON_IMAGE_LOCATION"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "SELECT * from TABLE_NAME2 where Name = ?";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1 , (String) comboBox.getSelectedItem());
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						tf1.setText(String.valueOf(rs.getInt(1)));
						tf2.setText(rs.getString(2));
						tf3.setText(String.valueOf(rs.getInt(3)));
						tf4.setText(rs.getString(4));
						tf5.setText(rs.getString(5));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				refreshTable();
				
			}
		});
		comboBox.setBackground(Color.LIGHT_GRAY);
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		comboBox.setBounds(10, 11, 216, 20);
		contentPane.add(comboBox);
		
		tf = new JTextField();
		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String query = "Select * from TABLE_NAME2 where Name LIKE ?";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1 , tf.getText());
					ResultSet rs = ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		tf.setBackground(Color.CYAN);
		tf.setFont(new Font("Calibri", Font.PLAIN, 12));
		tf.setBounds(300, 11, 230, 20);
		contentPane.add(tf);
		tf.setColumns(10);
		
		JButton btnNewButton = new JButton("Print");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewButton.setBounds(550, 10, 108, 20);
		contentPane.add(btnNewButton);
		
		table = new JTable();
		table.setEditingColumn(0);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						String query = "SELECT * from TABLE_NAME2 where EID = ?";
						try {
							PreparedStatement ps = conn.prepareStatement(query);
							ps.setInt(1 , (int) table.getValueAt(table.getSelectedRow(), 0));
							ResultSet rs = ps.executeQuery();
							rs.next();
							tf1.setText(String.valueOf(rs.getInt(1)));
							tf2.setText(rs.getString(2));
							tf3.setText(String.valueOf(rs.getInt(3)));
							tf4.setText(rs.getString(4));
							tf5.setText(rs.getString(5));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						
					}
					
				});
			}
		});
		table.setBackground(Color.CYAN);
		table.setFont(new Font("Calibri", Font.PLAIN, 12));
		table.setBounds(247, 54, 411, 276);
		contentPane.add(table);
		
		JButton btnNewButton_1 = new JButton("Load Data");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				refreshTable();
				
			}
		});
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewButton_1.setBounds(126, 290, 100, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Update");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "UPDATE TABLE_NAME2 SET EID = ? , Name = ? , Salary = ? , CName = ? , "
						+ "DOJ = ? where EID = ? or (Name = ? and DOJ = ? and CName = ?)";
				PreparedStatement ps;
				try {
					ps = conn.prepareStatement(query);
					ps.setInt(1 , Integer.parseInt(tf1.getText()));
					ps.setString(2 , tf2.getText());
					ps.setInt(3 , Integer.parseInt(tf3.getText()));
					ps.setString(4 , tf4.getText());
					ps.setString(5 , tf5.getText());
					ps.setInt(6 , Integer.parseInt(tf1.getText()));
					ps.setString(7 , tf2.getText());
					ps.setString(8 , tf5.getText());
					ps.setString(9 , tf4.getText());
					ps.execute();
					JOptionPane.showMessageDialog(null , "Data Updated Successfully");
					comboBox.removeAllItems();
					comboBoxFill();
					refreshTable();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null , "Failed to Update Data");
					e.printStackTrace();
				}

			}
		});
		btnNewButton_2.setBackground(Color.LIGHT_GRAY);
		btnNewButton_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewButton_2.setBounds(10, 290, 100, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Insert");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "INSERT into TABLE_NAME2 values(?,?,?,?,?)";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setInt(1 , Integer.parseInt(tf1.getText()));
					ps.setString(2 , tf2.getText());
					ps.setInt(3 , Integer.parseInt(tf3.getText()));
					ps.setString(4 , tf4.getText());
					ps.setString(5 , tf5.getText());
					
					ps.execute();
					JOptionPane.showMessageDialog(null , "Data Insertion Successful");
					comboBox.removeAllItems();
					comboBoxFill();
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null , "Data Insertion Failed");
					e1.printStackTrace();
				}
				refreshTable();
				
				
			}
		});
		btnNewButton_3.setBackground(Color.LIGHT_GRAY);
		btnNewButton_3.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewButton_3.setBounds(10, 256, 100, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Delete");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "DELETE from TABLE_NAME2 where EID = ?";
				try {
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setInt(1 , Integer.parseInt(tf1.getText()));
					int n = JOptionPane.showConfirmDialog(null , "Are you sure?");
					if(n == JOptionPane.YES_OPTION) {
						ps.execute();
						tf1.setText(null);
						JOptionPane.showMessageDialog(null , "Data successfully deleted");
						comboBox.removeAllItems();
						comboBoxFill();
						refreshTable();
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null , "Unable to delete data");
					e.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBackground(Color.LIGHT_GRAY);
		btnNewButton_4.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNewButton_4.setBounds(126, 256, 100, 23);
		contentPane.add(btnNewButton_4);
		
		tf5 = new JTextField();
		tf5.setText(" ");
		tf5.setBackground(Color.CYAN);
		tf5.setBounds(76, 215, 150, 30);
		contentPane.add(tf5);
		tf5.setColumns(10);
		
		tf4 = new JTextField();
		tf4.setText(" ");
		tf4.setBackground(Color.CYAN);
		tf4.setBounds(76, 174, 150, 30);
		contentPane.add(tf4);
		tf4.setColumns(10);
		
		tf3 = new JTextField();
		tf3.setText(" ");
		tf3.setBackground(Color.CYAN);
		tf3.setBounds(76, 133, 150, 30);
		contentPane.add(tf3);
		tf3.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setText(" ");
		tf2.setBackground(Color.CYAN);
		tf2.setBounds(76, 92, 150, 30);
		contentPane.add(tf2);
		tf2.setColumns(10);
		
		tf1 = new JTextField();
		tf1.setText(" ");
		tf1.setBackground(Color.CYAN);
		tf1.setBounds(76, 51, 150, 30);
		contentPane.add(tf1);
		tf1.setColumns(10);
		
		JLabel lblEmpId = new JLabel("Emp. ID");
		lblEmpId.setForeground(Color.ORANGE);
		lblEmpId.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblEmpId.setBounds(20, 59, 46, 14);
		contentPane.add(lblEmpId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(Color.ORANGE);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblName.setBounds(20, 100, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setForeground(Color.ORANGE);
		lblSalary.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblSalary.setBounds(20, 141, 46, 14);
		contentPane.add(lblSalary);
		
		JLabel lblCompanyName = new JLabel("Cmp. Name");
		lblCompanyName.setForeground(Color.ORANGE);
		lblCompanyName.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblCompanyName.setBounds(20, 182, 56, 14);
		contentPane.add(lblCompanyName);
		
		JLabel lblDate = new JLabel("DoJ");
		lblDate.setForeground(Color.ORANGE);
		lblDate.setFont(new Font("Calibri", Font.PLAIN, 11));
		lblDate.setBounds(20, 223, 46, 14);
		contentPane.add(lblDate);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.ORANGE);
		lblSearch.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSearch.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSearch.setBounds(247, 13, 46, 18);
		contentPane.add(lblSearch);
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setIcon(new ImageIcon("ENTER_BACKGROUND_IMAGE_LOCATION"));
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblNewLabel.setBounds(0, 0, 668, 341);
		contentPane.add(lblNewLabel);
		
		
		comboBoxFill();
		
	}
	public JTable getTable() {
		return table;
	}
}
