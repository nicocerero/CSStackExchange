package cs.stackexchange.gui;

import static com.mongodb.client.model.Filters.eq;
import static cs.stackexchange.bd.Neo4jConnector.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.bson.Document;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.BasicDBObject;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.bd.Neo4jConnector;
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
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JTextArea;

public class NewAnswerWindow extends JFrame {

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

					NewAnswerWindow frame = new NewAnswerWindow(3,"prueba");
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public NewAnswerWindow(int id,String username) {
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

		JLabel lblNewLabel_1 = new JLabel("User: " + username);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JButton btnProfile = new JButton("My Profile");
		GridBagConstraints gbc_btnProfile = new GridBagConstraints();
		gbc_btnProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProfile.insets = new Insets(0, 0, 5, 0);
		gbc_btnProfile.gridx = 1;
		gbc_btnProfile.gridy = 5;
		btnProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyProfileWindow mpw = new MyProfileWindow(username);
				mpw.setVisible(true);
				dispose();

			}
		});
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
		gbc_lblLogout.insets = new Insets(0, 0, 5, 0);
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
		gbc_lblMenu.insets = new Insets(0, 0, 5, 0);
		gbc_lblMenu.gridx = 1;
		gbc_lblMenu.gridy = 3;
		panel.add(lblMenu, gbc_lblMenu);

		JButton btnHome = new JButton("Home");
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHome.insets = new Insets(0, 0, 5, 0);
		gbc_btnHome.gridx = 1;
		gbc_btnHome.gridy = 4;
		btnHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mw = new MainWindow(username);
				mw.setVisible(true);
				dispose();

			}
		});
		panel.add(btnHome, gbc_btnHome);
		panel.add(btnProfile, gbc_btnProfile);

		JLabel lblBack = new JLabel("Back");
		GridBagConstraints gbc_lblBack = new GridBagConstraints();
		lblBack.setForeground(Color.BLUE);
		gbc_lblBack.insets = new Insets(0, 0, 5, 0);
		gbc_lblBack.gridx = 1;
		gbc_lblBack.gridy = 6;
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

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel lblBody = new JLabel("Body:");
		lblBody.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBody.setBounds(10, 104, 130, 24);
		panel_1.add(lblBody);

		JTextArea txtAnswer = new JTextArea();
		txtAnswer.setText("Write you answer here...");
		txtAnswer.setForeground(Color.GRAY);
		txtAnswer.setWrapStyleWord(true);
		txtAnswer.setBounds(10, 138, 516, 239);
		txtAnswer.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				txtAnswer.setText("");
				txtAnswer.setForeground(Color.BLACK);
			}
		});
		panel_1.add(txtAnswer);
		
		JScrollPane scroll = new JScrollPane(txtAnswer);
		scroll.setBounds(txtAnswer.getBounds());
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
				createAnswer(txtAnswer.getText().toString(), id, username);
				QuestionWindow qw = new QuestionWindow(id,username);
				qw.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnSave);

		JTextArea txtQuestion = new JTextArea();
		txtQuestion.setWrapStyleWord(true);
		txtQuestion.setText("Q: " + getPostById(id).getTitle());
		txtQuestion.setLineWrap(true);
		txtQuestion.setFont(new Font("Arial", Font.PLAIN, 20));
		txtQuestion.setEditable(false);
		txtQuestion.setBounds(22, 25, 486, 69);
		panel_1.add(txtQuestion);

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
				QuestionWindow qw = new QuestionWindow(id,username);
				qw.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnCancel);
	}

	public void createAnswer(String text, int id,String username) {
		MongoDBConnector.connect();

		Document d = new Document();
		d.append("id", getLastId().getId()+1);
		d.append("postTypeId", 2);
		d.append("acceptedAnswerId", null);
		d.append("creationDate", LocalDate.now().toString());
		d.append("score", 0);
		d.append("viewCount", null);
		d.append("body", text);
		d.append("ownerUserId", getUserId(username));
		d.append("title", null);
		d.append("tags", new ArrayList<Object>());
		d.append("parentId", id);
		d.append("comments", new ArrayList<Object>());

		MongoDBConnector.collection.insertOne(d);
		JOptionPane.showMessageDialog(null, "Answer added succesfully");
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
		Iterator<Document> it = MongoDBConnector.collection.find().sort(new BasicDBObject("id",-1)).limit(1).iterator();
		
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
}
