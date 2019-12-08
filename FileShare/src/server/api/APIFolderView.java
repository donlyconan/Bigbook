package server.api;

import org.apache.commons.net.ftp.FTPFile;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import server.api.search.FTPSearch;
import server.gui.item.FTPFileItem;
import server.platform.Platform;

public class APIFolderView implements Platform {
	private static FTPFile file;
	private Node curItem;
	private FlowPane flow;

	public APIFolderView(FlowPane flow) {
		super();
		this.flow = flow;
	}

	public void fillTable(FTPSearch search, EventHandler<MouseEvent> e) {
		file = null;
		while (!search.getResult().isEmpty()) {
			file = search.getResult().poll();
			if (file != null)
			{
				FTPFileItem item = new FTPFileItem(file);
				item.setOnMouseClicked(e);
				javafx.application.Platform.runLater(()->flow.getChildren().add(item));
			}
		}
	}

	public void removeCurrent() {
		flow.getChildren().remove(curItem);
	}

	public void reset() {
	}

	public Node getCurItem() {
		return curItem;
	}

	public void setCurItem(Node curItem) {
		this.curItem = curItem;
	}

	public FlowPane getFlow() {
		return flow;
	}

	public void setFlow(FlowPane flow) {
		this.flow = flow;
	}

}
