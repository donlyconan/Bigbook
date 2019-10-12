package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import load.resource.Fxml.Loader;

public class Manager implements Initializable
{

	@FXML StackPane stackNode;
	
	@Override
	public void initialize(URL location , ResourceBundle resources) { 
		try
		{
			BorderPane dash = (BorderPane) Loader.loadParent("DashBroad.fxml");
			stackNode.getChildren().add(dash);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
