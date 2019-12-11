package notification.print;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import bigbook.Platform.Platform;
import javafx.scene.control.TextArea;

public class Print implements Platform {
	

	public static final Logger log = Logger.getAnonymousLogger();
	public static final TextArea table = (TextArea) Share.get(Attribute.ITextArea);
	
	public static enum Content {
		WARNING, ERROR, INFO, RUNNING, ACCEPT, LOADING
	}

	public static void out(Content key, Object obj) {
		Calendar cal = Calendar.getInstance();
		if(obj == null)
		{
			@SuppressWarnings("null")
			String print = "[" + dateFormat.format(cal.getTime()) + "] [" + key.toString() +"] :> " + " [ERRO WARNING DATA IS NULL] =>" + obj.toString();
			table.appendText(print);
		}
		else
		{
			String print = "[" + dateFormat.format(cal.getTime()) + "] [" + key.toString() +"] :> " + obj.toString();
			table.appendText(print);
		}
	}
}
