package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JButton;

public class AdminWindow extends JFrame{
	
	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					AdminWindow frame = new AdminWindow("admin");
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});

	}
	
	public AdminWindow(String username) {
		
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 493);
		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(85, 107, 47), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdministration = new JLabel("ADMINISTRATION");
		lblAdministration.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministration.setFont(new Font("Dialog", Font.BOLD, 33));
		lblAdministration.setBounds(166, 51, 338, 54);
		contentPane.add(lblAdministration);
		
		JButton btnAdministrateUsers = new JButton("Administrate Users");
		btnAdministrateUsers.setForeground(Color.WHITE);
		btnAdministrateUsers.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAdministrateUsers.setBackground(new Color(47, 79, 79));
		btnAdministrateUsers.setBounds(213, 151, 250, 50);
		btnAdministrateUsers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminUsersWindow auw = new AdminUsersWindow();
				auw.setVisible(true);
				dispose();
				
			}
		});
		contentPane.add(btnAdministrateUsers);
		
		JLabel lblMainWindow = new JLabel("Back to Main");
		lblMainWindow.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMainWindow.setForeground(Color.BLUE);
		lblMainWindow.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainWindow.setBounds(39, 32, 90, 16);
		lblMainWindow.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblMainWindow.setForeground(Color.BLUE);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lblMainWindow.setForeground(Color.RED);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				MainWindow mw = new MainWindow(username);
				mw.setVisible(true);
				dispose();
				
			}
		});
		contentPane.add(lblMainWindow);
		
		JButton btnAdministratePost = new JButton("Administrate Post");
		btnAdministratePost.setForeground(Color.WHITE);
		btnAdministratePost.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAdministratePost.setBackground(new Color(47, 79, 79));
		btnAdministratePost.setBounds(213, 241, 250, 50);
		btnAdministratePost.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminPostWindow apw = new AdminPostWindow();
				apw.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnAdministratePost);
		
		JButton btnStatistics = new JButton("Statistics");
		btnStatistics.setForeground(Color.WHITE);
		btnStatistics.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnStatistics.setBackground(new Color(47, 79, 79));
		btnStatistics.setBounds(213, 334, 250, 50);
		btnStatistics.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminStatisticsWindow asw = new AdminStatisticsWindow();
				asw.setVisible(true);
				dispose();
			}
			
		});
		contentPane.add(btnStatistics);
	}
}
