package gameui;

import game.TypingThrower;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;

import connection.Controller;

public class GameUI {

	private JFrame frame;
	private String currentWord;
	private TypingThrower game;
	private JProgressBar HP1;
	private JProgressBar HP2;
	private JPanel playing;
	private JLabel word;
	private JLabel p1;
	private JLabel p2;
	private JLabel p1Name;
	private JLabel p2Name;
	private JPanel menu;

	/**
	 * Launch the application.
	 */
//	 public static void main(String[] args) {
//	 EventQueue.invokeLater(new Runnable() {
//	 public void run() {
//	 try {
//	 GameUI window = new GameUI();
//	 window.frame.setVisible(true);
//	 } catch (Exception e) {
//	 e.printStackTrace();
//	 }
//	 }
//	 });
//	 }

	/**
	 * Create the application.
	 */
	public GameUI(TypingThrower game) {
		frame = new JFrame("TypingThrower");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game = game;
		initComponent();
	}

	public void run() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, (int) (d.getWidth() / 1.25),
				(int) (d.getHeight() / 1.25));
		System.out.println(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		// frame.setBounds(100, 100, 1600, 900);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setUndecorated(true);
		// frame.setVisible(true);
		// frame.setVisible(false);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		initMenuUI();
	}

	public void initMenuUI() {
		menu = new JPanel();
		menu.setPreferredSize(frame.getSize());
		JButton start = new JButton("Play");
		start.setFont(new Font(Font.MONOSPACED, Font.BOLD, 300));
		start.setContentAreaFilled(false);
		start.setPreferredSize(frame.getSize());
		start.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
					initPlayingUI();
			}
		});
		start.addActionListener((e) -> {
			Controller.getInstance().findGame();
			start.removeKeyListener(start.getKeyListeners()[0]);
			start.setEnabled(false);
		});
		menu.add(start);
		frame.add(menu);
	}

	public void initPlayingUI() {
		menu.setVisible(false);
		frame.remove(menu);
		// frame.setVisible(false);
		// frame.setVisible(true);

		// JLabel background = new JLabel(new ImageIcon("BG.png"));
		// playing = new JPanel() {
		// @Override
		// protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		// try {
		// BufferedImage img = ImageIO.read(this.getClass()
		// .getResourceAsStream("/res/BG.png"));
		// g.drawImage(img, 0, 0, frame.getSize().width,
		// frame.getSize().height, null);
		// Graphics asdf = img.getGraphics();
		// asdf.drawRect(0, 0, 30, 30);
		// } catch (IOException e) {
		// // do nothing
		// }
		// }
		// };
		playing = new JPanel();
		playing.setLayout(null);
		playing.setSize(frame.getSize());
		frame.getContentPane().add(playing);

		HP1 = new JProgressBar();
		HP1.setBounds(frame.getWidth() / 18,
				frame.getHeight() - frame.getHeight() / 10, 650, 25);
		HP1.setMaximum(game.getP1().getHP());
		HP1.setValue(HP1.getMaximum());
		HP1.setForeground(Color.RED);
		playing.add(HP1);

		ImageIcon p1Pic = new ImageIcon(this.getClass().getResource(
				"/res/ninja1.png"));
		p1 = new JLabel(p1Pic);
		p1.setLocation(frame.getWidth() / 8, (int) (frame.getHeight() / 2.5));
		p1.setSize(p1Pic.getIconWidth(), p1Pic.getIconHeight());
		playing.add(p1);

		ImageIcon p2Pic = new ImageIcon(this.getClass().getResource(
				"/res/robot1.png"));
		p2 = new JLabel(p2Pic);
		p2.setLocation((frame.getWidth() - (int) (frame.getWidth() / 8))
				- p2Pic.getIconWidth(), (int) (frame.getHeight() / 2.5));
		p2.setSize(p2Pic.getIconWidth(), p2Pic.getIconHeight());
		playing.add(p2);

		p1Name = new JLabel(game.getP1().toString());
		p1Name.setFont(new Font("Trebuchet MS", Font.BOLD, 47));
		p1Name.setLocation(42, 650);
		p1Name.setBounds((int) frame.getWidth() / 7, (int) frame.getHeight()
				- (frame.getHeight() / 5), 670, 100);

		playing.add(p1Name);

		HP2 = new JProgressBar();
		HP2.setMaximum(game.getP2().getHP());
		HP2.setValue(HP2.getMaximum());
		HP2.setForeground(Color.RED);
		HP2.setBounds((frame.getWidth() - (frame.getWidth() / 18) - 650),
				frame.getHeight() - frame.getHeight() / 10, 650, 25);
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
		p2Name.setBounds((frame.getWidth() / 2),
				frame.getHeight() - (frame.getHeight() / 5), 670, 100);
		playing.add(p2Name);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == currentWord.charAt(0)) {
					Controller ctrl = Controller.getInstance();
					ctrl.attack();
					currentWord = currentWord.substring(1, currentWord.length());
					p1Attack();
					if (currentWord.length() == 0) {
						currentWord = game.getWord();
					}
					if (game.isP2Lose() || game.isP1Lose()) {
						word.setText("");
						frame.removeKeyListener(this);
					} else
						word.setText(currentWord);
				}
			}
		});
		// botAttack();
		frame.requestFocus();
		frame.setVisible(false);
		frame.setVisible(true);
	}

	public void botAttack() {
		javax.swing.Timer timer = new javax.swing.Timer(500, null);
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p2Attack();
				if (game.isP1Lose() || game.isP2Lose()) {
					word.setText("");
					timer.stop();
				}
			}
		});
		timer.start();
	}

	public void p1Attack() {
		if (!(game.isP2Lose() || game.isP1Lose())) {
			game.P1Attack();
			ImageIcon weaponPic = new ImageIcon(this.getClass().getResource(
					"/res/Kunai.png"));
			JLabel weapon = new JLabel(weaponPic);
			weapon.setSize(weaponPic.getIconWidth(), weaponPic.getIconHeight());
			weapon.setLocation(frame.getWidth() / 7,
					(int) (frame.getHeight() / 1.9));
			playing.add(weapon);

			JLabel p1Throw = new JLabel(new ImageIcon(this.getClass()
					.getResource("/res/ninja2.png")));
			p1Throw.setSize(p1.getSize());
			p1Throw.setLocation(p1.getLocation());
			p1Throw.setVisible(false);
			playing.add(p1Throw);
			p1.setVisible(false);
			p1Throw.setVisible(true);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					p1Throw.setVisible(false);
					p1.setVisible(true);
					timer.cancel();
				}
			}, 100);

			javax.swing.Timer timer2 = new javax.swing.Timer(10, null);
			timer2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					weapon.setLocation(weapon.getX() + 30, weapon.getY());
					if (weapon.getX() >= p2.getX() - weapon.getWidth()) {
						HP2.setValue(game.getP2().getHP());
						playing.remove(weapon);
						timer2.stop();
						p2.setVisible(false);
						if (game.isP2Lose()) {
							p2Lose();
						} else {
							Timer timer3 = new Timer();
							timer3.schedule(new TimerTask() {

								@Override
								public void run() {
									p2.setVisible(true);
									timer.cancel();
								}
							}, 100);
						}
					}
				}
			});
			timer2.start();
		}
	}

	public void p2Lose() {
		ImageIcon p2LosePic1 = new ImageIcon(this.getClass().getResource(
				"/res/robotDead1.png"));
		JLabel p2Lose1 = new JLabel(p2LosePic1);
		p2Lose1.setSize(p2LosePic1.getIconWidth(), p2LosePic1.getIconHeight());
		p2Lose1.setLocation(p2.getX() - 150, p2.getY() + 75);
		playing.add(p2Lose1);
		p2.setVisible(false);
		p2Lose1.setVisible(true);

		ImageIcon p2LosePic2 = new ImageIcon(this.getClass().getResource(
				"/res/robotDead2.png"));
		JLabel p2Lose2 = new JLabel(p2LosePic2);
		p2Lose2.setSize(p2LosePic2.getIconWidth(), p2LosePic2.getIconHeight());
		p2Lose2.setLocation(p2Lose1.getX() - 150, p2Lose1.getY());
		p2Lose2.setVisible(false);
		playing.add(p2Lose2);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				p2Lose1.setVisible(false);
				p2Lose2.setVisible(true);
				timer.cancel();
			}
		}, 1000);
	}

	public void p2Attack() {
		if (!(game.isP2Lose() || game.isP1Lose())) {
			game.P2Attack();
			ImageIcon weaponPic = new ImageIcon(this.getClass().getResource(
					"/res/fire.png"));
			JLabel weapon = new JLabel(weaponPic);
			weapon.setSize(weaponPic.getIconWidth(), weaponPic.getIconHeight());
			weapon.setLocation(frame.getWidth() - (int) (frame.getWidth() / 7)
					- weaponPic.getIconWidth(), (int) (frame.getHeight() / 1.9));
			playing.add(weapon);
			JLabel p2Throw = new JLabel(new ImageIcon(this.getClass()
					.getResource("/res/robot2.png")));
			p2Throw.setSize(p2.getSize());
			p2Throw.setLocation(p2.getLocation());
			p2Throw.setVisible(false);
			playing.add(p2Throw);
			p2.setVisible(false);
			p2Throw.setVisible(true);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					p2Throw.setVisible(false);
					p2.setVisible(true);
					timer.cancel();
				}
			}, 100);

			javax.swing.Timer timer2 = new javax.swing.Timer(10, null);
			timer2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					weapon.setLocation(weapon.getX() - 30, weapon.getY());
					if (weapon.getX() <= p1.getX() + p1.getWidth()) {
						HP1.setValue(game.getP1().getHP());
						playing.remove(weapon);
						timer2.stop();
						p1.setVisible(false);
						if (game.isP1Lose()) {
							p1Lose();
						} else {
							Timer timer3 = new Timer();
							timer3.schedule(new TimerTask() {

								@Override
								public void run() {
									p1.setVisible(true);
									timer.cancel();
								}
							}, 100);
						}
					}
				}
			});
			timer2.start();
		}
	}

	public void p1Lose() {
		ImageIcon p2LosePic1 = new ImageIcon(this.getClass().getResource(
				"/res/ninjaDead1.png"));
		JLabel p2Lose1 = new JLabel(p2LosePic1);
		p2Lose1.setSize(p2LosePic1.getIconWidth(), p2LosePic1.getIconHeight());
		p2Lose1.setLocation(p1.getX() + 150, p1.getY() + 75);
		playing.add(p2Lose1);
		p1.setVisible(false);
		p2Lose1.setVisible(true);

		ImageIcon p2LosePic2 = new ImageIcon(this.getClass().getResource(
				"/res/ninjaDead2.png"));
		JLabel p2Lose2 = new JLabel(p2LosePic2);
		p2Lose2.setSize(p2LosePic2.getIconWidth(), p2LosePic2.getIconHeight());
		p2Lose2.setLocation(p2Lose1.getX() + 100, p2Lose1.getY() + 75);
		p2Lose2.setVisible(false);
		playing.add(p2Lose2);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				p2Lose1.setVisible(false);
				p2Lose2.setVisible(true);
				timer.cancel();
			}
		}, 1000);
	}

	public void cantConnectToServer() {
		// Can't connect to server. Please try again or contact game master
	}

	public void waiting() {
		// Please wait message
	}
}
