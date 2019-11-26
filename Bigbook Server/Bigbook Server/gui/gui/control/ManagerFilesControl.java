package gui.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import load.resource.Plugin;

public class ManagerFilesControl implements Initializable
{
	@FXML TreeView<File> tree;
	@FXML TextField txtRoot;
	static ImageView imgfolder;
	static ImageView imgfile;
	@Override
	public void initialize( URL location, ResourceBundle resources) {
		imgfolder = new ImageView(new Image(Plugin.getInputStreamIcon("folder.png")));
		imgfolder.setFitWidth(25);
		imgfolder.setFitHeight(25);
		imgfile = new ImageView(new Image(Plugin.getInputStreamIcon("file.png")));
		imgfile.setFitWidth(25);
		imgfile.setFitHeight(25);
		
		
	}
	
	public void ButtonEV(ActionEvent e)
	{
		Button temp = (Button) e.getSource();
		if("select".equalsIgnoreCase(temp.getText()))
		{
			try {
				DirectoryChooser open = new DirectoryChooser();
				open.setTitle("Root Folder");
				File file = open.showDialog(new Stage());
				txtRoot.setText(file.getAbsolutePath());
				tree.setRoot(new TreeItem<File>(file,imgfolder));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void ButtonControl(ActionEvent e)
	{
		
	}
	
	public void loadFoder(TreeItem<FileView> root,FileView files)
	{
		if(files == null)
			return;
		for(File file : files.getList())
		{
			if(file.isFile())
			{
				root.getChildren().add(new TreeItem<FileView>(new FileView(file)));
			} else
			{
				FileView fv = new FileView(file);
				TreeItem<FileView> temp = new TreeItem<FileView>(fv);
				root.getChildren().add(temp);
				loadFoder(temp, fv);
			}
		}
	}

}
