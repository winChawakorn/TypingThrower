package gameui;

import game.Player;
import game.TypingThrower;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import connection.Controller;
import connection.DatabaseConnect;
import connection.UserTable;

/**
 * Home interface is showed when login success. There are 4 important buttons
 * provide user to choose play online, offline, logout(login again), or quit to
 * game. And show user score on right-top corner of the window.
 * 
 * @author vittunyutamaeprasart
 *
 */
public class HomeUI extends AbstractFont {

	private JPanel homePanel;
	private JButton btnOnline, btnOffline, btnLogout, btnQuit;
	private JPanel lblScore;
	private JLabel lblCharacter, lblScoreName, lblWPM, lblWin, lblLose, lblHp,
			lblAtk;
	private UserTable currentUser;
	private JLabel lblWPMNum, lblWinNum, lblLoseNum, lblHpNum, lblAtkNum;

	/**
	 * Create the application.
	 */
	public HomeUI() {
		currentUser = LoginUI.getCurrentUser();
		DatabaseConnect.getInstance().updateUserData(currentUser);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// set background of interface using external picture
		homePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass()
							.getResourceAsStream("/res/BG3.png"));
					g.drawImage(img, 0, 0, 1280, 768, null);
				} catch (IOException e) {
					// do nothing
				}
			}
		};
		homePanel.setBounds(0, 0, 1280, 768);
		homePanel.setLayout(null);

		// create 4 important buttons
		Font fontForBtn = getFont("planet benson 2.ttf").deriveFont(Font.BOLD,
				60);

		btnOnline = new JButton("Online");
		btnOnline.setFont(fontForBtn);
		btnOnline.setForeground(Color.ORANGE);
		btnOnline.setContentAreaFilled(false);
		btnOnline.setBorderPainted(false);
		btnOnline.setBounds(100, 25, 360, 137);
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
		btnOffline.setFont(fontForBtn);
		btnOffline.setForeground(Color.ORANGE);
		btnOffline.setContentAreaFilled(false);
		btnOffline.setBorderPainted(false);
		btnOffline.setBounds(100, 199, 360, 137);
		btnOffline
				.addActionListener((e) -> {
					GameUI ui = new GameUI();
					ui.setGame(new TypingThrower(new Player(LoginUI
							.getCurrentUser().getCharacterName(), 1000, 20),
							new Player("CPU", 1000, 20)));
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
		btnLogout.setFont(fontForBtn);
		btnLogout.setForeground(Color.ORANGE);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorderPainted(false);
		btnLogout.setBounds(100, 388, 360, 137);
		btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnLogout.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnLogout.setForeground(Color.ORANGE);
			}
		});
		btnLogout.addActionListener((e) -> {
			Controller.getInstance().logout();
			MainFrame.setFrame(new LoginUI().getLoginPanel());
		});
		homePanel.add(btnLogout);

		btnQuit = new JButton("Quit");
		btnQuit.setFont(fontForBtn);
		btnQuit.setForeground(Color.ORANGE);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.setBounds(100, 587, 360, 137);
		btnQuit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnQuit.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnQuit.setForeground(Color.ORANGE);
			}
		});
		btnQuit.addActionListener((e) -> {
			System.exit(0);
		});
		homePanel.add(btnQuit);
		
		ImageIcon avatarPic = new ImageIcon(this.getClass().getResource("/res/ninja1.png"));
		lblCharacter = new JLabel(avatarPic);
		lblCharacter.setBounds(593, 340, avatarPic.getIconWidth(),
				avatarPic.getIconHeight());
		homePanel.add(lblCharacter);
		
		lblScore = new JPanel() ;
		lblScore.setBackground(new Color(255, 255, 255, 120));
		lblScore.setBounds(936, 25, 324, 454);
		lblScore.setLayout(null);
		homePanel.add(lblScore);

		lblScoreName = new JLabel(currentUser.getCharacterName());
		lblScoreName.setLocation(0, 15);
		lblScoreName.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreName.setSize(324, 70);
		lblScoreName.setFont(getFont("ProFont For Powerline.ttf").deriveFont(
				Font.PLAIN, 60));
		lblScore.add(lblScoreName);

		Font detailFont = getFont("ProFont For Powerline.ttf").deriveFont(
				Font.BOLD, 40);

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

	}

	/**
	 * get panel of home interface
	 * 
	 * @return home interface
	 */
	public JPanel getHomePanel() {
		return homePanel;
	}
}
