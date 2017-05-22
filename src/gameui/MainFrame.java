package gameui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame {

	private static JFrame frame;

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JPanel newPane) {
		frame.getContentPane().removeAll();
		frame.repaint();
		newPane.setBounds(0, 0, 1024, 768);
		frame.getContentPane().add(newPane);
		newPane.setVisible(true);
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
//		Image cursorImage = new ImageIcon(this.getClass().getResource("/res/cursor2.gif")).getImage();  
//		Point hotspot = new Point(0, 0);  
//		String cursorName = "Lightsaber Cursor";
//		frame.setCursor(toolkit.createCustomCursor(cursorImage, hotspot, cursorName));

		Image image = toolkit.getImage("/res/cursor2.gif");
		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX(), frame.getY()), "");
		frame.getRootPane().setCursor(c);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(((int) dim.getWidth() - 1024) / 2, ((int) dim.getHeight() - 768) / 2, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		setFrame(LoginUI.getLoginPanel());
	}

}
