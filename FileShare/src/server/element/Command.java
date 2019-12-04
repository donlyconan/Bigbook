package server.element;

import java.io.IOException;

public class Command {

	public static void execute(String cmd) {
		try {
			Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",cmd});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
