package server.gui.run;

import java.util.logging.Level;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resource.Loader;
import server.api.APILoader;
import server.api.Notification;
import server.api.Print;
import server.platform.Platform;

public class Run extends Application implements Platform {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Print.log(Level.INFO, "Loading...");
		
		try {
			Loader.Load();
			Scene scene = new Scene((Parent) Data.get(Type.FXController));
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(APILoader.getIconFile("Java", 30, 30).getImage());
			primaryStage.setTitle("File Share");
			primaryStage.setOnCloseRequest(e -> {
				if (Notification.showYESNO("Bạn có muốn thoát không?"))
					System.exit(0);
				
			});
			primaryStage.centerOnScreen();
			stage = primaryStage;
			primaryStage.show();
		} catch (Exception e) {
			Notification.show(811111, e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
