package ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;

import bigbook.Platform.Platform;

public class Print implements Platform {
	private static JTextArea table;

	public static enum Content {
		ERROR, WARNING, INFO, LOADING, ACCEPT, MESSAGE, FINISH, PUSH_QUEUE, QUIT, RESPONE, RESTART, SUCCESS, PROCESSOR,
		ACCOUNT, REQUEST
	}

	public static void out(Object obj) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			if (table == null) {
				String print = "[" + dateFormat.format(cal.getTime()) + "] :> "
						+ " [NULL TABLE] =>" + obj.toString();
				Logger.getAnonymousLogger().log(Level.WARNING, "Table is null! " + print);
			} else if (obj == null) {
				String print = " [" + dateFormat.format(cal.getTime()) + "] :> "
						+ " [ERRO WARNING DATA IS NULL] =>" + obj;
				table.append(print + "\n");
			} else {
				String print = "[" + dateFormat.format(cal.getTime()) + "] :> "
						+ obj.toString();
				table.append(print + "\n");
			}
		} catch (Exception e) {
		}
	}

	public static void out(Content key, Object obj) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			if (table == null) {
				String print = "[" + dateFormat.format(cal.getTime()) + "] :> [" + key.toString() + "] :> "
						+ " [NULL TABLE] =>" + obj.toString();
				Logger.getAnonymousLogger().log(Level.WARNING, "Table is null! " + print);
			} else if (obj == null) {
				String print = "[" + dateFormat.format(cal.getTime()) + "] :> [" + key.toString() + "] :> "
						+ " [ERRO WARNING DATA IS NULL] =>" + obj;
				table.append(print + "\n");
			} else {
				String print = "[" + dateFormat.format(cal.getTime()) + "] :> [" + key.toString() + "] :> "
						+ obj.toString();
				table.append(print + "\n");
			}
		} catch (Exception e) {
		}
	}
	

	public static JTextArea getTable() {
		return table;
	}

	public static void setTable(JTextArea table) {
		Print.table = table;
	}

}
