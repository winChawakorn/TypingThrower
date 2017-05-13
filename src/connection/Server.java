package connection;

import java.io.IOException;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {

	public Server(int port) {
		super(port);
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println("Someone connected");
		System.out.println(this.getNumberOfClients());
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String message = (String) msg;
	}

	public static void main(String[] args) {
		Server s = new Server(3001);
		try {
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
