package resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import server.api.Print;
import server.api.Print.Content;
import server.platform.Platform;

public class Loader implements Platform {
	public static final String FILE = "data\\";
	public static final String FILE_ICON = "icon\\";
	public static final String FILE_FXML = "fxml\\";
	public static final String FILE_AUDIO = "audio\\";
	
	public static void Load() throws IOException {
		Data.put(Type.FXController, loadParent("fxml\\FXMLController.fxml"));
		Data.put(Type.FXComputer, loadParent("fxml\\FXMLComputers.fxml"));
		Data.put(Type.FXMyFiles, loadParent("fxml\\FXMLMyFiles.fxml"));
		Platform.stackAddChilden((Parent) Data.get(Type.FXComputer));
		Platform.stackAddChilden((Parent) Data.get(Type.FXMyFiles));
		Print.out(Content.CHECK_VALUE, "Checking values to start!");
		for(Object item : Data.values())
			Print.out(Content.CHECK_VALUE, item);
	}
	
	public static URL getResource(String pathname) {
		return Loader.class.getResource(pathname);
	}

	public static Parent loadParent(String pathname) throws IOException {
		return FXMLLoader.load(Loader.class.getResource(pathname));
	}
	

	public static Parent loadAudio(String pathname) throws IOException {
		return FXMLLoader.load(Loader.class.getResource(FILE_AUDIO + pathname));
	}

	public static Image loadImage(String pathname) {
		return new Image(Loader.class.getResourceAsStream(FILE_ICON + pathname));
	}

	public static List<Image> loadImages(String[] pathname)
	{
		List<Image> images = new ArrayList<Image>();
		for(String item : pathname)
			images.add(loadImage(item));
		return images;
	}
	
	
	public static InputStream getInputStream(String path) {
		return Loader.class.getResourceAsStream(path);
	}
}
