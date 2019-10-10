package bigbook.service.Facetime;

import java.awt.Point;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller implements Initializable
{
	@FXML
	MenuBar menubar;
	@FXML 
	Label lbtime;
	private Stage stage;
	public static Thread thread;
	
	@Override
	public void initialize(URL location , ResourceBundle resources) {
		menubar.toFront();
		final Point point = new Point();
		stage = FXFacetime.stage;
		
		menubar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				point.setLocation(stage.getX() - e.getScreenX(), stage.getY() - e.getScreenY());
				menubar.setCursor(Cursor.MOVE);
			}
		});
		menubar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent e) {
				stage.setX(e.getScreenX() + point.getX());
				stage.setY(e.getScreenY() + point.getY());
				menubar.setCursor(Cursor.DEFAULT);
			}
		});
		
		thread = new Thread(() -> countTime());
		thread.start();
		lbtime.toFront();
		stage.setMinHeight(FXFacetime.HEIGH);
		stage.setMinWidth(FXFacetime.WIDTH);
		stage.setMaxHeight(FXFacetime.HEIGH * 2.0);
		stage.setMaxWidth(FXFacetime.WIDTH * 2.0);
	}
	
	@SuppressWarnings ("deprecation")
	public void countTime() {
		int index = 0;
		Time time = new Time(0,0,0);
		
		while(true) {
			Platform.runLater(()-> lbtime.setText(time.toString()));
			index++;
			time.setSeconds(index);
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings ("deprecation")
	public void MenuBarAction(ActionEvent e) {
		MenuItem menu = (MenuItem) e.getSource();
		
		if ("Zoom in".equalsIgnoreCase(menu.getText()))
		{
			if (stage.getWidth() > FXFacetime.WIDTH * 2.0)
				return;
			stage.setWidth(stage.getWidth() * 1.1);
			stage.setHeight(stage.getHeight() * 1.1);
		}
		
		if ("Zoom out".equalsIgnoreCase(menu.getText()))
		{
			if (stage.getWidth() <= FXFacetime.WIDTH)
				return;
			stage.setWidth(stage.getWidth() / 1.2);
			stage.setHeight(stage.getHeight() / 1.2);
		}
		
		if ("End Call".equalsIgnoreCase(menu.getText()))
		{
			FXFacetime.myface.close();
			thread.stop();
			stage.close();;
		}
		
	}
	
}
