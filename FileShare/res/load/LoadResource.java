package load;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

public class LoadResource
{
	private static final Hashtable<String, Object> SHARE_DATA = new Hashtable<String, Object>();
	
	public static Parent loadParent(String pathname) throws IOException
	{
		return FXMLLoader.load(LoadResource.class.getResource("fxml//"+pathname));
	}
	
	public static Image loadImage(String pathname)
	{
		return new Image(LoadResource.class.getResourceAsStream(pathname));
	}
	
	static 
	{
		try {
			SHARE_DATA.put("main", loadParent("Main_Activity.fxml"));
			SHARE_DATA.put("folder", loadImage("icon//folder.png"));
			SHARE_DATA.put("file", loadImage("icon//file.png"));
		} catch (IOException e) {
			Alert alter = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
			alter.showAndWait();
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static InputStream getInputStream(String path)
	{
		return LoadResource.class.getResourceAsStream(path);
	}

	public static Hashtable<String, Object> ShareData( ) { return SHARE_DATA; }	
}
