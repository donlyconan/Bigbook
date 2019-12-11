package gui;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import bigbook.Platform.Platform;
import bigbook.listen.Listen;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import load.resource.Plugin;

public class Run extends Application implements Platform
{
	@Override
	public void start( Stage stage) throws Exception 
	{
		try {
			Plugin.load();
			Listen listen = new Listen();
			ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
			exec.execute(listen);
			
			Parent root = (Parent) Share.get(Attribute.FXMLControl);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> System.exit(0));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main( String[] args) throws InterruptedException {
		launch(args);
	}

}
