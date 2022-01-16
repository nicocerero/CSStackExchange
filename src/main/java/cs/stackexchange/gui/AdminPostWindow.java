package cs.stackexchange.gui;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import cs.stackexchange.bd.MongoDBConnector;

import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AdminPostWindow extends JFrame{
	
	private DefaultListModel<String> model = new DefaultListModel<String>();

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;
	
	JList<String> list1;
	JLabel lblMessage;
	Document doc;
	String fin;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPostWindow frame = new AdminPostWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});

	}
	
	public AdminPostWindow() {
		setTitle("CS StackExchange");
		setIconImage(new ImageIcon(getClass().getResource("images/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 493);
		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(85, 107, 47), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list1 = getPosts();
		list1.setBounds(67, 40, 536, 371);
		
		JScrollPane scroll = new JScrollPane(list1);
		scroll.setBounds(84, 51, 536, 354);
		contentPane.add(scroll);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"), new LineBorder(Color.RED, 2, true)));
		btnDelete.setBackground(Color.RED);
		btnDelete.setBounds(94, 413, 108, 33);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(list1.getSelectedValue() == null) {
					lblMessage.setText("Post not selected");
				}else {
					delete();
					model.remove(list1.getSelectedIndex());
					JOptionPane.showMessageDialog(null,"Post deleted succesfully");
					lblMessage.setText("");
				}
				
			}
			
		});
		contentPane.add(btnDelete);
		
		JLabel lblPostsAdministration = new JLabel("POSTS' ADMINISTRATION");
		lblPostsAdministration.setHorizontalAlignment(SwingConstants.CENTER);
		lblPostsAdministration.setFont(new Font("Dialog", Font.BOLD, 33));
		lblPostsAdministration.setBounds(128, 0, 441, 54);
		contentPane.add(lblPostsAdministration);
		
		lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(new Color(128, 0, 0));
		lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMessage.setBounds(213, 415, 250, 29);
		contentPane.add(lblMessage);
		
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
	}
	
	public JList<String> getPosts() {
		JList<String> list1 = new JList<String>(model);
		MongoDBConnector.connect();

		Bson projection = fields(include("title", "score","id"), excludeId());
		FindIterable<Document> iterDoc = MongoDBConnector.collection.find(eq("postTypeId", 1)).projection(projection)
				.sort(descending("score"));
		Iterator<Document> it = iterDoc.iterator();
		while (it.hasNext()) {
			model.addElement(it.next().toString().replace("Document{{","").replace("}}", "").replace(", title="," | Q:").replace("score=", ""));
		}
		MongoDBConnector.disconnect();
		return list1;
	}
	
	public void delete() {
		MongoDBConnector.connect();
		int last = list1.getSelectedValue().toString().indexOf(",");
		System.out.println(list1.getSelectedValue().toString().substring(3, last));
		MongoDBConnector.collection.deleteOne(Filters.eq("id", Integer.parseInt(list1.getSelectedValue().toString().substring(3, last))));
		MongoDBConnector.disconnect();
	}
}
