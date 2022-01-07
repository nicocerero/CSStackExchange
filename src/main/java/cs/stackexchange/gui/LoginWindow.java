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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LoginWindow extends JFrame{
	
	private JPanel contentPane;
	private JTextField txtUsername1;
	private JPasswordField txtPassword1;
	private JLabel lblLoginMessage1 = new JLabel("");
	
	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * 
	 */
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
		
		JLabel lblCineDeusto = new JLabel("CS StackExchange");
		lblCineDeusto.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblCineDeusto.setToolTipText("");
		lblCineDeusto.setBounds(200, 148, 299, 40);
		contentPane.add(lblCineDeusto);

		JPanel UserBox = new JPanel();
		UserBox.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		UserBox.setBackground(Color.WHITE);
		UserBox.setBounds(216, 219, 250, 40);
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
		txtUsername1.setText("User");
		txtUsername1.setBounds(10, 10, 170, 20);
		UserBox.add(txtUsername1);
		txtUsername1.setColumns(10);

		JPanel PassBox = new JPanel();
		PassBox.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		PassBox.setBackground(Color.WHITE);
		PassBox.setBounds(216, 269, 250, 40);
		contentPane.add(PassBox);
		PassBox.setLayout(null);

		txtPassword1 = new JPasswordField();
		txtPassword1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPassword1.getText().equals("Password")) {
					txtPassword1.setText("");
					txtPassword1.setEchoChar('\u25CF');
					
				} else {
					txtPassword1.selectAll();
					txtPassword1.setEchoChar('\u25CF');
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtPassword1.getText().equals("")) {
					txtPassword1.setText("Password");
					txtPassword1.setEchoChar((char) 0);
				}
			}
		});
		txtPassword1.setBorder(null);
		txtPassword1.setEchoChar((char) 0);
		txtPassword1.setFont(new Font("Arial", Font.BOLD, 14));
		txtPassword1.setText("Password");
		txtPassword1.setBounds(10, 10, 170, 20);
		PassBox.add(txtPassword1);
		
		lblLoginMessage1.setForeground(new Color(128, 0, 0));
		lblLoginMessage1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLoginMessage1.setBounds(173, 263, 250, 19);
		contentPane.add(lblLoginMessage1);
		
		JLabel ImagePanel = new JLabel();
		ImagePanel.setIcon(new ImageIcon(getClass().getResource("images/logo.png")));
		ImagePanel.setBounds(265, 10, 158, 128);
		contentPane.add(ImagePanel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLACK);
		loginButton.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(new Color(0, 0, 0), 2, true)));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow mw = new MainWindow();
				mw.setVisible(true);
				dispose();
			}
			
		});
		loginButton.setBounds(288, 330, 101, 29);
		contentPane.add(loginButton);
	}
}
