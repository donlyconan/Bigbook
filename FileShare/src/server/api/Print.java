package server.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.text.Text;
import server.platform.Platform;

public class Print implements Platform {
	public static enum Status {
		Upload, Download, Current_Item, Current_Parent, No_Notification, Current_path, FINISH
	}

	private static Text text = (Text) Data.get(Type.ITextPath);

	public synchronized static void out(Object obj) {
		if (text == null || obj == null) {
			log(Level.WARNING, obj);
		} else {
			try {
				String print = obj.toString();
				text.setText(print);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void out(Status key, Object obj) {
		if (text == null || obj == null) {
			log(Level.WARNING, obj);
		} else {
			try {
				String tostring = key.toString().replace('_', ' ');
				String print = tostring + ": " + obj.toString();
				text.setText(print);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void log(Level key, Object obj) {
		if (obj == null) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Null pointer: " + obj);
		} else
			Logger.getAnonymousLogger().log(key, obj.toString());
	}

	public static Text getText() {
		return text;
	}

	public static void setText(Text text) {
		Print.text = text;
	}

}
