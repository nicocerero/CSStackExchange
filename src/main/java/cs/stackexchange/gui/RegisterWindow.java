package cs.stackexchange.gui;

import static cs.stackexchange.bd.Neo4jConnector.driver;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.neo4j.driver.Session;

import cs.stackexchange.bd.Neo4jConnector;

import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;

public class RegisterWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JPasswordField pwdConfirmPassword;
	
	static Neo4jConnector neo4j;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					RegisterWindow frame = new RegisterWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});

	}

	public RegisterWindow() throws IOException {
		
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
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setToolTipText("");
		lblRegister.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblRegister.setBounds(276, 148, 125, 40);
		contentPane.add(lblRegister);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCancel.setBackground(Color.RED);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginWindow cl;
				try {
					cl = new LoginWindow();
					cl.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		btnCancel.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnCancel.setBounds(214, 375, 108, 33);
		getContentPane().add(btnCancel);

		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRegister.setBackground(Color.BLACK);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Register(txtUsername.getText().toString());
					MainWindow mw = new MainWindow();
					mw.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				dispose();
			}

		});
		btnRegister.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		btnRegister.setBounds(356, 375, 108, 33);
		getContentPane().add(btnRegister);
		
		JPanel UsernameBox = new JPanel();
		UsernameBox.setLayout(null);
		UsernameBox.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		UsernameBox.setBackground(Color.WHITE);
		UsernameBox.setBounds(214, 198, 250, 40);
		contentPane.add(UsernameBox);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setFont(new Font("Arial", Font.BOLD, 14));
		txtUsername.setColumns(10);
		txtUsername.setBorder(null);
		txtUsername.setBounds(10, 10, 170, 20);
		UsernameBox.add(txtUsername);
		
		JPanel PassBox = new JPanel();
		PassBox.setLayout(null);
		PassBox.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		PassBox.setBackground(Color.WHITE);
		PassBox.setBounds(214, 248, 250, 40);
		contentPane.add(PassBox);
		
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (passwordField.getText().equals("Password")) {
					passwordField.setText("");
					passwordField.setEchoChar('\u25CF');
					
				} else {
					passwordField.selectAll();
					passwordField.setEchoChar('\u25CF');
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (passwordField.getText().equals("")) {
					passwordField.setText("Password");
					passwordField.setEchoChar((char) 0);
				}
			}
		});
		passwordField.setText("Password");
		passwordField.setFont(new Font("Arial", Font.BOLD, 14));
		passwordField.setEchoChar(' ');
		passwordField.setBorder(null);
		passwordField.setBounds(10, 10, 170, 20);
		PassBox.add(passwordField);
		
		JPanel ConfirmPassBox = new JPanel();
		ConfirmPassBox.setLayout(null);
		ConfirmPassBox.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		ConfirmPassBox.setBackground(Color.WHITE);
		ConfirmPassBox.setBounds(214, 298, 250, 40);
		contentPane.add(ConfirmPassBox);
		
		pwdConfirmPassword = new JPasswordField();
		pwdConfirmPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (pwdConfirmPassword.getText().equals("Password")) {
					pwdConfirmPassword.setText("");
					pwdConfirmPassword.setEchoChar('\u25CF');
					
				} else {
					pwdConfirmPassword.selectAll();
					pwdConfirmPassword.setEchoChar('\u25CF');
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (pwdConfirmPassword.getText().equals("")) {
					pwdConfirmPassword.setText("Password");
					pwdConfirmPassword.setEchoChar((char) 0);
				}
			}
		});
		pwdConfirmPassword.setText("Confirm password");
		pwdConfirmPassword.setFont(new Font("Arial", Font.BOLD, 14));
		pwdConfirmPassword.setEchoChar(' ');
		pwdConfirmPassword.setBorder(null);
		pwdConfirmPassword.setBounds(10, 10, 170, 20);
		ConfirmPassBox.add(pwdConfirmPassword);

	}
	
	public static void Register(String username) throws Exception {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		Session session = driver.session();
		session.run("CREATE (user:User{username:'"+username+"'})");
		System.out.println("User added succesfully");
		
		/*try () {
			session.writeTransaction(tx -> {
				Result result = tx.run("CREATE (user:User{username:'"+username+"'})"+" RETURN user,{username: nico");
				return result.single().get(0).asString();
			});
			System.out.println("Añadido correctamente");
			neo4j.close();
		}
		MongoDBConnector.connect();
		Document doc = new Document();
		doc.append("DisplayName", username);
		MongoDBConnector.collection.insertOne(doc);*/
	}
}
