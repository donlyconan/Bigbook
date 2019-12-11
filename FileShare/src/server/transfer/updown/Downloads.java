package server.transfer.updown;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javafx.concurrent.Task;
import server.api.APIFTPFile;
import server.api.Print;
import server.api.Print.Content;
import server.platform.Platform;

public class Downloads extends Task<Boolean> implements Platform {
	private FTPClient ftp;
	private Queue<APIFTPFile> queue;
	private String rootFolder;
	private boolean result;

	public Downloads(FTPClient ftp, String root) {
		super();
		this.ftp = ftp;
		this.rootFolder = root;
		queue = new LinkedList<APIFTPFile>();
		try {
			ftp.type(FTPClient.BINARY_FILE_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void download(APIFTPFile ftpFile) {
		queue.add(ftpFile);

		if (!isRunning()) {
			Platform.start(this);
		}
	}

	@Override
	public void run() {
		Print.out(Content.START, "Start downloading...");
		while (!queue.isEmpty()) {
			APIFTPFile curItem = queue.poll();
			try {
				if (curItem.isFile()) {
					recieve(curItem.getDirectory(), curItem.getName());
				} else {
					recieveFolder(curItem, curItem.getName());
				}
			} catch (IOException e) {
				Print.out("Download: Error!  " + e.getMessage());
				e.printStackTrace();
			}
		}
		Print.out(result ? "Download: Finish!" : "Download: Error!");
		Print.out(Content.END, "End Download!");
	}

	@Override
	protected Boolean call() throws Exception {
		return null;
	}

	public void recieve(String dir, String name) throws IOException {
		String local = rootFolder + "\\" + name;
		String path = dir + "\\" + name;
		Print.out("Path: " + Uploads.cutText(path));
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		FileOutputStream fos = new FileOutputStream(local);
		result = ftp.retrieveFile(path, fos);
		fos.close();
	}

	public void recieveFolder(APIFTPFile folder, String subdir) throws IOException {
		Print.out(Content.DOWNLOD_FOLDER, folder);
		File mkd = new File(rootFolder + "\\" +  folder.getName());
		int index = folder.getAbsolutepath().length() - folder.getName().length();
		if(index == -1)
			throw new IOException("File not found!");
		mkd.mkdir();
		List<APIFTPFile> list = new ArrayList<APIFTPFile>();
		toList(folder.listFile(ftp), list, folder.getPathname(), index);
		for(APIFTPFile item : list)
		{
			String pathname = item.getAbsolutepath().substring(index, item.getAbsolutepath().length());
			recieve(item.getDirectory().substring(0, index), pathname);
		}
	}

	public void toList(List<APIFTPFile> listfile, List<APIFTPFile> list, String root, int index) throws IOException {
		for (APIFTPFile item : listfile) {
			if (item.isFile()) {
				list.add(item);
			} else {
				File mkd = new File(rootFolder + "\\" + root + "\\" + item.getName());
				mkd.mkdir();
				toList(item.listFile(ftp), list, root + "\\" + item.getName(), index);
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
