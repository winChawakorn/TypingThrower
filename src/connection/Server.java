package connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {
	// private List<Thread> ipChecker;
	// private List<ConnectionToClient> room;
	private List<GameRoom> rooms;
	// private Map<ConnectionToClient, String> users;
	private Map<ConnectionToClient, UserTable> users;

	// private GameRoom room;

	// private List<Player> player = new ArrayList<>();

	public Server(int port) {
		super(port);
		this.setBacklog(3);
		// this.ipChecker = new ArrayList<Thread>();
		// this.room = new ArrayList<ConnectionToClient>();
		this.rooms = new ArrayList<GameRoom>();
		// this.users = new HashMap<ConnectionToClient, String>();
		this.users = new HashMap<ConnectionToClient, UserTable>();
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
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
		if (users.containsKey(client)) {
			System.out.println("user " + users.remove(client).getUsername()
					+ " has disconnected");
			users.remove(client);
		} else
			System.out.println("\nSomeone has disconnected");
		System.out.println();
		System.out.println("Menu : (p)rint detail");
		for (GameRoom r : rooms) {
			if (r.getC1() == client) {
				r.p1Disconnected();
			} else if (r.getC2() == client) {
				r.p2Disconnected();
			}
			if (r.count() == 0)
				rooms.remove(r);
		}
	}

	@Override
	protected synchronized void handleMessageFromClient(Object msg,
			ConnectionToClient client) {
		if (msg instanceof String) {
			String message = (String) msg;
			// System.out.println(client);
			// System.out.println("From client: " + message);
			GameRoom findClientRoom = findClientRoom(client);
			if (message.equals("find room")) {
				if (rooms.size() > 0) {
					for (GameRoom r : rooms) {
						if (!r.isFull() && r.canAccess()) {
							r.add(client);
							if (r.isFull()) {
								try {
									client.sendToClient(users.get(r
											.getOpponent(client)));
									r.getOpponent(client).sendToClient(
											users.get(client));
									System.out
											.println("room "
													+ (rooms.indexOf(r) + 1)
													+ " start");
									break;
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				findClientRoom = findClientRoom(client);
				if (findClientRoom == null) {
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
			} else if (message.equals("Cancel") && findClientRoom != null) {
				if (findClientRoom.count() == 1) {
					rooms.remove(findClientRoom);
					try {
						client.sendToClient("Cancel");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message.equals("finish")) {
				if (findClientRoom != null) {
					if (rooms.contains(findClientRoom)) {
						rooms.remove(findClientRoom);
					}
					try {
						client.sendToClient("finish");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message.equals("attack")) {
				if (findClientRoom != null) {
					try {
						client.sendToClient("attack");
						findClientRoom.getOpponent(client).sendToClient(
								"attacked");
						// System.out.println("attack");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message.contains("wpm")) {
				if (findClientRoom != null) {
					String wpm = message.substring(4);
					try {
						client.sendToClient("myWPM " + wpm);
						findClientRoom.getOpponent(client).sendToClient(
								"oppoWPM " + wpm);
						// System.out.println("sent wpm = " + wpm);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message.equals("logout")) {
				System.out.println("\n" + users.get(client).getUsername()
						+ " has logged out\n");
				users.remove(client);
			}
		} else if (msg instanceof UserTable) {
			UserTable user = (UserTable) msg;
			System.out.println("\n+++Welcome " + user.getUsername()
					+ " to TypingThrower+++\n");
			users.put(client, user);
		}
		// System.out.println(rooms.size() + " rooms here : "
		// + Arrays.toString(rooms.toArray(new GameRoom[0])));
	}

	public GameRoom findClientRoom(ConnectionToClient client) {
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
					List<String> names = new ArrayList<String>();
					for (UserTable user : s.users.values()) {
						names.add(user.getUsername());
					}
					System.out.println("All login users : "
							+ Arrays.toString(names.toArray(new String[0]))
							+ "\n");
					if (s.rooms.size() > 0)
						for (GameRoom r : s.rooms) {
							System.out.println("There is " + r.count()
									+ " in room " + (s.rooms.indexOf(r) + 1));
						}
					System.out.println();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
