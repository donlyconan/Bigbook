package gui.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.Random;
import java.util.ResourceBundle;

import bigbook.Platform.Platform;
import bigbook.listen.Listen;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TextField;

public class DashbroadController implements Initializable, Runnable
{
	@FXML TextField txtNameServer;
	@FXML TextField txtSoCong;
	@FXML TextField txtSLToiDa;
	@FXML TextField txtTimeNow;
	@FXML TextField txtIP;
	@FXML TextField txtSLHangCho;
	@FXML TextField txtTongUser;
	@FXML TextField txtDNHienTai;
	@FXML LineChart<String,Number> lineChart;
	@FXML NumberAxis num;
	@FXML CategoryAxis cate;
	
	static ObservableList<Data<String,Number>> user;
	static ObservableList<Data<String,Number>> queue;
	static ObservableList<Series<String,Number>> data;
	static Listen listen;
	private int index;
	
	
	@SuppressWarnings ("unchecked")
	@Override
	public void initialize(URL location , ResourceBundle resources) {
		try
		{
			listen = new Listen(Listen.PORT);
			Platform.start(() -> {
				try
				{
					listen.start();
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			});
			
			txtIP.setText(InetAddress.getLocalHost().getHostAddress());
			data = lineChart.getData();
			
			Series<String,Number> userlogin = new Series<>();
			Series<String,Number> userlogout = new Series<>();
			userlogin.setName("Người dùng đăng nhập");
			userlogout.setName("Hàng đợi");
			data.addAll(userlogin,userlogout);
			user = data.get(0).getData();
			queue = data.get(1).getData();
			
			//Thiet lap linechart
			Platform.start(() -> {
				java.sql.Date date = new java.sql.Date(0);
				Time time = new Time(0);
				index = 0;
				final Random rd = new Random();
				while (true)
				{
					try
					{
						date.setTime(System.currentTimeMillis());
						time.setTime(System.currentTimeMillis());
						txtTimeNow.setText(time.toString() + "  " +date.toString());
						txtSLHangCho.setText("" + listen.getQueue().size());
						txtDNHienTai.setText("" + Listen.getUserOnline().size());
						
						javafx.application.Platform.runLater(() -> {
							if (index++ % 2 == 0)
							{
								if (user.size() > 10) {
									user.remove(0);
									queue.remove(0);
								}
								
								user.add(new XYChart.Data<String,Number>(time.toString(),
									listen.getUserOnline().size()));
								queue.add(new XYChart.Data<String,Number>(time.toString(),
									queue.size()));
							}
						});
						Thread.sleep(1000);
						
						
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			});
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML void TurnOFFServer() {
		try
		{
			listen.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	@FXML void EvThietLap() {
		
	}

	@Override
	public void run() { 
		
	}
	
}
