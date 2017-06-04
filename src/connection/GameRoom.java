package connection;

import com.lloseng.ocsf.server.ConnectionToClient;

/**
 * A game room for this game that use to manage the players in each room in the
 * server.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class GameRoom {
	private ConnectionToClient c1 = null;
	private ConnectionToClient c2 = null;
	private boolean isStart = false;

	/**
	 * Add client to empty slot of this room.
	 * 
	 * @param c
	 *            is the ConnectionToClient to be added.
	 */
	public void add(ConnectionToClient c) {
		if (!isFull()) {
			if (c1 == null)
				c1 = c;
			else
				c2 = c;
			count();
		}
	}

	/**
	 * Return the status of this room. Can access or not.
	 * 
	 * @return access which contains the access status.
	 */
	// public boolean canAccess() {
	// return this.access;
	// }

	/**
	 * Return the opponent of c which check by the another ConnectionToClient.
	 * 
	 * @param c
	 *            is the player to find the opponent for.
	 * @return the opponent of c.
	 */
	public ConnectionToClient getOpponent(ConnectionToClient c) {
		if (c == c1)
			return c2;
		if (c == c2)
			return c1;
		return null;
	}

	/**
	 * Make change when p1 is disconnected.
	 */
	public void p1Disconnected() {
		c1 = c2;
		c2 = null;
	}

	/**
	 * Make change when p2 is disconnected.
	 */
	public void p2Disconnected() {
		c2 = null;
	}

	/**
	 * Count the player in this room and return it.
	 * 
	 * @return the number of the player in this room.
	 */
	public int count() {
		int count = 0;
		if (c1 != null)
			count++;
		if (c2 != null)
			count++;
		if (count == 2)
			isStart = true;
		return count;
	}

	/**
	 * Return isFull status. True if this room is full or the game is started.
	 * False if this room is not full.
	 * 
	 * @return
	 */
	public boolean isFull() {
		if (count() == 2 || isStart)
			return true;
		return false;
	}

	/**
	 * Get the ConnectionToClient of c1
	 * 
	 * @return c1
	 */
	public ConnectionToClient getC1() {
		return c1;
	}

	/**
	 * * Get the ConnectionToClient of c2
	 * 
	 * @return c2
	 */
	public ConnectionToClient getC2() {
		return c2;
	}

}
