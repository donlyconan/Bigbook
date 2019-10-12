package load.document.fxml;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Loader
{
	public Parent loadParent(String path) throws IOException {
		return FXMLLoader.load(Loader.class.getResource(path));
	}
}
