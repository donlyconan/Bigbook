package bigbook.listen.Running;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bigbook.Platform.Work;
import bigbook.listen.Listen;
import bigbook.reprement.Group.Group;
import bigbook.service.Message;
import javafx.collections.ObservableMap;

public class SocketRunning implements Work
{
	private static final long serialVersionUID = 1L;
	private static final ObservableMap<String,SocketRunning> USER_ONLINE = Listen.getUserOnline();
	private static final ObservableMap<String,Group> GROUP_IDS = Listen.getGroupIds();
	
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Thread thread;
	
	public SocketRunning(Socket client) throws IOException
	{
		super();
		this.client = client;
		ois = new ObjectInputStream(client.getInputStream());
		oos = new ObjectOutputStream(client.getOutputStream());
	}
	
	/*
	 * Luồng thực thi. -Mỗi gói tin giử đến sẽ kèm theo mã code -Mã code này sẽ chứa
	 * status -code && status sẽ quyết định công việc cần thực hiện
	 */
	@Override
	public void run() {
		int key = 0;
		Message sms = null;
		
		while (!client.isClosed())
		{
			try
			{
				sms = (Message) Recieve();
				key = sms.getCode();
				
				switch (key)
				{
					case CMxUSER_LOGIN:
						break;
					case CMxMS_SEND:
						Response.sendUser(sms);
						break;
					case CMxMS_RECIEVE:
						
						break;
					case CMxEXIT:
						excuteExit();
						break;
					
					case CMxUSER_LOGOUT:
						throw new Exception("Exit");
					
					default:
						Error("Không tìm thấy lệnh");
						break;
				}
			} 
			catch (Exception e)
			{
				try
				{
					close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	private void excuteExit() {}
	
	public void Error(String message) throws Exception { throw new Exception(message); }
	
	/*
	 * Các hàm mặc định
	 */
	@Override
	public void Send(Object data) throws IOException { oos.writeObject(data); }
	
	@Override
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public Object Recieve() throws Exception { return ois.readObject(); }
	
	@SuppressWarnings ("deprecation")
	@Override
	public void stop() { thread.stop(); }
	
	@SuppressWarnings ("unlikely-arg-type")
	@Override
	public void close() throws IOException {
		USER_ONLINE.remove(this);
		ois.close();
		oos.close();
		client.close();
		stop();
	}
	
	public Socket getClient() { return client; }
	
	public void setClient(Socket client) { this.client = client; }
}
