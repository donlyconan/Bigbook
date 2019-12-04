package server.gui;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import load.LoadResource;
import server.data.ArrayByte;
import server.data.FileInfo;
import server.data.Search;
import server.data.SystemRunning;
import server.element.Command;
import server.element.FileItem;
import server.element.Load;
import server.element.Notification;
import server.parent.Platform;

public class Controller implements Initializable, Platform {
	@FXML
	TreeView<FileItem> myTree;
	@FXML
	TreeView<FileItem> yourTree;
	@FXML
	Label lbFile;
	@FXML
	ListView<Node> lisView;
	@FXML
	Button search;
	@FXML
	TextField txtSearch;
	@FXML
	TextArea txtCMD;
	
	@FXML
	ProgressIndicator progressp;
	private SystemRunning run;
	private boolean status;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoadResource.ShareData().put("tree", yourTree);
		LoadResource.ShareData().put("mytree", myTree);
//		LoadResource.ShareData().put("image", image);
//		LoadResource.ShareData().put("progress", progress);
//
//		progress.setProgress(0.0);
		search.setGraphic(Load.getIconFile("Search.png",30,30));
		for (Object item : LoadResource.ShareData().values())
			System.out.println(item);

		TreeItem<FileItem> root = new TreeItem<FileItem>(new FileItem("\\This PC"), Load.getIconFolder());
		TreeItem<FileItem> yourroot = new TreeItem<FileItem>(new FileItem("\\This PC"), Load.getIconFolder());
		myTree.setRoot(root);
		yourTree.setRoot(yourroot);
		myTree.getRoot().getChildren().addAll(Load.getItem(File.listRoots()));
		myTree.getRoot().getChildren().forEach(e -> {
			Load.addItems(e);
		});

		yourTree.setOnMouseClicked(e -> {
			status = false;
			ArrayByte tran = new ArrayByte(CMD.MSxRequestxItem);
			TreeItem<FileItem> get = yourTree.getSelectionModel().getSelectedItem();
			File file = get.getValue().getItem();
			lbFile.setText(file.getAbsolutePath() + " " + file.length() / 1024 + " KB");
			try {
				if (file.isFile())
					return;
				if (run == null || run.getDs().isClosed())
					throw new IOException("Chưa mở kết nối hoặc gặp một số vấn đề.");
				String name = yourTree.getSelectionModel().getSelectedItem().getValue().getItem().getName();
				if (get.getChildren().size() > 0)
					return;
				if ("This PC".equalsIgnoreCase(name)) {
					tran = new ArrayByte(CMD.MSxOpenLoad);
					run.getSend().send(tran.toByteArray());
				} else
					run.getSend().send(tran.toByteArray());
			} catch (IOException ex) {
				Notification.show(7777, ex.getMessage());
				ex.printStackTrace();
			}
		});

		myTree.setOnMouseClicked(e -> {
			status = true;
			TreeItem<FileItem> get = myTree.getSelectionModel().getSelectedItem();
			try {
				File file = get.getValue().getItem();
				lbFile.setText(file.getAbsolutePath() + " " + file.length() / 1024 + " KB");
			} catch (Exception e2) {
			}
			Platform.start(()->Load.FillData(myTree.getSelectionModel().getSelectedItem()));
		});
//		image.setImage(LoadResource.loadImage("icon\\red.png"));
	}

	public void EV_Control(ActionEvent e) {
		Button temp = (Button) e.getSource();
		String key = temp.getText().toLowerCase();

		switch (key) {
		case "close":
			if ("close".equalsIgnoreCase(temp.getText())) {
				if (Notification.showYESNO("Bạn có muốn thoát không?"))
					System.exit(0);
			}
			break;
		}
	}

	public void EV_Button(ActionEvent e) {
		try {
			Button temp = (Button) e.getSource();
			String key = temp.getText().toLowerCase();

			switch (key) {
			case "send":
				File file = myTree.getSelectionModel().getSelectedItem().getValue().getItem();
				if (file.isFile())
					Platform.start(() -> run.getSend().send(file));
				myTree.refresh();
				break;
			case "give":
				File filey = yourTree.getSelectionModel().getSelectedItem().getValue().getItem();
				File filem = myTree.getSelectionModel().getSelectedItem().getValue().getItem();
				if (filey.isFile() && !filem.isFile()) {
					FileInfo info = new FileInfo(filem);
					ArrayByte data = new ArrayByte(filey.getAbsolutePath(), CMD.MSxOpenGiveFile, info.toByteArray());
					run.getSend().send(data.toByteArray());
				}
				break;
			case "delete":
				if (status) {
					file = myTree.getSelectionModel().getSelectedItem().getValue().getItem();
					if (Notification.showYESNO("Bạn có muốn xóa file: \n" + file.getAbsolutePath())) {
						file.delete();
						myTree.getSelectionModel().getSelectedItem().getParent().getChildren()
								.remove(myTree.getSelectionModel().getSelectedItem());
						myTree.refresh();
					}
				} else {

				}
				break;
			case "search":
				String content = txtSearch.getText();
				if(content.length() > 0)
				{
					FileFilter fff = new FileFilter() {
						
						@Override
						public boolean accept(File pathname) {
							return !pathname.isFile() || pathname.getName().equalsIgnoreCase(content);
						}
					};
					Search search = new Search(fff, File.listRoots()[0]);
					Platform.start(()->search.search());
				}
				break;
			case "command":
				String cmd = txtCMD.getText();
				txtCMD.setText("");
				if(cmd.length() > 0)
				{
					Command.execute(cmd);
				}
				break;
			}
		} catch (Exception ex) {
			Notification.show(7777, ex.getMessage());
			ex.printStackTrace();
		}

	}

	private void Connect() throws IOException {
	
	}
}
