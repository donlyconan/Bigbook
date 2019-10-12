package gui.run.control;

import java.awt.Point;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import load.resource.Fxml.Loader;

public class run extends Application
{

	private static final String MANAGER = "Manager.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception { 
		try
		{
			Parent root = Loader.loadParent(MANAGER);
			Scene scene = new Scene(root);
			final Point p = new Point();
			
			root.setOnMousePressed(e->{
				p.setLocation(e.getSceneX(), e.getScreenY());
				root.setCursor(Cursor.MOVE);
			});
			
			root.setOnMouseDragged(e->{
				primaryStage.setX(e.getScreenX()-p.getX());
				primaryStage.setY(e.getScreenY()-p.getY());
				root.setCursor(Cursor.DEFAULT);
			});
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Có lỗi xảy ra!\n" + e.getMessage(), ButtonType.OK);
			alert.show();
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
