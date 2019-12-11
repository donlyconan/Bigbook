package server.gui.item;

import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import server.api.APIFTPFile;
import server.api.APILoader;
import server.platform.Platform;

public class FTPFileItem extends VBox implements Serializable, Platform {
	public static final String STYLE_CSS = "-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px;";
	public static final Rectangle REC_IMG = new Rectangle(70,70);
	public static final Rectangle REC_ITEM = new Rectangle(70,105);
	private static final long serialVersionUID = 1L;
	private APIFTPFile file;
	private Label text;

	public FTPFileItem(APIFTPFile file) {
		super();
		this.file = file;
		text = new Label(cut(file.getName()));
		text.setStyle(STYLE_CSS);

		if (file.isFile())
			getChildren().add(init());
		else
			getChildren().add(APILoader.getIconFile("folder", REC_IMG.getWidth(), REC_IMG.getHeight()));
		getChildren().add(text);
		text.setPrefSize(REC_IMG.getWidth(), 35);
		text.setWrapText(true);
		text.setAlignment(Pos.CENTER);
		text.setTextAlignment(TextAlignment.CENTER);
		setAlignment(Pos.TOP_CENTER);
	}

	private ImageView init() {
		FileType key = FileType.FILE;
		try {
			key = FileType.valueOf(getExtention().toUpperCase());
		} catch (Exception e) {
		}

		switch (key) {
		case JAR:
			return APILoader.getIconFile(".jar", REC_IMG.getWidth(), REC_IMG.getHeight());
		case CLASS:
			return APILoader.getIconFile(".class", REC_IMG.getWidth(), REC_IMG.getHeight());
		case CPP:
			return APILoader.getIconFile(".cpp", REC_IMG.getWidth(), REC_IMG.getHeight());
		case H:
			return APILoader.getIconFile(".h", REC_IMG.getWidth(), REC_IMG.getHeight());
		case ZIP:
		case RAR:
			return APILoader.getIconFile(".zip", REC_IMG.getWidth(), REC_IMG.getHeight());
		case JPG:
		case PNG:
		case JPGE:
			return APILoader.getIconFile(".img", REC_IMG.getWidth(), REC_IMG.getHeight());
		case ICO:
			return APILoader.getIconFile(".img", REC_IMG.getWidth(), REC_IMG.getHeight());
		case TXT:
			return APILoader.getIconFile(".txt", REC_IMG.getWidth(), REC_IMG.getHeight());
		case DLL:
			return APILoader.getIconFile(".dll", REC_IMG.getWidth(), REC_IMG.getHeight());
		default:
			return APILoader.getIconFile(".file", REC_IMG.getWidth(), REC_IMG.getHeight());
		}
	}
	
	public void setText(String content)
	{
		text.setText(content);
	}

	public String getExtention() {
		String name = file.getName();
		int index = name.lastIndexOf('.');
		if (file.isFile() && index != -1)
			return name.substring(index + 1, name.length());
		return "";
	}

	public String cut(String name) {
		return name.length() > 70 ? name.substring(0, 70) + "..." : name;
	}

	public APIFTPFile getFTPFile() {
		return file;
	}

	public void setFTPFile(APIFTPFile file) {
		this.file = file;
	}

}
