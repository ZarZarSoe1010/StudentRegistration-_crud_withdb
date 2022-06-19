package persistant.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	static Connection con=null;
	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentregistration","root","admin123");
		} catch (ClassNotFoundException e) {
			System.out.println("Connecting!!");
		} catch (SQLException e) {
			System.out.println("Database Not Found!");
		}
		return con;
	}

}
