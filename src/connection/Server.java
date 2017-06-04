package connection;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

/**
 * A server for this game. This server can manage all data that send through
 * this server to other clients.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class Server extends AbstractServer {
	private List<GameRoom> rooms;
	private Map<ConnectionToClient, UserTable> users;
	private List<String> usernames;

	public final static char FIND_ROOM = 'r';
	public final static char CANCEL_FIND_ROOM = 'c';
	public final static char END = 'e';
	public final static char ATTACK = 'a';
	public final static char HURT = 'h';
	public final static char LOGOUT = 'o';
	public final static char WAIT = 'w';
	public final static char LOGIN_FAIL = 'f';
	public final static char LOGIN_SUCCESS = 's';

	/**
	 * Initialize new Server with the specific port.
	 * 
	 * @param port
	 *            is the port number to open
	 */
	public Server(int port) {
		super(port);
		this.setBacklog(3);
		this.rooms = new ArrayList<GameRoom>();
		this.users = new HashMap<ConnectionToClient, UserTable>();
		this.usernames = new ArrayList<String>();
	}

	/**
	 * Print client's IP when the client connected.
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		System.out.print("\n"
				+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date()) + "  ");
		System.out.println(client.getInetAddress() + " has connected");
	}

	/**
	 * Print client's IP when the client disconnected,remove the username if
	 * this user has logged in, and remove the room if this disconnection make
	 * the room empty.
	 */
	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.print(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date()) + "  ");
		if (users.containsKey(client)) {
			System.out.println("user " + users.get(client).getUsername()
					+ " has disconnected\n");
			usernames.remove(users.get(client).getUsername());
			users.remove(client);
		} else
			System.out.println("Someone has disconnected\n");
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

	/**
	 * Handle the message from the client. If the msg is a UserTable, it will
	 * use this UserTable to login for this user. If the msg is a char or a
	 * String, it will make an action related with that object.
	 */
	@Override
	protected synchronized void handleMessageFromClient(Object msg,
			ConnectionToClient client) {
		GameRoom findClientRoom = findClientRoom(client);
		if (msg instanceof Character) {
			char message = (char) msg;
			if (message == FIND_ROOM) {
				if (rooms.size() > 0) {
					for (GameRoom r : rooms) {
						if (!r.isFull()) {
							r.add(client);
							if (r.isFull()) {
								try {
									client.sendToClient(users.get(r
											.getOpponent(client)));
									r.getOpponent(client).sendToClient(
											users.get(client));
									System.out.print(new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss")
											.format(new java.util.Date()));
									System.out
											.println("  room "
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
					System.err
							.print(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
									.format(new java.util.Date()));
					System.err.println("  create new room\n");
					GameRoom r2 = new GameRoom();
					r2.add(client);
					rooms.add(r2);
					try {
						client.sendToClient(WAIT);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message == CANCEL_FIND_ROOM && findClientRoom != null) {
				if (findClientRoom.count() == 1) {
					rooms.remove(findClientRoom);
					try {
						client.sendToClient(CANCEL_FIND_ROOM);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message == END) {
				if (findClientRoom != null)
					if (rooms.contains(findClientRoom))
						rooms.remove(findClientRoom);
			} else if (message == ATTACK) {
				if (findClientRoom != null) {
					try {
						client.sendToClient(ATTACK);
						findClientRoom.getOpponent(client).sendToClient(HURT);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (message == LOGOUT) {
				System.out
						.println("\n"
								+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
										.format(new java.util.Date()) + "  "
								+ users.get(client).getUsername()
								+ " has logged out\n");
				usernames.remove(users.get(client).getUsername());
				users.remove(client);
			}
		} else if (msg instanceof UserTable) {
			UserTable user = (UserTable) msg;
			if (usernames.contains(user.getUsername())) {
				try {
					client.sendToClient(LOGIN_FAIL);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			System.err.println("\n"
					+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
							.format(new java.util.Date()) + "  +++ Welcome "
					+ user.getUsername() + " to TypingThrower +++\n");
			users.put(client, user);
			usernames.add(user.getUsername());
			try {
				client.sendToClient(LOGIN_SUCCESS);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msg instanceof String) {
			String message = (String) msg;
			if (message.contains("wpm")) {
				if (findClientRoom != null) {
					String wpm = message.substring(4);
					try {
						client.sendToClient("myWPM " + wpm);
						findClientRoom.getOpponent(client).sendToClient(
								"oppoWPM " + wpm);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Find the room that contains this client.
	 * 
	 * @param client
	 *            is the client to find the room for.
	 * @return the room that contains this client.
	 */
	public GameRoom findClientRoom(ConnectionToClient client) {
		for (GameRoom r : rooms) {
			if (r.getC1() == client || r.getC2() == client)
				return r;
		}
		return null;
	}

	/**
	 * Main that use to run the server.
	 * 
	 * @param args
	 *            not use
	 */
	public static void main(String[] args) {
		Server s = new Server(3007);
		try {
			s.listen();
			System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.format(new java.util.Date()) + "  Server started");
			System.out.println();
			Scanner sc = new Scanner(System.in);
			while (true) {
				System.err.println("Menu : (p)rint detail");
				String ans = sc.nextLine();
				if (ans.equals("p"))
					s.printDetail();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print the details for this server. The details are number of current
	 * user, all current user's IP, number of room, number of player in each
	 * room, each name of the online username.
	 */
	public void printDetail() {
		System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date()));
		System.out.println("Current client(s) : " + getNumberOfClients());
		System.out.println("All users : "
				+ Arrays.toString(getClientConnections()));
		System.out.println(rooms.size() + " rooms here : "
				+ Arrays.toString(rooms.toArray(new GameRoom[0])));
		System.out.println("All login users : "
				+ Arrays.toString(usernames.toArray(new String[0])) + "\n");
		if (rooms.size() > 0)
			for (GameRoom r : rooms) {
				System.out.println(r.count() + " player(s) in room "
						+ (rooms.indexOf(r) + 1));
			}
		System.out.println();
	}
}
