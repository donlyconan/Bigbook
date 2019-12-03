package bigbook.connect.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ConnectSQL {
	private static String DATABASE_NAME = "";
	private static String USER = "";
	private static String PASSWORD = "";
	private static String URL = "";
	private static Connection con = null;
	
	static
	{
		Properties prop = new Properties();
		FileInputStream fis = null;
		try
		{
			Class.forName("");
		} catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		try {
			fis = new FileInputStream(new File("Data\\Properties\\Database.pro"));
			prop.load(fis);
			DATABASE_NAME = prop.getProperty("database");
			USER = prop.getProperty("user");
			PASSWORD = prop.getProperty("password");
		} catch (Exception e) {
			Alert noti = new Alert(AlertType.ERROR, "Error! load files: " + e.getMessage(), ButtonType.CLOSE);
			noti.setTitle("System");
			noti.showAndWait();
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
			con = DriverManager.getConnection(strcon);
		}
		return con;
	}
	
	
	public static void close() throws SQLException
	{
		if(!con.isClosed())
			con.close();
	}
}
