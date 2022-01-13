package cs.stackexchange.gui;

import static cs.stackexchange.bd.Neo4jConnector.driver;

import java.awt.Color;
import java.awt.EventQueue;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import cs.stackexchange.bd.Neo4jConnector;
import cs.stackexchange.data.User;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;

public class AdminUsersWindow extends JFrame {

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	DefaultListModel<User> model = new DefaultListModel<User>();
	JList<User> listUsers;

	static Neo4jConnector neo4j;
	Result result;
	List<Record> list;
	User u;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUsersWindow frame = new AdminUsersWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});

	}

	public AdminUsersWindow() {
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 493);
		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(85, 107, 47), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		listUsers = checkUser();
		listUsers.setBounds(84, 51, 536, 354);

		JScrollPane scroll = new JScrollPane(listUsers);
		scroll.setBounds(84, 51, 536, 354);
		contentPane.add(scroll);

		JLabel lblUsersAdministration = new JLabel("USERS' ADMINISTRATION");
		lblUsersAdministration.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersAdministration.setFont(new Font("Dialog", Font.BOLD, 33));
		lblUsersAdministration.setBounds(128, 0, 441, 54);
		contentPane.add(lblUsersAdministration);

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

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBorder(
				new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDelete.setBackground(Color.RED);
		btnDelete.setBounds(94, 413, 108, 33);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(listUsers.getSelectedValue().getUsername());
				delete(listUsers.getSelectedValue().getUsername());
				AdminUsersWindow auw = new AdminUsersWindow();
				auw.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnDelete);

	}

	public JList<User> checkUser() {
		JList<User> jlist = new JList<User>(model);
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");

		try (Session session = driver.session()) {
			session.readTransaction(tx -> {
				result = tx.run("MATCH (n:User) RETURN n.id, n.aboutMe,n.creationDate,n.reputation,n.username");
				list = result.list();
				Iterator<Record> iterator = list.iterator();
				while (iterator.hasNext()) {
					Record r = iterator.next();
					int id = Integer.parseInt(r.get("n.id").asObject().toString());
					int rep = Integer.parseInt(r.get("n.reputation").asObject().toString());
					String username = r.get("n.username").asString();
					String about = r.get("n.aboutMe").asString();
					String date = r.get("n.creationDate").asString();
					u = new User(id, rep, username, about, date);
					model.addElement(u);
				}
				return list;
			});
			return jlist;
		} catch (Exception e) {
			logger.log(Level.INFO, "ERROR", e);
			return null;
		}

	}

	public void delete(String username) {
		neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> {
				result = tx.run("MATCH (n:User) WHERE n.username='" + username + "' DETACH DELETE n");
				return null;
			});
		}
	}
}
