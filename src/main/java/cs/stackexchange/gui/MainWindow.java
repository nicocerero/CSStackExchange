package cs.stackexchange.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.List;
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
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

import cs.stackexchange.bd.MongoDBConnector;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;

public class MainWindow extends JFrame {

	private DefaultListModel<Document> model = new DefaultListModel<Document>();

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.log(Level.WARNING, "ERROR", e);
				}
			}
		});
	}

	public MainWindow() {

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
		contentPane.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 35, 55, 40 };
		gbl_panel.rowHeights = new int[] { 35, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_1 = new JLabel("User: " + getProp());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("MENU");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnNewButton_1 = new JButton("Home");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 2;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);

		JButton btnNewButton = new JButton("Profile");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		panel.add(btnNewButton, gbc_btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JList<Document> list = getMongo();
		list.setBounds(38, 45, 490, 380);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(0, 47, 551, 371);
		panel_1.add(scroll);

		JLabel lblStackExchange = new JLabel("CS StackExchange");
		lblStackExchange.setToolTipText("");
		lblStackExchange.setFont(new Font("Arial Nova", Font.PLAIN, 33));
		lblStackExchange.setBounds(118, 0, 299, 50);
		panel_1.add(lblStackExchange);

		JButton btnSelect = new JButton("Select");
		btnSelect.setForeground(Color.WHITE);
		btnSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelect.setBorder(new CompoundBorder(UIManager.getBorder("List.noFocusBorder"),
				new LineBorder(new Color(0, 0, 0), 2, true)));
		btnSelect.setBackground(Color.BLACK);
		btnSelect.setBounds(199, 423, 101, 29);
		btnSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.getSelectedValue();
				
			}
			
		});
		panel_1.add(btnSelect);

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

	public JList<Document> getMongo() {
		JList<Document> list1 = new JList<Document>(model);
		MongoDBConnector.connect();

		Bson projection = fields(include("title", "score", "viewCount"), excludeId());
		FindIterable<Document> iterDoc = MongoDBConnector.collection.find(eq("postTypeId", 1)).projection(projection)
				.sort(descending("score"));
		Iterator<Document> it = iterDoc.iterator();
		while (it.hasNext()) {
			model.addElement(it.next());
		}

		return list1;
	}
}
