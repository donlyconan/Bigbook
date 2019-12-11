package server.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.scene.text.Text;
import server.platform.Platform;

public class Print implements Platform {
	public static enum Status {
		Upload, Download, Current_Item, Current_Parent, No_Notification, Current_path
	}

	private static Text text = (Text) Data.get(Type.ITextPath);

	public static void out(Object obj) {
		try {
			String print = obj.toString();
			text.setText(print);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void out(Status key, Object obj) {
		try {
			String tostring = key.toString().replace('_', ' ');
			String print = tostring + ": " + obj.toString();
			text.setText(print);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static enum Content {
		WARNING, ERROR, INFO, RUNNING, ACCEPT, LOADING, START, FXML_ATTRIB, CHECK_VALUE, DOWLOAD, END, DOWNLOD_FOLDER
	}

	public static void out(Content key, Object obj) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		if (obj == null) {
			@SuppressWarnings("null")
			String print = "[" + format.format(cal.getTime()) + "] [" + key.toString() + "] :> "
					+ " [ERRO WARNING DATA IS NULL] =>" + obj.toString();
			System.out.println(print);
		} else {
			String print = "[" + format.format(cal.getTime()) + "] [" + key.toString() + "] :> " + obj.toString();
			System.out.println(print);
		}
	}

	public static Text getText() {
		return text;
	}

	public static void setText(Text text) {
		Print.text = text;
	}

}
