package server.api;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import resource.Loader;

public class Notification {
	public static void show(Object code, String sms) {
		Alert noti = new Alert(AlertType.ERROR, "[Error!: " + code + "] " + sms, ButtonType.CLOSE);
//		((Stage) noti.getDialogPane().getScene().getWindow()).getIcons()
//				.add(Loader.loadImage("dashbroad.png"));
		noti.showAndWait();
	}

	public static boolean showYESNO(String sms) {
		Alert noti = new Alert(AlertType.INFORMATION, sms, new ButtonType[] { ButtonType.YES, ButtonType.NO });
		((Stage) noti.getDialogPane().getScene().getWindow()).getIcons()
				.add(Loader.loadImage("dashbroad.png"));
		Optional<ButtonType> option = noti.showAndWait();
		return option.get() == ButtonType.YES;
	}

	public static String show(String header,String sms,String content) {
		Alert noti = new Alert(AlertType.INFORMATION, "File Share", new ButtonType[] { ButtonType.YES, ButtonType.NO });
		((Stage) noti.getDialogPane().getScene().getWindow()).getIcons()
				.add(Loader.loadImage("dashbroad.png"));
		noti.setHeaderText(header);
		
		TextField text = new TextField();
		text.setPrefWidth(300);
		text.setText(content);
		HBox box = new HBox(2,new Label(sms) ,text);
		box.setAlignment(Pos.CENTER_LEFT);
		noti.getDialogPane().setContent(box);
		Optional<ButtonType> option = noti.showAndWait();
		text.requestFocus();
		String fol = text.getText();
		return (option.get() == ButtonType.YES ? fol : null);
	}
	
	public static String show(String sms, String nameroot) {
		Alert noti = new Alert(AlertType.INFORMATION, "File Share", new ButtonType[] { ButtonType.YES, ButtonType.NO });
		((Stage) noti.getDialogPane().getScene().getWindow()).getIcons()
		.add(Loader.loadImage("dashbroad.png"));
		noti.setHeaderText(sms);
		
		TextField text = new TextField();
		text.setPrefWidth(300);
		text.setText(nameroot);
		text.setFocusTraversable(true);
		HBox box = new HBox(2,new Label(sms) ,text);
		box.setAlignment(Pos.CENTER_LEFT);
		noti.getDialogPane().setContent(box);
		Optional<ButtonType> option = noti.showAndWait();
		text.requestFocus();
		String fol = text.getText();
		return (option.get() == ButtonType.YES ? fol : null);
	}
	
	public static void print(String header, String status)
	{
		System.out.println("[" + header + "]\t" + status); 
	}
}
