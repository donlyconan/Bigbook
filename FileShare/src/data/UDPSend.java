package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import Element.FileView;
import Parent.Platform.CMD;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TreeView;
import load.LoadResource;

public class UDPSend
{
	public final static int PORT_SERVER = 8888;
	public static final int BINARY_SIZE = 1024*1024;
	private InetAddress ip;
	private DatagramSocket ds;
	private static TreeView<FileView> yourtree;
	
	public UDPSend(DatagramSocket ds, InetAddress ip) throws SocketException
	{
		super();
		this.ip = ip;
		this.ds = ds;
		yourtree = (TreeView<FileView>) LoadResource.ShareData().get("tree");
	}
	
	public void send(byte[] data) throws IOException
	{
		DatagramPacket dp = new DatagramPacket(data, data.length, ip, PORT_SERVER);
		ds.send(dp);
	}
	
	public Object read() throws IOException, ClassNotFoundException
	{
		byte[] buff = new byte[2 * FileInfo.buff];
		DatagramPacket dp = new DatagramPacket(buff, buff.length);
		ds.receive(dp);
		return Transfer.toMessage(dp.getData());
	}
	
	public static String getForder(File file)
	{
		if(file.isFile())
		{
			String name = file.getAbsolutePath();
			return name.substring(0, name.lastIndexOf(file.getName()));
		}
		return file.getAbsolutePath() + "\\";
	}
	
	public void send(File file) throws Exception
	{
		FileInfo info = new FileInfo(file);
		info.setPath(getForder(yourtree.getSelectionModel().getSelectedItem().getValue().getFile()) + file.getName());
		System.out.println(info);
		Transfer data = new Transfer(CMD.MSxFilexOpen, Transfer.OBJtoByte(info));
		send(data.toByteArray());
		
		List<byte[]> buffs = cut(file);
		for(byte[] item : buffs)
		{
			Transfer sms = new Transfer(CMD.MSxFilexSending, item);
			send(Transfer.OBJtoByte(sms));
			Thread.sleep(10);
		}
		Transfer tran = new Transfer(CMD.MSxFilexClose, "CLOSE".getBytes());
		send(tran.toByteArray());
	}
	
	public void give(FileInfo info, File file) throws Exception
	{
		System.out.println(info);
		info.setPath(getForder(file) + file.getName());
		Transfer data = new Transfer(CMD.MSxFilexOpen, Transfer.OBJtoByte(info));
		send(data.toByteArray());
		
		List<byte[]> buffs = cut(file);
		for(byte[] item : buffs)
		{
			Transfer sms = new Transfer(CMD.MSxFilexSending, item);
			send(Transfer.OBJtoByte(sms));
			Thread.sleep(10);
		}
		Transfer tran = new Transfer(CMD.MSxFilexClose, "CLOSE".getBytes());
		send(tran.toByteArray());
	}
	
	public List<byte[]> cut(File file) throws Exception
	{
		FileInputStream fis = new FileInputStream(file);
		FileInfo fif = new FileInfo(file);
		List<byte[]>  lis = new ArrayList<byte[]>();
		
		for(int i = 0; i < fif.getPart(); i++)
		{
			byte[] buff = new byte[FileInfo.buff];
			fis.read(buff);
			lis.add(buff);
		}
		
		byte[] buff = new byte[fif.getSizeLast()];
		fis.read(buff);
		lis.add(buff);
		fis.close();
		return lis;
	}
	
	public void close()
	{
		if(!ds.isClosed())
			ds.close();
	}

}
