package gui;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import Element.FileView;
import Element.ItemView;
import Element.Load;
import Element.Notification;
import Parent.Platform;
import data.FileInfo;
import data.SystemRunning;
import data.Transfer;
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
import javafx.scene.image.ImageView;
import load.LoadResource;

public class Controller implements Initializable, Platform
{
	@FXML
	TreeView<FileView> myTree;
	@FXML
	TreeView<FileView> yourTree;
	@FXML
	TextArea txtMS;
	@FXML
	TextField txtName;
	@FXML
	TextField myIP;
	@FXML
	TextField yourIP;
	@FXML
	Label lbFile;
	@FXML
	ListView<Node> lisView;
	@FXML
	ImageView image;
	@FXML
	ProgressIndicator progress;

	ImageView icFolder;
	ImageView icFile;
	private SystemRunning run;

	@Override
	public void initialize( URL location, ResourceBundle resources)
	{
		LoadResource.ShareData().put("my", myIP);
		LoadResource.ShareData().put("your", yourIP);
		LoadResource.ShareData().put("name", txtName);
		LoadResource.ShareData().put("list", lisView.getItems());
		LoadResource.ShareData().put("status", image);
		LoadResource.ShareData().put("tree", yourTree);
		LoadResource.ShareData().put("image", image);
		LoadResource.ShareData().put("progress", progress);
		LoadResource.ShareData().put("mytree", myTree);

		progress.setProgress(0.0);
		for (Object item : LoadResource.ShareData().values())
			System.out.println(item);

		icFolder = new ImageView(LoadResource.loadImage("icon\\folder.png"));
		ImageView yourFolder = new ImageView(LoadResource.loadImage("icon\\folder.png"));
		icFile = new ImageView(LoadResource.loadImage("icon\\file.png"));
		icFolder.setFitHeight(20);
		icFolder.setFitWidth(20);
		yourFolder.setFitHeight(20);
		yourFolder.setFitWidth(20);
		icFolder.setFitHeight(20);
		icFolder.setFitWidth(20);
		icFile.setFitHeight(20);
		icFile.setFitWidth(20);

		TreeItem<FileView> root = new TreeItem<FileView>(new FileView("\\This PC"), icFolder);
		TreeItem<FileView> yourroot = new TreeItem<FileView>(new FileView("\\This PC"), yourFolder);
		myTree.setRoot(root);
		yourTree.setRoot(yourroot);
		myTree.getRoot().getChildren().addAll(Load.getItems(File.listRoots()));
		myTree.getRoot().getChildren().forEach(e -> {
			File[] files = e.getValue().getFile().listFiles();
			if (files != null && e.getChildren().size() == 0)
				e.getChildren().addAll(Load.getItems(files));
		});

		myTree.setOnKeyPressed(v -> {
			File file = myTree.getSelectionModel().getSelectedItem().getValue().getFile();
			try
			{
				if (Notification.showYESNO("Bạn có muốn xóa file: \n" + file.getAbsolutePath()))
				{
					file.delete();
					myTree.getSelectionModel().getSelectedItem().getParent().getChildren().remove(myTree.getSelectionModel().getSelectedItem());
					myTree.refresh();
				}
			} finally
			{

			}
		});

		yourTree.setOnMouseClicked(e -> {
			Transfer tran = new Transfer(CMD.MSxRequestxItem);
			TreeItem<FileView> get = yourTree.getSelectionModel().getSelectedItem();
			File file = get.getValue().getFile();
			lbFile.setText(file.getAbsolutePath() + " " + file.length() / 1024 + " KB");
			try
			{
				if(file.isFile())
					return;
				if (run == null || run.getDs().isClosed())
					throw new IOException("Chưa mở kết nối hoặc gặp một số vấn đề.");
				String name = yourTree.getSelectionModel().getSelectedItem().getValue().getFile().getName();
				if (get.getChildren().size() > 0)
					return;
				if ("This PC".equalsIgnoreCase(name))
				{
					tran = new Transfer(CMD.MSxOpenLoad);
					run.getSend().send(tran.toByteArray());
				} else
					run.getSend().send(tran.toByteArray());
			} catch (IOException ex)
			{
				Notification.show(7777, ex.getMessage());
				ex.printStackTrace();
			}
		});

		try
		{
			myIP.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		}
		myTree.setOnMouseClicked(e -> {
			TreeItem<FileView> get = myTree.getSelectionModel().getSelectedItem();
			try
			{
				File file = get.getValue().getFile();
				lbFile.setText(file.getAbsolutePath() + " " + file.length() / 1024 + " KB");
			} catch (Exception e2)
			{}
			Load.FillData(myTree.getSelectionModel().getSelectedItem());
		});
		image.setImage(LoadResource.loadImage("icon\\red.png"));
	}

	public void EV_Button( ActionEvent e)
	{
		try
		{
			Button temp = (Button) e.getSource();
			if ("close".equalsIgnoreCase(temp.getText()))
			{
				if (Notification.showYESNO("Bạn có muốn thoát không?"))
					System.exit(0);
			} else if ("connect".equalsIgnoreCase(temp.getText()))
				Connect();
			else if (run == null || run.getDs().isClosed())
				throw new Exception("Connection is closing!");

			switch (temp.getText().toLowerCase())
				{
					case "message":
						String text = txtMS.getText();
						txtMS.setText("");
						ItemView item = new ItemView(txtName.getText(), text, true);
						lisView.getItems().add(item.getContent());
						Transfer tran = new Transfer(txtName.getText(), CMD.MSxMessage, text.getBytes("UTF-8"));
						run.getSend().send(tran.toByteArray());
						break;
					case "send":
						progress.setProgress(0);
						File file = myTree.getSelectionModel().getSelectedItem().getValue().getFile();
						if (file.isFile())
						{
							Platform.start(( ) -> {
								try
								{
									run.getSend().send(file);
								} catch (Exception e1)
								{
									e1.printStackTrace();
								}
							});
						}
						myTree.refresh();
						break;
					case "give":
						File filey = yourTree.getSelectionModel().getSelectedItem().getValue().getFile();
						File filem = myTree.getSelectionModel().getSelectedItem().getValue().getFile();
						if(filey.isFile() && !filem.isFile())
						{
							FileInfo info = new FileInfo(filem);
							Transfer data = new Transfer(filey.getAbsolutePath(),CMD.MSxOpenGiveFile, Transfer.OBJtoByte(info));
							run.getSend().send(data.toByteArray());
						}
						break;
				}
		} catch (Exception ex)
		{
			Element.Notification.show(7777, ex.getMessage());
			ex.printStackTrace();
		}

	}

	private void Connect( ) throws IOException
	{
		if(run != null) run.close();
		run = new SystemRunning(InetAddress.getByName(yourIP.getText()));
		run.restart();
		String connect = myIP.getText();
		Transfer tran = new Transfer(CMD.MSxSendxConnect, connect.getBytes("UTF-8"));
		run.getSend().send(Transfer.OBJtoByte(tran));
		tran = new Transfer(txtName.getText(), CMD.MSxMessage, "Đã kết nối!".getBytes("UTF-8"));
		run.getSend().send(tran.toByteArray());
	}
}
