package connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {
	// private List<Thread> ipChecker;
	// private List<ConnectionToClient> room;
	private List<GameRoom> rooms;

	// private GameRoom room;

	// private List<Player> player = new ArrayList<>();

	public Server(int port) {
		super(port);
		this.setBacklog(3);
		// this.ipChecker = new ArrayList<Thread>();
		// this.room = new ArrayList<ConnectionToClient>();
		this.rooms = new ArrayList<GameRoom>();
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		// if (ipChecker.size() > 0) {
		// for (Thread th : ipChecker) {
		// if (((ConnectionToClient) th).getInetAddress().equals(
		// client.getInetAddress())) {
		// try {
		// client.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// return;
		// }
		// client.sendToClient(new Player(name, HP, damage));
		// ipChecker = Arrays.asList(this.getClientConnections());
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

		for (GameRoom r : rooms) {
			if (r.getC1() == client)
				r.p1Disconnected();
			else if (r.getC2() == client)
				r.p2Disconnected();
			if (r.count() == 0)
				rooms.remove(r);
		}
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String message = (String) msg;
		System.out.println(client);
		System.out.println("From client: " + message);
		// Controller ctrl = Controller.getInstance();
		// if (message.equals("Attack")) {
		// try {
		// client.sendToClient("Attacked");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		if (message.equals("find room")) {
			if (rooms.size() > 0) {
				for (GameRoom r : rooms) {
					if (!r.isFull()) {
						r.add(client);
						if (r.isFull()) {
							try {
								client.sendToClient("start");
								r.getOpponent(client).sendToClient("start");
								System.out.println("start");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						GameRoom r2 = new GameRoom();
						r2.add(client);
						rooms.add(r2);
						try {
							client.sendToClient("wait");
							System.out.println("wait");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("create new room");
				GameRoom r2 = new GameRoom();
				r2.add(client);
				rooms.add(r2);
				try {
					client.sendToClient("wait");
					System.out.println("wait");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (message.equals("finish")) {
			GameRoom r = findRoom(client);
			if (r != null) {
				if (rooms.contains(r))
					rooms.remove(r);
			}
		} else if (message.equals("attack")) {
			GameRoom r = findRoom(client);
			if (r != null) {
				try {
					client.sendToClient("attack");
					r.getOpponent(client).sendToClient("attacked");
					System.out.println("attack");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(rooms.size() + " rooms here : "
				+ Arrays.toString(rooms.toArray(new GameRoom[0])));
	}

	public GameRoom findRoom(ConnectionToClient client) {
		for (GameRoom r : rooms) {
			if (r.getC1() == client || r.getC2() == client)
				return r;
		}
		return null;
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
					System.out
							.println(s.rooms.size()
									+ " rooms here : "
									+ Arrays.toString(s.rooms
											.toArray(new GameRoom[0])));
					System.out.println();
					if (s.rooms.size() > 0)
						System.out.println("There is " + s.rooms.get(0).count()
								+ " in the first room");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
