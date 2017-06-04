package connection;

import game.Player;
import game.TypingThrower;
import gameui.GameUI;
import gameui.HomeUI;
import gameui.LoginUI;
import gameui.MainFrame;

import java.io.IOException;

/**
 * Controller for this game. It's the most important class of this game, because
 * this class is a singleton which is the center of this game. Every class need
 * to call this class to make some action and connect with other classes.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class Controller {
	// Value for offline player
	private final char OFFLINE = 'f';
	// Singleton instant
	private static Controller ctrl = null;
	// Client that connect to
	private Client c;
	// UI that connect to
	private GameUI ui;
	// Check if already join server
	private boolean isJoinServer = false;
	private TypingThrower game;
	private Player p1, p2;
	// State for player (player one,player two, offline player)
	private char player = OFFLINE;
	private UserTable p1User, p2User;

	/**
	 * Initialize new Controller
	 */
	private Controller() {
	}

	/**
	 * Return this user's UserTable
	 * 
	 * @return p1User
	 */
	public UserTable getUser() {
		return p1User;
	}

	/**
	 * Return player state player1 = "1", player2 = "2", and play with computer
	 * = ""
	 * 
	 * @return player state
	 */
	public char getPlayer() {
		return this.player;
	}

	/**
	 * Set the isJoinServer status. True if already join the server, false if
	 * not.
	 * 
	 * @param status
	 *            is the join status to set to.
	 */
	public void setIsJoinServer(boolean status) {
		this.isJoinServer = status;
	}

	/**
	 * If Controller is already initialized, return that Controller. If it not
	 * initialized, create new Controller to ctrl and then return it.
	 * 
	 * @return a static Controller
	 */
	public static Controller getInstance() {
		if (ctrl == null)
			ctrl = new Controller();
		return ctrl;
	}

	/**
	 * Set the GameUI that will be controlled by this Controller.
	 * 
	 * @param ui
	 *            is the UI to be controlled.
	 */
	public void setUI(GameUI ui) {
		this.ui = ui;
	}

	/**
	 * Set the Client that will be controlled by this Controller.
	 * 
	 * @param c
	 *            is the Client to be Controlled.
	 */
	public void setClient(Client c) {
		this.c = c;
	}

	/**
	 * Make the Client join the server if it not already join.
	 * 
	 * @throws IOException
	 *             if cannot join the server.
	 */
	public void joinServer() throws IOException {
		if (!isJoinServer) {
			c.openConnection();
			isJoinServer = true;
		}
	}

	/**
	 * Order the server to create a game or find a game for this user.
	 */
	public void findGame() {
		try {
			c.sendToServer(Server.FIND_ROOM);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * Set this user to a waiting state when this user created a room.
	 */
	public void waiting() {
		player = '1';
	}

	/**
	 * Set the opponent detail and start the game.
	 * 
	 * @param opponent
	 */
	public void start(UserTable opponent) {
		p2User = opponent;
		if (player == 'f')
			player = '2';
		ui = new GameUI();
		createGame();
		ui.initComponent();
		ui.startGame(2);
		MainFrame.setFrame(ui.getGamePanel());
	}

	/**
	 * Create a game for this Controller to use with UI.
	 */
	public void createGame() {
		p1 = new Player(p1User.getCharacterName(), p1User.getHP(),
				p1User.getATK());
		p2 = new Player(p2User.getCharacterName(), p2User.getHP(),
				p2User.getATK());
		if (player == '1')
			this.game = new TypingThrower(p1, p2);
		else if (player == '2')
			this.game = new TypingThrower(p2, p1);
		ui.setGame(this.game);
	}

	/**
	 * Sent WPM to server for update in opponent UI.
	 * 
	 * @param value
	 *            is the WPM of this user.
	 */
	public void sentWPM(double value) {
		try {
			c.sendToServer(String.format("wpm %.2f", value));
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * Set WPM in UI for this player
	 * 
	 * @param value
	 *            is the wpm to be set to.
	 */
	public void mywpmUI(String value) {
		if (player == '1')
			ui.setP1WPM(value);
		else if (player == '2')
			ui.setP2WPM(value);
	}

	/**
	 * Set WPM in UI for this player's opponent.
	 * 
	 * @param value
	 *            is the wpm to be set to.
	 */
	public void oppowpmUI(String value) {
		if (player == '1')
			ui.setP2WPM(value);
		else if (player == '2')
			ui.setP1WPM(value);
	}

	/**
	 * Order the server to attack the opponent.
	 */
	public void attack() {
		try {
			c.sendToServer(Server.ATTACK);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * Order the UI to attack this user.
	 */
	public void attackedUI() {
		if (player == '1')
			ui.p2Attack();
		else if (player == '2')
			ui.p1Attack();
	}

	/**
	 * Order the UI to attack this user's opponent.
	 */
	public void attackUI() {
		if (player == '1')
			ui.p1Attack();
		else if (player == '2')
			ui.p2Attack();
	}

	/**
	 * Order the server to end this game and set some variable to be the starter
	 * value.
	 */
	public void endGame() {
		try {
			c.sendToServer(Server.END);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
		game = null;
		p1 = null;
		p2 = null;
		player = 'f';
		p2User = null;
	}

	/**
	 * Send the login request information(this user's UserTable) to the server
	 * to make the server let this user login.
	 */
	public void login() {
		this.p1User = LoginUI.getCurrentUser();
		try {
			c.sendToServer(p1User);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * Order the server be log this user out.
	 */
	public void logout() {
		p1User = null;
		isJoinServer = false;
		try {
			c.sendToServer(Server.LOGOUT);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * Order the UI to cancel finding a game and back to Home page.
	 */
	public void CancelfindGame() {
		player = 'f';
		MainFrame.setFrame(new HomeUI(p1User).getHomePanel());
	}

	/**
	 * Send a request for cancel finding a game.
	 */
	public void requestForCancel() {
		try {
			c.sendToServer(Server.CANCEL_FIND_ROOM);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * Change the UI to Home page when the login is successful.
	 */
	public void loginSuccess() {
		MainFrame.setFrame(new HomeUI(p1User).getHomePanel());
	}

	/**
	 * Show an error page if cannot login.
	 */
	public void loginError() {
		MainFrame.showSameUserErrorUI();
	}
}
