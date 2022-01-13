package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

import cs.stackexchange.bd.MongoDBConnector;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.JTextArea;

public class QuestionWindow extends JFrame {

	private DefaultListModel<Post> model = new DefaultListModel<Post>();

	private JPanel contentPane;

	public static Post post;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					QuestionWindow frame = new QuestionWindow(post.getId());
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public QuestionWindow(int id) {
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
		gbl_panel.rowHeights = new int[] { 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_1 = new JLabel("User: " + getProp().toString());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JButton btnProfile = new JButton("Profile");
		GridBagConstraints gbc_btnProfile = new GridBagConstraints();
		gbc_btnProfile.insets = new Insets(0, 0, 5, 0);
		gbc_btnProfile.gridx = 1;
		gbc_btnProfile.gridy = 5;
		btnProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProfileWindow pw = new ProfileWindow();
				pw.setVisible(true);
				dispose();
			}
		});

		JLabel lblMenu = new JLabel("MENU");
		GridBagConstraints gbc_lblMenu = new GridBagConstraints();
		gbc_lblMenu.insets = new Insets(0, 0, 5, 0);
		gbc_lblMenu.gridx = 1;
		gbc_lblMenu.gridy = 3;
		panel.add(lblMenu, gbc_lblMenu);

		JButton btnHome = new JButton("Home");
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.insets = new Insets(0, 0, 5, 0);
		gbc_btnHome.gridx = 1;
		gbc_btnHome.gridy = 4;
		btnHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mw = new MainWindow();
				mw.setVisible(true);
				dispose();

			}
		});
		panel.add(btnHome, gbc_btnHome);
		panel.add(btnProfile, gbc_btnProfile);

		JLabel lblLogout = new JLabel("Logout");
		lblLogout.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblLogout = new GridBagConstraints();
		gbc_lblLogout.gridx = 1;
		gbc_lblLogout.gridy = 14;
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

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel lblStackExchange = new JLabel("CS StackExchange");
		lblStackExchange.setToolTipText("");
		lblStackExchange.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblStackExchange.setBounds(118, 0, 299, 50);
		panel_1.add(lblStackExchange);

		JTextArea txtQuestion = new JTextArea();
		txtQuestion.setFont(new Font("Arial", Font.PLAIN, 20));
		txtQuestion.setLineWrap(true);
		txtQuestion.setWrapStyleWord(true);
		txtQuestion.setText("Q: " + post.getTitle());
		txtQuestion.setEditable(false);
		txtQuestion.setBounds(27, 74, 486, 69);
		panel_1.add(txtQuestion);

		JLabel lblScore = new JLabel("Score: " + post.getScore());
		lblScore.setBounds(27, 153, 64, 13);
		panel_1.add(lblScore);

		/*
		 * JButton btnSelect = new JButton("Select");
		 * btnSelect.setForeground(Color.WHITE);
		 * btnSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		 * btnSelect.setBorder(new
		 * CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
		 * new LineBorder(new Color(0, 0, 0), 2, true)));
		 * btnSelect.setBackground(Color.BLACK);
		 * btnSelect.setBounds(199, 423, 101, 29);
		 * btnSelect.addActionListener(new ActionListener() {
		 * 
		 * @Override
		 * public void actionPerformed(ActionEvent e) {
		 * // TODO: create new window.
		 * Post p = list.getSelectedValue();
		 * getPostById(p.getId());
		 * }
		 * 
		 * });
		 * panel_1.add(btnSelect);
		 */

		// Metodos de BD.
		JList<Post> list = getPostById(id);

		list.setBounds(38, 45, 490, 380);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(0, 176, 536, 276);
		panel_1.add(scroll);

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

	/*
	 * public JList<Post> getTopPosts() {
	 * JList<Post> list1 = new JList<Post>(model);
	 * MongoDBConnector.connect();
	 * 
	 * Bson projection = fields(include("title", "score", "id"), excludeId());
	 * Iterator<Document> it = MongoDBConnector.collection
	 * .find(eq("postTypeId", 1))
	 * .projection(projection)
	 * .sort(descending("score"))
	 * .limit(10)
	 * .iterator();
	 * 
	 * while (it.hasNext()) {
	 * Document d = it.next();
	 * Post p = new Post();
	 * p.setTitle((String) d.get("title"));
	 * p.setId((int) d.get("id"));
	 * p.setScore((int) d.get("score"));
	 * 
	 * System.out.println("Imprimiendo post: " + p.toString());
	 * model.addElement(p);
	 * }
	 * 
	 * return list1;
	 * }
	 */

	/**
	 * Post -> title, body, tags, votes, comments, ...
	 * Answers -> title, body, votes, comment, ordered by votes (first always
	 * correct answer).
	 */
	public JList<Post> getPostById(int id) {
		JList<Post> list1 = new JList<Post>(model);
		MongoDBConnector.connect();

		// First get the question Post
		Iterator<Document> it = MongoDBConnector.collection
				.find(eq("id", id))
				.iterator();

		if (!it.hasNext()) {
			logger.log(Level.SEVERE, "Post by ID not found.");
			return null;
		}
		Document d = it.next();
		post = new Post(d);

		// If Post has an acceptedAnswerId, then get that one first, 10 total
		// answers ranked by upvotes
		ArrayList<Post> temp = new ArrayList<>();
		it = MongoDBConnector.collection
				.find(eq("parentId", post.getId()))
				.sort(descending("score"))
				.limit(10)
				.iterator();

		if (!it.hasNext()) {
			return list1;
		}

		// If there are answersm, add them to a temporal list.
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
}