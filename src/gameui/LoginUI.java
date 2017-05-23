package gameui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import connection.Controller;
import connection.DatabaseConnect;
import connection.UserTable;

import java.awt.*;

public class LoginUI extends AbstractFont {

	private JTextField userField;
	private JPasswordField passwordField;
	private JLabel lblStatus, lblUsername, lblDontHaveAny, lblPassword;
	private JPanel loginPanel;
	private JPanel panel;
	private JButton btnLogin, btnSignUp;
	// private ConnectionSource source;
	// private Dao<UserTable, String> userDao;
	private java.util.List<UserTable> getDetailUser;
	private static UserTable currentUser;
	private DatabaseConnect dbConnect;

	/**
	 * Create the application.
	 */
	public LoginUI() {
		// try {
		// source = DatabaseConnect.getInstance();
		// userDao = DaoManager.createDao(source, UserTable.class);
		// } catch (SQLException e) {
		// System.out.println("Change to can't connect to server ui");
		// }
		initialize();
		dbConnect = DatabaseConnect.getInstance();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		loginPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/LoginBackground.jpg"));
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
		try {
			label.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.BOLD, 100));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		label.setBounds(6, 22, 1268, 96);
		loginPanel.add(label);

		panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(265, 145, 750, 498);
		panel.setLayout(null);
		loginPanel.add(panel);

		userField = new JTextField();
		try {
			userField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 20));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		userField.setBounds(304, 99, 300, 50);
		userField.setColumns(10);
		panel.add(userField);

		lblUsername = new JLabel("Username");
		lblUsername.setBounds(115, 99, 183, 57);
		lblUsername.setFont(new Font("Courier New", Font.PLAIN, 37));
		panel.add(lblUsername);

		passwordField = new JPasswordField();
		passwordField.setBounds(304, 180, 300, 50);
		panel.add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(292, 264, 161, 66);
		btnLogin.setBackground(Color.ORANGE);
		try {
			btnLogin.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 40));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		btnLogin.setBackground(Color.ORANGE);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginAction();
			}
		});
		panel.add(btnLogin);

		btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(428, 437, 135, 44);
		try {
			btnSignUp.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 25));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		btnSignUp.setBackground(Color.RED);
		btnSignUp.setBorderPainted(false);
		btnSignUp.setOpaque(true);
		btnSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getDetailUser = null;
				MainFrame.setFrame(new SignUpUI().getSignUpPanel());
			}
		});
		panel.add(btnSignUp);

		lblDontHaveAny = new JLabel("Don't have any account?");
		lblDontHaveAny.setBounds(163, 444, 253, 28);
		panel.add(lblDontHaveAny);
		lblDontHaveAny.setFont(new Font("Courier New", Font.PLAIN, 19));

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(115, 180, 191, 57);
		panel.add(lblPassword);
		lblPassword.setFont(new Font("Courier New", Font.PLAIN, 37));

		lblStatus = new JLabel("Welcome");
		lblStatus.setFont(new Font("Courier New", Font.BOLD, 18));
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(6, 20, 738, 28);
		panel.add(lblStatus);

		userField.addKeyListener(new LoginKeyAdapter());
		passwordField.addKeyListener(new LoginKeyAdapter());
		btnLogin.addKeyListener(new LoginKeyAdapter());

	}

	class LoginKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				loginAction();
			}
		}
	}

	public void loginAction() {
		// lblStatus.setVisible(false);
		// btnLogin.setEnabled(false);
		// userField.setFocusable(false);
		// passwordField.setFocusable(false);
		// btnSignUp.setEnabled(false);

		if (getDetailUser != null) {

			// try {
			// Controller.getInstance().joinServer();
			String username = userField.getText();
			String password = new String(passwordField.getPassword());
			boolean success = false;
			for (UserTable user : getDetailUser) {
				if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
					lblStatus.setForeground(new Color(17, 178, 19));
					lblStatus.setText("Login successed");
					System.err.println("Login successed");
					success = true;
					currentUser = user;
					MainFrame.setFrame(new HomeUI().getHomePanel());
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

			userField.setFocusable(true);
			passwordField.setFocusable(true);
			btnLogin.setEnabled(true);
			btnSignUp.setEnabled(true);
			// } catch (IOException e) {
			// System.out.println("<<<<");
			// MainFrame.addConnectionErrorUI(CantConnectUI.getCantConnectPane());
			// }
		} else
			getDetailUser = dbConnect.pullAllUserdata();
	}

	public JPanel getLoginPanel() {
		return loginPanel;
	}

	public static UserTable getCurrentUser() {
		return currentUser;
	}
}
