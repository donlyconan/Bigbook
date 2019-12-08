package server.gui.run.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import server.api.APILoader;
import server.platform.Platform;

public class ControlLogin implements Initializable, Platform {
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	CheckBox check;
	@FXML
	Label forgot;
	@FXML
	Label noti;
	@FXML
	ImageView image;
	static FTPClient ftp ;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ftp = new FTPClient();
		try {
			username.setFocusTraversable(true);
			ftp.connect(InetAddress.getLocalHost(), 21);
			image.setImage(APILoader.getIconFile("dashbroad.png", 160, 160).getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void EVLogin(ActionEvent e) throws InterruptedException {
		Button temp = (Button) e.getSource();
		Login key = Login.valueOf(temp.getText().toUpperCase());

		switch (key) {
		case LOGIN:
			try {
				if(ftp.login(username.getText(), password.getText()))
				{
//					Run.stage.hide();
//					Run.stage.setScene(new Scene(root));
//					Run.stage.centerOnScreen();
//					Thread.sleep(500);
//					Run.stage.show();
				}else {
					noti.setText("Login incorrect! Account or password faile!");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case LOGOUT:
			
			break;

		}
	}
	
	public static void animation(Parent node)
	{
		TranslateTransition tran = new TranslateTransition(Duration.millis(3.0), node);
		tran.setByX(1000);
		tran.setFromX(-1000);
		tran.play();
	}

}
