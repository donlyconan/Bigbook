package bigbook.connect.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLManager {
	//private static String DATABASE = "";
	private static String USER = "";
	private static String PASSWORD = "";
	private static Connection con = null;
	
	static
	{
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			Class.forName("");
			fis = new FileInputStream(new File("Data\\Properties\\Database.pro"));
			prop.load(fis);
//			DATABASE = prop.getProperty("database");
			USER = prop.getProperty("user");
			PASSWORD = prop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {}
		}
	}
	
	public static Connection getConnect() throws SQLException, ClassNotFoundException 
	{
		if(con == null || con.isClosed())
		{
			String strcon = "";
			con = DriverManager.getConnection(strcon, USER, PASSWORD);
		}
		return con;
	}
	
	
	public static void close() throws SQLException
	{
		if(con != null && !con.isClosed())
			con.close();
	}
}
