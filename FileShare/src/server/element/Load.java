package server.element;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import load.LoadResource;

public class Load implements Serializable {
	private static final long serialVersionUID = 1L;
	private File root;

	public Load(File root) {
		super();
		this.root = root;
	}

	public static void FillData(TreeItem<FileItem> fileRoot) {
		try {
			if (fileRoot != null && fileRoot.getChildren().size() == 0) {
				addItems(fileRoot);
				for (TreeItem<FileItem> item : fileRoot.getChildren()) {
					addItems(item);
				}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public static void addItems(TreeItem<FileItem> tree) {
		try {
			File[] files = tree.getValue().getItem().listFiles();
			for (File file : files) {

				if (file.isFile()) {
					TreeItem<FileItem> item = new TreeItem<FileItem>(new FileItem(file), getIconFile());
					tree.getChildren().add(item);
				} else {
					TreeItem<FileItem> item = new TreeItem<FileItem>(new FileItem(file), getIconFolder());
					tree.getChildren().add(item);
				}

			}
		} catch (Exception e) {
		}
	}

	public static List<TreeItem<FileItem>> getItem(File[] files) {
		List<TreeItem<FileItem>> lis = new ArrayList<TreeItem<FileItem>>();
		for (File item : files) {
			if (item.isFile()) {
				TreeItem<FileItem> itf = new TreeItem<FileItem>(new FileItem(item), getIconFile());
				lis.add(itf);
			} else {
				TreeItem<FileItem> itf = new TreeItem<FileItem>(new FileItem(item), getIconFolder());
				lis.add(itf);
			}
		}
		return lis;
	}

	public static File getPath(TreeItem<String> item) {
		String path = "";
		if (item != null) {
			path = item.getValue();
			TreeItem<String> parent = item.getParent();
			while (parent != null) {
				path = parent.getValue() + "\\" + path;
				parent = parent.getParent();
			}
		}
		return new File(path);
	}

	public static String getName(File file) {
		String[] names = file.getAbsolutePath().split("\\\\");
		String name = names[names.length - 1];
		return name;
	}

	public static void FillData(TreeItem<FileItem> root, List<FileItem> files) {
		if (files == null)
			return;
		for (FileItem file : files) {
			if (file.getItem().isFile()) {
				TreeItem<FileItem> item = new TreeItem<FileItem>(file, getIconFile());
				root.getChildren().add(item);
			} else {
				TreeItem<FileItem> item = new TreeItem<FileItem>(file, getIconFolder());
				root.getChildren().add(item);
			}
		}
	}


	public static ImageView getIconFile() {
		ImageView image = new ImageView(LoadResource.loadImage("icon\\file.png"));
		image.setFitHeight(18);
		image.setFitWidth(14);
		return image;
	}
	
	public static ImageView getIconFile(String pathname, int w, int h) {
		ImageView image = new ImageView(LoadResource.loadImage("icon\\" + pathname));
		image.setFitHeight(w);
		image.setFitWidth(h);
		return image;
	}

	public static ImageView getIconFolder() {
		ImageView image = new ImageView(LoadResource.loadImage("icon\\folder.png"));
		image.setFitHeight(16);
		image.setFitWidth(16);
		return image;
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

}
