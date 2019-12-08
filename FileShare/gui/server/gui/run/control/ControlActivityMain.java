package server.gui.run.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import resource.Loader;
import server.platform.Platform;

public class ControlActivityMain implements Initializable, Platform {

	@FXML
	StackPane stackPane;
	@FXML
	ImageView imgH;
	@FXML
	ImageView imgT;
	@FXML
	ImageView imgS;
	@FXML
	ImageView imgO;
	@FXML
	ImageView imgST;
	@FXML
	ImageView imgV;
	@FXML
	ImageView imgC;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Data.put(Type.StackPane, stackPane);
		imgH.setImage(Loader.loadImage("me.png"));
		imgS.setImage(Loader.loadImage("share.png"));
		imgT.setImage(Loader.loadImage("trash.png"));
		imgV.setImage(Loader.loadImage("cloud.png"));
		imgO.setImage(Loader.loadImage("online.png"));
		imgST.setImage(Loader.loadImage("setting.png"));
		imgC.setImage(Loader.loadImage("computer.png"));

		stackPane.getChildren().add((Node) Data.get(Type.FXComputer));
//		stackPane.getChildren().add((Node) Data.get(Type.FXMyFiles));
//		stackPane.getChildren().add(Platform.castNode(Type.FXMyFiles));
//		stackPane.getChildren().add(Platform.castNode(Type.FXMyFiles));
	}

	public void show(Type type)
	{
		System.out.println(((Node)Data.get(type)));
		((Node)Data.get(type)).toFront();;
	}
	
	@FXML
	public void EV_Control(ActionEvent e) {
		Button type = (Button) e.getSource();
		String text = type.getText().replace(' ', '_');
		Type key = Type.valueOf(text.toUpperCase());
		switch (key) {
		case HOME:
			Platform.show(Type.FXMyFiles);
			break;
		case COMPUTER:
			Platform.show(Type.FXComputer);
			break;
		case SETTINGS:
			Platform.show(Type.FXSettings);
			break;
		case SHARE_WITH_ME:
			Platform.show(Type.FXMyShare);
			break;
		case TRASH:
			Platform.show(Type.FXTrash);
			break;
		default:
			break;
		}
	}
}
