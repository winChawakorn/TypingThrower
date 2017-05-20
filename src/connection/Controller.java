package connection;

import gameui.GameUI;

import java.io.IOException;

public class Controller {
	private static Controller ctrl = null;
	private Client c;
	private GameUI ui;
	private boolean isJoin = false;

	private Controller() {
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

	public void join() {
		if (!isJoin) {
			try {
				c.openConnection();
			} catch (IOException e) {
				ui.cantConnectToServer();
			}
			isJoin = true;
		}
	}

	public void attack() {
		try {
			c.sendToServer("attack");
		} catch (IOException e) {
			ui.cantConnectToServer();
		}
	}

	public void findGame() {
		try {
			c.sendToServer("find room");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void attackedUI() {
		ui.p2Attack();
	}

	public void attackUI() {
		ui.p1Attack();
	}

	public void waitingUI() {
		ui.waiting();
	}

	public void start() {
		ui.initPlayingUI();
	}
}
