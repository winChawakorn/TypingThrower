package connection;

import java.io.IOException;

public class Controller {
	private Server s;
	private Client c;

	public Controller() {

	}

	public void host(int port) {
		s = new Server(port);
		try {
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void join(String ip, int port) {
		c = new Client(ip, port);
		try {
			c.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hostAttack() {
		s.sendToAllClients("hostAttack");
	}

	public void clientAttack() {
		try {
			c.sendToServer("clientAttack");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
