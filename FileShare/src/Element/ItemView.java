package Element;

import java.sql.Time;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import load.LoadResource;

public class ItemView 
{
	private Label content;
	
	public ItemView(String name,String text, boolean you)
	{
		content = new Label("["+name+"] : ["+ new Time(System.currentTimeMillis()) + "] \n-"+ text);
		content.setStyle("-fx-family: 'Cambria'; -fx-font-size: 13;");
		if(you)
		{
			ImageView img = new ImageView(LoadResource.loadImage("icon\\me.png"));
			img.setFitHeight(30);
			img.setFitWidth(30);
			content.setGraphic(img);
		}
		else
		{
			ImageView img = new ImageView(LoadResource.loadImage("icon\\you.png"));
			img.setFitHeight(30);
			img.setFitWidth(30);
			content.setGraphic(img);
			content.setAlignment(Pos.TOP_RIGHT);
		}
	}

	public Label getContent( )
	{ return content; }

	public void setContent( Label content)
	{ this.content = content; }
	
}
