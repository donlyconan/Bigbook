package server.gui.run.control;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import resource.Loader;
import server.api.APIComputerFolder;
import server.api.APILoader;
import server.api.Notification;
import server.api.search.MFSearch;
import server.gui.item.MFFileItem;
import server.platform.Platform;

public class ControlMyComputer implements Platform, Initializable {
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
	ContextMenu content;
	@FXML
	ScrollPane scrollPane;
	@FXML
	ImageView imgUL;
	@FXML
	ImageView imgDL;
	@FXML
	ImageView imgSearch;

//	static Downloads download;
//	static Uploads upload;
//	static APIFolder folder;
//	static FTPClient ftp;
	static MFSearch mfsea;
	static APIComputerFolder folder;
	private String fol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Data.put(Type.IMCTextPath, textPath);
		mfsea = new MFSearch();
		System.out.println(textPath);
		scrollPane.viewportBoundsProperty().addListener((u, v, e) -> {
			tableview.setPrefSize(e.getWidth(), e.getHeight());
		});
		folder = new APIComputerFolder(box.getChildren(), tableview.getChildren());
		imgUL.setImage(Loader.loadImage("upload.png"));
		imgDL.setImage(Loader.loadImage("download.png"));
		imgSearch.setImage(Loader.loadImage("search.png"));
		fileRoot.setGraphic(APILoader.getIconFile("home folder", 25, 25));
		List<ImageView> img = APILoader
				.getListImageViews(new String[] { "rename", "refresh", "search", "folder", "copy", "delete" }, 20, 20);

		for (int i = 0; i < img.size(); i++)
			content.getItems().get(i).setGraphic(img.get(i));
		txtSearch.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
				EVSearch(new ActionEvent());

		});
		fileRoot.setOnMouseClicked(folder);
		folder.make();

	}

	public void EVSearch(ActionEvent e) {
		mfsea.stop();

		if (txtSearch.getText().length() > 0) {
			folder.getData().clear();
			try {
				mfsea.search(folder.getCurParent().getAbsolutePath(), txtSearch.getText());

				Platform.start(() -> {
					System.out.println(mfsea.isRunning());
					while (mfsea.isRunning()) {
						if (!mfsea.getResult().isEmpty())
						{
							folder.addFileIteam(mfsea.toListItem());
							textPath.setText("Resut find: " + folder.getData().size() + "  finish!...");
						}
						
						try {
							Thread.sleep(300);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					folder.addFileIteam(mfsea.toListItem());
					textPath.setText("Resut find: " + folder.getData().size() + "  finish!...");
				});
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		txtSearch.requestFocus();
	}

	public void MenuContext(ActionEvent e) {
		try {
			MenuItem temp = (MenuItem) e.getSource();
			String strKey = temp.getText().toUpperCase().replace(' ', '_');
			strKey = strKey.replace('.', '_');
			MFFileItem item = (MFFileItem) folder.getCurItem();
			textPath.setText("Current Item: " + (item == null ? "no item seleted!" : item.getFile().getName()));
			Event key = Event.valueOf(strKey);

			switch (key) {

			case COPY_PATH:
				if (folder.getCurParent() != null) {
					File curfile = ((MFFileItem) folder.getCurItem()).getFile();
					Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
					clip.setContents(new StringSelection(curfile.getAbsolutePath()), null);
				}
				break;
			case DELETE:
				if (item == null)
					break;
				if (Notification.showYESNO("Bạn có muốn xóa file: " + item.getFile().getName() + "?"))
					folder.removeItem(item);
				break;
			case SEARCH:
				EVSearch(e);
				break;
			case REFRESH:
				folder.make();
				break;
			case NEW_FOLDER:
				fol = Notification.show("Create new folder", "Folder name", "");
				if (fol != null) {
					folder.new_folder(fol);
				}
				break;
			case RENAME___:
				if (item == null)
					throw new Exception("Lựa chọn không khả dụng");
				String fol = Notification.show("Rename file:\\...\\" + item.getFile().getName(), "Rename to",
						item.getFile().getName());
				if (fol != null) {
					folder.renameItem(item, fol);
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
