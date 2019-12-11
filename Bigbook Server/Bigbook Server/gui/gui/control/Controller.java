package gui.control;

import java.net.URL;
import java.util.ResourceBundle;

import bigbook.Platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class Controller implements Initializable, Platform
{
	static enum Type {STATUS, USER_ONLINE}
	@FXML TextArea idStatus;
	@FXML StackPane idStack;
	@FXML TableView<?> idTable;
	
	@Override
	public void initialize( URL location, ResourceBundle resources) { 
		Share.put(Attribute.ITextArea, idStatus);
		idTable.setVisible(true);
		idStatus.toFront();
		idStatus.setText(null);
	}
	
	public void EVtoolBar(ActionEvent e)
	{
		Button get = (Button) e.getSource();
		String text = get.getText().toUpperCase().replace(' ', '_');
		Type key = Type.valueOf(text);
		
		switch (key) {
		case STATUS:
			idStatus.toFront();
			break;
		case USER_ONLINE:
			idTable.toFront();
			break;
		default:
			break;

		}
	}

}
