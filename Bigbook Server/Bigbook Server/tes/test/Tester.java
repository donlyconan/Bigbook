package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import bigbook.Platform.Platform;
import bigbook.transfer.DataTransfer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Tester extends Application implements Initializable, Runnable {

	@FXML
	TextField txtLocal;
	@FXML
	TextArea txtTable;
	static SocketChannel socket;
	static ByteBuffer buffer;
	private ExecutorService threadPool;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Form.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		threadPool = Executors.newFixedThreadPool(5);
	}

	public void EV_Connect(ActionEvent e) {
		try {
			if (socket != null && socket.isOpen())
				socket.close();
			if (!threadPool.isShutdown())
				threadPool.shutdown();
			socket = SocketChannel.open(new InetSocketAddress(txtLocal.getText(), 8888));
			threadPool.execute(this);
			threadPool.execute(() -> {
				try {
					socket.read(buffer);
					print(DataTransfer.valuesOf(buffer.array()));
				} catch (ClassNotFoundException | IOException e1) {
					print(e1.getMessage());
				}
			});
		} catch (IOException e1) {
			Logger.getAnonymousLogger().log(Level.WARNING, e1.getMessage());
			print(e1.getMessage());
		}
	}

	@Override
	public void run() {
		String[] test = new String[] { "T", "E", "S", "T", "END!", "RESTART!" };
		int index = 0;
		while (true) {
			DataTransfer data = new DataTransfer(Platform.Request.RQxSMessage, test[index % test.length]);
			print(test[index % test.length]);
			try {
				socket.write(data.toByteBuffer());
				Thread.sleep(1000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void print(Object obj) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		txtTable.setText("[" + format.format(cal.getTime()) + "] :> " + obj.toString());

	}

	public static void main(String[] args) {
		launch(args);
	}

}
