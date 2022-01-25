package cs.stackexchange.gui;

import static com.mongodb.client.model.Filters.eq;
import static cs.stackexchange.bd.Neo4jConnector.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.bd.Neo4jConnector;
import cs.stackexchange.data.Comment;
import cs.stackexchange.data.Post;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Rectangle;

public class NewCommentWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	Neo4jConnector neo4j;
	static Post post;
	int idOwn;

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					NewCommentWindow frame = new NewCommentWindow(3, "prueba");
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public NewCommentWindow(int id, String username) {
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
				NewAnswerWindow naw = new NewAnswerWindow(id,username);
				naw.setVisible(true);
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

		JLabel lblComment = new JLabel("Comment: ");
		lblComment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblComment.setBounds(10, 210, 130, 24);
		panel_1.add(lblComment);

		JTextArea txtComment = new JTextArea();
		txtComment.setText("Write your comment here...");
		txtComment.setForeground(Color.GRAY);
		txtComment.setWrapStyleWord(true);
		txtComment.setBounds(10, 138, 516, 239);
		txtComment.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				txtComment.setText("");
				txtComment.setForeground(Color.BLACK);
			}
		});

		JScrollPane scroll = new JScrollPane(txtComment);
		scroll.setBounds(new Rectangle(10, 244, 516, 133));
		panel_1.add(scroll);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.WHITE);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSave.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSave.setBackground(Color.BLACK);
		btnSave.setBounds(375, 387, 133, 33);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Comment c = new Comment(id, txtComment.getText().toString(), getUserId(username));
				addComment(id, c);

				QuestionWindow qw = new QuestionWindow(id, username);
				qw.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnSave);

		JTextPane txtPost = new JTextPane();
		if (getPostById(id).getTitle() == null) {
			txtPost.setText(getPostById(id).getBody());
			txtPost.setContentType("text/html");
		} else {
			txtPost.setText(getPostById(id).getTitle());
			txtPost.setFont(new Font("Arial", Font.PLAIN, 20));
		}
		txtPost.setEditable(false);
		txtPost.setBounds(22, 25, 486, 69);

		JScrollPane scrollPost = new JScrollPane(txtPost);
		scrollPost.setBounds(22, 25, 486, 175);
		panel_1.add(scrollPost);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCancel.setBorder(
				new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnCancel.setBackground(Color.RED);
		btnCancel.setBounds(30, 387, 133, 33);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				QuestionWindow qw = new QuestionWindow(id, username);
				qw.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnCancel);
	}

	public int getUserId(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.id");
				String idOwner = result.single().get(0).asString();
				idOwn = Integer.parseInt(idOwner);
				return idOwner;
			});
			return idOwn;
		} catch (Exception e) {
			logger.log(Level.INFO, "ERROR", e);
			return -1;
		}

	}

	public Post getLastId() {
		MongoDBConnector.connect();
		Iterator<Document> it = MongoDBConnector.collection.find().sort(new BasicDBObject("id", -1)).limit(1)
				.iterator();

		if (!it.hasNext()) {
			logger.log(Level.SEVERE, "Post by ID not found.");
			return null;
		}
		Document d = it.next();
		Post p = new Post(d);
		return p;
	}

	public Post getPostById(int id) {
		MongoDBConnector.connect();

		Iterator<Document> it = MongoDBConnector.collection.find(eq("id", id)).iterator();

		if (!it.hasNext()) {
			logger.log(Level.SEVERE, "Post by ID not found.");
			return null;
		}
		Document d = it.next();
		Post p = new Post(d);
		return p;
	}

	// UPDATE COMMENTS
	public void addComment(int postId, Comment comment) {
		MongoDBConnector.connect();

		Document query = new Document().append("id", postId);

		Map<String, Object> documentMap = new HashMap<String, Object>();

		documentMap.put("postId", comment.getPostId());
		documentMap.put("text", comment.getText());
		documentMap.put("userId", comment.getUserId());
		Bson updates = Updates.combine(Updates.addToSet("comments", new BasicDBObject(documentMap)));

		UpdateOptions options = new UpdateOptions().upsert(true);

		try {

			UpdateResult result = MongoDBConnector.collection.updateOne(query, updates, options);
			System.out.println("Modified document count: " + result.getModifiedCount());
			System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is
																			// performed
		} catch (MongoException me) {
			System.err.println("Unable to update due to an error: " + me);
		}
	}
}
