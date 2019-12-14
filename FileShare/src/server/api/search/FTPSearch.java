package server.api.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.net.ftp.FTPClient;

import server.api.APIFTPFile;
import server.api.Print;
import server.api.search.MFSearch.Status;
import server.platform.Platform;

public class FTPSearch implements Platform {
	public static final int SIZExLOAD = 100;
	private Thread thread;
	private FTPClient ftp;
	private Queue<APIFTPFile> result;
	private Status status;
	private int index;
	private int load;

	public FTPSearch(FTPClient ftp) {
		super();
		this.ftp = ftp;
		result = new LinkedList<APIFTPFile>();
	}

	public void search(APIFTPFile root, String name) {
		stop();
		result.clear();
		index = load = 0;
		status = Status.START;
		
		APIFTPFile file = null;
		try {
			file = APIFTPFile.getFTPFile(ftp, name);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (file != null && (!file.isFile() || file.isFile())) {
			result.add(file);
			Print.out("Result find: " + result.size() + "  finish!");
			status = Status.FINISH;
		} else {

			thread = Platform.start(() -> {
				FTPFilter fil = new FTPFilter(name);
				
				try {
					excutingSearchFTP(root, fil, name);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				status = Status.FINISH;
			});
		}
	}

	public synchronized void excutingSearchFTP(APIFTPFile root, FTPFilter fil, String name)
			throws IOException, InterruptedException {
		synchronized (ftp) {
			List<APIFTPFile> list = root.listFile(ftp, fil);
			
			if (load > SIZExLOAD) {
				synchronized (thread) {
					thread.wait();
				}
			}
			
			status = Status.RUNNING;

			for (APIFTPFile item : list) {
				if (item.isFile()) {
					index++;
					load++;
					result.add(item);
					
				} else {
					
					if (item.getName().equalsIgnoreCase(name)) {
						index++;
						load++;
						result.add(item);
					}
					
					excutingSearchFTP(item, fil, name);
				}
			}
		}
	}

	public List<APIFTPFile> toListItem() {
		List<APIFTPFile> items = new ArrayList<APIFTPFile>();
		while (!result.isEmpty())
			items.add(result.poll());
		return items;
	}
//
//	public static void main(String[] args) throws Exception {
//		FTPClient ftp = new FTPClient();
//		ftp.connect(InetAddress.getByName("192.168.1.150"), 21);
//		System.out.println(ftp.login("donly", "root"));
//		
////		
////		int res = ftp.sendCommand("scp -r donly@" +InetAddress.getLocalHost().getHostAddress()+ ":/donly/Image");
////		ftp.printWorkingDirectory();
////		System.out.println(res);
//		
//		ftp.logout();
//		ftp.disconnect();
//		System.out.println("End");
//	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null && thread.isAlive())
			thread.stop();
		status = Status.FINISH;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}



	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
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
