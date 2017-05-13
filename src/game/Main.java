package game;

import gameui.GameUI;

public class Main {
	public static void main(String[] args) {
		// Controller c = new Controller();
		new GameUI(new TypingThrower(new Player("Aom", 1000, 100), new Player(
				"Win", 1000, 100))).run();
	}
}
