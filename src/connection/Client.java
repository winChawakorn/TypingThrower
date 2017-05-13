package connection;

import java.io.IOException;

import com.lloseng.ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		String message = (String) msg;
		if (message.equals("hostAttack")) {

		}
	}

	public static void main(String[] args) {
		Client c = new Client("", 3001);
		try {
			c.openConnection();
			// System.out.println("Connected");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
