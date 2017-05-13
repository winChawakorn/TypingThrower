package gameui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Login page is used to indicate who is the user for getting the detail of the
 * user.
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class LoginUI extends JFrame {
	private JButton loginbtn,createbtn;
	private JLabel gameName;
	private JTextField usernameField, passwordField;
	private JPanel loginPanel;
	
	/**
	 * set default for the view
	 */
	public LoginUI() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(((int) dim.getWidth()-800)/2, ((int) dim.getHeight()-600)/2 ,800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("TypingThrower Game");
		initComponents();
	}

	/**
	 * Initialize all component
	 */
	public void initComponents() {
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		int x = 100;
		Font font = new Font("Comic Sans MS", Font.PLAIN, 30);
		
		gameName = new JLabel("Typing Thrower");
		gameName.setFont(new Font("American Typewriter", Font.BOLD, 60));
//		gameName.setForeground(Color.GREEN);
		gameName.setAlignmentX(CENTER_ALIGNMENT);
		
		usernameField = new JTextField(30);
		usernameField.setPreferredSize(new Dimension(300, 50));
		usernameField.setAlignmentX(CENTER_ALIGNMENT);
		
		
		passwordField = new JTextField(30);
		
		loginbtn = new JButton("   Sign in   ");
		loginbtn.setFont(font);
		loginbtn.setAlignmentX(CENTER_ALIGNMENT);
//		loginbtn.setPreferredSize(new Dimension(400, 50));
		loginbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		createbtn = new JButton("   Sign up   ");
		createbtn.setFont(font);
		createbtn.setAlignmentX(CENTER_ALIGNMENT);
//		createbtn.setPreferredSize(new Dimension(400, 50));
		createbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		loginPanel.add(gameName);
		loginPanel.add(Box.createVerticalGlue());
		loginPanel.add(usernameField);
		loginPanel.add(Box.createVerticalGlue());
		loginPanel.add(passwordField);
		loginPanel.add(Box.createVerticalGlue());
		loginPanel.add(loginbtn);
		loginPanel.add(Box.createVerticalGlue());
		loginPanel.add(createbtn);
		loginPanel.add(Box.createVerticalGlue());
		this.add(loginPanel);
	}

	/**
	 * run this view
	 */
	public void run() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		LoginUI ui = new LoginUI();
		ui.run();
		
	}
}
