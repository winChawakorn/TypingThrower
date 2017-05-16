package connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {
	private List<Thread> ipChecker;
	private List<ConnectionToClient> room;

	public Server(int port) {
		super(port);
		this.setBacklog(3);
		this.ipChecker = new ArrayList<Thread>();
		this.room = new ArrayList<ConnectionToClient>();
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		if (ipChecker.size() > 0) {
			for (Thread th : ipChecker) {
				if (((ConnectionToClient) th).getInetAddress().equals(
						client.getInetAddress())) {
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return;
		}
		ipChecker = Arrays.asList(this.getClientConnections());
		System.out.println("Someone connected");
		System.out.println("Current client : " + this.getNumberOfClients());
		System.out.println("this user : " + client.getInetAddress());
		System.out.println("All users : "
				+ Arrays.toString(this.getClientConnections()));
		System.out.println();
		System.out.println("Menu : (p)rint detail");
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.println("\nSomeone has disconnected");
		// System.out.println("Current client : " + this.getNumberOfClients());
		// System.out.println("All users : "
		// + Arrays.toString(this.getClientConnections()));
		System.out.println();
		System.out.println("Menu : (p)rint detail");
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String message = (String) msg;
		// Controller ctrl = Controller.getInstance();
		if (message.equals("Attack")) {
			try {
				client.sendToClient("Attacked");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (message.equals("play")) {
			room.add(client);
			if (room.size() == 2) {
				room.get(0).setInfo(room.get(1).getInfo("Name") + "", "vs");
				room.get(1).setInfo(room.get(0).getInfo("Name") + "", "vs");
				room.remove(1);
				room.remove(0);
			}
		} else if (message.contains("Login")) {
			String[] split = message.split(" ");
			client.setInfo(split[1], "Name");
		} else if (message.contains("Attack")) {
			for (ConnectionToClient cl : (ConnectionToClient[]) this
					.getClientConnections()) {
				if (cl.getInfo("vs").equals(message.substring(7))) {
					try {
						cl.sendToClient("Attacked");
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		Server s = new Server(3001);
		try {
			s.listen();
			System.out.println("Server started");
			System.out.println();
			Scanner sc = new Scanner(System.in);
			while (true) {
				System.out.println("Menu : (p)rint detail");
				String ans = sc.nextLine();
				if (ans.equals("p")) {
					System.out.println("Current client : "
							+ s.getNumberOfClients());
					System.out.println("All users : "
							+ Arrays.toString(s.getClientConnections()));
					System.out.println();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
