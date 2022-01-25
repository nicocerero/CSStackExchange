package cs.stackexchange.gui;

import static com.mongodb.client.model.Projections.*;
import static cs.stackexchange.bd.Neo4jConnector.driver;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.bd.Neo4jConnector;
import cs.stackexchange.data.Comment;
import cs.stackexchange.data.Post;
import java.awt.Font;

public class MyPostsWindow extends JFrame {

	private DefaultListModel<Post> model = new DefaultListModel<Post>();
	private DefaultListModel<Post> model2 = new DefaultListModel<Post>();
	private DefaultListModel<Comment> model3 = new DefaultListModel<Comment>();

	private JPanel contentPane;

	static Neo4jConnector neo4j;

	int id = 0;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MyPostsWindow frame = new MyPostsWindow("codd");
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
				MyProfileWindow mpw = new MyProfileWindow(username);
				mpw.setVisible(true);
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

		JList<Post> listQ = getQuestions(getId(username));

		JScrollPane scrollQ = new JScrollPane(listQ);
		scrollQ.setBounds(10, 34, 516, 99);
		panel_1.add(scrollQ);

		JList<Post> listA = getAnswers(getId(username));

		JScrollPane scrollA = new JScrollPane(listA);
		scrollA.setBounds(10, 174, 516, 99);
		panel_1.add(scrollA);

		JList<Comment> listC = getComments(getId(username));

		JScrollPane scrollC = new JScrollPane(listC);
		scrollC.setBounds(10, 316, 516, 99);
		panel_1.add(scrollC);

		JLabel lblMyQuestions = new JLabel("My questions:");
		lblMyQuestions.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMyQuestions.setBounds(10, 10, 130, 24);
		panel_1.add(lblMyQuestions);

		JLabel lblMyAnswers = new JLabel("My answers:");
		lblMyAnswers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMyAnswers.setBounds(10, 152, 130, 24);
		panel_1.add(lblMyAnswers);

		JButton btnDeleteAnswer = new JButton("Delete Answer");
		btnDeleteAnswer.setForeground(Color.WHITE);
		btnDeleteAnswer.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteAnswer.setBorder(
				new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDeleteAnswer.setBackground(Color.RED);
		btnDeleteAnswer.setBounds(387, 270, 139, 33);
		btnDeleteAnswer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deletePost(listA);
				model2.remove(listA.getSelectedIndex());
				JOptionPane.showMessageDialog(null, "Answer deleted succesfully");

			}
		});
		panel_1.add(btnDeleteAnswer);

		JButton btnDeleteQuestion = new JButton("Delete Question");
		btnDeleteQuestion.setForeground(Color.WHITE);
		btnDeleteQuestion.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteQuestion.setBorder(
				new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDeleteQuestion.setBackground(Color.RED);
		btnDeleteQuestion.setBounds(387, 130, 139, 33);
		btnDeleteQuestion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deletePost(listQ);
				model.remove(listQ.getSelectedIndex());
				JOptionPane.showMessageDialog(null, "Question deleted succesfully");
			}
		});
		panel_1.add(btnDeleteQuestion);

		JLabel lblMyComments = new JLabel("My comments:");
		lblMyComments.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMyComments.setBounds(10, 294, 130, 24);
		panel_1.add(lblMyComments);

		JButton btnDeleteComment = new JButton("Delete Comment");
		btnDeleteComment.setForeground(Color.WHITE);
		btnDeleteComment.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteComment.setBorder(
				new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDeleteComment.setBackground(Color.RED);
		btnDeleteComment.setBounds(387, 409, 139, 33);
		btnDeleteComment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteComment(listC);
				model3.remove(listC.getSelectedIndex());
				JOptionPane.showMessageDialog(null, "Comment deleted succesfully");

			}
		});
		panel_1.add(btnDeleteComment);

	}

	public int getId(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.id");
				id = Integer.parseInt(result.single().get(0).asString());
				return id;
			});
		}
		return id;
	}

	public JList<Post> getQuestions(int id) {
		JList<Post> listPosts = new JList<Post>(model);
		MongoDBConnector.connect();

		Bson projection = fields(include("title", "score", "id", "postTypeId"), excludeId());
		Bson typeFilter = Filters.eq("postTypeId", 1);
		Bson userFilter = Filters.eq("ownerUserId", id);

		Iterator<Document> it = MongoDBConnector.collection.find(Filters.and(typeFilter, userFilter))
				.projection(projection).iterator();

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

	public JList<Post> getAnswers(int id) {
		JList<Post> listAnswers = new JList<Post>(model2);
		MongoDBConnector.connect();

		Bson projection = fields(include("body", "score", "id", "postTypeId"), excludeId());
		Bson typeFilter = Filters.eq("postTypeId", 2);
		Bson userFilter = Filters.eq("ownerUserId", id);

		Iterator<Document> it = MongoDBConnector.collection.find(Filters.and(typeFilter, userFilter))
				.projection(projection).iterator();

		while (it.hasNext()) {
			Document d = it.next();
			Post p = new Post();
			p.setPostTypeId((int) d.get("postTypeId"));
			p.setBody((String) d.get("body"));
			p.setId((int) d.get("id"));
			p.setScore((int) d.get("score"));
			model2.addElement(p);
		}

		return listAnswers;
	}

	public JList<Comment> getComments(int id) {
		JList<Comment> listComments = new JList<Comment>(model3);
		MongoDBConnector.connect();
		Post p = null;

		Iterator<Document> it = MongoDBConnector.collection.find(Filters.eq("ownerUserId", id)).iterator();

		while (it.hasNext()) {
			Document d = it.next();
			p = new Post(d);
		}

		ArrayList<Comment> temp = p.getComments();
		for (Comment co : temp) {
			model3.addElement(co);
		}

		return listComments;
	}

	public void deletePost(JList<Post> list) {
		MongoDBConnector.connect();

		if (list.getSelectedValue().getPostTypeId() == 1) {
			MongoDBConnector.collection.deleteOne(Filters.eq("id", list.getSelectedValue().getId()));
		} else {
			MongoDBConnector.collection.deleteOne(Filters.eq("id", list.getSelectedValue().getId()));
		}

		MongoDBConnector.disconnect();
	}

	public void deleteComment(JList<Comment> list) {
		MongoDBConnector.connect();

		BasicDBObject match = new BasicDBObject("id", list.getSelectedValue().getPostId()); // to match your document
		BasicDBObject update = new BasicDBObject("comments",
				new BasicDBObject("text", list.getSelectedValue().getText()));
		MongoDBConnector.collection.updateOne(match, new BasicDBObject("$pull", update));
		model3.remove(list.getSelectedIndex());
	}
}
