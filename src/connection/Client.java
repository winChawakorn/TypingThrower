package connection;

import game.Player;

import java.io.IOException;
import java.util.Scanner;

import com.lloseng.ocsf.client.AbstractClient;

public class Client extends AbstractClient {
	private Player player;

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof Player) {

		}
		String message = (String) msg;
		System.out.println(message);
		if (message.contains("player")) {

		}
		Controller ctrl = Controller.getInstance();
		if (message.equals("Attacked")) {
			ctrl.attacked();
		}
	}

	public static void main(String[] args) {
		Client c = new Client("", 3001);
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
