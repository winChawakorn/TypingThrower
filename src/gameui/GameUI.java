package gameui;

import game.TypingThrower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;

import stopwatch.Stopwatch;
import connection.Controller;
import connection.DatabaseConnect;
import connection.UserTable;

/**
 * Game UI for this game when playing.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class GameUI {
	private final int WIDTH = 1280;
	private final int HEIGHT = 768;

	private JPanel pane, resultPane, playing;
	private String currentWord;
	private TypingThrower game;
	private JProgressBar HP1, HP2;
	private JLabel word, p1, p2, p1Name, p2Name, p1wpm, p2wpm;
	private int typeCount, botAttackCount = 0;
	private double wpm;
	private Stopwatch watch;
	private boolean isShowResult = false;
	private Controller ctrl = Controller.getInstance();

	/**
	 * Create the application.
	 */
	public GameUI() {
	}

	/**
	 * Return this UI JPanel.
	 * 
	 * @return JPanel that display this playing UI.
	 */
	public JPanel getGamePanel() {
		return pane;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initComponent() {
		isShowResult = false;
		pane = new JPanel();
		pane.setBounds(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT);
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		playing = new JPanel();
		playing.setLayout(null);
		playing.setSize(pane.getSize());

		resultPane = new JPanel();
		resultPane.setVisible(false);
		playing.add(resultPane);

		HP1 = new JProgressBar();
		HP1.setBounds(pane.getWidth() / 18, pane.getHeight() - pane.getHeight()
				/ 7, (int) (pane.getWidth() / 2.7), pane.getHeight() / 43);
		HP1.setMaximum(game.getP1().getHP());
		HP1.setValue(HP1.getMaximum());
		HP1.setForeground(Color.RED);
		HP1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		playing.add(HP1);

		ImageIcon p1Pic = new ImageIcon(this.getClass().getResource(
				"/res/ninja1.png"));
		p1 = new JLabel(p1Pic);
		p1.setLocation(pane.getWidth() / 8, (int) (pane.getHeight() / 2.75));
		p1.setSize(p1Pic.getIconWidth(), p1Pic.getIconHeight());
		playing.add(p1);

		ImageIcon p2Pic = new ImageIcon(this.getClass().getResource(
				"/res/robot1.png"));
		p2 = new JLabel(p2Pic);
		p2.setLocation(
				(pane.getWidth() - (int) (pane.getWidth() / 8))
						- p2Pic.getIconWidth(), (int) (pane.getHeight() / 2.75));
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
		HP2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		HP2.setBounds((pane.getWidth() - (pane.getWidth() / 18) - (int) (pane
				.getWidth() / 2.7)), pane.getHeight() - pane.getHeight() / 7,
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
		p1wpm.setBounds(pane.getWidth() / 15, 0, pane.getWidth() / 4,
				pane.getHeight() / 5);
		p2wpm = new JLabel("WPM : 0.00");
		p2wpm.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		p2wpm.setLocation(pane.getWidth(), 0);
		p2wpm.setBounds(
				(pane.getWidth() - (pane.getWidth() / 15)) - pane.getWidth()
						/ 5, 0, pane.getWidth() / 4, pane.getHeight() / 5);
		playing.add(p1wpm);
		playing.add(p2wpm);

		JLabel background = new JLabel(new ImageIcon(this.getClass()
				.getResource("/res/BG2.png")));
		background.setSize(playing.getSize());
		pane.setLayout(null);
		pane.add(playing);
		playing.setOpaque(false);
		pane.add(background);
		watch = new Stopwatch();
		watch.start();
	}

	/**
	 * Start the game with mode selected by the parameter. mode 1 = offline,
	 * mode 2 = online
	 * 
	 * @param mode
	 *            is the mode offline or online.1 = offline, 2 = online
	 */
	public void startGame(int mode) {
		countDown();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Stopwatch stopwatch = new Stopwatch();
				if (mode == 1)
					botAttack();
				pane.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyChar() == currentWord.charAt(0)) {
							typeCount++;
							stopwatch.start();
							if (mode == 2)
								ctrl.attack();
							else
								p1Attack();
							currentWord = currentWord.substring(1,
									currentWord.length());
							if (currentWord.length() == 0) {
								currentWord = game.getWord();
								double timeMin = stopwatch.getElapsed() / 60;
								wpm = ((typeCount / 5) / timeMin);
								if (mode == 2)
									ctrl.sentWPM(wpm);
								else
									setP1WPM(String.format("%.2f", wpm));
							}
							if (game.isEnd()) {
								gameEnd();
							} else
								word.setText(currentWord);
						}
					}
				});
			}
		}, 4000);
	}

	/**
	 * Create count down number in the center of the panel when the game
	 * started.
	 */
	public void countDown() {
		JLabel number = new JLabel("3", SwingConstants.CENTER);
		number.setFont(CreatedFont.fontCountNum());
		number.setSize(playing.getWidth(), playing.getHeight() / 2);
		number.setLocation((word.getWidth() - number.getWidth()) / 2,
				((playing.getHeight() - number.getHeight()) / 2));
		number.setForeground(Color.YELLOW);
		playing.add(number);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				number.setText("2");
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						number.setText("1");
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								number.setText("TYPE");
								number.setFont(CreatedFont.fontCountType());
								timer.schedule(new TimerTask() {
									@Override
									public void run() {
										number.setVisible(false);
										playing.remove(number);
									}
								}, 1000);
							}
						}, 1000);
					}
				}, 1000);
			}
		}, 1000);
	}

	/**
	 * Set some value to the starter value when the game is end.
	 */
	public void gameEnd() {
		word.setText("");
		typeCount = 0;
		pane.removeKeyListener(pane.getKeyListeners()[0]);
	}

	/**
	 * Set WPM UI to player2
	 * 
	 * @param value
	 *            is the WPM to set to.
	 */
	public void setP2WPM(String value) {
		p2wpm.setText("WPM : " + value);
	}

	/**
	 * Set WPM UI to player1
	 * 
	 * @param value
	 *            is the WPM to set to.
	 */
	public void setP1WPM(String value) {
		p1wpm.setText("WPM : " + value);
	}

	/**
	 * Order the robot to play as player2.
	 */
	public void botAttack() {
		botAttackCount = 0;
		Stopwatch stopwatch = new Stopwatch();
		Random r = new Random();
		final int FASTEST = 100;
		final int SLOWEST = 1000;
		javax.swing.Timer timer = new javax.swing.Timer(500, null);
		timer.addActionListener((e) -> {
			int result = r.nextInt(SLOWEST - FASTEST) + FASTEST;
			timer.setDelay(result);
			botAttackCount++;
			stopwatch.start();
			if (botAttackCount % 6 == 0) {
				double timeMin = stopwatch.getElapsed() / 60;
				double botwpm = ((botAttackCount / 5) / timeMin);
				setP2WPM(String.format("%.2f", botwpm));
			}
			p2Attack();
			if (game.isEnd()) {
				word.setText("");
				timer.stop();
			}
		});
		timer.start();
	}

	/**
	 * Order p1 to attack
	 */
	public void p1Attack() {
		if (!(game.isEnd())) {
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
			timer2.addActionListener((e) -> {
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
			});
			timer2.start();
		}
	}

	/**
	 * Make player2 dead.
	 */
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
				showGameResult();
				timer.cancel();
			}
		}, 1000);
	}

	/**
	 * Order p2 to attack.
	 */
	public void p2Attack() {
		if (!(game.isEnd())) {
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
			timer2.addActionListener((e) -> {
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
			});
			timer2.start();
		}
	}

	/**
	 * Make player1 dead.
	 */
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
				watch.stop();
				showGameResult();
				timer.cancel();
			}
		}, 1000);
	}

	/**
	 * Set the game for this UI.
	 * 
	 * @param game
	 *            is the game to set to.
	 */
	public void setGame(TypingThrower game) {
		this.game = game;
	}

	/**
	 * Show the game result
	 */
	public void showGameResult() {
		if (!isShowResult) {
			isShowResult = true;
			word.setVisible(false);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					resultPane.setLayout(new BorderLayout());
					resultPane.setBackground(new Color(0, 0, 0, 120));
					resultPane.setSize(new Dimension(
							(int) (playing.getWidth() / 1.6), (int) (playing
									.getHeight() / 1.6)));
					resultPane.setLocation(playing.getWidth() / 5,
							playing.getHeight() / 5);
					Font font = new Font(Font.MONOSPACED, Font.BOLD, 60);

					JButton btnOK = new JButton("OK");
					btnOK.setBackground(new Color(135, 245, 255));
					btnOK.setFont(font);
					btnOK.setSize(resultPane.getWidth() / 4,
							resultPane.getHeight() / 6);
					UserTable user = ctrl.getUser();
					btnOK.addActionListener((e) -> {
						if (ctrl.getUser() == null)
							MainFrame.setPane(new LoginUI().getLoginPanel());
						else {
							ctrl.endGame();
							DatabaseConnect.getInstance().updateUserData(user);
							MainFrame.setPane(new HomeUI(user).getHomePanel());
						}
					});
					resultPane.add(btnOK, BorderLayout.SOUTH);

					JLabel winOrLose = new JLabel("", SwingConstants.CENTER);
					winOrLose.setForeground(Color.YELLOW);
					winOrLose.setFont(font);
					resultPane.add(winOrLose, BorderLayout.NORTH);
					JTextArea detail = new JTextArea();
					detail.setOpaque(false);
					detail.setEditable(false);
					detail.setPreferredSize(resultPane.getSize());
					detail.setForeground(Color.WHITE);
					detail.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
					detail.append("\n");
					JLabel space = new JLabel("    ");
					space.setFont(font);
					JLabel space2 = new JLabel("    ");
					space2.setFont(font);
					resultPane.add(space, BorderLayout.WEST);
					resultPane.add(space2, BorderLayout.EAST);
					resultPane.add(detail, BorderLayout.CENTER);
					if (ctrl.getPlayer() == 'f') {
						if (game.isP2Lose()) {
							winOrLose.setText("YOU WIN");
							if (user != null)
								user.setWinRound(user.getWinRound() + 1);
						} else {
							winOrLose.setText("YOU LOSE");
							if (user != null) {
								user.setLoseRound(user.getLoseRound() + 1);
							}
						}
						detail.append(p1wpm.getText());
						if (user == null)
							detail.append("\nPlease login to \nrecord your score");
						else {
							user.setTotalWPM(wpm + user.getTotalWPM());
						}
					} else {
						if (ctrl.getPlayer() == '2') {
							if (game.isP1Lose()) {
								user.setWinRound(user.getWinRound() + 1);
								winOrLose.setText("YOU WIN");
							} else {
								user.setLoseRound(user.getLoseRound() + 1);
								winOrLose.setText("YOU LOSE");
							}
							detail.append(p2wpm.getText());
						} else if (ctrl.getPlayer() == '1') {
							if (game.isP2Lose()) {
								user.setWinRound(user.getWinRound() + 1);
								winOrLose.setText("YOU WIN");
							} else {
								user.setLoseRound(user.getLoseRound() + 1);
								winOrLose.setText("YOU LOSE");
							}
							detail.append(p1wpm.getText());
						}
						user.setTotalWPM(wpm + user.getTotalWPM());
					}
					detail.append(String.format("\nTime : %.0f seconds",
							Math.floor(watch.getElapsed())));
					resultPane.setVisible(true);
					playing.repaint();
				}
			}, 1500);
		}
	}
}
