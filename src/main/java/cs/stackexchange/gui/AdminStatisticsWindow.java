package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;

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

	// private DefaultListModel<String> model = new DefaultListModel<String>();

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	Neo4jConnector neo4j;

	String count;

	JList<String> list1;
	Document doc;
	String fin;

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
				AdminWindow aw = new AdminWindow();
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
					
				};
			}
		});
		contentPane.add(checkBox);
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

	public void delete() {
		MongoDBConnector.connect();
		int last = list1.getSelectedValue().toString().indexOf(",");
		System.out.println(list1.getSelectedValue().toString().substring(3, last));
		MongoDBConnector.collection
				.deleteOne(Filters.eq("id", Integer.parseInt(list1.getSelectedValue().toString().substring(3, last))));
		MongoDBConnector.disconnect();
	}
}
