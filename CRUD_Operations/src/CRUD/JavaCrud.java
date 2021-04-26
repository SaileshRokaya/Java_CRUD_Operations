package CRUD;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtbookId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}

	// JDBC Connection methods
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/crudjava", "root", "root");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	// Load the date in JTable

	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1146, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(530, 10, 170, 77);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(30, 77, 446, 236);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setBounds(34, 43, 163, 30);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(34, 103, 163, 30);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(34, 164, 163, 30);
		panel.add(lblNewLabel_1_2);

		JTextField txtbookname = new JTextField();
		txtbookname.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtbookname.setBounds(194, 43, 195, 29);
		panel.add(txtbookname);
		txtbookname.setColumns(10);

		txtEdition = new JTextField();
		txtEdition.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtEdition.setColumns(10);
		txtEdition.setBounds(194, 104, 195, 29);
		panel.add(txtEdition);

		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPrice.setColumns(10);
		txtPrice.setBounds(194, 165, 195, 29);
		panel.add(txtPrice);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(30, 351, 446, 87);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		// Save query.
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String bname, edition, price;
				bname = txtbookname.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();

				try {
					pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					// load the table
					table_load();

					txtbookname.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtbookname.requestFocus();
				}

				catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnSave.setFont(new Font("Times New Roman", Font.BOLD, 25));
		btnSave.setBounds(32, 20, 101, 43);
		panel_1.add(btnSave);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Times New Roman", Font.BOLD, 25));
		btnExit.setBounds(180, 20, 101, 43);
		panel_1.add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Times New Roman", Font.BOLD, 25));
		btnClear.setBounds(318, 20, 101, 43);
		panel_1.add(btnClear);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(515, 86, 594, 352);
		frame.getContentPane().add(scrollPane);
		table = new JTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(30, 448, 446, 87);
		frame.getContentPane().add(panel_1_1);

		JButton btnBookId = new JButton("Book Id");
		btnBookId.setFont(new Font("Times New Roman", Font.BOLD, 25));
		btnBookId.setBounds(32, 20, 125, 43);
		panel_1_1.add(btnBookId);

		txtbookId = new JTextField();
		txtbookId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				// For Search Btn
				try {

					String id = txtbookId.getText();

					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {

						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);

						txtbookname.setText(name);
						txtEdition.setText(edition);
						txtPrice.setText(price);

					} else {
						txtbookname.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
					}

				}

				catch (SQLException ex) {

				}
			}
		});

		txtbookId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtbookId.setColumns(10);
		txtbookId.setBounds(183, 19, 220, 45);
		panel_1_1.add(txtbookId);

		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(515, 448, 320, 87);
		frame.getContentPane().add(panel_1_2);
		panel_1_2.setLayout(null);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {

			// Update Query
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price, bid;

				bname = txtbookname.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				bid = txtbookId.getText();

				try {
					pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Update!!!!!");
					table_load();

					txtbookname.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtbookname.requestFocus();
				}

				catch (SQLException e1) {

					e1.printStackTrace();
				}

			}

		});

		btnUpdate.setBounds(32, 20, 118, 43);
		btnUpdate.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel_1_2.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			// Delete query
			public void actionPerformed(ActionEvent e) {
				String bid;
				bid = txtbookId.getText();

				try {
					pst = con.prepareStatement("delete from book where id =?");

					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
					table_load();

					txtbookname.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtbookname.requestFocus();
				}

				catch (SQLException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnDelete.setBounds(180, 20, 101, 43);
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel_1_2.add(btnDelete);
	}
}
