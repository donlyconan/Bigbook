package server.api;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import resource.Loader;

public class APILoader {

	public static Button createButton(String text) {
		Button but = new Button(text, getIconFile("folder", 20, 20));
		but.setStyle("-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px;");
		but.setPrefHeight(50);
		but.setMaxWidth(100);
		but.setMinWidth(60);
		return but;
	}
	
	public static Text createText() {
		Text tv = new Text(">");
		tv.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
		return tv;
	}

	public static List<ImageView> getListImageViews(String []image, int width, int height) {
		List<ImageView> imgs = new ArrayList<ImageView>();
		for (String item : image) {
			imgs.add(getIconFile(item, width, height));
		}
		return imgs;
	}

	public static ImageView getIconFile(String name) {
		ImageView image = new ImageView(Loader.loadImage(name + ".png"));
		image.setFitHeight(60);
		image.setFitWidth(60);
		return image;
	}

	public static ImageView getIconFile(String pathname, double d, double f) {
		try {
			ImageView image = new ImageView(Loader.loadImage(pathname + ".png"));
			image.setFitHeight(d);
			image.setFitWidth(f);
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static ImageView getIconFolder() {
		ImageView image = new ImageView(Loader.loadImage("folder.png"));
		image.setFitHeight(60);
		image.setFitWidth(60);
		return image;
	}
}
