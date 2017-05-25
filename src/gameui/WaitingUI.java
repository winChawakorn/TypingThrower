package gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import connection.Controller;

import javax.swing.JButton;

public class WaitingUI {

	private JTextArea message;
	private JPanel panel;
	private JButton btnCancel;

	/**
	 * Create the application.
	 */
	public WaitingUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// panel = new JPanel();
		// panel.setBackground(new Color(255, 251, 175));
		//
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass()
							.getResourceAsStream("/res/waitbg.png"));
					g.drawImage(img, 0, 0, 1280, 768, null);
				} catch (IOException e) {
					// do nothing
				}
			}
		};

		panel.setSize(1280, 768);
		panel.setLocation(0, 0);
		panel.setLayout(null);
		message = new JTextArea(
				"Game has been created\nWaiting for other player...");
		message.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 90));
		message.setLocation(50, panel.getHeight() / 4);
		message.setSize(panel.getWidth(),
				(int) (message.getFont().getSize() * 1.25 * 2));
		message.setForeground(Color.BLUE);
		message.setBackground(new Color(150, 190, 255, 190));
		message.setEditable(false);
		// message.setOpaque(false);
		Icon icon = new ImageIcon(this.getClass().getResource(
				"/res/rabbit2.gif-c200"));
		JLabel pic1 = new JLabel(icon);
		pic1.setSize(icon.getIconWidth(), icon.getIconHeight());
		pic1.setLocation(352, 444);
		panel.add(message);
		panel.add(pic1);

		btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(56, 195, 216));
		btnCancel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBounds(panel.getWidth() / 2,
				panel.getHeight() - (panel.getHeight() / 3),
				(int) (panel.getWidth() / 2.5), panel.getHeight() / 8);
		btnCancel.addActionListener((e) -> {
			Controller.getInstance().requestForCancel();
		});
		panel.add(btnCancel);
		Controller.getInstance().findGame();
	}

	public JPanel getWaitingPanel() {
		return this.panel;
	}
}
