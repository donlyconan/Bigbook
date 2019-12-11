package bigbook.connect.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import load.resource.Print;
import load.resource.Print.Content;

public class SQLManager {
	//private static String DATABASE = "";
	private static String USER = "";
	private static String DATABASE = "";
	private static String PASSWORD = "";
	private static Connection con = null;
	
	static
	{
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			fis = new FileInputStream(new File("Data\\Properties\\Database.pro"));
			prop.load(fis);
			DATABASE = prop.getProperty("database");
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
			String strcon = "jdbc:sqlserver://localhost:1433;databasename=" + DATABASE;
			con = DriverManager.getConnection(strcon, USER, PASSWORD);
		}
		
		if(con != null)
			Print.out(Content.SUCCESS, "Connect sql successfully!");
		else
			Print.out(Content.ERROR, "Connect is error!");
		return con;
	}
	
	
	public static void close() throws SQLException
	{
		if(con != null && !con.isClosed())
			con.close();
	}
}
