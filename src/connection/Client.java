package connection;

import com.lloseng.ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected synchronized void handleMessageFromServer(Object msg) {
		Controller ctrl = Controller.getInstance();
		if (msg instanceof String) {
			String message = (String) msg;
			if (message.equals("login success")) {
				ctrl.loginSuccess();
			}
			if (message.equals("cant login")) {
				ctrl.loginError();
			}
			if (message.equals("Cancel")) {
				ctrl.CancelfindGame();
			}
			if (message.equals("attacked")) {
				ctrl.attackedUI();
			} else if (message.equals("attack")) {
				ctrl.attackUI();
			} else if (message.equals("wait")) {
				ctrl.waiting();
			} else if (message.substring(0, 5).equals("myWPM")) {
				ctrl.mywpmUI(message.substring(6));
			} else if (message.substring(0, 7).equals("oppoWPM")) {
				ctrl.oppowpmUI(message.substring(8));
			}
		} else if (msg instanceof UserTable) {
			UserTable opponent = (UserTable) msg;
			ctrl.start(opponent);
		}
	}
}
