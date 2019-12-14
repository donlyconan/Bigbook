package server.api.search;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import server.api.Print;
import server.platform.Platform;

public class MFSearch implements Platform {
	public static enum Status {
		RUNNING, WATTING, START, STOP, FINISH
	}

	private static final int SIZExLOAD = 100;
	private Thread thread;
	private Queue<File> result;
	private int index = 0;
	private int load;
	private Status status;

	public MFSearch() {
		super();
		result = new LinkedList<File>();
	}

	public void search(String dir, String name) throws Exception {
		/*
		 * Khoi tao ban dau de tien hanh duyet file
		 */
		stop();
		index = load = 0;
		result.clear();
		status = Status.START;
		
		/*
		 * Khoi tao doi tuong dau vao
		 */
		File file = new File(dir);
		File filename = new File(name);

		if (filename.exists()) {
			result.add(filename);
			Print.out("Finish! result find: " + result.size());
		}
		// Khoi tao tim kiem file
		else if (file.exists()) {
			thread = Platform.start(() -> {
				MFFilter fil = new MFFilter(name);
				Print.out("Searching...");
				
				try {
					exceutingMF(file.listFiles(fil), fil, name);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			});
		} else {
			
			status = Status.FINISH;
			throw new Exception("File not found!");
		}

		status = Status.FINISH;
	}

	// De quy tim kiem file dua tren filter
	public void exceutingMF(File[] files, MFFilter fil, String name) throws InterruptedException {
		if (load > SIZExLOAD) {
			
			System.out.println("Size load is max!" + load);
			synchronized (thread) {
				load = 0;
				status = Status.WATTING;
				System.out.println("Thread is waiting");
				thread.wait();
			}
		}
		
		status = Status.RUNNING;

		if (files != null) {
			for (File item : files) {
				if (item.isFile()) {
					index++;
					load++;
					result.add(item);
					
				} else {
					
					if (item.getName().equalsIgnoreCase(name)) {
						result.add(item);
					}
					
					exceutingMF(item.listFiles(fil), fil, name);
				}
			}
		}
	}

	public List<File> toListItem() {
		List<File> items = new ArrayList<File>();
		
		while (!result.isEmpty()) {
			try {
				items.add(result.poll());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return items;
	}

//	public static void main(String[] args) throws Exception {
//		MFSearch ms = new MFSearch();
//		ms.search("/home", "jfxrt.jar");
//		System.out.println("Search");
//	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null && thread.isAlive()) {
			thread.stop();
		}
	}

	@SuppressWarnings("deprecation")
	public void restart() {
		thread.suspend();
		thread.resume();
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

	public Queue<File> getResult() {
		return result;
	}

	public void setResult(Queue<File> result) {
		this.result = result;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
