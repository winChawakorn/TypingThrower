package GameUI;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JProgressBar;

import Game.Player;
import Game.TypingThrower;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Component;
import java.io.File;
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
				try {
					BufferedImage img = ImageIO.read(this.getClass()
							.getResourceAsStream("/res/BG.png"));
					g.drawImage(img, 0, 0, frame.getSize().width,
							frame.getSize().height, null);
					// Graphics asdf = img.getGraphics();
					// asdf.drawRect(0, 0, 30, 30);
				} catch (IOException e) {
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
						HP2.setValue(game.getP2().getHP());
						currentWord = game.getWord();
					}
					word.setText(currentWord);
				}
			}
		});
	}
}
