package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.MongoException;
import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static cs.stackexchange.bd.Neo4jConnector.driver;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.bd.Neo4jConnector;
import cs.stackexchange.data.Post;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class QuestionWindow extends JFrame {

	private DefaultListModel<Post> model = new DefaultListModel<Post>();

	private JPanel contentPane;

	public static Post post;

	Neo4jConnector neo4j;

	int idUser;

	String owner;

	JButton btnUpdate;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					QuestionWindow frame = new QuestionWindow(3, "codd");
					frame.setVisible(true);

				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public QuestionWindow(int id, String username) {
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 733, 493);
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

		JList<Post> list = getPostById(id);
		list.setBounds(38, 45, 490, 380);
		list.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));

		JTextArea txtQuestion = new JTextArea();
		txtQuestion.setFont(new Font("Arial", Font.PLAIN, 17));
		txtQuestion.setLineWrap(true);
		txtQuestion.setWrapStyleWord(true);
		txtQuestion.setEditable(false);
		txtQuestion.setBounds(27, 10, 486, 56);
		txtQuestion.setText("Q: " + post.getTitle());
		panel_1.add(txtQuestion);

		JTextPane txtBody = new JTextPane();
		txtBody.setContentType("text/html");
		txtBody.setEditable(false);
		txtBody.setText(post.getBody());

		JScrollPane scrollBody = new JScrollPane(txtBody);
		scrollBody.setBounds(27, 89, 461, 115);
		panel_1.add(scrollBody);

		JLabel lblScore = new JLabel("" + post.getScore());
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(0, 139, 27, 13);
		panel_1.add(lblScore);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(10, 249, 496, 142);
		panel_1.add(scroll);

		MyCellRenderer cellRenderer = new MyCellRenderer(380);
		list.setCellRenderer(cellRenderer);

		JButton btnSelect = new JButton("Select");
		btnSelect.setForeground(Color.WHITE);
		btnSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelect.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSelect.setBackground(Color.BLACK);
		btnSelect.setBounds(10, 401, 133, 33);
		btnSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int id = list.getSelectedValue().getId();
				AnswerWindow aw = new AnswerWindow(id, username);
				aw.setVisible(true);
				dispose();

			}
		});
		panel_1.add(btnSelect);

		JButton btnNewAnswer = new JButton("New Answer");
		btnNewAnswer.setForeground(Color.WHITE);
		btnNewAnswer.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewAnswer.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnNewAnswer.setBackground(Color.BLACK);
		btnNewAnswer.setBounds(373, 401, 133, 33);
		btnNewAnswer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NewAnswerWindow naw = new NewAnswerWindow(id, username);
				naw.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnNewAnswer);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnConfirm.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),

				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnConfirm.setBackground(Color.BLACK);
		btnConfirm.setBounds(275, 214, 106, 25);
		btnConfirm.setVisible(false);
		btnConfirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtQuestion.setEditable(false);
				txtBody.setEditable(false);
				btnUpdate.setVisible(true);
				btnConfirm.setVisible(false);
				update(id, txtQuestion.getText().toString(), txtBody.getText().toString());
			}

		});
		panel_1.add(btnConfirm);

		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(275, 214, 106, 25);

		if (post.getUserId() == getId(username)) {
			btnUpdate.setVisible(true);
		} else {
			btnUpdate.setVisible(false);
		}

		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				txtQuestion.setEditable(true);
				txtBody.setEditable(true);
				btnUpdate.setVisible(false);
				btnConfirm.setVisible(true);
			}

		});
		panel_1.add(btnUpdate);

		JTextArea textTags = new JTextArea();
		textTags.setText("Tags: " + post.getTags());
		textTags.setBounds(10, 206, 255, 38);
		textTags.setEditable(false);
		textTags.setWrapStyleWord(true);
		textTags.setLineWrap(true);
		panel_1.add(textTags);

		JLabel lblMadeBy = new JLabel("Created by: " + getOwner(post.getUserId()));
		lblMadeBy.setBounds(27, 65, 165, 13);
		panel_1.add(lblMadeBy);
		
		JLabel lblVote = new JLabel("Vote");
		lblBack.setForeground(Color.BLUE);
		lblVote.setHorizontalAlignment(SwingConstants.CENTER);
		lblVote.setBounds(0, 109, 27, 13);
		lblVote.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				lblVote.setForeground(Color.BLUE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblVote.setForeground(Color.RED);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				vote(id);
				lblScore.setText(""+post.getScore());
			}
		});
		panel_1.add(lblVote);

	}

	/**
	 * Post -> title, body, tags, votes, comments, ... Answers -> title, body,
	 * votes, comment, ordered by votes (first always correct answer).
	 */
	public JList<Post> getPostById(int id) {
		JList<Post> list1 = new JList<Post>(model);
		MongoDBConnector.connect();

		// First get the question Post
		Iterator<Document> it = MongoDBConnector.collection.find(eq("id", id)).iterator();

		if (!it.hasNext()) {
			logger.log(Level.SEVERE, "Post by ID not found.");
			return null;
		}
		Document d = it.next();
		post = new Post(d);

		// If Post has an acceptedAnswerId, then get that one first, 10 total
		// answers ranked by upvotes
		ArrayList<Post> temp = new ArrayList<>();
		it = MongoDBConnector.collection.find(eq("parentId", post.getId())).sort(descending("score")).iterator();

		if (!it.hasNext()) {
			return list1;
		}

		// If there are answers, add them to a temporal list.
		while (it.hasNext()) {
			Post pTemp = new Post(it.next());
			temp.add(pTemp);
		}

		// We have to make sure that the accepted answer is the first one.
		if (post.getAcceptedAnswerId() != 0) { // That is, there is an accepted answer.
			for (Post po : temp) {
				if (po.getId() == post.getAcceptedAnswerId()) {
					model.addElement(po);
					break;
				}
			}
			// Then we delete the element from the list
			temp.removeIf(po -> po.getId() == post.getAcceptedAnswerId());
		}

		// Now we add the rest of the answers
		for (Post po : temp) {
			model.addElement(po);
		}

		return list1;
	}

	public int getId(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.username = '" + username + "' RETURN u.id");
				idUser = Integer.parseInt(result.single().get(0).toString());
				return idUser;
			});
		}
		return idUser;
	}

	public String getOwner(int id) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.id = " + id + " RETURN u.username");
				owner = result.single().get(0).asString();
				return owner;
			});
		}
		return owner;
	}

	public void update(int id, String title, String body) {
		MongoDBConnector.connect();

		Document query = new Document().append("id", id);

		Bson updates = Updates.combine(Updates.set("title", title), Updates.set("body", body));

		try {

			MongoDBConnector.collection.updateOne(query, updates);
			JOptionPane.showMessageDialog(null, "Updated succesfully");

		} catch (MongoException me) {

			JOptionPane.showMessageDialog(null, "Error on update: " + me);

		}

	}
	
	public void vote(int id) {
		MongoDBConnector.connect();
		
		Document query = new Document().append("id",  id);
		
		Bson updates = Updates.combine(Updates.set("score", post.getScore()+1));
		post.setScore(post.getScore() +1);
		
		MongoDBConnector.collection.updateOne(query, updates);
		
	}

	class MyCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;
		public static final String HTML_1 = "<html><body style='width: ";
		public static final String HTML_2 = "px'>";
		public static final String HTML_3 = "</html>";
		private int width;

		public MyCellRenderer(int width) {
			this.width = width;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Post label = (Post) value;
			String body = label.getBody();
			int score = label.getScore();
			String date = label.getCreationDate().toString().split("T")[0];
			String text = HTML_1 + String.valueOf(width) + HTML_2 + "Score: " + score + "<br/>Cretation date: " + date
					+ "<br/><br/>" + body + "<br/><br/><br/>" + HTML_3;
			return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		}

	}
}
