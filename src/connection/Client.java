package connection;

import com.lloseng.ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected synchronized void handleMessageFromServer(Object msg) {
		String message = (String) msg;
		Controller ctrl = Controller.getInstance();
		if (message.equals("attacked")) {
			ctrl.attackedUI();
		} else if (message.equals("attack")) {
			ctrl.attackUI();
		} else if (message.equals("wait")) {
			ctrl.setPlayer("1");
			ctrl.waitingUI();
		} else if (message.equals("start")) {
			ctrl.start();
		} else if (message.substring(0, 5).equals("myWPM")) {
			ctrl.mywpmUI(message.substring(6));
		} else if (message.substring(0, 7).equals("oppoWPM")) {
			ctrl.oppowpmUI(message.substring(8));
		} else if (message.equals("finish")) {
			ctrl.endGame();
		}

	}
	//
	// public static void main(String[] args) {
	// Client c = new Client("35.185.188.93", 3001);
	// try {
	// c.openConnection();
	// System.out.println("Connected");
	// Scanner sc = new Scanner(System.in);
	// while (true) {
	// c.sendToServer(sc.nextLine());
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
