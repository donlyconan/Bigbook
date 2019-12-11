package load.resource;

import java.io.IOException;
import java.io.InputStream;

import bigbook.Platform.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Plugin implements Platform {
	public static final String ROOT_PATH = "Fxml\\";
	public static final String ROOT_FILE = "File\\";

	public static void load() throws IOException {

		Share.put(Attribute.FXMLControl, loadParent("ControlServer.fxml"));

	};

	public static Parent loadParent(String pathname) throws IOException {
		return FXMLLoader.load(Plugin.class.getResource(ROOT_PATH + pathname));
	}

	public static InputStream getInputStream(String pathname) {
		return Plugin.class.getResourceAsStream(ROOT_FILE + pathname);
	}

	public static InputStream getInputStreamIcon(String pathname) {
		return Plugin.class.getResourceAsStream("Icon\\" + pathname);
	}

}
