package connection;

import java.io.IOException;
import java.util.Scanner;

import com.lloseng.ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		String message = (String) msg;
		System.out.println(message);
		Controller ctrl = Controller.getInstance();
		if (message.equals("attacked")) {
			ctrl.attackedUI();
		} else if (message.equals("attack")) {
			ctrl.attackUI();
		} else if (message.equals("wait")) {
			ctrl.waitingUI();
		} else if (message.equals("start")) {
			ctrl.start();
		}
	}

	public static void main(String[] args) {
		Client c = new Client("35.185.188.93", 3001);
		try {
			c.openConnection();
			System.out.println("Connected");
			Scanner sc = new Scanner(System.in);
			while (true) {
				c.sendToServer(sc.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
