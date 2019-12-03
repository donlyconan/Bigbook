package Element;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import load.LoadResource;

public class Notification
{
	public static void show(int code ,String sms)
	{
		Alert noti = new Alert(AlertType.ERROR,"[Error: 0x" + code + "] " + sms, ButtonType.CLOSE);
		((Stage)noti.getDialogPane().getScene().getWindow()).getIcons().add(LoadResource.loadImage("icon\\dashbroad.png"));
		noti.showAndWait();
	}
	
	public static boolean showYESNO(String sms)
	{
		Alert noti = new Alert(AlertType.INFORMATION, sms, new ButtonType[] {ButtonType.YES, ButtonType.NO});
		((Stage)noti.getDialogPane().getScene().getWindow()).getIcons().add(LoadResource.loadImage("icon\\dashbroad.png"));
		Optional<ButtonType> option = noti.showAndWait();
		return option.get() == ButtonType.YES;
	}
}
