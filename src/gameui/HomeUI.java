package gameui;

import game.Player;
import game.TypingThrower;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import connection.Main;
import connection.UserTable;

public class HomeUI extends AbstractFont {

	private JFrame frame;
	private static JPanel homePanel;
	private JButton btnOnline, btnOffline, btnLogout, btnQuit;
	private JPanel lblScore;
	private JLabel lblCharacter, lblScoreName, lblWPM, lblWin, lblLose, lblHp,
			lblAtk;
	private UserTable currentUser;
	private JLabel lblWPMNum;
	private JLabel lblWinNum;
	private JLabel lblLoseNum;
	private JLabel lblHpNum;
	private JLabel lblAtkNum;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// HomeUI window = new HomeUI();
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
	public HomeUI() {
		currentUser = LoginUI.getCurrentUser();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(250, 240, 230));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(((int) dim.getWidth() - 1024) / 2,
				((int) dim.getHeight() - 768) / 2, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		homePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass()
							.getResourceAsStream("/res/BG3.png"));
					g.drawImage(img, 0, 0, 1024, 768, null);

				} catch (IOException e) {
					// do nothing
				}
			}
		};
		homePanel.setBounds(0, 0, 1024, 768);
		homePanel.setLayout(null);

		frame.getContentPane().add(homePanel);

		btnOnline = new JButton("Online");
		try {
			btnOnline.setFont(getFont("planet benson 2.ttf").deriveFont(
					Font.BOLD, 60));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		btnOnline.setForeground(Color.ORANGE);
		btnOnline.setContentAreaFilled(false);
		btnOnline.setBorderPainted(false);
		btnOnline.setBounds(86, 25, 290, 137);
		btnOnline.addActionListener((e) -> {
			MainFrame.setFrame(new WaitingUI().getWaitingPanel());
		});
		btnOnline.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnOnline.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnOnline.setForeground(Color.ORANGE);
			}
		});
		homePanel.add(btnOnline);

		btnOffline = new JButton("Offline");
		try {
			btnOffline.setFont(getFont("planet benson 2.ttf").deriveFont(
					Font.BOLD, 60));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		btnOffline.setForeground(Color.ORANGE);
		btnOffline.setContentAreaFilled(false);
		btnOffline.setBorderPainted(false);
		btnOffline.setBounds(86, 199, 290, 137);
		btnOffline.addActionListener((e) -> {
			GameUI ui = new GameUI();
			ui.setGame(new TypingThrower(new Player("You", 1000, 20),
					new Player("Computer", 1000, 20)));
			ui.initComponent();
			ui.offlineGame();
			MainFrame.setFrame(ui.getGamePanel());
		});
		btnOffline.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnOffline.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnOffline.setForeground(Color.ORANGE);
			}
		});
		homePanel.add(btnOffline);

		btnLogout = new JButton("Logout");
		try {
			btnLogout.setFont(getFont("planet benson 2.ttf").deriveFont(
					Font.BOLD, 60));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		btnLogout.setForeground(Color.ORANGE);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorderPainted(false);
		btnLogout.setBounds(87, 388, 290, 137);
		btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnLogout.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnLogout.setForeground(Color.ORANGE);
			}
		});
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.setFrame(LoginUI.getLoginPanel());
			}
		});
		homePanel.add(btnLogout);

		btnQuit = new JButton("Quit");
		try {
			btnQuit.setFont(getFont("planet benson 2.ttf").deriveFont(
					Font.BOLD, 60));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		btnQuit.setForeground(Color.ORANGE);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.setBounds(86, 587, 290, 137);
		btnQuit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnQuit.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnQuit.setForeground(Color.ORANGE);
			}
		});
		btnQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		homePanel.add(btnQuit);

		ImageIcon avatarPic = new ImageIcon(this.getClass().getResource(
				"/res/robot1.png"));
		lblCharacter = new JLabel(avatarPic);
		lblCharacter.setBounds(456, 342, avatarPic.getIconWidth(),
				avatarPic.getIconHeight());

		homePanel.add(lblCharacter);

		lblScore = new JPanel() {
			{
				add(new JLabel());
				setBackground(new Color(255, 255, 255, 120));
			}
		};
		// lblScore.setBackground(new Color(192, 192, 192));
		lblScore.setBounds(668, 25, 324, 454);
		lblScore.setLayout(null);
		homePanel.add(lblScore);

		lblScoreName = new JLabel(currentUser.getCharacterName());
		lblScoreName.setLocation(0, 15);
		lblScoreName.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreName.setSize(324, 70);
		try {
			lblScoreName.setFont(getFont("ProFont for Powerline.ttf")
					.deriveFont(Font.PLAIN, 60));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		lblScore.add(lblScoreName);

		Font detailFont = null;
		try {
			detailFont = getFont("ProFont for Powerline.ttf").deriveFont(
					Font.BOLD, 40);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		lblWPM = new JLabel("WPM: ");
		lblWPM.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWPM.setFont(new Font("Lucida Grande", Font.BOLD, 50));
		lblWPM.setFont(detailFont);

		lblWPM.setBounds(10, 100, 170, 60);
		lblScore.add(lblWPM);

		lblWin = new JLabel("Win: ");
		lblWin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWin.setFont(detailFont);
		lblWin.setBounds(10, 172, 170, 60);
		lblScore.add(lblWin);

		lblLose = new JLabel("Lose: ");
		lblLose.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLose.setFont(detailFont);
		lblLose.setBounds(10, 240, 170, 60);
		lblScore.add(lblLose);

		lblHp = new JLabel("HP: ");
		lblHp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHp.setFont(detailFont);
		lblHp.setBounds(10, 310, 170, 60);
		lblScore.add(lblHp);

		lblAtk = new JLabel("ATK: ");
		lblAtk.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtk.setFont(detailFont);
		lblAtk.setBounds(10, 380, 170, 60);
		lblScore.add(lblAtk);

		lblWPMNum = new JLabel(currentUser.getWPM() + "");
		lblWPMNum.setHorizontalAlignment(SwingConstants.LEFT);
		lblWPMNum.setFont(detailFont);
		lblWPMNum.setBounds(180, 100, 138, 60);
		lblScore.add(lblWPMNum);

		lblWinNum = new JLabel(currentUser.getWinRound() + "");
		lblWinNum.setFont(detailFont);
		lblWinNum.setBounds(180, 170, 138, 60);
		lblScore.add(lblWinNum);

		lblLoseNum = new JLabel(currentUser.getLoseRound() + "");
		lblLoseNum.setHorizontalAlignment(SwingConstants.LEFT);
		lblLoseNum.setFont(detailFont);
		lblLoseNum.setBounds(180, 240, 138, 60);
		lblScore.add(lblLoseNum);

		lblHpNum = new JLabel(currentUser.getHP() + "");
		lblHpNum.setHorizontalAlignment(SwingConstants.LEFT);
		lblHpNum.setFont(detailFont);
		lblHpNum.setBounds(180, 310, 138, 60);
		lblScore.add(lblHpNum);

		lblAtkNum = new JLabel(currentUser.getATK() + "");
		lblAtkNum.setHorizontalAlignment(SwingConstants.LEFT);
		lblAtkNum.setFont(detailFont);
		lblAtkNum.setBounds(180, 380, 138, 60);
		lblScore.add(lblAtkNum);

		// return homePanel;
	}

	public static JPanel getHomePanel() {
		if (homePanel == null)
			new HomeUI();
		return homePanel;
	}
}
