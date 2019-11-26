package gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import load.resource.Plugin;

public class Run extends Application
{

	@Override
	public void start( Stage stage) throws Exception 
	{
		Plugin.getDataShare().put("stage", stage);
		Parent root = Plugin.loadParent("ControlServer.fxml");
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main( String[] args) {
		launch(args);
	}

}
