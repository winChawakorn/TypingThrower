package gameui;

import javax.swing.*;

import stopwatch.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class create panel which show when application can't connect to the
 * database or the server. And, it returns the panel for using in MainFrame.
 * 
 * @author vittunyutamaeprasart
 *
 */
public class CantConnectUI {

	private static JPanel cantConnectPane;
	private JLabel lblDetail;
	private JLabel lblDetail2;
	private JButton btnOK;
	private JLabel lblgif;

	/**
	 * Create the application.
	 */
	public CantConnectUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the <code>cantCaonnectPane</code>.
	 */
	private void initialize() {

		cantConnectPane = new JPanel() {
			{
				// add(new JLabel());
				setBackground(new Color(0, 0, 0, 190));
			}
		};
		cantConnectPane.setBounds(0, 0, 1280, 768);
		cantConnectPane.setLayout(null);

		lblDetail = new JLabel("Can't connect to the server.");
		lblDetail.setForeground(Color.WHITE);
		lblDetail.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail.setFont(new Font("Courier New", Font.PLAIN, 25));
		lblDetail.setBounds(6, 217, 1268, 63);
		cantConnectPane.add(lblDetail);

		lblDetail2 = new JLabel(
				"Please check your internet connection or contact game master.");
		lblDetail2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail2.setFont(new Font("Courier New", Font.PLAIN, 25));
		lblDetail2.setForeground(Color.WHITE);
		lblDetail2.setBounds(6, 292, 1268, 63);
		cantConnectPane.add(lblDetail2);

		btnOK = new JButton("OK");
		btnOK.setForeground(Color.BLUE);
		btnOK.setFont(new Font("Courier New", Font.PLAIN, 30));
		btnOK.setBounds(556, 469, 168, 51);
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cantConnectPane.setVisible(false);
				MainFrame.setFrame(new LoginUI().getLoginPanel());
			}
		});
		cantConnectPane.add(btnOK);

		ImageIcon gifImage = new ImageIcon(new ImageIcon(this.getClass()
				.getResource("/res/dinosaur.gif")).getImage()
				.getScaledInstance(120, 120, java.awt.Image.SCALE_DEFAULT));
		lblgif = new JLabel(gifImage);
		lblgif.setBounds(874, 141, 120, 120);
		cantConnectPane.add(lblgif);
	}

	/**
	 * get panel of that show the error of can't connect to server
	 * 
	 * @return can't connect to sever panel
	 */
	public static JPanel getCantConnectPane() {
		if (cantConnectPane == null)
			new CantConnectUI();
		return cantConnectPane;
	}
}
