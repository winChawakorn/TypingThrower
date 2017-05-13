package connection;

import java.sql.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Main {
	public static void main(String[] args) throws Exception {
		// Accessing driver from the JAR file
//		 Class.forName("com.mysql.jdbc.Driver");

		// Creating a variable for the connection called "con"
//		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/names","root","WinAom555");

//		 if(con!=null){
//		 System.out.println("Success");
//		 }

		// Here we create our query
		// select * from 'tablename'
//		 PreparedStatement statement = con.prepareStatement("select * from names");

		// Creating a variable to execute query
//		 ResultSet result = statement.executeQuery();
		
//	     while(result.next()){
//			System.out.println(result.getString(1)+" "+result.getString(2));
        		// getString return the data
				// 1 is the first field in the table
				// 2 is the second field
//		}
		

		// Using ORMLite to connect to the database
		// Creating a variable for the connection called "conectionSource"
		ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:mysql://localhost:3306/names", "root", "WinAom555");
		// "jdbc:mysql://localhost:3306/names" -> names is database's names

		// Creating a table of Account class if the table hasn't already exist.
		TableUtils.createTableIfNotExists(connectionSource, Account.class);
		// Look at Account to get the detail of the table.

		//Creating new account 
		Account ac = new Account("Jim", "pass");

		//Translate IRMLite to SQL
		Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);

		//Adding the account to table.
		accountDao.createIfNotExists(ac);

		// create an instance of Account
		Account account = new Account();
		account.setName("Jim Coakley");

		// persist the account object to the database
		accountDao.createIfNotExists(account);

		// retrieve the account from the database by its id field (name)
		Account account2 = accountDao.queryForId("Jim Coakley");
		System.out.println("Account: " + account2.getName());

		// close the connection source
//		connectionSource.close();

		TableUtils.createTableIfNotExists(connectionSource, UserTable.class);
		UserTable user = new UserTable("Vittunyuta", "password", "Shaphita");
		Dao<UserTable, String> userDao = DaoManager.createDao(connectionSource, UserTable.class);
		userDao.createIfNotExists(user);
		user = new UserTable("Chawakorn", "password", "Nazario");
		userDao.createIfNotExists(user);
		// get HP
		UserTable getDetailUser = userDao.queryForId("Vittunyuta");
		System.out.println(getDetailUser.getHP());
		

	}

}
