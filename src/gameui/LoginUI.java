package gameui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import connection.DatabaseConnect;
import connection.UserTable;

import java.awt.*;

public class LoginUI extends AbstractFont {

	private JFrame frame;
	private JTextField userField;
	private JPasswordField passwordField;
	private JLabel lblStatus, lblUsername, lblDontHaveAny, lblPassword;
	private JPanel backgroundPanel;
	private JPanel panel;
	private JButton btnLogin, btnSignUp, btnCancel;
	private ConnectionSource source;
	private Dao<UserTable, String> userDao;
	private java.util.List<UserTable> getDetailUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(250, 240, 230));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(((int) dim.getWidth() - 1024) / 2, ((int) dim.getHeight() - 768) / 2, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		backgroundPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/LoginBackground.jpg"));
					// g.drawImage(img, 0, 0, frame.getSize().width,
					// frame.getSize().height, null);
					g.drawImage(img, 0, 0, 1024, 768, null);

				} catch (IOException e) {
					// do nothing
				}
			}
		};
		backgroundPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		backgroundPanel.setLayout(null);

		JLabel label = new JLabel("TypingThrower");
		label.setForeground(new Color(247, 211, 84));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			label.setFont(new Font("ProFont for Powerline", Font.BOLD, 90));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		label.setBounds(6, 22, 1012, 96);
		backgroundPanel.add(label);

		panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(135, 145, 750, 498);
		panel.setLayout(null);
		backgroundPanel.add(panel);

		frame.getContentPane().add(backgroundPanel);

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
		try {
			btnLogin.setFont(new Font("ProFont for Powerline", Font.PLAIN, 40));
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
				lblStatus.setText("");
				getDetailUser = null;
				backgroundPanel.setVisible(false);
				frame.getContentPane().removeAll();
				JPanel signUpPanel = new SignUpUI().initialize();
				signUpPanel.setBounds(0, 0, 1024, 768);
				frame.getContentPane().add(signUpPanel);
				signUpPanel.setVisible(true);

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

		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Courier New", Font.PLAIN, 16));
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(6, 20, 749, 16);
		panel.add(lblStatus);
		userField.addKeyListener(new LoginKeyAdapter());
		passwordField.addKeyListener(new LoginKeyAdapter());
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
	
	public JPanel getLoginPanel(){
		return backgroundPanel;
	}

	public void loginAction() {
		if (getDetailUser == null) {
			try {
				source = DatabaseConnect.getInstance();
				userDao = DaoManager.createDao(source, UserTable.class);
				getDetailUser = userDao.queryForAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String username = userField.getText();
		String password = new String(passwordField.getPassword());
		boolean success = false;
		for (UserTable user : getDetailUser) {
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				lblStatus.setForeground(new Color(17, 178, 19));
				lblStatus.setText("Login successed");
				System.err.println("Login successed");
				success = true;
				break;
			}
		}
		if (!success) {
			lblStatus.setForeground(Color.RED);
			lblStatus.setText("Wrong username/password");
			System.err.println("Login failed");
		}
	}
	
	class SignUpUI extends AbstractFont {

		// private JFrame frame;
		private JPanel backgroundPanel;
		private JPasswordField passwordField, ConfirmPasswordField;
		private JTextField usernameField, characterField;
		private JLabel lblUsername, lblCharacter, lblPass, lblConfirmPass, status;
		private JButton btnConfirm;
		private ConnectionSource source;
		private Dao<UserTable, String> userDao;
		private java.util.List<UserTable> getDetailUser;

		/**
		 * Create the panel.
		 */
		public SignUpUI() {
			getDetailUser = null;
		}

		/**
		 * Initialize the contents of the frame.
		 * 
		 * @wbp.parser.entryPoint
		 */
		public JPanel initialize() {

			backgroundPanel = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					try {
						BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/LoginBackground.jpg"));
						Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
						;
						// g.drawImage(img, ((int) dim.getWidth() - 1024) / 2,
						// ((int) dim.getHeight() - 768) / 2, 1024, 768,null);
						g.drawImage(img, 0, 0, 1024, 768, null);

					} catch (IOException e) {
						// do nothing
					}
				}
			};
			backgroundPanel.setLayout(null);

			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(new Color(211, 211, 211));
			panel.setBounds(135, 145, 750, 498);

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
			usernameField.addKeyListener(new ConfirmKeyAdapter());
			panel.add(usernameField);

			lblPass = new JLabel("Password");
			lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPass.setFont(new Font("Courier New", Font.PLAIN, 30));
			lblPass.setBounds(176, 247, 155, 57);
			panel.add(lblPass);

			passwordField = new JPasswordField();
			passwordField.setBounds(343, 250, 300, 50);
			passwordField.addKeyListener(new ConfirmKeyAdapter());
			panel.add(passwordField);

			lblConfirmPass = new JLabel("Confirm password");
			lblConfirmPass.setHorizontalAlignment(SwingConstants.RIGHT);
			lblConfirmPass.setFont(new Font("Courier New", Font.PLAIN, 30));
			lblConfirmPass.setBounds(31, 320, 300, 50);
			panel.add(lblConfirmPass);

			ConfirmPasswordField = new JPasswordField();
			ConfirmPasswordField.setBounds(343, 320, 300, 50);
			ConfirmPasswordField.addKeyListener(new ConfirmKeyAdapter());
			panel.add(ConfirmPasswordField);

			btnConfirm = new JButton("Confirm");
			btnConfirm.setBounds(343, 414, 174, 66);
			panel.add(btnConfirm);
			btnConfirm.setFont(new Font("ProFont for Powerline", Font.PLAIN, 40));
			btnConfirm.setBackground(Color.ORANGE);
			btnConfirm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					confirmAction();

				}
			});

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
				characterField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 20));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			characterField.setColumns(10);
			characterField.setBounds(343, 110, 300, 50);
			characterField.addKeyListener(new ConfirmKeyAdapter());
			panel.add(characterField);

			backgroundPanel.add(panel);

			btnCancel = new JButton("Cancel");
			try {
				btnCancel.setFont(new Font("ProFont for Powerline", Font.PLAIN, 40));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			btnCancel.setBackground(new Color(255, 99, 71));
			btnCancel.setBounds(540, 414, 174, 66);
			btnCancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					backToLogin();
					
				}
			});
			panel.add(btnCancel);
			
			JLabel lblGameName = new JLabel("TypingThrower");
			lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
			lblGameName.setForeground(new Color(247, 211, 84));
			try {
				lblGameName.setFont(new Font("ProFont for Powerline", Font.BOLD, 90));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			lblGameName.setBounds(6, 22, 1012, 96);
			backgroundPanel.add(lblGameName);

			return backgroundPanel;
		}

		public void confirmAction() {
			try {
				if (getDetailUser == null) {
					source = DatabaseConnect.getInstance();
					userDao = DaoManager.createDao(source, UserTable.class);
					getDetailUser = userDao.queryForAll();
				}
				String characterName = characterField.getText();
				if (characterName.equals("")) {
					status.setForeground(Color.RED);
					status.setText("please fill in your character name.");
					System.err.println("character is emtry");
				} else {
					// checking is new username exist?
					String inputUsername = usernameField.getText();
					UserTable username = userDao.queryForId(inputUsername);
					if (username != null) {
						System.err.println("username is already exist");
						status.setForeground(Color.RED);
						status.setText("username is already exist. please change username.");
					} else if (inputUsername.equals("")) {
						status.setForeground(Color.RED);
						status.setText("please fill in an username.");
						System.err.println("username is emtry");
					} else {
						// checking password and confirm password are match
						String password = new String(passwordField.getPassword());
						String conPassword = new String(ConfirmPasswordField.getPassword());
						if (password.equals("") || conPassword.equals("")) {
							status.setText("please fill in passwords");
							System.err.println("one of passwords is emtry");
						} else if (!password.equals(conPassword)) {
							status.setText("error: passwords don't match");
							System.err.println("password and confirm password don't match.");
						} else {
							status.setText("");

							// create account in database
							UserTable user = new UserTable(inputUsername, password, characterName);
							userDao.createIfNotExists(user);
							status.setForeground(new Color(17, 178, 19));
							status.setText("creating success");
							backToLogin();
							
						}
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		class ConfirmKeyAdapter extends KeyAdapter {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					confirmAction();
				}
			}
		}
		
		private void backToLogin(){
			getDetailUser = null;
			backgroundPanel.setVisible(false);
			frame.getContentPane().removeAll();
			JPanel loginPanel = getLoginPanel();
			loginPanel.setBounds(0, 0, 1024, 768);
			frame.getContentPane().add(loginPanel);
			loginPanel.setVisible(true);
		}
		
	}
}
