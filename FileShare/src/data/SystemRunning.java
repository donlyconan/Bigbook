package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import Element.FileView;
import Element.ItemView;
import Element.Load;
import Element.Notification;
import Parent.Platform.CMD;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import load.LoadResource;

public class SystemRunning extends Thread
{
	private static int index;
	private static FileInfo info = null;
	
	private String name;
	private InetAddress ip;
	private DatagramSocket ds;
	private ObservableList<Node> items;
	private UDPSend send;

	public SystemRunning( InetAddress ip ) throws SocketException
	{
		if(ds == null ||!ds.isClosed())
			ds		= new DatagramSocket(8888);
		this.ip	= ip;
		send	= new UDPSend(ds, ip);
		name	= ((TextField) LoadResource.ShareData().get("name")).getText();
	}

	@Override
	public void run( )
	{
		FileOutputStream fos = null;
		FileInputStream fis = null;
		TextField txtFile = null;
		index = 0;
		String text = "";
		@SuppressWarnings( "unchecked" )
		ObservableList<Node> lis = (ObservableList<Node>) LoadResource.ShareData().get("list");
		final TextField txtName = (TextField) LoadResource.ShareData().get("name");
		final TextField yourIP = (TextField) LoadResource.ShareData().get("your");
		final TreeView<FileView> tree = (TreeView<FileView>) LoadResource.ShareData().get("tree");
		final TreeView<FileView> mytree = (TreeView<FileView>) LoadResource.ShareData().get("mytree");
		ImageView image = (ImageView) LoadResource.ShareData().get("image");
		Transfer data = null;
		TreeItem<FileView> item = null;
		List<FileView> lisItem = null;
		final ProgressIndicator progress = (ProgressIndicator) LoadResource.ShareData().get("progress");
		while (!ds.isClosed())
		{
			try
			{
				Transfer sms = (Transfer) send.read();
				Object key = sms.getCode();
				switch (key.toString())
					{
						case "MSxMessage":
							Platform.runLater(()->{
								try
								{
									String mes = new String(sms.getBuff(), "UTF-8");
									lis.add(new ItemView(sms.getSender(), mes, false).getContent());
								} catch (UnsupportedEncodingException e)
								{
									e.printStackTrace();
								}
							});
							break;
						case "MSxSendxConnect":
							String temp = new String(sms.getBuff(), "UTF-8");
							Platform.runLater(()-> {
								if(Notification.showYESNO(temp + " đang kết nối đến bạn!"))
								{
									yourIP.setText(temp);
									Transfer tran = new Transfer(CMD.MSxAcceptxConnect, "TRUE".getBytes());
									try
									{
										send.send(tran.toByteArray());
									} catch (IOException e)
									{
										e.printStackTrace();
									}
								}
							});
							break;
						case "MSxAcceptxConnect":
							image.setImage(LoadResource.loadImage("icon\\online.png"));
							Transfer tran = new Transfer(CMD.MSxConnectxSuccess,
									(txtName.getText() + " has connected!").getBytes("UTF-8"));
							send.send(tran.toByteArray());
							break;

						case "MSxGetIP":
							text = new String(sms.getBuff());
							yourIP.setText(getMessage(text));
							break;
						case "MSxResponexItem":
							item = tree.getSelectionModel().getSelectedItem();
							Load.FillData(item, sms.getItem());
							tree.refresh();
							break;
						case "MSxRequestxItem":
							item = tree.getSelectionModel().getSelectedItem();
							lisItem = Load.getItemFileView(item.getValue().getFile().listFiles());
							data = new Transfer(CMD.MSxResponexItem);
							data.setItem(lisItem);
							send.send(data.toByteArray());
							break;
						case "MSxOpenLoad":
							lisItem = Load.getItemFileView(File.listRoots());
							data = new Transfer(CMD.MSxResponexItem);
							data.setItem(lisItem);
							send.send(data.toByteArray());
							break;
//
//						case "0xGetName":
//							txtFile = (TextField) LoadResource.ShareData().get("name");
//							text = new String(sms.getBuff());
//							txtFile.setText(text);
//							break;

						case "MSxFilexOpen":
							info = (FileInfo) Transfer.toMessage(sms.getBuff());
							fos = new FileOutputStream(info.getPath());
							item = tree.getSelectionModel().getSelectedItem();
							progress.setProgress(0.0);
							index = 1;
							TreeItem<FileView> tempx = new TreeItem<FileView>(new FileView(new File(info.getPath())),Load.getIconFile());
							if(!item.getChildren().contains(tempx))
								item.getChildren().add(tempx);
							tree.refresh();
							break;

						case "MSxFilexSending":
							try
							{
								fos.write(sms.getBuff());
								Platform.runLater(()->progress.setProgress((double)index++/info.getPart()));
							} catch (Exception e)
							{
								e.printStackTrace();
							}
							break;

						case "MSxFilexClose":
							try
							{
								fos.flush();
								fos.close();
							} finally
							{}
							break;
						case "MSxOpenGiveFile":
							FileInfo fi = (FileInfo) Transfer.toMessage(sms.getBuff());
							progress.setProgress(0.0);
							index = 0;
							info = fi;
							Parent.Platform.start(()->{
								try
								{
									send.give(fi, new File(sms.getSender()));
								} catch (Exception e)
								{
									e.printStackTrace();
								}
							});
							break;
						
					}
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("Close [Exeption: LP]");
		close();
		try
		{
			fos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings( "deprecation" )
	public void restart()
	{
		if(isAlive())
			stop();
		start();
	}
	
	public String getMessage( String text)
	{ return text.substring(0, text.indexOf("\0")); }

	public void close( )
	{
		if (!ds.isClosed())
			ds.close();
	}
	
	public ObservableList<Node> getItems( )
	{ return items; }

	public void setItems( ObservableList<Node> items)
	{ this.items = items; }

	public DatagramSocket getDs( )
	{ return ds; }

	public void setDs( DatagramSocket ds)
	{ this.ds = ds; }

	public InetAddress getIp( )
	{ return ip; }

	public void setIp( InetAddress ip)
	{ this.ip = ip; }

	public UDPSend getSend( )
	{ return send; }

	public void setSend( UDPSend send)
	{ this.send = send; }

	public String getNames( )
	{ return name; }

	public void setNames( String name)
	{ this.name = name; }

}
