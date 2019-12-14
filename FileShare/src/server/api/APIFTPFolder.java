package server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.gui.item.FTPFileItem;
import server.gui.item.Folder;
import server.platform.Platform;

public class APIFTPFolder implements Platform, EventHandler<MouseEvent> {
	private FTPClient ftpClient;
	private APIFTPFile curParent;
	private Node curItem;
	private volatile ObservableList<Node> folder;
	private volatile ObservableList<Node> data;

	public APIFTPFolder(FTPClient ftp, ObservableList<Node> box, ObservableList<Node> flow) {
		super();
		this.ftpClient = ftp;
		this.folder = box;
		this.data = flow;
		curParent = APIFTPFile.getRoot();
	}

	public void remove(Node node) {
		synchronized (ftpClient) {
			int index = folder.indexOf(node);
			if (index == 1) {
				folder.remove(2, folder.size());
				curParent = APIFTPFile.getRoot();
			} else if (index > -1 && index < folder.size()) {
				folder.remove(index + 1, folder.size());
				curParent = ((Folder) folder.get(index)).getFtpFile();
			}
		}
	}

	public void make() {
		synchronized (ftpClient) {
			try {
				data.clear();
				List<APIFTPFile> files = curParent.listFile(ftpClient);
				System.out.println(curParent);
				for (APIFTPFile item : files) {
					FTPFileItem fitem = new FTPFileItem(item);
					fitem.setOnMouseClicked(this);
					data.add(fitem);
				}

				if (data.isEmpty()) {
					data.add(APILoader.createLableEmpty());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean removeItem(Node item) throws IOException {
		boolean res = false;
		synchronized (ftpClient) {
			if (item instanceof FTPFileItem) {
				FTPFileItem fitem = (FTPFileItem) item;
				if (fitem.getFTPFile().isFile())
					res = fitem.getFTPFile().delete(ftpClient);
				else
					res = fitem.getFTPFile().delete(ftpClient);
				if (res)
					data.remove(fitem);
			}
		}
		return res;
	}

	public void move(String form, String to) {
//		ftpClient.move
	}

	public void addFileItem(List<APIFTPFile> files) {
		for (APIFTPFile item : files) {
			if (item != null) {
				javafx.application.Platform.runLater(() -> {
					FTPFileItem fitem = new FTPFileItem(item);
					fitem.setOnMouseClicked(this);
					data.add(fitem);
				});
			}
		}
	}

	@Override
	public void handle(MouseEvent event) {
		Node node = (Node) event.getSource();
		Print.log(Level.INFO, node);

		if (node instanceof FTPFileItem) {
			curItem = node;
			FTPFileItem item = (FTPFileItem) node;
			Print.out("Item selected: \\" + item.getFTPFile().getName() + "   "
					+ (item.getFTPFile().isFile() ? item.getFTPFile().getSize() + " bytes" : ""));

			if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				if (item.getFTPFile().isFolder()) {
					curParent = item.getFTPFile();
					Text temp = new Folder(item.getFTPFile().getName(), item.getFTPFile());
					temp.setOnMouseClicked(this);
					folder.add(temp);
					make();
				}
			}
		} else if (node instanceof Folder) {
			remove(node);
			make();
		} else {
			folder.remove(2, folder.size());
			curParent = APIFTPFile.getRoot();
			make();
		}
	}

	public boolean rename(APIFTPFile item, String newname) throws IOException {
		return item.rename(ftpClient, newname);
	}

	public boolean removeFolder(String dir, String name) throws IOException {
		List<String> lis = new ArrayList<String>();
		removeAllFile(ftpClient.listFiles(dir + name), dir + name, lis);
		System.out.println(dir + name);
		for (int i = lis.size() - 1; i >= 0; i--) {
			ftpClient.removeDirectory(lis.get(i));
		}
		return ftpClient.removeDirectory(dir + name);

	}

	public void removeAllFile(FTPFile[] files, String dir, List<String> lis) throws IOException {
		for (FTPFile file : files) {
			String pathname = dir + "\\" + file.getName();
			if (file.isFile()) {
				ftpClient.deleteFile(pathname);
			} else {
				lis.add(pathname);
				removeAllFile(ftpClient.listFiles(pathname), pathname, lis);
			}
		}
	}

	public String getCurrentPath() {
		return curParent.getPathname();
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public Node getCurItem() {
		return curItem;
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

	public APIFTPFile getCurParent() {
		return curParent;
	}

	public void setCurParent(APIFTPFile curParent) {
		this.curParent = curParent;
	}

}
