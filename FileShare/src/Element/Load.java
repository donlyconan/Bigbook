package Element;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import load.LoadResource;

public class Load implements Serializable
{
	private static final long serialVersionUID = 1L;
	private File root;
	
	public Load( File root )
	{
		super();
		this.root = root;
	}

	public static void FillData(TreeItem<FileView> fileRoot)
	{
		try
		{
			if (fileRoot != null && fileRoot.getChildren().size() == 0)
			{
				FileView fv = fileRoot.getValue();
				fileRoot.getChildren().addAll(getItems(fv.getFile().listFiles()));
				for (TreeItem<FileView> item : fileRoot.getChildren())
				{
					File[] files = item.getValue().getFile().listFiles();
					item.getChildren().addAll(getItems(files));
				}
			}

		} catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}
	

	public static List<TreeItem<FileView>> getItems(File[] files) 
	{
		List<TreeItem<FileView>> lis = new ArrayList<TreeItem<FileView>>();
		if (files != null)
		{
			for (File file : files)
			{
				if (file.isFile())
				{
					TreeItem<FileView> item = new TreeItem<FileView>(new FileView(file), getIconFile());
					lis.add(item);
				} else
				{
					TreeItem<FileView> item = new TreeItem<FileView>(new FileView(file), getIconFolder());
					lis.add(item);
				}
			}
		}
		return lis;
	}
	
	public static List<FileView> getItemFileView(File[] files) 
	{
		List<FileView> lis = new ArrayList<FileView>();
		if(files != null)
		{
			for(File file : files)
				lis.add(new FileView(file));
		}
		return lis;
	}
	
	public static void FillData(TreeItem<FileView> root,List<FileView> files)
	{
		if(files == null) return;
		for(FileView file : files)
		{
			if(file.getFile().isFile())
			{
				TreeItem<FileView> item = new TreeItem<FileView>(file,getIconFile());
				root.getChildren().add(item);
			}
			else
			{
				TreeItem<FileView> item = new TreeItem<FileView>(file,getIconFolder());
				root.getChildren().add(item);
			}
		}
	}
	
	public static ImageView getIconFile()
	{
		ImageView image = new ImageView(LoadResource.loadImage("icon\\file.png"));
		image.setFitHeight(18);
		image.setFitWidth(14);
		return image;
	}
	
	public static ImageView getIconFolder()
	{
		ImageView image = new ImageView(LoadResource.loadImage("icon\\folder.png"));
		image.setFitHeight(16);
		image.setFitWidth(16);
		return image;
	}
	public File getRoot( )
	{ return root; }

	public void setRoot( File root)
	{ this.root = root; }
	
	
	
}
