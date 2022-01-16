package cs.stackexchange.gui;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import org.bson.Document;
import org.bson.conversions.Bson;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.data.Post;
import java.awt.Font;

public class MyPostsWindow extends JFrame{
	
	private DefaultListModel<Post> model = new DefaultListModel<Post>();
	private DefaultListModel<Post> model2 = new DefaultListModel<Post>();
	
	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MyPostsWindow frame = new MyPostsWindow("prueba");
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}
	
	public MyPostsWindow(String username) {
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
		
		JList<Post> listQ = getQuestions();
		listQ.setBounds(38, 45, 490, 380);

		JScrollPane scrollQ = new JScrollPane(listQ);
		scrollQ.setBounds(10, 45, 516, 109);
		panel_1.add(scrollQ);
		
		JList<Post> listA = getAnswers();
		listA.setBounds(0, 0, 524, 202);

		JScrollPane scrollA = new JScrollPane(listA);
		scrollA.setBounds(10, 201, 516, 109);
		panel_1.add(scrollA);
		
		JLabel lblMyQuestions = new JLabel("My questions:");
		lblMyQuestions.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMyQuestions.setBounds(10, 22, 130, 24);
		panel_1.add(lblMyQuestions);
		
		JLabel lblMyAnswers = new JLabel("My answers:");
		lblMyAnswers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMyAnswers.setBounds(10, 177, 130, 24);
		panel_1.add(lblMyAnswers);
		
		JButton btnDeleteAnswer = new JButton("Delete Answer");
		btnDeleteAnswer.setForeground(Color.WHITE);
		btnDeleteAnswer.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteAnswer.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDeleteAnswer.setBackground(Color.RED);
		btnDeleteAnswer.setBounds(387, 309, 139, 33);
		panel_1.add(btnDeleteAnswer);
		
		JButton btnDeleteQuestion = new JButton("Delete Question");
		btnDeleteQuestion.setForeground(Color.WHITE);
		btnDeleteQuestion.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteQuestion.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDeleteQuestion.setBackground(Color.RED);
		btnDeleteQuestion.setBounds(387, 153, 139, 33);
		panel_1.add(btnDeleteQuestion);
		
	}
	
	public JList<Post> getQuestions() {
		JList<Post> listPosts = new JList<Post>(model);
		MongoDBConnector.connect();

		Bson projection = fields(include("title", "score", "id"), excludeId());
		Iterator<Document> it = MongoDBConnector.collection.find(eq("postTypeId", 1)).projection(projection).iterator();

		while (it.hasNext()) {
			Document d = it.next();
			Post p = new Post();
			p.setTitle((String) d.get("title"));
			p.setId((int) d.get("id"));
			p.setScore((int) d.get("score"));
			model.addElement(p);
		}

		return listPosts;
	}
	
	public JList<Post> getAnswers() {
		JList<Post> listAnswers = new JList<Post>(model2);
		MongoDBConnector.connect();

		Bson projection = fields(include("body", "score", "id"), excludeId());
		Iterator<Document> it = MongoDBConnector.collection.find(eq("postTypeId", 2)).projection(projection).iterator();

		while (it.hasNext()) {
			Document d = it.next();
			Post p = new Post();
			p.setBody((String)d.get("body"));
			p.setId((int) d.get("id"));
			p.setScore((int) d.get("score"));
			model2.addElement(p);
		}

		return listAnswers;
	}

}
