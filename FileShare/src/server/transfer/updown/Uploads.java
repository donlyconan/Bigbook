package server.transfer.updown;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.util.Pair;
import server.api.Notification;
import server.api.Print;
import server.api.search.MFSearch.Status;
import server.platform.Platform;

public class Uploads implements Platform {
	private FTPClient ftp;
	private Queue<Pair<String, File>> heap;
	private Pair<String, File> curItem;
	private Boolean result;
	public Status status;

	public Uploads(FTPClient ftp) {
		super();
		this.ftp = ftp;
		heap = new LinkedList<Pair<String, File>>();
		status = Status.FINISH;
	}

	public void uploads(File file, String dir) throws Exception {
		heap.add(new Pair<String, File>(dir, file));
	}

	public void handle() {
		Notification.print("Upload", "Heap run = " + heap.isEmpty());
		Print.out("Uploading...");
		status = Status.START;

		while (!heap.isEmpty()) {
			curItem = heap.poll();
			status = Status.RUNNING;
			
			if (curItem.getValue().isFile()) {
				try {
					String curPath = curItem.getKey() + "/" + curItem.getValue().getName();
					curPath = renameFTPFolder(curPath, ftp);
					System.out.println("Upload file: " + curPath);

					sendFile(curPath, curItem.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
				try {
					sendFolder();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		status = Status.FINISH;
		Print.log(Level.INFO, "End Upload!");
		Print.out(result ? "Upload: Finish!" : "Upload: Error!");
	}

	public synchronized void sendFile(String folder, File file) throws IOException {
		synchronized (ftp) {
			Print.out("Path: " + cutText(file.getAbsolutePath()));
			try (FileInputStream fis = new FileInputStream(file)) {
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				result = ftp.storeFile(folder, fis);
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * CurKey : localFolder on cloud of user curItem: files on my computer
	 */
	public synchronized void sendFolder() throws IOException {
		String pathname = curItem.getValue().getAbsolutePath(), pathfile = null;
		int start = pathname.length();

		String curPath = curItem.getKey() + curItem.getValue().getName();
		curPath = renameFTPFolder(curPath, ftp);
		ftp.makeDirectory(curPath);

		List<File> lisfile = new ArrayList<File>();
		toList(curItem.getValue().listFiles(), lisfile);

		for (File item : lisfile) {
			pathname = item.getAbsolutePath();
			pathfile = curPath + "/" + pathname.substring(start, pathname.length());
			System.out.println(pathfile);

			if (item.isFile())
				sendFile(pathfile, item);
			else
				ftp.makeDirectory(pathfile);
		}
	}

	public static void toList(File[] files, List<File> list) {
		for (File item : files) {
			list.add(item);
			if (!item.isFile()) {
				toList(item.listFiles(), list);
			}
		}
	}

	public static String renameFTPFolder(String pathname, FTPClient ftp) throws IOException {
		synchronized (ftp) {
			int index = 1, lenght = pathname.length();
			FTPFile file = ftp.mlistFile(pathname);

			String extend = "";
			if (file != null && file.isFile()) {
				int last = file.getName().lastIndexOf('.');
				if (last != -1 && last < lenght) {
					extend = pathname.substring(last, lenght);
					lenght = last;
				}
			}

			while (file == null) {
				pathname = pathname.substring(0, lenght) + "(" + index + ")" + extend;
				file = ftp.mlistFile(pathname);
				index++;
			}
		}
		return pathname;
	}

	public static String cutText(String text) {
		return text.length() > 80 ? "...\\" + text.substring(text.length() - 80, text.length()) : text;
	}

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public Queue<Pair<String, File>> getHeap() {
		return heap;
	}

	public void setHeap(Queue<Pair<String, File>> heap) {
		this.heap = heap;
	}

	public Pair<String, File> getCurItem() {
		return curItem;
	}

	public void setCurItem(Pair<String, File> curItem) {
		this.curItem = curItem;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

}
