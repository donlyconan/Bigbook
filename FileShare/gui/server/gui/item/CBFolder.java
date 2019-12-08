package server.gui.item;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.platform.Platform;

public class CBFolder extends Text implements Platform {

	private String pathname;

	public CBFolder(String text, String pathname) {
		super(text + "\\");
		setStyle("-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px; -fx-background-color: transparent;");
		autosize();
		this.pathname = pathname;
	}

	public File getFile() {
		return new File(pathname);
	}

	public ObservableList<Node> fillList(EventHandler<MouseEvent> e) {
		File file = new File(pathname);
		ObservableList<Node> list = FXCollections.observableArrayList();
		File[] files = file.listFiles();
		if (files != null)
		{
			
			for (File item : files) {
				MFFileItem mfitem = new MFFileItem(item);
				mfitem.setOnMouseClicked(e);
				list.add(mfitem);
			}
		} else
		{
			Label lab = new Label("Folder is empty!");
			lab.setStyle("-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px; -fx-background-color: transparent;");
			list.add(lab);
		}
		return list;
	}

	@Override
	public String toString() {
		return "CBFolder [pathname=" + pathname + "]";
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

}
