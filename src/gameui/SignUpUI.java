package gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
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

	private JPanel signUpPanel;
	private JPasswordField passwordField, ConfirmPasswordField;
	private JTextField usernameField, characterField;
	private JLabel lblUsername, lblCharacter, lblPass, lblConfirmPass, status;
	private JButton btnConfirm, btnCancel;
//	private ConnectionSource source;
//	private Dao<UserTable, String> userDao;
	private DatabaseConnect dbConnect;

	/**
	 * Create the panel.
	 */
	public SignUpUI() {
		dbConnect = DatabaseConnect.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {

		signUpPanel = new JPanel() {
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
//		signUpPanel.setPreferredSize(new Dimension(1280, 768));
		signUpPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(265, 145, 750, 498);

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
			usernameField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 25));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		usernameField.setColumns(10);
		usernameField.setBounds(343, 180, 300, 50);
		usernameField.addKeyListener(new ConfirmKeyAdapter(usernameField));
		panel.add(usernameField);

		lblPass = new JLabel("Password");
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setFont(new Font("Courier New", Font.PLAIN, 30));
		lblPass.setBounds(176, 247, 155, 57);
		panel.add(lblPass);

		passwordField = new JPasswordField();
		passwordField.setBounds(343, 250, 300, 50);
		passwordField.addKeyListener(new ConfirmKeyAdapter(passwordField));
		panel.add(passwordField);

		lblConfirmPass = new JLabel("Confirm password");
		lblConfirmPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPass.setFont(new Font("Courier New", Font.PLAIN, 30));
		lblConfirmPass.setBounds(31, 320, 300, 50);
		panel.add(lblConfirmPass);

		ConfirmPasswordField = new JPasswordField();
		ConfirmPasswordField.setBounds(343, 320, 300, 50);
		ConfirmPasswordField.addKeyListener(new ConfirmKeyAdapter(ConfirmPasswordField));
		panel.add(ConfirmPasswordField);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(343, 414, 174, 66);
		panel.add(btnConfirm);
		try {
			btnConfirm.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 40));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
			characterField.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 25));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		characterField.setColumns(10);
		characterField.setBounds(343, 110, 300, 50);
		characterField.addKeyListener(new ConfirmKeyAdapter(characterField));
		panel.add(characterField);

		signUpPanel.add(panel);

		btnCancel = new JButton("Cancel");
		try {
			btnCancel.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.PLAIN, 40));
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
			lblGameName.setFont(getFont("ProFont For Powerline.ttf").deriveFont(Font.BOLD, 100));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		lblGameName.setBounds(6, 22, 1268, 96);
		signUpPanel.add(lblGameName);
	}

	public void confirmAction() {
			
			String characterName = characterField.getText();
			if (characterName.equals("")) {
				status.setForeground(Color.RED);
				status.setText("please fill in your character name.");
				System.err.println("character is emtry");
			} else {
				// checking is new username exist?
				String inputUsername = usernameField.getText();
				if (dbConnect.isUserExist(inputUsername)) {
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
						dbConnect.createUser(user);
						status.setForeground(new Color(17, 178, 19));
						status.setText("creating success");
						backToLogin();
						
					}
				}
			}

	}

	/**
	 * Provide pressing enter to sign up and limit number of character when typing
	 * on the text field
	 * 
	 * @author vittunyutamaeprasart
	 *
	 */
	class ConfirmKeyAdapter extends KeyAdapter {
		private JTextField textfield;
		
		public ConfirmKeyAdapter(JTextField textField) {
			textfield = textField;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (textfield.getText().length() >= 20) {
				e.consume();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				confirmAction();
			}
		}
	}
	
	private void backToLogin(){
		characterField.setText("");
		usernameField.setText("");
		passwordField.setText("");
		ConfirmPasswordField.setText("");
		status.setText("");
		MainFrame.setFrame(new LoginUI().getLoginPanel());
		
	}
	
	public JPanel getSignUpPanel(){
		return signUpPanel;
	}

}
