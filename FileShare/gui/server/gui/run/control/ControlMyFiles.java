package server.gui.run.control;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import resource.Loader;
import server.api.APIFolder;
import server.api.APILoader;
import server.api.Notification;
import server.api.search.FTPSearch;
import server.gui.item.FTPFileItem;
import server.platform.Platform;
import server.transfer.updown.Downloads;
import server.transfer.updown.Uploads;

public class ControlMyFiles implements Platform, Initializable {
	@FXML
	ListView<Node> lisView;
	@FXML
	Button search;
	@FXML
	TextField txtSearch;
	@FXML
	FlowPane tableview;
	@FXML
	HBox box;
	@FXML
	Button fileRoot;
	@FXML
	Text textPath;
	@FXML
	ProgressBar progress;
	@FXML
	ContextMenu content;
	@FXML
	ScrollPane scrollPane;
	@FXML
	ImageView imgUL;
	@FXML
	ImageView imgDL;
	@FXML
	ImageView imgSearch;

	static Downloads download;
	static Uploads upload;
	static APIFolder folder;
	static FTPClient ftp;
	static FTPSearch ftpsea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ftp = new FTPClient();
		Data.put(Type.ITextPath, textPath);
		folder = new APIFolder(ftp, box.getChildren(), tableview.getChildren());
		ftpsea = new FTPSearch(ftp);
		Init();
		scrollPane.viewportBoundsProperty().addListener((u, v, e) -> {
			tableview.setPrefSize(e.getWidth(), e.getHeight());
		});
		imgUL.setImage(Loader.loadImage("upload.png"));
		imgDL.setImage(Loader.loadImage("download.png"));
		imgSearch.setImage(Loader.loadImage("search.png"));
		fileRoot.setGraphic(APILoader.getIconFile("home folder", 25, 25));
		List<ImageView> img = APILoader.getListImageViews(
				new String[] { "rename", "refresh","search", "folder", "share", "upload", "upload", "download", "delete" }, 20,
				20);

		for (int i = 0; i < img.size(); i++)
			content.getItems().get(i).setGraphic(img.get(i));
		txtSearch.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
				EVSearch(new ActionEvent());

		});
		fileRoot.setOnMouseClicked(folder);
		try {
			folder.make();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void Init() {
		try {
			download = new Downloads(ftp, "E:\\Downloads");
			upload = new Uploads(ftp);
			ftp.connect(InetAddress.getLocalHost(), 21);
			ftp.login("donly", "root");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void reload() {
		try {
			if (ftpsea.isRunning()) {
				ftpsea.getThread().stop();
			}
			folder.make();
			textPath.setText("");
			txtSearch.setText("");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void EVSearch(ActionEvent e) {
		if (txtSearch.getText().length() > 0) {
			folder.getData().clear();
			ftpsea.search(folder.getCurrentPathFile(), txtSearch.getText());

			Platform.start(() -> {
				System.out.println(ftpsea.isRunning());
				while (ftpsea.isRunning()) {
					if(!ftpsea.getResult().isEmpty())
						folder.addFileIteam(ftpsea.toListItem());
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				folder.addFileIteam(ftpsea.toListItem());
				textPath.setText("Resut find: " + folder.getData().size() + "  finish!...");
			});
		} 
		txtSearch.requestFocus();
	}

	public void MenuContext(ActionEvent e) {
		try {
			MenuItem temp = (MenuItem) e.getSource();
			String strKey = temp.getText().toUpperCase().replace(' ', '_');
			strKey = strKey.replace('.', '_');
			FTPFileItem item = (FTPFileItem) folder.getCurItem();
			textPath.setText("Current Item: " + (item == null ?"no item seleted!" :item.getFile().getName()));
			Event key = Event.valueOf(strKey);

			switch (key) {
			case UPLOAD_FILE:
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Upload");
				List<File> files = chooser.showOpenMultipleDialog(box.getScene().getWindow());
				
				if (files != null) {
					String path = folder.getCurrentPathFile();

					for (File cur : files)
						upload.uploads(cur, path);
				}
				break;
			case UPLOAD_FOLDER:
				DirectoryChooser FCchooser = new DirectoryChooser();
				FCchooser.setTitle("Upload");
				File file = FCchooser.showDialog(box.getScene().getWindow());
				
				if (file != null && file.exists()) {
					String path = folder.getCurrentPathFile();
					upload.uploads(file, path);
				}
				break;

			case DOWNLOAD:
				if (item.getFile() != null) {
					download.download(folder.getCurrentPathFile(), item.getFile());
				}
				break;
			case DELETE:
				if (item == null)
					break;
				if (Notification.showYESNO("Bạn có muốn xóa file: " + item.getFile().getName() + "?"))
					folder.removeItem(folder.getCurItem());
				break;
			case SEARCH:
				EVSearch(e);
				break;
			case REFRESH:
				folder.make();
				break;
			case NEW_FOLDER:
				String fol = Notification.show("Folder name", "");
				if (fol != null) {
					ftp.mkd(folder.getCurrentPathFile() + "\\" + fol);
					reload();
				}
				break;
			case RENAME___:
				if (item == null)
					throw new Exception("Lựa chọn không khả dụng");
				else {
					String name = item.getFile().getName();
					String filenname = Notification.show("Folder name", name);
					if (filenname != null)
						folder.rename(item, filenname);
				}
				break;
			case MOVE_FILE:

				break;
			case SYNC:

				break;
			default:
				break;
			}
		} catch (Exception ex) {
			Notification.show(814777, ex.getMessage());
			ex.printStackTrace();
		}

	}

}
