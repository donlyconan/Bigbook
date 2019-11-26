package load.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Plugin
{
	public static final String ROOT_PATH = "Fxml\\";
	public static final String ROOT_FILE = "File\\";
	static final Hashtable<String, Object> DATA_SHARE = new Hashtable<String, Object>();

	static 
	{
		try {
			DATA_SHARE.put("main", loadParent("123"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	
	public static Parent loadParent( String pathname) throws IOException {
		return FXMLLoader.load(Plugin.class.getResource(ROOT_PATH + pathname));
	}

	public static InputStream getInputStream( String pathname) {
		return Plugin.class.getResourceAsStream(ROOT_FILE + pathname);
	}
	
	public static InputStream getInputStreamIcon( String pathname) {
		return Plugin.class.getResourceAsStream("Icon\\" + pathname);
	}

	public static Hashtable<String, Object> getDataShare( ) { return DATA_SHARE; }

}
