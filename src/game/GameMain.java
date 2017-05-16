package game;

import connection.Client;
import connection.Controller;
import gameui.GameUI;

public class GameMain {
	public static void main(String[] args) {
		// Controller c = new Controller();
		GameUI ui = new GameUI(new TypingThrower(new Player("Aom", 1000, 100),
				new Player("Win", 1000, 100)));
		ui.run();
		Controller ctrl = Controller.getInstance();
		ctrl.setUI(ui);
		ctrl.setClient(new Client("", 3001));
		ctrl.join();
	}
}
