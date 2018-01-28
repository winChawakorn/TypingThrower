package connection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import gameui.MainFrame;

/**
 * This class provides the important items for connection with source and
 * provides some actions with the connection with source
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class DatabaseConnect {
	private static DatabaseConnect databaseConnect = null;
	private static ConnectionSource connectionSource = null;
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("mystuff");
	private static final String USERNAME = bundle.getString("jdbc.user");;
	private static final String PASSWORD = bundle.getString("jdbc.password");
	private static final String URL = bundle.getString("jdbc.url");
	private Dao<UserTable, String> userDao;
	private List<UserTable> getDetailUser;
	private UpdateBuilder<UserTable, String> updateBuilder;

	/**
	 * set start connection source to be null
	 */
	private DatabaseConnect() {
		try {
			connectionSource = new JdbcConnectionSource(URL, USERNAME, PASSWORD);
			userDao = DaoManager.createDao(connectionSource, UserTable.class);
			updateBuilder = userDao.updateBuilder();
			// TableUtils.createTableIfNotExists(connectionSource,
			// UserTable.class);
		} catch (SQLException e) {
			System.out.println("error");
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
		if (databaseConnect == null)
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
	 * 
	 * @throws SQLException
	 *             when application can't connect to the database
	 */
	public List<UserTable> pullAllUserdata() {
		try {
			getDetailUser = userDao.queryForAll();
		} catch (SQLException e) {
			System.out.println("pull data error");
			MainFrame.showConnectionErrorUI();
		}
		return getDetailUser;
	}

	/**
	 * Checking an input user account is already exist, or not.
	 * 
	 * @param id
	 *            is id of account (id is username)
	 * @return true if the input user account is already exist, otherwise return
	 *         false.
	 */
	public boolean isUserExist(String id) {
		UserTable userTable = null;
		try {
			userTable = userDao.queryForId(id);
		} catch (SQLException e) {
			System.out.println("User exist error");
			MainFrame.showConnectionErrorUI();
		}
		return userTable != null;
	}

	/**
	 * Creating input account in the database.
	 * 
	 * @param userToAdd
	 *            is new account to create in the database.
	 */
	public void createUser(UserTable userToAdd) {
		try {
			userDao.createIfNotExists(userToAdd);
		} catch (SQLException e) {
			System.out.println("create user error");
			MainFrame.showConnectionErrorUI();
		}
	}

	/**
	 * update this user data on the database
	 * 
	 * @param user
	 */
	public void updateUserData(UserTable user) {
		try {
			double win = user.getWinRound();
			double lose = user.getLoseRound();
			double totalwpm = user.getTotalWPM();
			double wpm = 0;
			if (win + lose != 0)
				wpm = totalwpm / (win + lose);
			user.setWPM(wpm);
			// find target row
			updateBuilder.where().eq("Username", user.getUsername());
			// update values
			updateBuilder.updateColumnValue("WPM", wpm);
			updateBuilder.updateColumnValue("WinRound", win);
			updateBuilder.updateColumnValue("LoseRound", lose);
			updateBuilder.updateColumnValue("TotalWPM", totalwpm);
			updateBuilder.update();
		} catch (SQLException e) {
			System.out.println("55555");
			System.out.println(e.getMessage());

			MainFrame.showConnectionErrorUI();
		}
	}

}
