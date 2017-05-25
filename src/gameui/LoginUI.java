package gameui;

import game.Player;
import game.TypingThrower;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

import connection.Controller;
import connection.DatabaseConnect;
import connection.UserTable;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Login interface is the first page that user will see. User can choose to
 * login (can play online) or practice (play with bot without score recording).
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class LoginUI extends AbstractFont {

	private JTextField userField;
	private JPasswordField passwordField;
	private JLabel lblStatus, lblUsername, lblDontHaveAny, lblPassword;
	private JPanel loginPanel;
	private JPanel panel;
	private JButton btnLogin, btnSignUp;
	private java.util.List<UserTable> getDetailUser;
	private static UserTable currentUser;
	private DatabaseConnect dbConnect;
	private JButton btnPractice;
	private JButton btnQuit;

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
		dbConnect = DatabaseConnect.getInstance();
		currentUser = null;
		getDetailUser = null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		loginPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass()
							.getResourceAsStream("/res/LoginBackground.jpg"));
					g.drawImage(img, 0, 0, 1280, 768, null);
				} catch (IOException e) {
					// do nothing
				}
			}
		};
		loginPanel.setPreferredSize(new Dimension(1280, 768));
		loginPanel.setLayout(null);

		JLabel label = new JLabel("TypingThrower");
		label.setForeground(new Color(247, 211, 84));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(getFont("ProFont For Powerline.ttf").deriveFont(
				Font.BOLD, 100));
		label.setBounds(6, 22, 1268, 96);
		loginPanel.add(label);

		panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(265, 145, 750, 498);
		panel.setLayout(null);
		loginPanel.add(panel);

		userField = new JTextField();
		userField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(
				Font.PLAIN, 20));
		userField.setBounds(304, 64, 300, 50);

		userField.setColumns(10);
		userField.addKeyListener(new EnterAndTypeLimitKeyAdapter(userField,
				this));
		panel.add(userField);

		lblUsername = new JLabel("Username");
		lblUsername.setBounds(115, 64, 183, 57);
		lblUsername.setFont(new Font("Courier New", Font.PLAIN, 37));
		panel.add(lblUsername);

		passwordField = new JPasswordField();
		passwordField.setBounds(304, 140, 300, 50);
		passwordField.addKeyListener(new EnterAndTypeLimitKeyAdapter(
				passwordField, this));
		panel.add(passwordField);

		Font fontForBtn = getFont("ProFont For Powerline.ttf").deriveFont(
				Font.PLAIN, 40);
		btnLogin = new JButton("Login");
		btnLogin.setBounds(296, 227, 161, 66);
		btnLogin.setFont(fontForBtn);
		btnLogin.setBackground(new Color(102, 186, 255));
		btnLogin.addActionListener((e) -> {
			loginAction();
		});
		btnLogin.addKeyListener(new EnterAndTypeLimitKeyAdapter(this));
		panel.add(btnLogin);

		btnQuit = new JButton("Quit");
		btnQuit.setFont(fontForBtn);
		btnQuit.setBackground(new Color(244, 174, 9));
		btnQuit.setBounds(529, 227, 127, 66);
		btnQuit.addActionListener((e) -> {
			System.exit(0);
		});
		btnQuit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
					System.exit(0);
			}
		});
		panel.add(btnQuit);

		btnPractice = new JButton("Practice");
		btnPractice.setFont(fontForBtn);
		btnPractice.setBackground(new Color(186, 102, 255));
		btnPractice.setBounds(275, 327, 205, 66);
		btnPractice.addActionListener((e) -> {
			GameUI ui = new GameUI();
			ui.setGame(new TypingThrower(new Player("You", 1500, 10),
					new Player("CPU", 1500, 10)));
			ui.initComponent();
			ui.offlineGame();
			MainFrame.setFrame(ui.getGamePanel());
		});
		panel.add(btnPractice);

		btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(428, 437, 135, 44);
		btnSignUp.setFont(getFont("ProFont For Powerline.ttf").deriveFont(
				Font.PLAIN, 25));
		btnSignUp.setBackground(new Color(237, 80, 104));
		btnSignUp.setBorderPainted(false);
		btnSignUp.setOpaque(true);
		btnSignUp.addActionListener((e) -> {
			getDetailUser = null;
			MainFrame.setFrame(new SignUpUI().getSignUpPanel());
		});
		panel.add(btnSignUp);

		lblDontHaveAny = new JLabel("Don't have any account?");
		lblDontHaveAny.setBounds(163, 444, 253, 28);
		panel.add(lblDontHaveAny);
		lblDontHaveAny.setFont(new Font("Courier New", Font.PLAIN, 19));

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(115, 137, 191, 57);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Courier New", Font.PLAIN, 37));

		lblStatus = new JLabel("Welcome");
		lblStatus.setFont(new Font("Courier New", Font.BOLD, 23));
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(6, 6, 738, 42);
		panel.add(lblStatus);

		JLabel lblOr = new JLabel("Or");
		lblOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblOr.setFont(new Font("Courier New", Font.BOLD, 20));
		lblOr.setBounds(6, 295, 738, 28);
		panel.add(lblOr);

	}

	/**
	 * Action is used when user is loging-in. It check the connection to
	 * database and server. If application can connect, it will check username
	 * and password to the database.
	 */
	public void loginAction() {
		if (getDetailUser == null)
			getDetailUser = dbConnect.pullAllUserdata();

		if (getDetailUser != null) {

			try {
				Controller.getInstance().setIsJoinServer(false);
				Controller.getInstance().joinServer();
				String username = userField.getText();
				String password = new String(passwordField.getPassword());
				boolean success = false;
				for (UserTable user : getDetailUser) {
					if (username.equals(user.getUsername())
							&& password.equals(user.getPassword())) {
						lblStatus.setForeground(new Color(17, 178, 19));
						lblStatus.setText("Login successed");
						// System.err.println("Login successed");
						success = true;
						currentUser = user;
						Controller.getInstance().login();
						return;
					}
				}
				if (!success) {
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							lblStatus.setVisible(true);
						}
					}, 100);
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Wrong username/password");
					System.err.println("Login failed");
				}

			} catch (IOException e) {
				userField.setFocusable(false);
				passwordField.setFocusable(false);
				btnLogin.setEnabled(false);
				btnSignUp.setEnabled(false);
				System.out.println("Can't connect to server while logging in");
				MainFrame.showConnectionErrorUI();
			}
		}
	}

	/**
	 * Get this login panel for using in MainFrame.
	 * 
	 * @return login panel
	 */
	public JPanel getLoginPanel() {
		return loginPanel;
	}

	/**
	 * This Login class know who login. So return the user data for using
	 * outside this class.
	 * 
	 * @return the user who login success.
	 */
	public static UserTable getCurrentUser() {
		return currentUser;
	}
}
