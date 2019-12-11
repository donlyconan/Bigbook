package server.api.search;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.scene.text.Text;
import server.platform.Platform;

public class MFSearch implements Platform {
	private static Text text;
	private Thread thread;
	private Queue<File> result;
	private int index = 0;
	private boolean running;

	public MFSearch() {
		super();
		result = new LinkedList<File>();
		text = (Text) Data.get(Type.IMCTextPath);
	}

	public void search(String dir, String name) throws Exception {
		this.stop();
		index = 0;

		// Tam dung thread
		result.clear();
		running = true;
		File file = new File(dir);
		File filename = new File(name);
		
		if (filename.exists()) {
			result.add(filename);
		}
		// Khoi tao tim kiem file
		else if (file.exists()) {
			thread = Platform.start(() -> {
				MFFilter fil = new MFFilter(name);
				text.setText("Resut find: " + result.size());
				exceutingMF(file.listFiles(fil), fil, name);
				running = false;
			});
		} else {
			throw new Exception("File not found!");
		}

	}

	// De quy tim kiem file dua tren filter
	public void exceutingMF(File[] files, MFFilter fil, String name) {
		if (files == null)
			return;
		for (File item : files) {
			if (item.isFile()) {
				index++;
				System.out.println(item);
				result.add(item);
			} else {
				if (item.getName().equalsIgnoreCase(name)) {
					result.add(item);
				}
				exceutingMF(item.listFiles(fil), fil, name);
			}
		}
	}

	public List<File> toListItem() {
		List<File> items = new ArrayList<File>();
		while (!result.isEmpty())
			items.add(result.poll());
		return items;
	}

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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public static Text getText() {
		return text;
	}

	public static void setText(Text text) {
		MFSearch.text = text;
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
