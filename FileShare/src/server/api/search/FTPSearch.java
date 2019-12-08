package server.api.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javafx.scene.text.Text;
import server.platform.Platform;

public class FTPSearch implements Platform {
	private static Text text;
	private Thread thread;
	private FTPClient ftp;
	private Queue<FTPFile> result;
	private boolean running;

	public FTPSearch(FTPClient ftp) {
		super();
		this.ftp = ftp;
		result = new LinkedList<FTPFile>();
		text = (Text) Data.get(Type.ITextPath);
	}

	@SuppressWarnings("deprecation")
	public void search(String dir, String name) {
		if(thread != null && thread.isAlive()) 
			thread.stop();
		result.clear();
		running = true;
		
		thread = Platform.start(()->{
			FTPFilter fil = new FTPFilter(name);
			text.setText("Resut find: " + result.size());
			try {
				excutingSearchFTP(ftp.listFiles(dir, fil), fil, dir, name);
			} catch (IOException e) {
				e.printStackTrace();
			}
			running = false;
		});
		
	}

	public void excutingSearchFTP(FTPFile[] ftpfile, FTPFilter fil, String path, String name) throws IOException {
		for (FTPFile item : ftpfile) {
			String temp = path + "\\" + item.getName();
//			System.out.println(temp);
			if (item.isFile()) {
				result.add(item);
				text.setText("Resut find: " + result.size() + "  running...");
			} else {
				if (item.getName().equalsIgnoreCase(name)) {
					result.add(item);
					text.setText("Resut find: " + result.size() + "  running...");
				}
				excutingSearchFTP(ftp.listFiles(temp, fil), fil, temp, name);
			}
		}
	}
	
	public List<FTPFile> toListItem()
	{
		List<FTPFile> items = new ArrayList<FTPFile>();
		while(!result.isEmpty())
			items.add(result.poll());
		return items;
	}

//	public static void main(String[] args) throws IOException {
//		FTPClient ftp = new FTPClient();
//		ftp.connect(InetAddress.getLocalHost(), 21);
//		System.out.println(ftp.login("donly", "root"));
//		Search ser= new Search(ftp);
//		ser.search("\\", "me.png");
////		ftp.disconnect();
//		System.out.println("End");
//	}
	
	
	public FTPFile getNext() throws IOException {
		return this.result.poll();
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

	public Queue<FTPFile> getResult() {
		return result;
	}

	public void setResult(Queue<FTPFile> result) {
		this.result = result;
	}


}
