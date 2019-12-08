package resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import server.platform.Platform;

public class Loader implements Platform {
	public static final String FILE = "data\\";
	public static final String FILE_ICON = "icon\\";
	public static final String FILE_FXML = "fxml\\";
	public static final String FILE_AUDIO = "audio\\";

	public static void Load() throws IOException {
		Data.put(Type.FXMyFiles, Loader.loadParent("FXMLMyFiles.fxml"));
		Data.put(Type.FXComputer, Loader.loadParent("FXMLComputers.fxml"));
		Data.put(Type.FXController, loadParent("FXMLController.fxml"));
		
		for(Object item : Data.values())
			System.out.println("Check Values = " + item);
	}

	public static Parent loadParent(String pathname) throws IOException {
		return FXMLLoader.load(Loader.class.getResource(FILE_FXML + pathname));
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
