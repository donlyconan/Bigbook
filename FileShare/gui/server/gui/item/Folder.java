package server.gui.item;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.api.APIFTPFile;
import server.platform.Platform;

public class Folder extends Text implements Platform {
	private static final String STYLE = "-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px; -fx-background-color: transparent;";
	private APIFTPFile ftpFile;
	private File file;

	public Folder(String text, String pathname) {
		super(text + "\\");
		autosize();
		setStyle(STYLE);
		this.file = new File(pathname);
	}

	public Folder(String text, APIFTPFile ftpFile) {
		super(text + "\\");
		autosize();
		setStyle(STYLE);
		this.ftpFile = ftpFile;
	}

	public File getFile() {
		return file;
	}

	@SuppressWarnings("null")
	public ObservableList<Node> fillList(EventHandler<MouseEvent> e, FTPClient user) throws IOException
	{
		List<APIFTPFile> listapi = ftpFile.listFile(user);
		ObservableList<Node> list = FXCollections.observableArrayList();
		if (listapi != null || listapi.size() == 0)
		{
			
			for (APIFTPFile item : listapi) {
				FTPFileItem fitem = new FTPFileItem(item);
				fitem.setOnMouseClicked(e);
				list.add(fitem);
			}
		} else
		{
			Label lab = new Label("Folder is empty!");
			lab.setStyle("-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px; -fx-background-color: transparent;");
			list.add(lab);
		}
		return list;
	}

	public ObservableList<Node> fillList(EventHandler<MouseEvent> e) {
		ObservableList<Node> list = FXCollections.observableArrayList();
		File[] files = file.listFiles();
		if (files != null) {

			for (File item : files) {
				MFFileItem mfitem = new MFFileItem(item);
				mfitem.setOnMouseClicked(e);
				list.add(mfitem);
			}
		} else {
			Label lab = new Label("Folder is empty!");
			lab.setStyle(
					"-fx-font-family: 'cambria'; -fx-font-size: 13; -fx-background-radius: 6px; -fx-background-color: transparent;");
			list.add(lab);
		}
		return list;
	}

	public APIFTPFile getFtpFile() {
		return ftpFile;
	}

	public void setFtpFile(APIFTPFile ftpFile) {
		this.ftpFile = ftpFile;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
