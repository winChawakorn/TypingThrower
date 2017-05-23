package gameui;

import game.Player;
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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;

import stopwatch.Stopwatch;
import connection.Controller;

public class GameUI {

	// private JFrame frame;
	private JPanel pane;
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
	// private JPanel menu;
	private int typeCount;
	private double wpm;
	private JLabel p1wpm;
	private JLabel p2wpm;
	private final int WIDTH = 1280;
	private final int HEIGHT = 768;

	// private int typeErrorCount;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
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
	// }

	/**
	 * Create the application.
	 */
	public GameUI() {
	}

	public JPanel getGamePanel() {
		return pane;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initComponent() {
		pane = new JPanel();
		pane.setBounds(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		playing = new JPanel();
		playing.setLayout(null);
		playing.setSize(pane.getSize());
		pane.add(playing);

		HP1 = new JProgressBar();
		HP1.setBounds(pane.getWidth() / 18, pane.getHeight() - pane.getHeight()
				/ 10, (int) (pane.getWidth() / 2.7), pane.getHeight() / 43);
		HP1.setMaximum(game.getP1().getHP());
		HP1.setValue(HP1.getMaximum());
		HP1.setForeground(Color.RED);
		playing.add(HP1);

		ImageIcon p1Pic = new ImageIcon(this.getClass().getResource(
				"/res/ninja1.png"));
		p1 = new JLabel(p1Pic);
		p1.setLocation(pane.getWidth() / 8, (int) (pane.getHeight() / 3));
		p1.setSize(p1Pic.getIconWidth(), p1Pic.getIconHeight());
		playing.add(p1);

		ImageIcon p2Pic = new ImageIcon(this.getClass().getResource(
				"/res/robot1.png"));
		p2 = new JLabel(p2Pic);
		p2.setLocation(
				(pane.getWidth() - (int) (pane.getWidth() / 8))
						- p2Pic.getIconWidth(), (int) (pane.getHeight() / 3));
		p2.setSize(p2Pic.getIconWidth(), p2Pic.getIconHeight());
		playing.add(p2);

		p1Name = new JLabel(game.getP1().toString());
		p1Name.setFont(new Font("Trebuchet MS", Font.BOLD, 47));
		p1Name.setBounds((int) pane.getWidth() / 15, (int) pane.getHeight()
				- (pane.getHeight() / 4), HP1.getWidth(), pane.getHeight() / 10);

		playing.add(p1Name);

		HP2 = new JProgressBar();
		HP2.setMaximum(game.getP2().getHP());
		HP2.setValue(HP2.getMaximum());
		HP2.setForeground(Color.RED);
		HP2.setBounds((pane.getWidth() - (pane.getWidth() / 18) - (int) (pane
				.getWidth() / 2.7)), pane.getHeight() - pane.getHeight() / 10,
				(int) (pane.getWidth() / 2.7), pane.getHeight() / 43);
		playing.add(HP2);
		currentWord = game.getWord();
		word = new JLabel(currentWord, SwingConstants.CENTER);
		word.setAlignmentX(Component.CENTER_ALIGNMENT);
		word.setFont(new Font("Tahoma", Font.BOLD, 99));
		word.setBounds(0, 65, (int) pane.getSize().getWidth(), word.getFont()
				.getSize() * 2);
		playing.add(word);

		p2Name = new JLabel(game.getP2().toString(), SwingConstants.RIGHT);
		p2Name.setFont(new Font("Trebuchet MS", Font.BOLD, 47));
		p2Name.setSize(HP2.getWidth(), pane.getHeight() / 10);
		p2Name.setLocation(
				(int) (pane.getWidth() - (pane.getWidth() / 15) - p2Name
						.getWidth()),
				(int) pane.getHeight() - (pane.getHeight() / 4));
		playing.add(p2Name);

		p1wpm = new JLabel("WPM : 0.00");
		p1wpm.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		p1wpm.setBounds(pane.getWidth() / 15, 0, pane.getWidth() / 5,
				pane.getHeight() / 5);
		p2wpm = new JLabel("WPM : 0.00");
		p2wpm.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		p2wpm.setLocation(pane.getWidth(), 0);
		p2wpm.setBounds(
				(pane.getWidth() - (pane.getWidth() / 15)) - pane.getWidth()
						/ 5, 0, pane.getWidth() / 5, pane.getHeight() / 5);
		playing.add(p1wpm);
		playing.add(p2wpm);

		// botAttack();
		// pane.requestFocus();
		// pane.setVisible(false);
		// pane.setVisible(true);

		// pane.setFocusable(true);
		// pane.requestFocusInWindow();
	}

	public void onlineGame() {
		Stopwatch stopwatch = new Stopwatch();
		pane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				typeCount++;

				if (e.getKeyChar() == currentWord.charAt(0)) {
					stopwatch.start();
					Controller ctrl = Controller.getInstance();
					ctrl.attack();
					currentWord = currentWord.substring(1, currentWord.length());
					if (currentWord.length() == 0) {
						currentWord = game.getWord();
						double timeMin = stopwatch.getElapsed() / 60;
						wpm = ((typeCount / 5) / timeMin);
						ctrl.sentWPM(wpm);
						// System.out.println(typeCount);
						// System.out.println("t>" + timeMin);
						// System.out.println("wpm>" + wpm);
					}
					if (game.isP2Lose() || game.isP1Lose()) {
						word.setText("");
						typeCount = 0;
						pane.removeKeyListener(this);
					} else
						word.setText(currentWord);
				}
			}
		});
	}

	public void offlineGame() {
		Stopwatch stopwatch = new Stopwatch();
		botAttack();
		pane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				typeCount++;
				if (e.getKeyChar() == currentWord.charAt(0)) {
					stopwatch.start();
					p1Attack();
					currentWord = currentWord.substring(1, currentWord.length());
					if (currentWord.length() == 0) {
						currentWord = game.getWord();
						double timeMin = stopwatch.getElapsed() / 60;
						wpm = ((typeCount / 5) / timeMin);
						setP1WPM(String.format("%.2f", wpm));
						// System.out.println(typeCount);
						// System.out.println("t>" + timeMin);
						// System.out.println("wpm>" + wpm);
					}
					if (game.isEnd()) {
						gameEnd();
					} else
						word.setText(currentWord);
				}
			}
		});
	}

	public void gameEnd() {
		word.setText("");
		typeCount = 0;
		pane.removeKeyListener(pane.getKeyListeners()[0]);
	}

	public void setP2WPM(String value) {
		p2wpm.setText("WPM : " + value);
	}

	public void setP1WPM(String value) {
		p1wpm.setText("WPM : " + value);
	}

	public void botAttack() {
		Random r = new Random();
		final int FASTEST = 100;
		final int SLOWEST = 1000;
		javax.swing.Timer timer = new javax.swing.Timer(500, null);
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = r.nextInt(SLOWEST - FASTEST) + FASTEST;
				timer.setDelay(result);
				p2Attack();
				if (game.isEnd()) {
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
			weapon.setLocation(pane.getWidth() / 7,
					(int) (pane.getHeight() / 1.9));
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
			weapon.setLocation(pane.getWidth() - (int) (pane.getWidth() / 7)
					- weaponPic.getIconWidth(), (int) (pane.getHeight() / 1.9));
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

	public void setGame(TypingThrower game) {
		this.game = game;
	}
}
