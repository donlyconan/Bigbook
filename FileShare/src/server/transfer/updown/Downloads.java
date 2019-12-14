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

import resource.Loader;
import server.api.APIFTPFile;
import server.api.Print;
import server.api.search.MFSearch.Status;
import server.platform.Platform;

public class Downloads implements Platform {
	private FTPClient ftp;
	private Queue<APIFTPFile> queue;
	private String rootFolder;
	private boolean result;
	public Status status;

	public Downloads(FTPClient ftp) {
		super();
		this.ftp = ftp;
		queue = new LinkedList<APIFTPFile>();
		status = Status.FINISH;

		rootFolder = Loader.getProperties("DirectoryDownloads");
	}

	public void download(APIFTPFile ftpFile) {
		queue.add(ftpFile);
	}

	public void handle() {
		Print.out("Downloading...");
		status = Status.START;

		while (!queue.isEmpty()) {
			APIFTPFile curItem = queue.poll();
			status = Status.RUNNING;
			try {
				if (curItem.isFile()) {
					recieveFile(curItem.getDirectory(), curItem.getName());
				} else {
					recieveFolder(curItem, curItem.getName());
				}
			} catch (IOException e) {
				Print.out("Download: Error!  " + e.getMessage());
				e.printStackTrace();
			}
		}
		status = Status.FINISH;
		Print.out(result ? "Download: Finish!" : "Download: Error!");
	}

	public synchronized void recieveFile(String dir, String name) throws IOException {
		synchronized (ftp) {
			File file = new File(rootFolder + "/" + name);
			String path = dir + "/" + name;

			file = renameFile(file);
			Print.out("Download: " + Uploads.cutText(path));

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			FileOutputStream fos = new FileOutputStream(file);
			result = ftp.retrieveFile(path, fos);
			fos.close();
		}
	}

	public synchronized void recieve(String localDownload, String dir, String name) throws IOException {
		synchronized (ftp) {
			String path = dir + "/" + name;
			Print.out("Download: " + Uploads.cutText(path));
			System.out.println("File=" + path);

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			FileOutputStream fos = new FileOutputStream(localDownload);
			result = ftp.retrieveFile(path, fos);
			fos.close();
		}
	}

	public synchronized void recieveFolder(APIFTPFile folder, String subdir) throws IOException {
		Print.out("Download: " + folder);
		File mkd = new File(rootFolder + "/" + folder.getName());

		mkd = renameFile(mkd);

		int index = folder.getAbsolutepath().length();
		System.out.println("Folder root = " + mkd + " index of folder=" + index);

		mkd.mkdir();
		List<APIFTPFile> list = new ArrayList<APIFTPFile>();
		toList(folder.listFile(ftp), list, folder.getPathname(), mkd.getName());

		for (APIFTPFile item : list) {
			int end = item.getAbsolutepath().length() - item.getName().length() - 1;
			String pathname = item.getAbsolutepath().substring(0, end);
			String localDownload = rootFolder + "/" + mkd.getName()
					+ item.getAbsolutepath().substring(index, item.getAbsolutepath().length());
			recieve(localDownload, pathname, item.getName());
		}
	}

	public void toList(List<APIFTPFile> listfile, List<APIFTPFile> list, String FTProot, String myRoot)
			throws IOException {
		synchronized (ftp) {
			for (APIFTPFile item : listfile) {
				if (item.isFile()) {
					list.add(item);
				} else {
					File mkd = new File(rootFolder + "/" + myRoot + "/" + item.getName());
					mkd.mkdir();
					toList(item.listFile(ftp), list, FTProot + "/" + item.getName(), myRoot + "/" + item.getName());
				}
			}
		}
	}

	public static File renameFile(File file) {
		int end = file.getAbsolutePath().length(), index = 1;
		String extend = "";

		if (file.isFile()) {
			String pathname = file.getAbsolutePath();
			end = pathname.lastIndexOf('.');
			if (end != -1) {
				extend = pathname.substring(end, pathname.length());
			} else {
				end = pathname.length();
			}
		}

		while (file.exists()) {
			String rootname = file.getAbsolutePath().substring(0, end);
			file = new File(rootname + "(" + index + ")" + extend);
			index++;
		}
		return file;
	}
//	public static void main(String[] args) throws IOException {
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.connect(InetAddress.getByName("192.168.1.150"), 21);
//		ftpClient.login("donly", "root");
//		Downloads.
//		
//		ftpClient.logout();
//		ftpClient.disconnect();
//	}

	public boolean checkRoot() {
		return new File(rootFolder).exists();
	}

	public FTPClient getFtp() {
		return ftp;
	}

	public Queue<APIFTPFile> getQueue() {
		return queue;
	}

	public void setQueue(Queue<APIFTPFile> queue) {
		this.queue = queue;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
