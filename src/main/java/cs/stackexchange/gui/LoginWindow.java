package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import cs.stackexchange.bd.Neo4jConnector;

import static cs.stackexchange.bd.Neo4jConnector.driver;

import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername1;
	private JLabel lblLoginMessage1 = new JLabel("");

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static Neo4jConnector neo4j;

	Result result;
	String un;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});

	}

	public LoginWindow() throws IOException {
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 493);
		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(85, 107, 47), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel ImagePanel = new JLabel();
		ImagePanel.setIcon(new ImageIcon(getClass().getResource("images/logo.png")));
		ImagePanel.setBounds(261, 10, 158, 128);
		contentPane.add(ImagePanel);

		JLabel lblStackExchange = new JLabel("CS StackExchange");
		lblStackExchange.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblStackExchange.setToolTipText("");
		lblStackExchange.setBounds(200, 148, 299, 40);
		contentPane.add(lblStackExchange);

		JPanel UserBox = new JPanel();
		UserBox.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		UserBox.setBackground(Color.WHITE);
		UserBox.setBounds(216, 227, 250, 40);
		contentPane.add(UserBox);
		UserBox.setLayout(null);

		txtUsername1 = new JTextField();
		txtUsername1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsername1.getText().equals("Username")) {
					txtUsername1.setText("");
				} else {
					txtUsername1.selectAll();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtUsername1.getText().equals(""))
					txtUsername1.setText("Username");
			}
		});
		txtUsername1.setBorder(null);
		txtUsername1.setFont(new Font("Arial", Font.BOLD, 14));
		txtUsername1.setText("Username");
		txtUsername1.setBounds(10, 10, 170, 20);
		UserBox.add(txtUsername1);
		txtUsername1.setColumns(10);

		lblLoginMessage1.setForeground(new Color(128, 0, 0));
		lblLoginMessage1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLoginMessage1.setBounds(216, 198, 250, 19);
		contentPane.add(lblLoginMessage1);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLACK);
		loginButton.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (checkUser(txtUsername1.getText()).equals(un)) {
					MainWindow mw = new MainWindow(un);
					mw.setVisible(true);
					dispose();
				}
			}

		});
		loginButton.setBounds(216, 315, 101, 29);
		contentPane.add(loginButton);

		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		registerButton.setBackground(Color.BLACK);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterWindow rw;
				try {
					rw = new RegisterWindow();
					rw.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}

		});
		registerButton.setBounds(365, 315, 101, 29);
		contentPane.add(registerButton);
	}

	public String checkUser(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				result = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.username");
				un = result.single().get(0).asString();
				return un;
			});
			return un;
		} catch (Exception e) {
			logger.log(Level.INFO, "ERROR", e);
			lblLoginMessage1.setText("User not found!");
			return null;
		}

	}
}
