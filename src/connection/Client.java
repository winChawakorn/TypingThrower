package connection;

import com.lloseng.ocsf.client.AbstractClient;

/**
 * Client for this game. Used to connect to the server and make a communication.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class Client extends AbstractClient {
	/**
	 * Initialize new Client with host and port.
	 * 
	 * @param host
	 *            is the server IP address.
	 * @param port
	 *            is the port number of the server.
	 * @see AbstractClient#AbstractClient(String, int)
	 */
	public Client(String host, int port) {
		super(host, port);
	}

	/**
	 * Handle the message from the server and make some action. If msg is a
	 * UserTable(the opponent information), the online game will start. If msg
	 * is a String, it will make an action as the same meaning with that
	 * message. Such as "cant login" will make this method invoke loginError()
	 * from the Controller.
	 */
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
