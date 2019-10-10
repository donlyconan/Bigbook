package bigbook.connect.SqlServer;

public class Connection {
	private static String DATABASE_NAME;
	private static String PASSWORD;
	private static String URL = "";
	private static java.sql.Connection con;
	
	public static java.sql.Connection getConnect() {
		return con;
	}
}
