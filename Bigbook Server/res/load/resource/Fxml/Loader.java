package load.resource.Fxml;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Loader
{
	public static Parent loadParent(String parentname) throws IOException {
		return FXMLLoader.load(Loader.class.getResource(parentname));
	}
}
