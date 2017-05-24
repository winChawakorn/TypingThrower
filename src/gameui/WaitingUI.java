package gameui;

import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import connection.Controller;

public class WaitingUI {

	private JTextArea message;
	private JPanel panel;

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
		panel = new JPanel();
		panel.setSize(1280, 768);
		panel.setLocation(0, 0);
		panel.setLayout(null);
		message = new JTextArea(
				"Game has been created\nWaiting for other player...");
		message.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
		message.setLocation(50, panel.getHeight() / 4);
		message.setSize(panel.getWidth(),
				(int) (message.getFont().getSize() * 1.25 * 2));
		message.setEditable(false);
		message.setOpaque(false);
		Icon icon = new ImageIcon(this.getClass().getResource(
				"/res/rabbit2.gif-c200"));
		JLabel pic1 = new JLabel(icon);
		pic1.setSize(icon.getIconWidth(), icon.getIconHeight());
		pic1.setLocation(message.getX(), message.getY() + message.getHeight());
		panel.add(message);
		panel.add(pic1);
		Controller.getInstance().findGame();
	}

	public JPanel getWaitingPanel() {
		return this.panel;
	}
}
