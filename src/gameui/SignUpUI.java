package gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import connection.DatabaseConnect;
import connection.UserTable;

public class SignUpUI extends AbstractFont {

	private JFrame frame;
	private JPanel backgroundPanel;
	private JPasswordField ConfirmPasswordField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField characterField;
	private JLabel lblCharacter;
	private JLabel lblUsername;
	private JLabel lblPass;
	private JLabel lblConfirmPass;
	private JButton btnConfirm;
	private ConnectionSource source;
	private Dao<UserTable, String> userDao;
	private java.util.List<UserTable> getDetailUser;
	private JLabel status;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpUI window = new SignUpUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignUpUI() {
		getDetailUser = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(((int) dim.getWidth() - 1024) / 2, ((int) dim.getHeight() - 768) / 2, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		backgroundPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/LoginBackground.jpg"));
					g.drawImage(img, 0, 0, frame.getSize().width, frame.getSize().height, null);
				} catch (IOException e) {
					// do nothing
				}
			}
		};
		backgroundPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		backgroundPanel.setLayout(null);
		frame.getContentPane().add(backgroundPanel);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(130, 156, 750, 498);
		backgroundPanel.add(panel);

		JLabel lblTitle = new JLabel("Create your own character");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Courier New", Font.BOLD, 35));
		lblTitle.setBounds(6, 6, 749, 50);
		panel.add(lblTitle);

		lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Courier New", Font.PLAIN, 30));
		lblUsername.setBounds(148, 183, 183, 44);
		panel.add(lblUsername);

		usernameField = new JTextField();
		try {
			usernameField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 20));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		usernameField.setColumns(10);
		usernameField.setBounds(343, 180, 300, 50);
		panel.add(usernameField);

		lblPass = new JLabel("Password");
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setFont(new Font("Courier New", Font.PLAIN, 30));
		lblPass.setBounds(176, 247, 155, 57);
		panel.add(lblPass);

		passwordField = new JPasswordField();
		passwordField.setBounds(343, 250, 300, 50);
		panel.add(passwordField);

		lblConfirmPass = new JLabel("Confirm password");
		lblConfirmPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPass.setFont(new Font("Courier New", Font.PLAIN, 30));
		lblConfirmPass.setBounds(31, 320, 300, 50);
		panel.add(lblConfirmPass);

		ConfirmPasswordField = new JPasswordField();
		ConfirmPasswordField.setBounds(343, 320, 300, 50);
		panel.add(ConfirmPasswordField);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(343, 414, 174, 66);
		panel.add(btnConfirm);
		btnConfirm.setFont(new Font("ProFont for Powerline", Font.PLAIN, 40));
		btnConfirm.setBackground(Color.ORANGE);

		status = new JLabel("");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setFont(new Font("Courier New", Font.BOLD, 18));
		status.setBounds(6, 75, 749, 28);
		panel.add(status);

		lblCharacter = new JLabel("Character name");
		lblCharacter.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCharacter.setFont(new Font("Courier New", Font.PLAIN, 30));
		lblCharacter.setBounds(56, 115, 275, 44);
		panel.add(lblCharacter);

		characterField = new JTextField();
		try {
			usernameField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 20));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		characterField.setColumns(10);
		characterField.setBounds(343, 110, 300, 50);
		panel.add(characterField);

		JLabel lblGameName = new JLabel("TypingThrower");
		lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameName.setForeground(new Color(247, 211, 84));
		lblGameName.setFont(new Font("ProFont for Powerline", Font.BOLD, 90));
		lblGameName.setBounds(6, 6, 1012, 96);
		backgroundPanel.add(lblGameName);
	}

	public void confirmAction() {
		try {
			if (getDetailUser == null) {
				source = DatabaseConnect.getInstance();
				userDao = DaoManager.createDao(source, UserTable.class);
				getDetailUser = userDao.queryForAll();
			}
			String inputUsername = usernameField.getText();
			UserTable username = userDao.queryForId(inputUsername);
			if(username != null){
				status.setForeground(Color.RED);
				status.setText("username is already exist");
				System.err.println("username is already exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// checking is new username exist?

	}
}
