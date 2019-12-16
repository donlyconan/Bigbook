package ui;

import java.io.IOException;

import javax.swing.JOptionPane;

import bigbook.Platform.Platform;
import bigbook.listen.action.NIOServer;

public class Run implements Platform
{
	public static void main( String[] args)  {
		try {
			SystemRunning.createSysteamRunning();
			NIOServer server = new NIOServer();
			server.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

}
