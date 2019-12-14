package resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import server.api.Print;
import server.platform.Platform;

public class Loader implements Platform {
	public static final String FILE = "data/";
	public static final String FILE_ICON = "icon/";
	public static final String FILE_FXML = "fxml/";
	public static final String FILE_AUDIO = "audio/";

	public static void Load() throws IOException {
		Data.put(Type.FXController, loadParent("fxml/FXMLController.fxml"));
		Data.put(Type.FXComputer, loadParent("fxml/FXMLComputers.fxml"));
		Data.put(Type.FXMyFiles, loadParent("fxml/FXMLMyFiles.fxml"));
		Platform.stackAddChilden((Parent) Data.get(Type.FXComputer));
		Platform.stackAddChilden((Parent) Data.get(Type.FXMyFiles));
		Print.log(Level.FINE, "Checking values to start!");
		for (Object item : Data.values())
			Print.log(Level.FINE, item);
	}

	public static URL getResource(String pathname) {
		return Loader.class.getResource(pathname);
	}

	public static String getProperties(String key) {
		String value = null;
		File file = new File("Data/File/Attribute.pro");
		try (FileInputStream fis = new FileInputStream(file)) {
			Properties pro = new Properties();
			pro.load(fis);
			value = pro.getProperty(key);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (value != null && value.length() < 1) {
			value = null;
		}
		return value;
	}

	public static boolean writeProperties(String key, String value) {
		File file = new File("Data/File/Attribute.pro");
		
		try (FileOutputStream fos = new FileOutputStream(file)) {
			Properties pro = new Properties();
			pro.put(key, value);
			pro.store(fos, "Diretory key");
			fos.flush();
			
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
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

	public static List<Image> loadImages(String[] pathname) {
		List<Image> images = new ArrayList<Image>();
		for (String item : pathname)
			images.add(loadImage(item));
		return images;
	}

	public static InputStream getInputStream(String path) {
		return Loader.class.getResourceAsStream(path);
	}
}
