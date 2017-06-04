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
	 * is a char, it will make an action. Such as 'f' will make this method
	 * invoke loginError() from the Controller.
	 */
	@Override
	protected synchronized void handleMessageFromServer(Object msg) {
		Controller ctrl = Controller.getInstance();
		if (msg instanceof Character) {
			char message = (char) msg;
			if (message == Server.LOGIN_SUCCESS) {
				ctrl.loginSuccess();
			}
			if (message == Server.LOGIN_FAIL) {
				ctrl.loginError();
			}
			if (message == Server.CANCEL_FIND_ROOM) {
				ctrl.CancelfindGame();
			}
			if (message == Server.HURT) {
				ctrl.attackedUI();
			} else if (message == Server.ATTACK) {
				ctrl.attackUI();
			} else if (message == Server.WAIT) {
				ctrl.waiting();
			}
		} else if (msg instanceof UserTable) {
			UserTable opponent = (UserTable) msg;
			ctrl.start(opponent);
		} else if (msg instanceof String) {
			String message = (String) msg;
			if (message.substring(0, 5).equals("myWPM")) {
				ctrl.mywpmUI(message.substring(6));
			} else if (message.substring(0, 7).equals("oppoWPM")) {
				ctrl.oppowpmUI(message.substring(8));
			}
		}
	}
}
