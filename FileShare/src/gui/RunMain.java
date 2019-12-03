package gui;

import Element.Notification;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import load.LoadResource;

public class RunMain extends Application
{

	@Override
	public void start( Stage primaryStage) throws Exception
	{
		try
		{
			Parent root = (Parent) LoadResource.ShareData().get("main");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add((Image) LoadResource.ShareData().get("folder"));
			primaryStage.setTitle("File Share");
			primaryStage.setOnCloseRequest(e -> {
				if (Notification.showYESNO("Bạn có muốn thoát không?"))
					System.exit(0);
			});
			primaryStage.show();
		} catch (Exception e)
		{
			Notification.show(1111, e.getMessage());
			System.exit(0);
		}

	}

	public static void main( String[] args)
	{ launch(args); }

}
