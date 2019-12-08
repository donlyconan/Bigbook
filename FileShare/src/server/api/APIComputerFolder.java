package server.api;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.gui.item.CBFolder;
import server.gui.item.MFFileItem;
import server.platform.Platform;

public class APIComputerFolder implements Platform, EventHandler<MouseEvent> {
	private static Text text;
	private File curParent;
	private Node curItem;
	private ObservableList<Node> folder;
	private ObservableList<Node> data;
	private List<APILocalDisk> root;

	public APIComputerFolder(ObservableList<Node> box, ObservableList<Node> flow) {
		super();
		this.folder = box;
		this.data = flow;
		curParent = null;
		init();
	}

	private void init() {
		root = FXCollections.observableArrayList();
		File[] files = File.listRoots();
		for (File item : files) {
			APILocalDisk fr = new APILocalDisk(item);
			fr.setOnEvent(event);
			root.add(fr);
		}
		data.addAll(root);
		text = (Text) Data.get(Type.IMCTextPath);
	}

	private EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent e) {
			Node node = (Node) e.getSource();
			if (node instanceof ProgressBar) {
				ProgressBar pro = (ProgressBar) node;
				APILocalDisk root = (APILocalDisk) pro.getParent();
				curParent = root.getCurrentFile();
				if (!folder.contains(root.getHeader())) {
					folder.add(root.getHeader());
					text.setText("Current Item: " + root.getHeader().getText());
				}
			} else {
				Text header = (Text) node;
				remove(header);
				curParent = new File(header.getText());
				text.setText("Current Item: " + header.getText());
			}

			curItem = new MFFileItem(curParent);
			if (e.getButton() == MouseButton.PRIMARY) {
				make();
			}
		}
	};

	public void remove(Node node) {
		int index = folder.indexOf(node);
		if (index == 0) {
			curParent = null;
			folder.remove(1, folder.size());
		} else if (index != -1 && index < folder.size()) {
			folder.remove(index + 1, folder.size());
		}
	}

	public void make() {
		if (curParent != null) {
			File[] files = curParent.listFiles();
			data.clear();
			if (files != null) {
				for (File item : files) {
					MFFileItem file = new MFFileItem(item);
					file.setOnMouseClicked(this);
					data.add(file);
				}
			}
		} else {
			data.clear();
			data.addAll(root);
		}
	}

	public void removeItem(MFFileItem item) {
		File file = item.getFile();
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else {
				removeFolder(file);
			}

		}
		if (!file.exists())
			data.remove(item);
	}

	public void new_folder(String name) {
		MFFileItem item = (MFFileItem) curItem;
		File file = new File(item.getFile().getAbsolutePath() + "\\" + name);
		if (!file.exists()) {

			if (file.mkdirs()) {
				MFFileItem mfitem = new MFFileItem(file);
				mfitem.setOnMouseClicked(this);
				data.add(mfitem);
			}
		}
	}

	public void renameItem(MFFileItem item, String new_name) throws Exception {
		File file = item.getFile();
		if (file.exists()) {
			File new_file = new File(file.getParent(), new_name);
			System.out.println("Rename =" + file.getParent());
			System.out.println("Rename =" + new_file.getAbsolutePath());
			if (!new_file.exists() && file.renameTo(new_file)) {
				item.setText(new_name);
			} else
				throw new Exception("File is exists!");
		} else
			throw new Exception("File not found!");
	}

	public void move(String form, String to) {
//		ftpClient.move
	}

	public void addFileIteam(List<File> files) {
		for (File item : files) {
			if (item != null) {
				javafx.application.Platform.runLater(() -> {
					MFFileItem fitem = new MFFileItem(item);
					fitem.setOnMouseClicked(this);
					data.add(fitem);
				});
			}
		}
	}

	@Override
	public void handle(MouseEvent event) {
		Node node = (Node) event.getSource();
		System.out.println(node);
		if (node instanceof MFFileItem) {
			curItem = node;
			MFFileItem item = (MFFileItem) curItem;
			text.setText("Item current: \\" + MFFileItem.cutPath(item.getFile().getAbsolutePath()) + "\t"
					+ (item.getFile().isFile() ? item.getFile().length() + " bytes" : ""));

			if (!item.getFile().isFile() && event.getButton() == MouseButton.PRIMARY) {
				curParent = item.getFile();
				Text temp = new CBFolder(item.getFile().getName(), item.getFile().getAbsolutePath());
				temp.setOnMouseClicked(this);
				folder.add(temp);
				make();
			}
		} else if (node instanceof CBFolder) {
			if (event.getButton() == MouseButton.PRIMARY) {
				remove(node);
				CBFolder fol = (CBFolder) node;
				text.setText("Item current: \\" + fol.getFile().getAbsolutePath());
				curParent = fol.getFile();
				data.clear();
				data.addAll(fol.fillList(this));
			}
		} else {
			remove(node);
			text.setText("");
			data.clear();
			data.addAll(root);
		}
	}
//	public boolean rename(FTPFileItem item, String newname) throws IOException
//	{
////		boolean res = ftpClient.rename(getCurrentPathFile() + "\\" + item.getFile().getName(), newname);
////		if(res)
////		{
////			item.setText(newname);
////		}
////		return res;
//	}

//	public boolean removeFolder(String dir, String name) throws IOException {
//		List<String> lis = new ArrayList<String>();
//		removeAllFile(ftpClient.listFiles(dir + name), dir + name, lis);
//		System.out.println(dir + name);
//		for (int i = lis.size() - 1; i >= 0; i--) {
//			ftpClient.removeDirectory(lis.get(i));
//		}
//		return ftpClient.removeDirectory(dir + name);
//
//	}

	public void removeFolder(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File item : files) {
				if (item.isFile()) {
					item.delete();
				} else {
					removeFolder(item);
				}
			}
		}
		file.delete();
	}

//	public String getCurrentPathFile() {
//		String curPath = "\\";
//		for (int i = 2; i < folder.size(); i++) {
//			Node node = folder.get(i);
//			if (node instanceof Button)
//				curPath += ((Button) node).getText() + "\\";
//		}
//		return curPath;
//	}

	public Node getCurItem() {
		return curItem;
	}

	public File getCurParent() {
		return curParent;
	}

	public void setCurParent(File curParent) {
		this.curParent = curParent;
	}

	public List<APILocalDisk> getRoot() {
		return root;
	}

	public void setRoot(List<APILocalDisk> root) {
		this.root = root;
	}

	public void setCurItem(Node curItem) {
		this.curItem = curItem;
	}

	public ObservableList<Node> getFolder() {
		return folder;
	}

	public void setFolder(ObservableList<Node> folder) {
		this.folder = folder;
	}

	public ObservableList<Node> getData() {
		return data;
	}

	public void setData(ObservableList<Node> data) {
		this.data = data;
	}

}
