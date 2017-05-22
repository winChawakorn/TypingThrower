package game;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;

import connection.Client;
import connection.Controller;
import connection.DatabaseConnect;
import gameui.GameUI;

public class GameMain {
	public static void main(String[] args) {
		GameUI ui = new GameUI();
		ui.run();
		Controller ctrl = Controller.getInstance();
		ctrl.setUI(ui);
		ctrl.setClient(new Client("", 3001));
		ctrl.joinServer();
		try {
			ConnectionSource source = DatabaseConnect.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}