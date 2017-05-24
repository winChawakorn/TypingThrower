package connection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import gameui.CantConnectUI;
import gameui.MainFrame;

/**
 * This class provides the important items for connection with source and
 * provides some actions with the connection with source
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class DatabaseConnect{
	private static DatabaseConnect databaseConnect = null;
	private static ConnectionSource connectionSource = null;
	private final static String USERNAME = "root";
	private final static String PASSWORD = "WinAom555";
	private final static String URL = "jdbc:mysql://104.198.173.104:3306/names";
	private Dao<UserTable, String> userDao;
	private List<UserTable> getDetailUser;
	
	/**
	 * set start connection source to be null
	 */
	private DatabaseConnect() {
		try {
			connectionSource = new JdbcConnectionSource(URL, USERNAME, PASSWORD);
			userDao = DaoManager.createDao(connectionSource, UserTable.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * start and get the connection source
	 * 
	 * @return the connection source
	 * @throws SQLException 
	 */
	public static DatabaseConnect getInstance() {
		if(databaseConnect == null)
			databaseConnect = new DatabaseConnect();
		return databaseConnect;
	}

	/**
	 * close the connection source
	 * 
	 * @throws IOException
	 */
	public void closeConnect() throws IOException {
		connectionSource.close();
	}
	
	/**
	 * get all users data in database.
	 * @throws SQLException when application can't connect to the database
	 */
	public List<UserTable> pullAllUserdata() {
		try {
			getDetailUser = userDao.queryForAll();
		} catch (SQLException e) {
			System.out.println("<");
			MainFrame.showConnectionErrorUI();
		}
		return getDetailUser;
	}
	
	public boolean isUserExist(String id) {
		UserTable userTable = null;
		try {
			userTable = userDao.queryForId(id);
		} catch (SQLException e) {
			System.out.println("<<");
			MainFrame.showConnectionErrorUI();
		}
		return userTable != null;
	}
	
	public void createUser(UserTable userToAdd){
		try {
			userDao.createIfNotExists(userToAdd);
		} catch (SQLException e) {
			System.out.println("<<<");
			MainFrame.showConnectionErrorUI();
		}
	}
	
}
