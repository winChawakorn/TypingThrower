package gameui;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import connection.Controller;

public class WaitingUI {

	private JLabel message;
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
		message = new JLabel("Waiting for other player...");
		message.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
		message.setLocation(50, panel.getHeight() / 4);
		message.setSize(panel.getWidth(),
				(int) (message.getFont().getSize() * 1.25));

		panel.add(message);
		Controller.getInstance().findGame();
	}

	public JPanel getWaitingPanel() {
		return this.panel;
	}
}
