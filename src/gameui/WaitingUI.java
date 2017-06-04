package gameui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import connection.Controller;
import javax.swing.JButton;

/**
 * A waiting UI used when the player is finding a room.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class WaitingUI {

	private JTextArea message;
	private JPanel panel;
	private JButton btnCancel;
	private Font messageFont = CreatedFont.fontOfWaitMessage();

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
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/waitbg.png"));
					g.drawImage(img, 0, 0, 1280, 768, null);
				} catch (IOException e) {
					// do nothing
				}
			}
		};
		panel.setSize(1280, 768);
		panel.setLocation(0, 0);
		panel.setLayout(null);
		message = new JTextArea("Game has been created\nWaiting for other player...");
		message.setFont(messageFont);
		message.setLocation(50, panel.getHeight() / 4);
		message.setSize(panel.getWidth(), (int) (message.getFont().getSize() * 1.25 * 2));
		message.setForeground(Color.BLUE);
		message.setBackground(new Color(150, 190, 255, 190));
		message.setEditable(false);
		Icon icon = new ImageIcon(this.getClass().getResource("/res/rabbit2.gif-c200"));
		JLabel pic1 = new JLabel(icon);
		pic1.setSize(icon.getIconWidth(), icon.getIconHeight());
		pic1.setLocation(352, 444);
		panel.add(message);
		panel.add(pic1);
		btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(56, 195, 216));
		btnCancel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBounds(panel.getWidth() / 2, panel.getHeight() - (panel.getHeight() / 3),
				(int) (panel.getWidth() / 2.5), panel.getHeight() / 8);
		btnCancel.addActionListener((e) -> {
			Controller.getInstance().requestForCancel();
		});
		panel.add(btnCancel);
		Controller.getInstance().findGame();
	}
	/**
	 * Return this UI JPanel.
	 * 
	 * @return JPanel displying this waiting UI.
	 */
	public JPanel getWaitingPanel() {
		return this.panel;
	}
}
