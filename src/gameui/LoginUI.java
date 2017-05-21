package gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginUI {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel lblusername;
	private JButton btnLogin;
	private JButton button_1;
	private JLabel label_2;
	private JLabel lblPassword;
	private JLabel label_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
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
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("TypingThrower");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(247, 211, 84));
		label.setFont(new Font("ProFont for Powerline", Font.BOLD, 90));
		label.setBounds(6, 22, 1012, 96);
		frame.getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(211, 211, 211));
		panel.setBounds(135, 145, 750, 498);
		frame.getContentPane().add(panel);
		
		lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Courier New", Font.PLAIN, 37));
		lblusername.setBounds(115, 99, 183, 57);
		panel.add(lblusername);
		
		usernameField = new JTextField();
		usernameField.setFont(null);
		usernameField.setColumns(10);
		usernameField.setBounds(304, 99, 300, 50);
		panel.add(usernameField);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Courier New", Font.PLAIN, 37));
		lblPassword.setBounds(115, 180, 191, 57);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(304, 180, 300, 50);
		panel.add(passwordField);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("ProFont for Powerline", Font.PLAIN, 40));
		btnLogin.setBackground(Color.ORANGE);
		btnLogin.setBounds(292, 264, 161, 66);
		panel.add(btnLogin);
		
		button_1 = new JButton("Sign Up");
		button_1.setOpaque(true);
		button_1.setFont(null);
		button_1.setBorderPainted(false);
		button_1.setBackground(Color.RED);
		button_1.setBounds(428, 437, 135, 44);
		panel.add(button_1);
		
		label_2 = new JLabel("Don't have any account?");
		label_2.setFont(new Font("Courier New", Font.PLAIN, 19));
		label_2.setBounds(163, 444, 253, 28);
		panel.add(label_2);
		
		label_4 = new JLabel("");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Courier New", Font.PLAIN, 16));
		label_4.setBounds(6, 20, 749, 16);
		panel.add(label_4);
	}
}
