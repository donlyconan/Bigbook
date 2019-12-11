package server.api.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.net.ftp.FTPClient;

import javafx.scene.text.Text;
import server.api.APIFTPFile;
import server.platform.Platform;

public class FTPSearch implements Platform {
	private static Text text;
	private Thread thread;
	private FTPClient ftp;
	private Queue<APIFTPFile> result;
	private boolean running;
	private int index;

	public FTPSearch(FTPClient ftp) {
		super();
		this.ftp = ftp;
		result = new LinkedList<APIFTPFile>();
		text = (Text) Data.get(Type.ITextPath);
	}

	public void search(APIFTPFile root, String name) {
		stop();
		result.clear();
		running = true;
		index = 0;

		APIFTPFile file = null;
		try {
			file = APIFTPFile.getFTPFile(ftp, name);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (file != null && (!file.isFile() || file.isFile())) {
			result.add(file);
			text.setText("Resut find: " + result.size() + "  finish!");
		} else {

			thread = Platform.start(() -> {
				FTPFilter fil = new FTPFilter(name);
				text.setText("Resut find: " + result.size());
				try {
					excutingSearchFTP(root, fil, name);
				} catch (IOException e) {
					e.printStackTrace();
				}
				running = false;
			});
		}
		text.setText("Resut find: " + index + "  finish!");
	}

	public void excutingSearchFTP(APIFTPFile root, FTPFilter fil, String name) throws IOException {
		List<APIFTPFile> list = root.listFile(ftp, fil);
		
		for (APIFTPFile item : list) {
			if (item.isFile()) {
				System.out.println(item);
				result.add(item);
			} else {
				if (item.getName().equalsIgnoreCase(name)) {
					result.add(item);
						text.setText("Resut find: " + result.size() + "  running...");
				}
				excutingSearchFTP(item, fil, name);
			}
		}
	}

	public List<APIFTPFile> toListItem() {
		List<APIFTPFile> items = new ArrayList<APIFTPFile>();
		while (!result.isEmpty())
			items.add(result.poll());
		return items;
	}

//	public static void main(String[] args) throws Exception {
//		FTPClient ftp = new FTPClient();
//		ftp.connect(InetAddress.getLocalHost(), 21);
//		System.out.println(ftp.login("donly", "root"));
//		
//		int res = ftp.sendCommand("scp -r donly@" +InetAddress.getLocalHost().getHostAddress()+ ":/donly/Image");
////		ftp.printWorkingDirectory();
//		System.out.println(res);
//		
//		ftp.logout();
//		ftp.disconnect();
//		System.out.println("End");
//	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null && thread.isAlive())
			thread.stop();
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public Queue<APIFTPFile> getResult() {
		return result;
	}

	public void setResult(Queue<APIFTPFile> result) {
		this.result = result;
	}

}
