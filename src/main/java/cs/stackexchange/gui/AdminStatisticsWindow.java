package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
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
import javax.swing.JList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.client.AggregateIterable;

import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

import static cs.stackexchange.bd.Neo4jConnector.driver;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.bd.Neo4jConnector;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class AdminStatisticsWindow extends JFrame {

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	Neo4jConnector neo4j;

	String count;
	String username;

	private DefaultListModel<String> model = new DefaultListModel<String>();
	private DefaultListModel<String> model2 = new DefaultListModel<String>();

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminStatisticsWindow frame = new AdminStatisticsWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});

	}

	public AdminStatisticsWindow() {
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 493);
		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(85, 107, 47), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblStatistics = new JLabel("STATISTICS");
		lblStatistics.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatistics.setFont(new Font("Dialog", Font.BOLD, 33));
		lblStatistics.setBounds(128, 0, 441, 54);
		contentPane.add(lblStatistics);

		JLabel lblBack = new JLabel("Back");
		lblBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBack.setForeground(Color.BLUE);
		lblBack.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBack.setBounds(10, 30, 61, 16);
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
				AdminWindow aw = new AdminWindow("admin");
				aw.setVisible(true);
				dispose();
			}
		});
		contentPane.add(lblBack);

		JLabel lblTotalPosts = new JLabel("Total number of Posts: " + getPostsCount());
		lblTotalPosts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalPosts.setBounds(85, 121, 261, 29);
		contentPane.add(lblTotalPosts);

		JLabel lblTotalUsers = new JLabel("Total number of Users: " + getUsersCount());
		lblTotalUsers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalUsers.setBounds(85, 160, 388, 29);
		contentPane.add(lblTotalUsers);

		JLabel lblTotalQuestions = new JLabel("-> Total number of Questions: " + getQuestionsCount());
		lblTotalQuestions.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalQuestions.setBounds(128, 160, 261, 29);
		lblTotalQuestions.setVisible(false);
		contentPane.add(lblTotalQuestions);

		JLabel lblTotalAnswers = new JLabel("-> Total number of Answers: " + getAnswersCount());
		lblTotalAnswers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalAnswers.setBounds(128, 199, 261, 29);
		lblTotalAnswers.setVisible(false);
		contentPane.add(lblTotalAnswers);

		JCheckBox checkBox = new JCheckBox("Display division");
		checkBox.setBounds(360, 127, 136, 21);
		checkBox.setBackground(Color.WHITE);
		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {// checkbox has been selected

					lblTotalQuestions.setVisible(true);
					lblTotalAnswers.setVisible(true);
					lblTotalUsers.setBounds(85, 238, 388, 29);

				} else {// checkbox has been deselected

					lblTotalQuestions.setVisible(false);
					lblTotalAnswers.setVisible(false);
					lblTotalUsers.setBounds(85, 160, 388, 29);

				}
				;
			}
		});
		contentPane.add(checkBox);

		JList<String> list1 = getNumberOfPosts();
		list1.setBounds(67, 40, 536, 371);

		JScrollPane scroll = new JScrollPane(list1);
		scroll.setBounds(42, 315, 273, 97);
		contentPane.add(scroll);

		JLabel lblTopContributors = new JLabel("Top 5 contributors:");
		lblTopContributors.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTopContributors.setBounds(54, 289, 136, 21);
		contentPane.add(lblTopContributors);
		
		JList<String> list2 = getQualityOfPosts();
		list2.setBounds(67, 40, 536, 371);

		JScrollPane scroll2 = new JScrollPane(list2);
		scroll2.setBounds(360, 315, 300, 97);
		contentPane.add(scroll2);
		
		JLabel lblTopQuality = new JLabel("Top Votes per Post: ");
		lblTopQuality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTopQuality.setBounds(371, 289, 136, 21);
		contentPane.add(lblTopQuality);
	}

	public long getPostsCount() {
		MongoDBConnector.connect();
		return MongoDBConnector.collection.countDocuments();
	}

	public long getQuestionsCount() {
		MongoDBConnector.connect();
		Bson query = eq("postTypeId", 1);
		return MongoDBConnector.collection.countDocuments(query);
	}

	public long getAnswersCount() {
		MongoDBConnector.connect();
		Bson query = eq("postTypeId", 2);
		return MongoDBConnector.collection.countDocuments(query);
	}

	public String getUsersCount() {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) RETURN COUNT(u)");
				count = result.single().toString().substring(18, 24);
				return count;
			});
		}
		return count;
	}

	private JList<String> getNumberOfPosts() {
		MongoDBConnector.connect();
		JList<String> list = new JList<String>(model);

		Bson sortByCount = sortByCount("$ownerUserId");
		Bson limit = limit(5);

		List<Document> results = MongoDBConnector.collection.aggregate(Arrays.asList(sortByCount, limit))
				.into(new ArrayList<>());

		Iterator<Document> it = results.iterator();

		if (!it.hasNext()) {
			System.out.println("Null");
		}

		while (it.hasNext()) {
			Document d = it.next();
			String id = d.get("_id").toString();
			String count2 = d.get("count").toString();
			String string = getUserById(id) + " | Number of Posts: " + count2;
			model.addElement(string);
		}
		return list;
	}

	private String getUserById(String id) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				Result result = tx.run("MATCH (u:User) WHERE u.id = " + id + " RETURN u.username");
				username = result.single().get(0).toString();
				return username;
			});
		}
		return username;
	}

	private JList<String> getQualityOfPosts() {
		
		MongoDBConnector.connect();
		JList<String> list = new JList<String>(model2);
		Bson limit = limit(5);

		AggregateIterable<Document> result = MongoDBConnector.collection.aggregate(Arrays.asList(
				new Document("$group", new Document("_id", "$ownerUserId")
						.append("Count", new Document("$count", new Document()))
						.append("sumVotes", new Document("$sum", "$score"))),
				new Document("$project", new Document("Quality", new Document("$divide", Arrays.asList("$sumVotes", "$Count")))),
				new Document("$sort", new Document("Quality", -1)),limit));

		Iterator<Document> it = result.iterator();

		if (!it.hasNext()) {
			System.out.println("Null");
		}

		while (it.hasNext()) {
			Document d = it.next();
			String id = d.get("_id").toString();
			String quality = d.get("Quality").toString();
			String string = getUserById(id) + " | Quality of Posts: " + quality;
			model2.addElement(string);
		}
		return list;
	}
}
