package gameui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.PropertyConfigurator;

import connection.Client;
import connection.Controller;

public class MainFrame {

	private int FRAME_WIDTH = 1280;
	private int FRAME_HEIGHT = 768;
	private static JFrame frame;
	private JPanel connectionErrorPane;

	public static JFrame getFrame() {
		return frame;

	}

	public static void setFrame(JPanel newPane) {
		frame.getContentPane().removeAll();
		frame.repaint();
		newPane.setBounds(0, 0, 1280, 768);
		frame.getContentPane().add(newPane);
		newPane.setVisible(true);
		newPane.setFocusable(true);
		newPane.requestFocusInWindow();
	}

	public static void showConnectionErrorUI() {
		frame.getGlassPane().setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		MainFrame main = new MainFrame();
		main.run();
	}

	public void run() {
		frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		Controller ctrl = Controller.getInstance();
		ctrl.setClient(new Client("", 3001));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Typing Thrower");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(((int) dim.getWidth() - FRAME_WIDTH) / 2,
				((int) dim.getHeight() - FRAME_HEIGHT) / 2, FRAME_WIDTH,
				FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		connectionErrorPane = CantConnectUI.getCantConnectPane();
		connectionErrorPane.setFocusable(false);
		frame.setGlassPane(connectionErrorPane);

		setFrame(new LoginUI().getLoginPanel());
	}

}
