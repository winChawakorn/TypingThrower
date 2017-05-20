package game;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;

import connection.Client;
import connection.Controller;
import connection.DatabaseConnect;
import gameui.GameUI;

public class GameMain{
	public static void main(String[] args) {
		// Controller c = new Controller();
		GameUI ui = new GameUI(new TypingThrower(new Player("Aom", 1000, 100),
				new Player("Win", 1000, 100)));
		ui.run();
		Controller ctrl = Controller.getInstance();
		ctrl.setUI(ui);
//		ctrl.setClient(new Client("35.185.188.93", 3001));
		ctrl.setClient(new Client("104.198.173.104", 3306));
		ctrl.join();
		
		try {
			ConnectionSource source = DatabaseConnect.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
