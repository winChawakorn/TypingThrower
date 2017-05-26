package gameui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;

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
	private static JFrame frame;

	/**
	 * set frame to show a given panel.
	 * 
	 * @param newPane
	 *            is current panel to be showed
	 */
	public static void setFrame(JPanel newPane) {
		frame.getContentPane().removeAll();
		frame.repaint();
		newPane.setBounds(0, 0, 1280, 768);
		frame.getContentPane().add(newPane);

		newPane.setVisible(true);
		newPane.setFocusable(true);
		newPane.requestFocusInWindow();

		// System.out.println("before");
		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		// Image image = toolkit.getImage("cursor2.gif");
		// Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "");
		// frame.setCursor(c);
		// // frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// System.out.println("after");
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
		ctrl.setClient(new Client("35.185.188.93", 3007));
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
		frame.getGlassPane().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					MainFrame.setFrame(new LoginUI().getLoginPanel());
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					MainFrame.setFrame(new LoginUI().getLoginPanel());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					MainFrame.setFrame(new LoginUI().getLoginPanel());
				}
			}
		});
		frame.getContentPane().setLayout(null);

		// set the first page
		setFrame(new LoginUI().getLoginPanel());
	}

}
