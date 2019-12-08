package server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.gui.item.FTPFileItem;
import server.platform.Platform;

public class APIFolder implements Platform, EventHandler<MouseEvent> {
	private static Text text = (Text) Data.get(Type.ITextPath);
	private FTPClient ftpClient;
	private Node curParent;
	private Node curItem;
	private ObservableList<Node> folder;
	private ObservableList<Node> data;

	public APIFolder(FTPClient ftp, ObservableList<Node> box, ObservableList<Node> flow) {
		super();
		this.ftpClient = ftp;
		this.folder = box;
		this.data = flow;
	}
	
	public void remove(Node node) {
		int index = folder.indexOf(node);
		if (index == 1)
			folder.remove(1, folder.size());
		else if (index < folder.size())
			folder.remove(index + 2, folder.size());
	}

	public void make() throws IOException {
		FTPFile[] ftpFiles = ftpClient.listFiles(getCurrentPathFile());
		data.clear();
		for (FTPFile item : ftpFiles) {
			FTPFileItem fitem = new FTPFileItem(item);
			fitem.setOnMouseClicked(this);
			data.add(fitem);
		}
	}

	public boolean removeItem(Node item) throws IOException
	{
		boolean res = false;
		if (item instanceof FTPFileItem) {
			FTPFileItem fitem = (FTPFileItem) item;
			if (fitem.getFile().isFile())
				res = ftpClient.deleteFile(getCurrentPathFile() + "\\" + fitem.getFile().getName());
			else
				res = removeFolder(getCurrentPathFile(), fitem.getFile().getName());
			if(res)
			{
				data.remove(fitem);
			}
		}
		return res;
	}
	
	public void move(String form, String to)
	{
//		ftpClient.move
	}

	public void addFileIteam(List<FTPFile> files) {
		for (FTPFile item : files) {
			if (item != null) {
				javafx.application.Platform.runLater(()->{
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

		if (node instanceof FTPFileItem) {
			curItem = node;
			FTPFileItem item = (FTPFileItem) node;
			text.setText("Item current: \\" + item.getFile().getName() + "\t"
					+ (item.getFile().isFile() ? item.getFile().getSize() + " bytes" : ""));

			if (event.getButton() == MouseButton.PRIMARY) {
				if (!item.getFile().isFile()) {
					Button temp = APILoader.createButton(item.getFile().getName());
					temp.setOnMouseClicked(this);
					folder.addAll(temp, APILoader.createText());
					try {
						make();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			this.remove(node);
			try {
				make();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public boolean rename(FTPFileItem item, String newname) throws IOException
	{
		boolean res = ftpClient.rename(getCurrentPathFile() + "\\" + item.getFile().getName(), newname);
		if(res)
		{
			item.setText(newname);
		}
		return res;
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

	public String getCurrentPathFile() {
		String curPath = "\\";
		for (int i = 2; i < folder.size(); i++) {
			Node node = folder.get(i);
			if (node instanceof Button)
				curPath += ((Button) node).getText() + "\\";
		}
		return curPath;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public Node getCurParent() {
		return curParent;
	}

	public void setCurParent(Node curParent) {
		this.curParent = curParent;
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

}
