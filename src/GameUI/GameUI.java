package GameUI;

import java.awt.EventQueue;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	private Timer timer;
	private JLabel weapon;

	private List<JLabel> bullets = new ArrayList<JLabel>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameUI window = new GameUI();
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
	public GameUI() {
		frame = new JFrame();
		game = new TypingThrower(new Player(100, 10), new Player(100, 10),
				"dictionary.txt");
		initComponent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		// frame.setResizable(false);
		frame.setTitle("Typing Thrower");
		frame.setBounds(100, 100, 1600, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		playing = new JPanel() {
			// JLabel playing = new JLabel(new ImageIcon("BG.jpg"));
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// try {
				// BufferedImage img = ImageIO.read(this.getClass()
				// .getResourceAsStream("/res/BG.png"));
				// g.drawImage(img, 0, 0, frame.getSize().width,
				// frame.getSize().height, null);
				// // Graphics asdf = img.getGraphics();
				// // asdf.drawRect(0, 0, 30, 30);
				// } catch (IOException e) {
				// }
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

		ImageIcon ninja1 = new ImageIcon("ninja1.png");
		p1 = new JLabel(ninja1);
		p1.setLocation(250, 350);
		p1.setSize(ninja1.getIconWidth(), ninja1.getIconHeight());
		playing.add(p1);

		HP2 = new JProgressBar();
		HP2.setValue(100);
		HP2.setForeground(Color.RED);
		HP2.setBounds(870, 750, 670, 25);
		playing.add(HP2);
		currentWord = game.getWord();
		word = new JLabel(currentWord, SwingConstants.CENTER);
		word.setAlignmentX(Component.CENTER_ALIGNMENT);
		word.setFont(new Font("Tahoma", Font.BOLD, 99));
		word.setBounds(0, 65, (int) frame.getSize().getWidth(), 136);
		playing.add(word);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == currentWord.charAt(0)) {
					currentWord = currentWord.substring(1, currentWord.length());
					if (currentWord.length() == 0) {
						game.P1Attack();

						ImageIcon weaponPic = new ImageIcon("Kunai.png");
						weapon = new JLabel(weaponPic);
						weapon.setLocation(300, 500);
						weapon.setSize(weaponPic.getIconWidth(),
								weaponPic.getIconHeight());
						playing.add(weapon);

						// ImageIcon weaponPic = new ImageIcon("Kunai.png");
						// JLabel bullet = new JLabel(weaponPic);
						// bullet.setLocation(300, 350);
						// bullet.setSize(weaponPic.getIconWidth(),
						// weaponPic.getIconHeight());
						// bullets.add(bullet);

						p1Attack();

						currentWord = game.getWord();
					}
					if (game.isP2Die())
						word.setText("");
					else
						word.setText(currentWord);

				}
			}
		});
	}

	private void p1Attack() {
		timer = new Timer();
		// new javax.swing.Timer(500, new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // for () {
		// // update x
		// // }
		// // System.out.println("test");
		// // System.out.println(bullets.size());
		// for (JLabel b : bullets) {
		// playing.add(b);
		// b.setLocation(b.getX() + 10, b.getY());
		// }
		// // bullets = new ArrayList<JLabel>();
		// }
		// }).start();

		// ImageIcon weaponPic = new ImageIcon("Kunai.png");
		// JLabel weapon = new JLabel(weaponPic);
		// weapon.setLocation(300, 350);
		// weapon.setSize(weaponPic.getIconWidth(), weaponPic.getIconHeight());
		// bullets.add(weapon);
		// timer.schedule(new TimerTask() {
		// @Override
		// public synchronized void run() {
		// for (JLabel b : bullets) {
		// playing.add(b);
		// b.setLocation(b.getX() + 30, b.getY());
		// if (b.getX() >= 1200) {
		// timer.cancel();
		// playing.remove(b);
		// HP2.setValue(game.getP2().getHP());
		// }
		// }
		// }
		// }, 0, 1);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				playing.add(weapon);
				weapon.setLocation(weapon.getX() + 30, weapon.getY());
				if (weapon.getX() >= 1200) {
					timer.cancel();
					playing.remove(weapon);
					HP2.setValue(game.getP2().getHP());
				}
			}
		}, 0, 1);

	}
}
