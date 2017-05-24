package connection;

import game.Player;
import game.TypingThrower;
import gameui.GameUI;
import gameui.HomeUI;
import gameui.LoginUI;
import gameui.MainFrame;

import java.io.IOException;

public class Controller {
	private static Controller ctrl = null;
	private Client c;
	private GameUI ui;
	private boolean isJoinServer = false;
	private TypingThrower game;
	private Player p1;
	private Player p2;
	private String player;
	private UserTable p1User;
	private UserTable p2User;

	private Controller() {
		player = "";
	}

	public UserTable getUser() {
		return p1User;
	}

	public String getPlayer() {
		return this.player;
	}

	public void setIsJoinServer(boolean status) {
		this.isJoinServer = status;
	}

	public static Controller getInstance() {
		if (ctrl == null)
			ctrl = new Controller();
		return ctrl;
	}

	public void setUI(GameUI ui) {
		this.ui = ui;
	}

	public void setClient(Client c) {
		this.c = c;
	}

	public void joinServer() throws IOException {
		if (!isJoinServer) {
			c.openConnection();
			isJoinServer = true;
		}
	}

	public void findGame() {
		try {
			c.sendToServer("find room");
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	public void waiting() {
		player = "1";
	}

	public void start(UserTable opponent) {
		p2User = opponent;
		if (player.equals(""))
			player = "2";
		ui = new GameUI();
		createGame();
		ui.initComponent();
		ui.onlineGame();
		MainFrame.setFrame(ui.getGamePanel());
	}

	public void createGame() {
		p1 = new Player(p1User.getCharacterName(), p1User.getHP(),
				p1User.getATK());
		p2 = new Player(p2User.getCharacterName(), p2User.getHP(),
				p2User.getATK());
		if (player.equals("1"))
			this.game = new TypingThrower(p1, p2);
		else if (player.equals("2"))
			this.game = new TypingThrower(p2, p1);

		// for testing without Database
		// this.game = new TypingThrower(new Player("Aom", 1000, 20), new
		// Player(
		// "Win", 1000, 20));

		ui.setGame(this.game);
	}

	public void sentWPM(double value) {
		try {
			c.sendToServer(String.format("wpm %.2f", value));
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	public void mywpmUI(String value) {
		if (player.equals("1"))
			ui.setP1WPM(value);
		else if (player.equals("2"))
			ui.setP2WPM(value);
	}

	public void oppowpmUI(String value) {
		if (player.equals("1"))
			ui.setP2WPM(value);
		else if (player.equals("2"))
			ui.setP1WPM(value);
	}

	public void attack() {
		try {
			c.sendToServer("attack");
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	public void attackedUI() {
		if (player.equals("1"))
			ui.p2Attack();
		else if (player.equals("2"))
			ui.p1Attack();
	}

	public void attackUI() {
		// System.out.println("u r p" + player);
		if (player.equals("1"))
			ui.p1Attack();
		else if (player.equals("2"))
			ui.p2Attack();
	}

	public void endGame() {
		try {
			c.sendToServer("finish");
		} catch (IOException e) {
			e.printStackTrace();
		}
		game = null;
		p1 = null;
		p2 = null;
		player = "";
	}

	public void login() {
		this.p1User = LoginUI.getCurrentUser();
		try {
			c.sendToServer(p1User);
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	public void logout() {
		p1User = null;
		isJoinServer = false;
		try {
			c.sendToServer("logout");
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	public void CancelfindGame() {
		player = "";
		MainFrame.setFrame(new HomeUI().getHomePanel());
	}

	public void requestForCancel() {
		try {
			c.sendToServer("Cancel");
		} catch (IOException e) {
			MainFrame.showConnectionErrorUI();
		}
	}

	public void loginSuccess() {
		MainFrame.setFrame(new HomeUI().getHomePanel());
	}

	public void loginError() {

	}
}
