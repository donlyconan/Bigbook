package server.gui.run.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ControlEdit implements Initializable {
	
	
	public void EV_Button(ActionEvent e)
	{
		Button temp = (Button) e.getSource();
		if("ok".equalsIgnoreCase(temp.getText()))
		{
			
		}
		else {
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
