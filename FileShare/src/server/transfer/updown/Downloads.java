package server.transfer.updown;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.concurrent.Task;
import javafx.scene.text.Text;
import javafx.util.Pair;
import server.platform.Platform;

public class Downloads extends Task<Boolean> implements Platform {
	private static Text text;
	private FTPClient ftp;
	private Queue<Pair<String, FTPFile>> queue;
	private String rootFolder;
	private boolean result;

	public Downloads(FTPClient ftp, String root) {
		super();
		this.ftp = ftp;
		this.rootFolder = root;
		text = (Text) Data.get(Type.ITextPath);
		queue = new LinkedList<Pair<String, FTPFile>>();
	}

	public void download(String dir, FTPFile ftpFile) {
		queue.add(new Pair<String, FTPFile>(dir, ftpFile));
		
		if(!isRunning())
		{
			Platform.start(this);
		}
	}

	@Override
	public void run() {
		while (!queue.isEmpty()) {
			Pair<String, FTPFile> curItem = queue.poll();
			try {
				if (curItem.getValue().isFile()) {
					recieve(curItem.getKey(), curItem.getValue().getName());
				} else {
					recieveFolder(curItem.getKey(), curItem.getValue());
				}
			} catch (IOException e) {
				text.setText("Download: Error!  " + e.getMessage());
				e.printStackTrace();
			}
		}
		text.setText(result ? "Download: Finish!" : "Download: Error!");
	}

	@Override
	protected Boolean call() throws Exception {
		return null;
	}

	public void recieve(String dir, String name) throws IOException {
		String local = rootFolder + "\\" + name;
		String path = dir + name;
		text.setText(Uploads.cutText(local));
		FileOutputStream fos = new FileOutputStream(local);
		result = ftp.retrieveFile(path, fos);
		fos.close();
	}

	public void recieveFolder(String dir, FTPFile file) throws IOException {
		System.out.println("Dir = " + dir + "  \\" + file.getName());
		List<String> list = new ArrayList<String>();
		File mkd = new File(rootFolder + "\\" + file.getName());
		mkd.mkdir();

		toList(ftp.listFiles(dir + file.getName()), list, dir + file.getName());
		for (String item : list)
			recieve(dir, item);
	}

	public void toList(FTPFile[] listfile, List<String> list, String root) throws IOException {
		for (FTPFile item : listfile) {
			String path = root + "\\" + item.getName();
			System.out.println(rootFolder + " - " + path);
			if (item.isFile()) {
				list.add(path);
			} else {
				File file = new File(rootFolder + "\\" + path);
				file.mkdirs();
				toList(ftp.listFiles(path), list, path);
			}
		}
	}

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public String getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}

}
