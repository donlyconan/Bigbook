package bigbook.service.Facetime;


import com.github.sarxos.webcam.WebcamResolution;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resource.load.FXML.Loader;

public class FXFacetime extends Application
{
	public static final double WIDTH = 640.0;
	public static final double HEIGH = 504.0;
	
	static Faces myface;
	static SwingNode myself;
	static AnchorPane root;
	
	static SwingNode opposite;
	static SwingNode friend;
	static Stage stage;
	static Scene scene;
	
	@Override
	public void start(Stage stage) throws Exception {
		try
		{
			FXFacetime.stage = stage;
			
			root = (AnchorPane) Loader.loadParent("Facetime.fxml");
			myself = new SwingNode();
			friend = new SwingNode();
			
			Init();
			root.getChildren().addAll(friend, myself);
			
			scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (Exception e)
		{
			Alert aler = new Alert(AlertType.ERROR, "Lỗi! " + e.getMessage(), ButtonType.OK);
			aler.show();
			e.printStackTrace();
		}
	}
	
	private void Init() {
		myface = new Faces(WebcamResolution.VGA.getSize(), WebcamResolution.VGA.getSize());
		
		myself.setContent(myface.getMyself());
		AnchorPane.setBottomAnchor(myself, 0.0);
		AnchorPane.setRightAnchor(myself, 0.0);
		AnchorPane.setTopAnchor(myself, 370.0);
		AnchorPane.setLeftAnchor(myself, 460.0);
		myself.toFront();
		
		friend.setContent(myface.getOpposite());
		AnchorPane.setLeftAnchor(friend, 0.0);
		AnchorPane.setRightAnchor(friend, 0.0);
		AnchorPane.setTopAnchor(friend, 25.0);
		AnchorPane.setBottomAnchor(friend, 0.0);
	}
	
	public static void main(String[] args) {
		launch(args);
		myface.close();
	}
}
