package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.client.AggregateIterable;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
import static cs.stackexchange.bd.Neo4jConnector.driver;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.bd.Neo4jConnector;
import cs.stackexchange.data.Post;
import cs.stackexchange.data.User;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	private DefaultListModel<Post> model = new DefaultListModel<Post>();
	private DefaultListModel<User> model2 = new DefaultListModel<User>();
	private DefaultListModel<String> model3 = new DefaultListModel<String>();

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	static Neo4jConnector neo4j;
	Result result;
	List<Record> list;
	User u;
	String username2;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow("codd");
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public MainWindow(String username) {

		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 541);
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
		gbl_panel.rowHeights = new int[] { 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
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
		GridBagConstraints gbc_lblBack = new GridBagConstraints();
		gbc_lblBack.insets = new Insets(0, 0, 5, 5);
		gbc_lblBack.gridx = 1;
		gbc_lblBack.gridy = 7;
		lblBack.setVisible(false);
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
		
		JButton btnAdministration = new JButton("Administration");
		btnAdministration.setVisible(false);
		if(username.equals("admin")) {
			btnAdministration.setVisible(true);
		}
		btnAdministration.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminWindow aw = new AdminWindow(username);
				aw.setVisible(true);
				dispose();
				
			}
		});
		GridBagConstraints gbc_btnAdministration = new GridBagConstraints();
		gbc_btnAdministration.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdministration.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdministration.gridx = 1;
		gbc_btnAdministration.gridy = 16;
		panel.add(btnAdministration, gbc_btnAdministration);

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

		JList<Post> listPosts = getTopPosts();
		listPosts.setBounds(38, 45, 490, 380);

		JScrollPane scroll = new JScrollPane(listPosts);
		scroll.setBounds(10, 74, 492, 96);
		panel_1.add(scroll);

		JLabel lblStackExchange = new JLabel("CS StackExchange");
		lblStackExchange.setToolTipText("");
		lblStackExchange.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblStackExchange.setBounds(118, 0, 299, 50);
		panel_1.add(lblStackExchange);

		JButton btnSelectPost = new JButton("Select");
		btnSelectPost.setForeground(Color.WHITE);
		btnSelectPost.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelectPost.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSelectPost.setBackground(Color.BLACK);
		btnSelectPost.setBounds(20, 169, 101, 29);
		btnSelectPost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = listPosts.getSelectedValue().getId();
				QuestionWindow qw = new QuestionWindow(id, username);
				qw.setVisible(true);
				dispose();
			}

		});
		panel_1.add(btnSelectPost);

		JLabel lblTopQuestions = new JLabel("Top 5 questions:");
		lblTopQuestions.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTopQuestions.setBounds(10, 50, 130, 24);
		panel_1.add(lblTopQuestions);

		JLabel lblTopUsersRep = new JLabel("Top 5 users by reputation:");
		lblTopUsersRep.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTopUsersRep.setBounds(10, 205, 211, 24);
		panel_1.add(lblTopUsersRep);

		JList<User> listUsers = getUsers();
		listUsers.setBounds(0, 0, 524, 202);

		JScrollPane scrollUsers = new JScrollPane(listUsers);
		scrollUsers.setBounds(10, 227, 492, 96);
		panel_1.add(scrollUsers);

		JButton btnSelectUser = new JButton("Select");
		btnSelectUser.setForeground(Color.WHITE);
		btnSelectUser.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelectUser.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSelectUser.setBackground(Color.BLACK);
		btnSelectUser.setBounds(20, 322, 101, 29);
		btnSelectUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = listUsers.getSelectedValue().getId();
				UserProfileWindow upw = new UserProfileWindow(id, username);
				upw.setVisible(true);
				dispose();
			}

		});
		panel_1.add(btnSelectUser);
		
		JList<String> listUsers2 = getTotalScore();
		listUsers.setBounds(0, 0, 524, 202);
		
		JScrollPane scrollUsersScore = new JScrollPane(listUsers2);
		scrollUsersScore.setBounds(10, 375, 492, 96);
		panel_1.add(scrollUsersScore);
		
		JButton btnSelectUserScore = new JButton("Select");
		btnSelectUserScore.setForeground(Color.WHITE);
		btnSelectUserScore.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelectUserScore.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
						new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSelectUserScore.setBackground(Color.BLACK);
		btnSelectUserScore.setBounds(20, 471, 101, 29);
		panel_1.add(btnSelectUserScore);
		
		JLabel lblTopUsers = new JLabel("Top 5 users by total score:");
		lblTopUsers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTopUsers.setBounds(10, 352, 211, 24);
		panel_1.add(lblTopUsers);

	}

	public JList<Post> getTopPosts() {
		JList<Post> listPosts = new JList<Post>(model);
		MongoDBConnector.connect();

		Bson projection = fields(include("title", "score", "id", "postTypeId"), excludeId());
		Iterator<Document> it = MongoDBConnector.collection.find(eq("postTypeId", 1)).projection(projection)
				.sort(descending("score")).limit(10).iterator();

		while (it.hasNext()) {
			Document d = it.next();
			Post p = new Post();
			p.setPostTypeId((int) d.get("postTypeId"));
			p.setTitle((String) d.get("title"));
			p.setId((int) d.get("id"));
			p.setScore((int) d.get("score"));
			model.addElement(p);
		}

		return listPosts;
	}

	public JList<User> getUsers() {
		JList<User> jlist = new JList<User>(model2);
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				result = tx.run(
						"MATCH (n:User) RETURN n.id, n.aboutMe,n.creationDate,n.reputation,n.username ORDER BY n.reputation DESC LIMIT 5");
				list = result.list();
				Iterator<Record> iterator = list.iterator();
				while (iterator.hasNext()) {
					Record r = iterator.next();
					u = new User(r);
					model2.addElement(u);
				}
				return list;
			});
			return jlist;
		} catch (Exception e) {
			logger.log(Level.INFO, "ERROR", e);
			return null;
		}

	}
	
	public JList<String> getTotalScore() {
		JList<String> jlist = new JList<String>(model3);
		MongoDBConnector.connect();

		AggregateIterable<Document> result = MongoDBConnector.collection.aggregate(Arrays.asList(new Document("$group", 
		    new Document("_id", "$ownerUserId")
		            .append("rep", 
		    new Document("$sum", "$score"))), 
		    new Document("$sort", 
		    new Document("rep", -1L)), 
		    new Document("$limit", 5L)));
		
		Iterator<Document> it = result.iterator();

		if (!it.hasNext()) {
			System.out.println("Null");
		}

		while (it.hasNext()) {
			Document d = it.next();
			String id = d.get("_id").toString();
			String votes = d.get("rep").toString();
			String string = getUserById(id) + " | Total Votes: " + votes;
			model3.addElement(string);
		}
		return jlist;
	}
	
	private String getUserById(String id) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.id = " + id + " RETURN u.username");
				username2 = result.single().get(0).toString();
				return username2;
			});
		}
		return username2;
	}
}
