package server.gui.item;

import java.io.File;
import java.io.Serializable;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.shape.Rectangle;
import server.api.APILoader;
import server.platform.Platform;

public class MFFileItem extends Button implements Serializable, Platform {
	public static final String STYLE_CSS = "-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px;";
	public static final Rectangle REC_IMG = new Rectangle(65, 65);
	public static final Rectangle REC_ITEM = new Rectangle(70,90);
	private static final long serialVersionUID = 1L;
	private File file;

	public MFFileItem(File item) {
		super();
		this.file = item;
		setStyle(STYLE_CSS);
		setText(cut(item.getName()));
		setPrefSize(REC_ITEM.getWidth(), REC_ITEM.getHeight());
		setContentDisplay(ContentDisplay.TOP);

		if (item.isFile())
			init();
		else
		{
			setGraphic(APILoader.getIconFolder());
		}
	}

	private void init() {
		FileType key = FileType.FILE;
		try {
			 key = FileType.valueOf(getExtention().toUpperCase());
		} catch (Exception e) {}
	
		switch (key) {
		case JAR:
			setGraphic(APILoader.getIconFile(".jar",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case CLASS:
			setGraphic(APILoader.getIconFile(".class",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case CPP:
			setGraphic(APILoader.getIconFile(".cpp",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case H:
			setGraphic(APILoader.getIconFile(".h",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case ZIP:
		case RAR:
			setGraphic(APILoader.getIconFile(".zip",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case JPG:
		case PNG:
		case JPGE:
			setGraphic(APILoader.getIconFile(".img",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case ICO:
			setGraphic(APILoader.getIconFile(".img",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case TXT:
			setGraphic(APILoader.getIconFile(".txt",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		case DLL:
			setGraphic(APILoader.getIconFile(".dll",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		default:
			setGraphic(APILoader.getIconFile(".file",REC_IMG.getWidth(), REC_IMG.getHeight()));
			break;
		}
	}
	
	
	
	@Override
	public String toString() {
		return "MFFileItem [file=" + file + "]";
	}

	public String getExtention()
	{
		String name = file.getName();
		int index = name.lastIndexOf('.');
		if(file.isFile() && index != -1)
			return name.substring(index+1, name.length());
		return "";
	}

	public String cut(String name) {
		return name.length() > 25 ? name.substring(0, 25) + "..." : name;
	}
	
	public static String cutPath(String name) {
		return name.length() > 60 ? name.substring(name.length()-60, name.length()) + "..." : name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}



}
