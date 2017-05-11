package GameUI;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import Game.Player;
import Game.TypingThrower;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Component;
import java.io.IOException;

import javax.swing.SwingConstants;

public class GameUI {

	private JFrame frame;
	private String currentWord;
	private TypingThrower game;
	private JProgressBar HP1;
	private JProgressBar HP2;
	private JPanel playing;
	private JLabel word;
	private JLabel p1;
	private JLabel p1Name;
	private JLabel p2Name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// EventQueue.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// GameUI window = new GameUI();
		// window.frame.setVisible(true);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });
		GameUI ui = new GameUI();
		ui.run();
	}

	/**
	 * Create the application.
	 */
	public GameUI() {
		frame = new JFrame("TypingThrower");
		game = new TypingThrower(new Player("Win", 100, 1), new Player("Aom",
				100, 1), "dictionary.txt");

		initComponent();
	}

	public void run() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		// Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// frame.setBounds(100, 100, (int) d.getWidth(), (int) d.getHeight());
		// frame.setLocationRelativeTo(null);
		// frame.setResizable(false);
		// frame.setBounds(100, 100, 1600, 900);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		playing = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass()
							.getResourceAsStream("/res/BG.png"));
					g.drawImage(img, 0, 0, frame.getSize().width,
							frame.getSize().height, null);
					// Graphics asdf = img.getGraphics();
					// asdf.drawRect(0, 0, 30, 30);
				} catch (IOException e) {
					// do nothing
				}
			}
		};
		playing.setLayout(null);
		playing.setSize(frame.getSize());
		frame.getContentPane().add(playing);

		HP1 = new JProgressBar();
		HP1.setBounds(42, 750, 670, 25);
		HP1.setValue(100);
		HP1.setForeground(Color.RED);
		playing.add(HP1);

		ImageIcon p1Pic = new ImageIcon("ninja1.png");
		p1 = new JLabel(p1Pic);
		p1.setLocation(250, 350);
		p1.setSize(p1Pic.getIconWidth(), p1Pic.getIconHeight());
		playing.add(p1);

		p1Name = new JLabel(game.getP1().toString());
		p1Name.setFont(new Font("Trebuchet MS", Font.BOLD, 47));
		p1Name.setLocation(42, 650);
		p1Name.setSize(670, 100);

		playing.add(p1Name);

		HP2 = new JProgressBar();
		HP2.setValue(100);
		HP2.setForeground(Color.RED);
		HP2.setBounds(870, 750, 670, 25);
		playing.add(HP2);
		currentWord = game.getWord();
		word = new JLabel(currentWord, SwingConstants.CENTER);
		word.setAlignmentX(Component.CENTER_ALIGNMENT);
		word.setFont(new Font("Tahoma", Font.BOLD, 99));
		word.setBounds(0, 65, (int) frame.getSize().getWidth(), word.getFont()
				.getSize() * 2);
		playing.add(word);

		p2Name = new JLabel(game.getP2().toString(), SwingConstants.RIGHT);
		p2Name.setFont(new Font("Trebuchet MS", Font.BOLD, 47));
		p2Name.setBounds(870, 650, 670, 100);
		playing.add(p2Name);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == currentWord.charAt(0)) {
					currentWord = currentWord.substring(1, currentWord.length());
					game.P1Attack();

					Attack();
					if (currentWord.length() == 0) {
						currentWord = game.getWord();
					}
					if (game.isP2Die()) {
						word.setText("");
						frame.removeKeyListener(this);
					} else
						word.setText(currentWord);

				}
			}
		});
	}

	public void Attack() {
		ImageIcon weaponPic = new ImageIcon("Kunai.png");
		JLabel weapon = new JLabel(weaponPic);
		weapon.setLocation(300, 500);
		weapon.setSize(weaponPic.getIconWidth(), weaponPic.getIconHeight());
		playing.add(weapon);
		weapon.setLocation(300, 500);
		javax.swing.Timer timer2 = new javax.swing.Timer(0, null);
		timer2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				weapon.setLocation(weapon.getX() + 100, weapon.getY());
				if (weapon.getX() >= 1200) {
					HP2.setValue(game.getP2().getHP());
					playing.remove(weapon);
					timer2.stop();
				}
			}
		});
		timer2.start();
	}
}
