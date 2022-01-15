package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
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
import static com.mongodb.client.model.Filters.*;

import cs.stackexchange.bd.MongoDBConnector;
import cs.stackexchange.data.Comment;
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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class AnswerWindow extends JFrame {

	private DefaultListModel<Comment> model = new DefaultListModel<Comment>();

	private JPanel contentPane;

	public static Post post;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					AnswerWindow frame = new AnswerWindow(post.getId());
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public AnswerWindow(int id) {
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

		JButton btnProfile = new JButton("My Profile");
		GridBagConstraints gbc_btnProfile = new GridBagConstraints();
		gbc_btnProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProfile.insets = new Insets(0, 0, 5, 0);
		gbc_btnProfile.gridx = 1;
		gbc_btnProfile.gridy = 5;
		btnProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyProfileWindow mpw = new MyProfileWindow();
				mpw.setVisible(true);
				dispose();

			}
		});
		btnProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyProfileWindow pw = new MyProfileWindow();
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
				MainWindow mw = new MainWindow();
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
				MainWindow mw = new MainWindow();
				mw.setVisible(true);
				dispose();

			}
		});
		panel.add(lblBack, gbc_lblBack);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel lblStackExchange = new JLabel("CS StackExchange");
		lblStackExchange.setToolTipText("");
		lblStackExchange.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblStackExchange.setBounds(118, 0, 299, 50);
		panel_1.add(lblStackExchange);
		
		JTextPane txtAnswer = new JTextPane();
		txtAnswer.setContentType("text/html");
		txtAnswer.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
//		txtAnswer.setLineWrap(true);
//		txtAnswer.setWrapStyleWord(true);
		txtAnswer.setEditable(false);
		txtAnswer.setBounds(27, 74, 486, 69);
		
		JScrollPane scroll2 = new JScrollPane(txtAnswer);
		scroll2.setBounds(27, 51, 486, 215);
		panel_1.add(scroll2);

		JList<Comment> list = getPostById(id);
		list.setBounds(38, 45, 490, 380);
		txtAnswer.setText(post.getBody());

		JLabel lblScore = new JLabel(String.valueOf(post.getScore()));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(0, 152, 31, 13);
		panel_1.add(lblScore);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(10, 312, 516, 79);
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
		panel_1.add(btnSelect);

		JButton btnComment = new JButton("Comment");
		btnComment.setForeground(Color.WHITE);
		btnComment.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnComment.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnComment.setBackground(Color.BLACK);
		btnComment.setBounds(393, 401, 133, 33);
		panel_1.add(btnComment);
		
		JLabel lblComments = new JLabel("Comments:");
		lblComments.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblComments.setBounds(13, 278, 130, 24);
		panel_1.add(lblComments);

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

	/**
	 * Post -> title, body, tags, votes, comments, ... Answers -> title, body,
	 * votes, comment, ordered by votes (first always correct answer).
	 */
	public JList<Comment> getPostById(int id) {
		JList<Comment> list1 = new JList<Comment>(model);
		MongoDBConnector.connect();

		Iterator<Document> it = MongoDBConnector.collection.find(eq("id", id)).iterator();

		if (!it.hasNext()) {
			logger.log(Level.SEVERE, "Post by ID not found.");
			return null;
		}
		Document d = it.next();
		post = new Post(d);
		
		ArrayList<Comment> temp = post.getComments();
		for (Comment co : temp) {
			model.addElement(co);
		}

		return list1;
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
			Comment label = (Comment) value;
			String body = label.getText();
			//int id = label.getId();
			int userid = label.getUserId();
//			String date = label.getCreationDate().toString().split("T")[0];
			String text = HTML_1 + String.valueOf(width) + HTML_2 +"(UserId: " + userid +")"+ body + "<br/><br/>" + HTML_3;
			return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		}

	}
}
