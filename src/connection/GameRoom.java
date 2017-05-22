package connection;

import com.lloseng.ocsf.server.ConnectionToClient;

public class GameRoom {
	private ConnectionToClient c1 = null;
	private ConnectionToClient c2 = null;
	private boolean access = true;

	/**
	 * Add client to null slot
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

	public boolean canAccess() {
		return this.access;
	}

	public ConnectionToClient getOpponent(ConnectionToClient c) {
		if (c == c1)
			return c2;
		if (c == c2)
			return c1;
		return null;
	}

	public void p1Disconnected() {
		c1 = c2;
		c2 = null;
	}

	public void p2Disconnected() {
		c2 = null;
	}

	public int count() {
		int count = 0;
		if (c1 != null)
			count++;
		if (c2 != null)
			count++;
		if (count == 2)
			access = false;
		return count;
	}

	public boolean isFull() {
		if (count() == 2) {
			return true;
		}
		return false;
	}

	public ConnectionToClient getC1() {
		return c1;
	}

	public ConnectionToClient getC2() {
		return c2;
	}

}
