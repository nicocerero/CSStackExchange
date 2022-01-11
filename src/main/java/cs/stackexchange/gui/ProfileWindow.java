package cs.stackexchange.gui;

import static cs.stackexchange.bd.Neo4jConnector.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
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

import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;

public class ProfileWindow extends JFrame {

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static Neo4jConnector neo4j;
	static Neo4jConnector neo4j_2;
	

	Result result;
	Result result2;
	Result result3;
	Result update;
	String un;
	String un2;
	String un3;
	public String un4;
	
	private JTextArea textArea;
	JButton btnUpdate;
	JButton btnConfirm;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ProfileWindow frame = new ProfileWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public ProfileWindow() {
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
		panel.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		contentPane.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 35, 55, 40 };
		gbl_panel.rowHeights = new int[] { 35, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("MENU");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnNewButton_1 = new JButton("Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mw = new MainWindow();
				mw.setVisible(true);
				dispose();
				
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 2;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);

		JButton btnNewButton = new JButton("Profile");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel lblUsername = new JLabel(getProp().toString());
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(67, 64, 166, 34);
		panel_1.add(lblUsername);
		
		read(lblUsername.getText().toString());
		
		textArea = new JTextArea();
		if(un == "") {
			textArea.setText("Click on 'Update AboutMe' to update your description");
			textArea.setEditable(false);
		}else {
			textArea.setText(un);
			textArea.setEditable(false);
		}
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea.setBounds(67, 153, 459, 181);
		panel_1.add(textArea);
		
		JLabel lblReputation = new JLabel("Reputation: " + un2);
		lblReputation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReputation.setBounds(261, 64, 166, 34);
		panel_1.add(lblReputation);
		
		JLabel lblReputation_1 = new JLabel("Creation Date: " + un3);
		lblReputation_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReputation_1.setBounds(67, 108, 459, 34);
		panel_1.add(lblReputation_1);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnConfirm.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		btnConfirm.setBackground(Color.BLACK);
		btnConfirm.setBounds(371, 344, 133, 33);
		btnConfirm.setVisible(false);
		btnConfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				update(lblUsername.getText().toString());
				btnConfirm.setVisible(false);
				textArea.setEditable(false);
				btnUpdate.setVisible(true);
			}
			
		});
		panel_1.add(btnConfirm);
		
		btnUpdate = new JButton("Update AboutMe");
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(371, 344, 133, 33);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("Tell us something about you...");
				textArea.setEditable(true);
				btnUpdate.setVisible(false);
				btnConfirm.setVisible(true);
			}
			
		});
		panel_1.add(btnUpdate);
		
		
	}

	public static String getProp() {
		File archivo = new File("resources/username");
		try {
			FileInputStream fis = new FileInputStream(archivo);
			Properties propConfig = new Properties();
			propConfig.load(fis);
			String nombre = propConfig.getProperty("user");
			return nombre;
		} catch (IOException e) {
			logger.log(Level.WARNING, "ERROR", e);
			e.printStackTrace();
			return null;
		}
	}

	public String read(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		
			try (Session session = driver.session()) {
				session.readTransaction(tx -> {
					result = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.aboutMe");
					result2= tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.reputation");
					result3= tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.creationDate");
					un = result.single().get(0).asString();
					un2 = result2.single().get(0).toString();
					un3 = result3.single().get(0).toString();
					return un;
				});
			}
		return un;	
	}
	
	public String update(String username) {
		neo4j_2 = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> {
				update = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' " + "SET u.aboutMe = '" + textArea.getText() +"' RETURN u.aboutMe");
				un = update.single().get(0).asString();
				return un;
			});
		JOptionPane.showMessageDialog(this,"Description updated successfully","Confirmation",JOptionPane.INFORMATION_MESSAGE);
		return un;
		}
	}
}
