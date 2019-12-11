package load.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import bigbook.Platform.Platform;
import javafx.scene.control.TextArea;

public class Print implements Platform{
	public static final TextArea table = (TextArea) Share.get(Attribute.ITextArea);
	
	
	public static enum Content {
		ERROR,WARNING, INFO, LOADING, ACCEPT, MESSAGE, FINISH, PUSH_QUEUE, QUIT, RESPONE, RESTART, SUCCESS
	}

	public static void out(Content key, Object obj) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			if(table == null)
			{
				String print = "[" + dateFormat.format(cal.getTime()) + "] [" + key.toString() +"] :> " + " [NULL TABLE] =>" + obj.toString();
				Logger.getAnonymousLogger().log(Level.WARNING, "Table is null! " + print);
			} 
			else if(obj == null)
			{
				String print = "[" + dateFormat.format(cal.getTime()) + "] [" + key.toString() +"] :> " + " [ERRO WARNING DATA IS NULL] =>" + obj;
				table.appendText(print + "\n");
			}
			else
			{
				String print = "[" + dateFormat.format(cal.getTime()) + "] [" + key.toString() +"] :> " + obj.toString();
				table.appendText(print + "\n");
			}
		} catch (Exception e) {
		}
	}

}
