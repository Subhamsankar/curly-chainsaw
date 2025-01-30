import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		//Register the driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Establish the connection
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Subham@123");
		System.out.println("Connection created");
	}
}
