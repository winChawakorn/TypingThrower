package connection;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

/**
 * This class provides the important items for connection with source and
 * provides some actions with the connection with source
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class JDBCconnect {
	private ConnectionSource connectionSource;
	private final String USERNAME = "root";
	private final String PASSWORD = "WinAom555";
	private final String URL = "jdbc:mysql://localhost:3306/names";

	/**
	 * set start connection source to be null
	 */
	public JDBCconnect() {
		connectionSource = null;
	}

	
	/**
	 * start and get the connection source
	 * 
	 * @return the connection source
	 * @throws SQLException 
	 */
	public ConnectionSource startConnect() throws SQLException {
		connectionSource = new JdbcConnectionSource(URL, USERNAME, PASSWORD);
		return connectionSource;
	}

	/**
	 * close the connection source
	 * 
	 * @throws IOException
	 */
	public void closeConnect() throws IOException {
		connectionSource.close();
	}
}
