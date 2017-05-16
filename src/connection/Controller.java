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

	//
	// public static void setController(Controller controller) {
	// ctrl = controller;
	// }

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
			c.sendToServer("Attack");
		} catch (IOException e) {
			ui.cantConnectToServer();
		}
		// ui.p1Attack();
	}

	public void attacked() {
		ui.p2Attack();
		ui.p1Attack();
	}
	//
	// public void p2Attack() {
	// try {
	// c.sendToServer("P2Attack");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}
