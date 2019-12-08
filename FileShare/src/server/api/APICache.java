package server.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.net.ftp.FTPFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import server.gui.item.FTPFileItem;

public class APICache {
	public static final int OVER_NEXT = 80;
	private Queue<FTPFile> queue;
	private ObservableList<Node> folder;
	private ObservableList<Node> data;
	private int index;

	public APICache() {
		index = 0;
		queue = new LinkedList<FTPFile>();
	}

	public APICache(Queue<FTPFile> files, int index) {
		super();
		this.queue = files;
		this.index = index;
	}
	
	public void add(FTPFile file)
	{
		queue.add(file);
	}
//	
//	public void fill(List<FTPFile> listfile)
//	{
//		queue = new LinkedList<FTPFile>(listfile);
//		curData = FXCollections.observableArrayList();
//	}
//
//	public ObservableList<Node> getArrayNext() {
//		if(queue.isEmpty())
//			return null;
//		for (int i = index; i <= index + OVER_NEXT && !queue.isEmpty(); i++) {
//			FileItem item = new FileItem(queue.poll());
//			item.setOnMouseClicked(e);
//			curData.add(item);
//		}
//
//		index += OVER_NEXT;
//		return curData;
//	}
//	
//	public void clear() {queue.clear();}
//	
//	public ObservableList<Node> getFTPItems() {
//		curData = FXCollections.observableArrayList();
//		while (!queue.isEmpty()) {
//			curData.add(new FileItem(queue.poll()));
//		}
//		return curData;
//	}
//	
//	
//	public EventHandler<MouseEvent> getEvent() {
//		return e;
//	}
//
//	public void setEvent(EventHandler<MouseEvent> e) {
//		this.e = e;
//	}
//
//	public static int getOverNext() {
//		return OVER_NEXT;
//	}
//
//	public ObservableList<Node> getCurData() {
//		return curData;
//	}
//
//	public void setCurData(ObservableList<Node> curData) {
//		this.curData = curData;
//	}
//
//	public int getNext() {return Math.min(index+OVER_NEXT, queue.size());}
//
//	public Queue<FTPFile> getQueue() {
//		return queue;
//	}
//
//	public void setQueue(Queue<FTPFile> queue) {
//		this.queue = queue;
//	}
//
//	public int getIndex() {
//		return index;
//	}
//
//	public void setIndex(int index) {
//		this.index = index;
//	}

}
