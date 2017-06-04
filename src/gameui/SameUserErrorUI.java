package gameui;

import javax.swing.*;
import java.awt.*;

/**
 * This class create panel which show when application can't connect to the
 * database or the server. And, it returns the panel for using in MainFrame.
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class SameUserErrorUI {

	private static JPanel sameUserErrorPane;
	private JLabel lblDetail, lblDetail2;
	private JButton btnOK;
	private JLabel lblgif;

	/**
	 * Create the application.
	 */
	public SameUserErrorUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the <code>cantCaonnectPane</code>.
	 */
	private void initialize() {

		sameUserErrorPane = new JPanel();
		sameUserErrorPane.setBackground(new Color(0, 0, 0, 190));
		sameUserErrorPane.setBounds(0, 0, 1280, 768);
		sameUserErrorPane.setLayout(null);

		lblDetail = new JLabel("Can't Login.");
		lblDetail.setForeground(Color.WHITE);
		lblDetail.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail.setFont(new Font("Courier New", Font.PLAIN, 25));
		lblDetail.setBounds(16, 277, 1268, 63);
		sameUserErrorPane.add(lblDetail);

		lblDetail2 = new JLabel("This account is already login. Please try again later.");
		lblDetail2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail2.setFont(new Font("Courier New", Font.PLAIN, 25));
		lblDetail2.setForeground(Color.WHITE);
		lblDetail2.setBounds(16, 352, 1268, 63);
		sameUserErrorPane.add(lblDetail2);

		btnOK = new JButton("OK");
		btnOK.setForeground(Color.BLUE);
		btnOK.setFont(new Font("Courier New", Font.PLAIN, 30));
		btnOK.setBounds(556, 469, 168, 51);
		btnOK.addActionListener((e) -> {
				closeSameUserErrorPane();
		});
		btnOK.addKeyListener(new EnterAndTypeLimitKeyAdapter(this));
		sameUserErrorPane.add(btnOK);

		ImageIcon gifImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/res/sorry.gif-c200")).getImage()
				.getScaledInstance(200, 200, java.awt.Image.SCALE_DEFAULT));
		lblgif = new JLabel(gifImage);
		lblgif.setBounds(535, 40, 200, 200);
		sameUserErrorPane.add(lblgif);
	}

	/**
	 * get panel of that show the error of can't connect to server
	 * 
	 * @return can't connect to sever panel
	 */
	public static JPanel getSameUserErrorPane() {
		if (sameUserErrorPane == null)
			new SameUserErrorUI();
		return sameUserErrorPane;
	}
	
	/**
	 * close panel of can't connect to server and change to Login interface
	 */
	public void closeSameUserErrorPane() {
		sameUserErrorPane.setVisible(false);
		MainFrame.setPane(new LoginUI().getLoginPanel());
	}
}
