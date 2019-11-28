package load.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import bigbook.Platform.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Plugin implements Platform
{
	public static final String ROOT_PATH = "Fxml\\";
	public static final String ROOT_FILE = "File\\";

	static
	{
		// Load resource
		try
		{
			DATA_SHARE.put("CT", loadParent("ControlServer.fxml"));
			DATA_SHARE.put("DB", loadParent("DashBroad.fxml"));
			DATA_SHARE.put("MG", loadParent("Manager.fxml"));
			DATA_SHARE.put("MGF", loadParent("ManagerFilesSystem.fxml"));
		} catch (IOException e)
		{
			Alert noti = new Alert(AlertType.ERROR, "Error! load files: " + e.getMessage(), ButtonType.CLOSE);
			noti.setTitle("System");
			noti.showAndWait();
			e.printStackTrace();
			System.exit(0);
		}
	};

	public static Parent loadParent( String pathname) throws IOException
	{ return FXMLLoader.load(Plugin.class.getResource(ROOT_PATH + pathname)); }

	public static InputStream getInputStream( String pathname)
	{ return Plugin.class.getResourceAsStream(ROOT_FILE + pathname); }

	public static InputStream getInputStreamIcon( String pathname)
	{ return Plugin.class.getResourceAsStream("Icon\\" + pathname); }

	public static Hashtable<String, Object> getDataShare( )
	{ return DATA_SHARE; }

}
