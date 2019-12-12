package gui;

import java.io.IOException;

import bigbook.Platform.Platform;
import bigbook.listen.action.Server;
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
			Server server = new Server();
			Platform.start(() -> {
				try {
					server.start();
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(0);
				}
			});
			
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
