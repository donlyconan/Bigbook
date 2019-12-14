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

import javafx.concurrent.Task;
import javafx.util.Pair;
import server.api.Notification;
import server.api.Print;
import server.platform.Platform;

public class Uploads extends Task<Boolean> implements Platform {
	private FTPClient ftp;
	private Queue<Pair<String, File>> heap;
	private Pair<String, File> curItem;
	private Boolean result;

	public Uploads(FTPClient ftp) {
		super();
		this.ftp = ftp;
		heap = new LinkedList<Pair<String, File>>();
	}

	public void uploads(File file, String dir) throws Exception {
		if (heap.size() > 15) {
			throw new Exception("Out size!");
		}

		Notification.print("Header", "bl = " + isRunning());
		heap.add(new Pair<String, File>(dir, file));

		if (!isRunning()) {
			Platform.start(this);
		}
	}

	@Override
	public void run() {
		Notification.print("Upload", "Heap run = " + heap.isEmpty());
		Print.out("Uploading...");

		while (!heap.isEmpty()) {
			curItem = heap.poll();
			if (curItem.getValue().isFile()) {
				String curPath = curItem.getKey() + "\\" + curItem.getValue().getName();
				try {
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

		Print.log(Level.INFO, "End Upload!");
		Print.out(result ? "Upload: Finish!" : "Upload: Error!");
	}

	@Override
	protected Boolean call() throws Exception {
		return null;
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

	public synchronized void sendFolder() throws IOException {
		synchronized (ftp) {
			String pathname = curItem.getValue().getAbsolutePath(), pathfile = null;
			int start = pathname.length() - curItem.getValue().getName().length();
			ftp.makeDirectory(curItem.getKey() + curItem.getValue().getName());
			List<File> lisfile = new ArrayList<File>();
			toList(curItem.getValue().listFiles(), lisfile);

			for (File item : lisfile) {
				pathname = item.getAbsolutePath();
				pathfile = curItem.getKey() + "\\" + pathname.substring(start, pathname.length());
				System.out.println(pathfile);

				if (item.isFile())
					sendFile(pathfile, item);
				else
					ftp.makeDirectory(pathfile);
			}
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
