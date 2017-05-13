package connection;

import java.sql.*;

public class Main {
	public static void main(String[] args) throws Exception {
		// Accessing driver from the JAR file
		Class.forName("com.mysql.jdbc.Driver");

		// Creating a variable for the connection called "con"
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/tested", "root", "");
		// jdbc:mysql://localhost:3306/tested --> This is the database
		// root is the database
		// root is the password
		if (con != null) {
			System.out.println("Success");
		}

		// Here we create our query
		// select * from 'tablename'
		PreparedStatement statement = con
				.prepareStatement("select * from names");

		// Creating a variable to execute query
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			System.out.println(result.getString(1) + " " + result.getString(2));
			// getString return the data
			// 1 is the first field in the table
			// 2 is the second field
		}

	}

}
