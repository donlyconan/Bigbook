package server.api;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import server.gui.item.FTPFileItem;
import server.platform.Platform;

public class APILocalDisk extends VBox implements Platform {
	private ProgressBar procgress;
	private Text header;
	private Label disk;
	private Text text;
	private String file;

	public APILocalDisk(File file) {
		disk = new Label("Local Disk " + file.getAbsolutePath());
		procgress = new ProgressBar((double) file.getUsableSpace() / file.getTotalSpace());
		String use = String.format("%.2f", file.getUsableSpace() / Math.pow(1024, 3));
		String tol = String.format("%.2f", file.getTotalSpace() / Math.pow(1024, 3));
		disk.setStyle(FTPFileItem.STYLE_CSS + "-fx-font-weight: bold;");
		Text info = new Text(use + " GB Free space / " + tol + " GB");
		info.setStyle(FTPFileItem.STYLE_CSS);
		procgress.setPrefSize(220, 30);
		getChildren().addAll(disk, procgress, info);
		setPadding(new Insets(0, 10, 0, 0));
		this.file = file.getAbsolutePath();
		header = new Text(file.getAbsolutePath());
		header.setStyle(FTPFileItem.STYLE_CSS + "-fx-font-weight: bold;");
	}
	
	

	public Text getHeader() {
		return header;
	}



	public void setHeader(Text header) {
		this.header = header;
	}



	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setOnEvent(EventHandler<MouseEvent> e) {
		procgress.setOnMouseClicked(e);
		header.setOnMouseClicked(e);
	}

	public ProgressBar getSource() {
		return procgress;
	}

	public ProgressBar getProcgress() {
		return procgress;
	}

	public void setProcgress(ProgressBar procgress) {
		this.procgress = procgress;
	}

	public Label getDisk() {
		return disk;
	}

	public void setDisk(Label disk) {
		this.disk = disk;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public File getCurrentFile() {
		return new File(file);
	}

	public void setCurFile(File curFile) {
		this.file = curFile.getAbsolutePath();
	}

}
