package server.gui.run.control;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

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
import javafx.stage.Stage;
import resource.Loader;
import server.api.APIFTPFile;
import server.api.APIFTPFolder;
import server.api.APILoader;
import server.api.Notification;
import server.api.Print;
import server.api.search.FTPSearch;
import server.api.search.MFSearch.Status;
import server.gui.item.FTPFileItem;
import server.platform.Platform;
import server.transfer.updown.Downloads;
import server.transfer.updown.Uploads;

public class ControlCloud implements Platform, Initializable {
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
	@FXML
	Text textHome;

	static Downloads download;
	static Uploads upload;
	static APIFTPFolder folder;
	static FTPClient ftp;
	static FTPSearch ftpsea;
	static ExecutorService threadPool;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ftp = new FTPClient();
		ftp.enterLocalActiveMode();

		folder = new APIFTPFolder(ftp, box.getChildren(), tableview.getChildren());
		ftpsea = new FTPSearch(ftp);
		Init();

		scrollPane.viewportBoundsProperty().addListener((u, v, e) -> {
			tableview.setPrefSize(e.getWidth(), e.getHeight());
		});
		imgSearch.setImage(Loader.loadImage("search.png"));

		fileRoot.setGraphic(APILoader.getIconFile("home folder", 25, 25));
		List<ImageView> img = APILoader.getListImageViews(new String[] { "rename", "refresh", "search", "copy",
				"folder", "share", "upload", "upload", "download", "delete" }, 20, 20);

		for (int i = 0; i < img.size(); i++)
			content.getItems().get(i).setGraphic(img.get(i));
		txtSearch.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
				EVSearch(new ActionEvent());

		});

		fileRoot.setText("My Cloud");
		fileRoot.setOnMouseClicked(folder);

		textHome.setOnMouseClicked(e -> {
			if (!folder.getCurParent().equals(APIFTPFile.getRoot())) {
				folder.setCurParent(APIFTPFile.getRoot());
				folder.getFolder().remove(2, folder.getFolder().size());
				folder.make();
			}
		});

		scrollPane.vvalueProperty().addListener((e, u, v) -> {
			if (ftpsea.getStatus() == Status.WATTING && v.doubleValue() >= 1.0) {
				synchronized (ftpsea.getThread()) {
					ftpsea.getThread().notifyAll();
					ftpsea.setStatus(Status.RUNNING);
				}
			}
		});

		threadPool = Executors.newFixedThreadPool(3);
	}

	private void Init() {
		try {
			ftp.setConnectTimeout(30);
			ftp.connect(InetAddress.getByName("192.168.1.150"), 21);
			boolean res = ftp.login("donly", "root");
			if(!res)
				Print.log(Level.WARNING, "Can't connected to server!");
			
			download = new Downloads(ftp);
			upload = new Uploads(ftp);
			folder.make();
		} catch (IOException e) {
			Print.log(Level.WARNING, e.getMessage());
		}
	}

	private void reload() {
		Print.out("Loading...");
		folder.make();
		ftpsea.stop();
		txtSearch.setText("");
	}

	public void EVSearch(ActionEvent e) {
		if (txtSearch.getText().length() > 0) {
			folder.getData().clear();
			ftpsea.search(folder.getCurParent(), txtSearch.getText());

			threadPool.execute(() -> {
				System.out.println(ftpsea.getStatus());
				while (ftpsea.getStatus() != Status.FINISH) {
					if (!ftpsea.getResult().isEmpty())
						folder.addFileItem(ftpsea.toListItem());
					Print.out("Result find: " + ftpsea.getIndex() + "  loading...!");
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				folder.addFileItem(ftpsea.toListItem());
				Print.out("Finish! Resut find: " + folder.getData().size());
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
			Print.out("Current Item: " + (item == null ? "no item seleted!" : item.getFTPFile().getName()));
			Event key = Event.valueOf(strKey);

			switch (key) {
			case UPLOAD_FILE:
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Upload");
				List<File> files = chooser.showOpenMultipleDialog(box.getScene().getWindow());

				if (files != null) {
					String path = folder.getCurParent().getPathname();

					for (File cur : files)
						upload.uploads(cur, path);

					if (upload.status == Status.FINISH)
						threadPool.execute(() -> upload.handle());
				}
				break;
			case COPY_PATH:
				if (item != null) {
					Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
					clip.setContents(new StringSelection(item.getFTPFile().getAbsolutepath()), null);
				}
				break;
			case UPLOAD_FOLDER:
				DirectoryChooser FCchooser = new DirectoryChooser();
				FCchooser.setTitle("Upload");
				File file = FCchooser.showDialog(box.getScene().getWindow());

				if (file != null && file.exists()) {
					String path = folder.getCurParent().getAbsolutepath();
					upload.uploads(file, path);

					if (upload.status == Status.FINISH)
						threadPool.execute(() -> upload.handle());
				}
				break;

			case DOWNLOAD:
				if (item != null) {
					if (download.checkRoot()) {
						DirectoryChooser dir = new DirectoryChooser();
						dir.setTitle("Save");
						File dirfile = dir.showDialog(new Stage());
						download.setRootFolder(dirfile.getAbsolutePath());
						
						Loader.writeProperties("DirectoryDownloads", dirfile.getAbsolutePath());
					}

					if (item.getFTPFile() != null) {
						Print.out("Dowloading: " + item);

						download.download(item.getFTPFile());

						if (download.status == Status.FINISH)
							threadPool.execute(() -> download.handle());
					}
				}
				break;
			case DELETE:
				if (item != null) {
					if (Notification.showYESNO("Do you want to delete: " + item.getFTPFile().getName() + "?"))
						folder.removeItem(folder.getCurItem());
				}
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
					ftp.mkd(folder.getCurrentPath() + "\\" + fol);
					reload();
				}
				break;
			case RENAME___:
				if (item != null) {
					String name = item.getFTPFile().getName();
					String filenname = Notification.show("Folder name", name);
					if (filenname != null)
						if (item.getFTPFile().rename(ftp, filenname))
							item.setText(filenname);
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
