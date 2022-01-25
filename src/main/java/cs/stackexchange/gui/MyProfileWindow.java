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
import javax.swing.JTextField;
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
		gbl_panel.rowHeights = new int[] { 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblUser = new JLabel("User: " + username);
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 1;
		gbc_lblUser.gridy = 0;
		panel.add(lblUser, gbc_lblUser);

		JButton btnProfile = new JButton("My Profile");
		GridBagConstraints gbc_btnProfile = new GridBagConstraints();
		gbc_btnProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProfile.insets = new Insets(0, 0, 5, 5);
		gbc_btnProfile.gridx = 1;
		gbc_btnProfile.gridy = 5;
		btnProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyProfileWindow pw = new MyProfileWindow(username);
				pw.setVisible(true);
				dispose();
			}
		});

		JLabel lblLogout = new JLabel("Logout");
		lblLogout.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblLogout = new GridBagConstraints();
		gbc_lblLogout.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogout.gridx = 1;
		gbc_lblLogout.gridy = 1;
		lblLogout.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				lblLogout.setForeground(Color.BLUE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblLogout.setForeground(Color.RED);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					LoginWindow lw = new LoginWindow();
					lw.setVisible(true);
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		panel.add(lblLogout, gbc_lblLogout);

		JLabel lblMenu = new JLabel("MENU");
		GridBagConstraints gbc_lblMenu = new GridBagConstraints();
		gbc_lblMenu.insets = new Insets(0, 0, 5, 5);
		gbc_lblMenu.gridx = 1;
		gbc_lblMenu.gridy = 3;
		panel.add(lblMenu, gbc_lblMenu);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mw = new MainWindow(username);
				mw.setVisible(true);
				dispose();

			}
		});
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHome.insets = new Insets(0, 0, 5, 5);
		gbc_btnHome.gridx = 1;
		gbc_btnHome.gridy = 4;
		panel.add(btnHome, gbc_btnHome);
		panel.add(btnProfile, gbc_btnProfile);

		JButton btnNewQuestion = new JButton("New Question");
		GridBagConstraints gbc_btnNewQuestion = new GridBagConstraints();
		gbc_btnNewQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewQuestion.gridx = 1;
		gbc_btnNewQuestion.gridy = 6;
		btnNewQuestion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NewQuestionWindow nqw = new NewQuestionWindow(username);
				nqw.setVisible(true);
				dispose();

			}
		});
		panel.add(btnNewQuestion, gbc_btnNewQuestion);

		JLabel lblBack = new JLabel("Back");
		lblBack.setForeground(Color.BLUE);
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
		GridBagConstraints gbc_lblBack = new GridBagConstraints();
		gbc_lblBack.insets = new Insets(0, 0, 5, 5);
		gbc_lblBack.gridx = 1;
		gbc_lblBack.gridy = 7;
		panel.add(lblBack, gbc_lblBack);

		JLabel lblSpace = new JLabel("aaaaaaaaaaaaaaaaa");
		lblSpace.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSpace = new GridBagConstraints();
		gbc_lblSpace.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpace.gridx = 1;
		gbc_lblSpace.gridy = 8;
		panel.add(lblSpace, gbc_lblSpace);

		JLabel lblSearch = new JLabel("SEARCH");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch.gridx = 1;
		gbc_lblSearch.gridy = 9;
		panel.add(lblSearch, gbc_lblSearch);

		JTextField textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 10;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 11;
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField.getText().isEmpty() == true || textField.getText().isBlank() == true) {
						JOptionPane.showMessageDialog(null, "Text is empty");
					} else {
						SearchWindow sw = new SearchWindow(username, textField.getText().toString());
						sw.setVisible(true);
						dispose();
					}

				} catch (NullPointerException n) {
					JOptionPane.showMessageDialog(null, "Text not found: " + n);
				}

			}
		});
		panel.add(btnSearch, gbc_btnSearch);

		JLabel lblSpace2 = new JLabel("aaaaaaaaaaaaaaaaa");
		lblSpace2.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSpace2 = new GridBagConstraints();
		gbc_lblSpace2.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpace2.gridx = 1;
		gbc_lblSpace2.gridy = 12;
		panel.add(lblSpace2, gbc_lblSpace2);

		/*
		 * JLabel lblSearchTag = new JLabel("SEARCH BY TAG"); GridBagConstraints
		 * gbc_lblSearchTag = new GridBagConstraints(); gbc_lblSearchTag.insets = new
		 * Insets(0, 0, 5, 5); gbc_lblSearchTag.gridx = 1; gbc_lblSearchTag.gridy = 13;
		 * panel.add(lblSearchTag, gbc_lblSearchTag);
		 * 
		 * JComboBox<Tag> comboBox = new JComboBox<Tag>(); GridBagConstraints
		 * gbc_comboBox = new GridBagConstraints(); gbc_comboBox.insets = new Insets(0,
		 * 0, 5, 5); gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		 * gbc_comboBox.gridx = 1; gbc_comboBox.gridy = 14; panel.add(comboBox,
		 * gbc_comboBox);
		 */

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
