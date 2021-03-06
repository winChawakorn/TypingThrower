package gameui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.PropertyConfigurator;

import connection.Client;
import connection.Controller;

/**
 * Main class use to run the application. This class provide main frame which
 * can set current panel.
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class MainFrame {

	private int FRAME_WIDTH = 1280;
	private int FRAME_HEIGHT = 768;
	private static String ip = "";
	private static JFrame frame;

	/**
	 * set frame to show a given panel.
	 * 
	 * @param newPane
	 *            is current panel to be showed
	 */
	public static void setPane(JPanel newPane) {
		frame.getContentPane().removeAll();
		frame.repaint();
		newPane.setBounds(0, 0, 1280, 768);
		frame.getContentPane().add(newPane);
		newPane.setVisible(true);
		newPane.setFocusable(true);
		newPane.requestFocusInWindow();
	}

	/**
	 * set frame to show same user login error panel
	 */
	public static void showSameUserErrorUI() {
		frame.setGlassPane(SameUserErrorUI.getSameUserErrorPane());
		Controller.getInstance().setIsJoinServer(false);
		frame.getGlassPane().setVisible(true);
	}

	/**
	 * set frame to show can't connect to server or database error panel
	 */
	public static void showConnectionErrorUI() {
		frame.setGlassPane(CantConnectUI.getCantConnectPane());
		Controller.getInstance().setIsJoinServer(false);
		frame.getGlassPane().setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			ip = args[0];
		} else {
			ip = "158.108.132.32";
		}
		// set
		PropertyConfigurator.configure("log4j.properties");
		MainFrame main = new MainFrame();
		main.run();
	}

	/**
	 * run the frame
	 */
	public void run() {
		frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		Controller ctrl = Controller.getInstance();
		ctrl.setClient(new Client(ip, 3007));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// set property of frame
		frame = new JFrame("Typing Thrower");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(((int) dim.getWidth() - FRAME_WIDTH) / 2,
				((int) dim.getHeight() - FRAME_HEIGHT) / 2, FRAME_WIDTH,
				FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		ImageIcon frameIcon = new ImageIcon(this.getClass().getResource(
				"/res/frameIcon.png"));
		frame.setIconImage(frameIcon.getImage());
		frame.getContentPane().setLayout(null);

		// set the first page
		setPane(new LoginUI().getLoginPanel());
	}

}
