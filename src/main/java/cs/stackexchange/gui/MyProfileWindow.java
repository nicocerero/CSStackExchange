package cs.stackexchange.gui;

import static cs.stackexchange.bd.Neo4jConnector.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import cs.stackexchange.bd.Neo4jConnector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;

public class MyProfileWindow extends JFrame {

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static Neo4jConnector neo4j;

	String un;
	String un2;
	String un3;

	private JTextArea textArea;
	JButton btnUpdate;
	JButton btnSave;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MyProfileWindow frame = new MyProfileWindow("prueba");
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public MyProfileWindow(String username) {
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 493);
		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(85, 107, 47), 2));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		contentPane.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 35, 55, 40 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0 };
		panel.setLayout(gbl_panel);

		JLabel lblBack = new JLabel("Back");
		GridBagConstraints gbc_lblBack = new GridBagConstraints();
		lblBack.setForeground(Color.BLUE);
		gbc_lblBack.insets = new Insets(0, 0, 5, 0);
		gbc_lblBack.gridx = 1;
		gbc_lblBack.gridy = 0;
		lblBack.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				lblBack.setForeground(Color.BLUE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack.setForeground(Color.RED);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				MainWindow mw = new MainWindow(username);
				mw.setVisible(true);
				dispose();

			}
		});
		panel.add(lblBack, gbc_lblBack);

		JLabel lblNewLabel = new JLabel("MENU");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnHome = new JButton("Home");
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHome.insets = new Insets(0, 0, 5, 0);
		gbc_btnHome.gridx = 1;
		gbc_btnHome.gridy = 2;
		btnHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mw = new MainWindow(username);
				mw.setVisible(true);
				dispose();

			}
		});
		panel.add(btnHome, gbc_btnHome);

		JButton btnNewButton = new JButton("My Profile");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		panel.add(btnNewButton, gbc_btnNewButton);

		JLabel lblLogout = new JLabel("Logout");
		lblLogout.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblLogout = new GridBagConstraints();
		gbc_lblLogout.insets = new Insets(0, 0, 5, 0);
		gbc_lblLogout.gridx = 1;
		gbc_lblLogout.gridy = 4;
		panel.add(lblLogout, gbc_lblLogout);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel lblUsername = new JLabel(username);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(67, 39, 166, 34);
		panel_1.add(lblUsername);

		read(lblUsername.getText().toString());

		textArea = new JTextArea();
		if (un == "" | un.equals("null")) {
			textArea.setText("Click on 'Update AboutMe' to update your description");
			textArea.setEditable(false);
		} else {
			textArea.setText(un);
			textArea.setEditable(false);
		}
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea.setBounds(45, 127, 481, 181);
		panel_1.add(textArea);

		JLabel lblReputation = new JLabel("Reputation: " + un2);
		lblReputation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReputation.setBounds(260, 39, 166, 34);
		panel_1.add(lblReputation);

		JLabel lblDate = new JLabel("Creation Date: " + un3);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDate.setBounds(67, 83, 459, 34);
		panel_1.add(lblDate);

		btnSave = new JButton("Save");
		btnSave.setForeground(Color.WHITE);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSave.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSave.setBackground(Color.BLACK);
		btnSave.setBounds(393, 318, 133, 33);
		btnSave.setVisible(false);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update(lblUsername.getText().toString());
				btnSave.setVisible(false);
				textArea.setEditable(false);
				btnUpdate.setVisible(true);
			}

		});
		panel_1.add(btnSave);

		btnUpdate = new JButton("Update AboutMe");
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(393, 318, 133, 33);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("Tell us something about you...");
				textArea.setEditable(true);
				btnUpdate.setVisible(false);
				btnSave.setVisible(true);
			}

		});
		panel_1.add(btnUpdate);

		JButton btnMyPosts = new JButton("My posts");
		btnMyPosts.setForeground(Color.WHITE);
		btnMyPosts.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnMyPosts.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnMyPosts.setBackground(Color.BLACK);
		btnMyPosts.setBounds(45, 384, 126, 34);
		btnMyPosts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyPostsWindow mpw = new MyPostsWindow(username);
				mpw.setVisible(true);
				dispose();

			}
		});
		panel_1.add(btnMyPosts);

		JButton btnDeleteAccout = new JButton("Delete Accout");
		btnDeleteAccout.setForeground(Color.WHITE);
		btnDeleteAccout.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteAccout.setBorder(
				new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDeleteAccout.setBackground(Color.RED);
		btnDeleteAccout.setBounds(393, 384, 133, 34);
		btnDeleteAccout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int sel = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?",
						"Confimation", JOptionPane.YES_NO_OPTION);

				if (sel == 0) {
					try {
						delete(username);
						LoginWindow lw = new LoginWindow();
						lw.setVisible(true);
						dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		panel_1.add(btnDeleteAccout);

	}

	public String read(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.aboutMe");
				Result result2 = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.reputation");
				Result result3 = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.creationDate");
				un = result.single().get(0).asString();
				un2 = result2.single().get(0).toString().replaceAll("\"", "");
				un3 = result3.single().get(0).toString().split("T")[0].replace("\"", "");
				return un;
			});
		}
		return un;
	}

	public String update(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.writeTransaction(tx -> {
				Result update = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' " + "SET u.aboutMe = '"
						+ textArea.getText() + "' RETURN u.aboutMe");
				un = update.single().get(0).asString();
				return un;
			});
			JOptionPane.showMessageDialog(this, "Description updated successfully", "Confirmation",
					JOptionPane.INFORMATION_MESSAGE);
			return un;
		}
	}

	public void delete(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> {
				tx.run("MATCH (u:User) WHERE u.username = '" + username + "' DETACH DELETE u");
				return null;
			});

		}
	}
}
