package gameui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CantConnectUI {

//	private JFrame frame;
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
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(0, 0, 1280, 768);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
		
		cantConnectPane = new JPanel() {
			{
				add(new JLabel());
				setBackground(new Color(0, 0, 0, 150));
			}
		};
		cantConnectPane.setBounds(0, 0, 1280, 768);
		
//		frame.getContentPane().add(cantConnectPane, BorderLayout.SOUTH);
		cantConnectPane.setLayout(null);
		
		lblDetail = new JLabel("Can't connect to the server.");
		lblDetail.setForeground(Color.WHITE);
		lblDetail.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail.setFont(new Font("Courier New", Font.PLAIN, 25));
		lblDetail.setBounds(6, 217, 1268, 63);
		cantConnectPane.add(lblDetail);
		
		lblDetail2 = new JLabel("Please check your internet connection or contact game master.");
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
				
			}
		});
		cantConnectPane.add(btnOK);

		
		ImageIcon gifImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/res/dinosaur.gif")).getImage().getScaledInstance(120, 120, java.awt.Image.SCALE_DEFAULT));
		lblgif = new JLabel(gifImage);
		lblgif.setBounds(874, 141, 120, 120);
		cantConnectPane.add(lblgif);
	}
	
	public static JPanel getCantConnectPane(){
		if(cantConnectPane == null)
			new CantConnectUI();
		return cantConnectPane;
	}
}
