package bigbook.listen.Running;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bigbook.Platform.Represent;
import bigbook.Platform.Work;
import bigbook.listen.Listen;
import bigbook.reprement.Account;
import bigbook.reprement.Group.Group;
import bigbook.service.Message;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class SocketRunning implements Work
{
	private static final long serialVersionUID = 1L;
	private static final ObservableMap<String, SocketRunning> USER_ONLINE = Listen.getUserOnline();
	private static final ObservableMap<String, Group> GROUP_IDS = Listen.getGroupIds();
	
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
	 * Luồng thực thi.
	 * -Mỗi gói tin giử đến sẽ kèm theo mã code
	 * -Mã code này sẽ chứa status 
	 * -code && status sẽ quyết định công việc cần thực hiện
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
				System.out.println(sms.getContent());
				
				switch (key)
				{
					case CMxUSER_LOGIN:
						excuteLogin(sms);
						break;
					case CMxMS_SEND:
						excuteSendMessage(sms);
						break;
					case CMxMS_RECIEVE:
						excuteReciveMessage(sms);
						break;
					case CMxEXIT:
						excuteExit();
						break;
					
					case CMxUSER_LOGOUT:
						
						break;
					
					default:
						Error("Không tìm thấy lệnh");
						break;
				}
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				try
				{
					Error("Không tìm thấy lệnh");
					close();
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * nếu đúng xác nhận online thêm user và thông báo đến bạn bè, group là user này online
	 * nếu sai giử thông báo về và hủy luồng.
	 */
	private void excuteLogin(Message sms) throws IOException {
		if (Represent.Verification(sms.getContent()))
		{
			Account user = new Account(sms.getReciever(), sms.getContent());
			if(Represent.Verification(user))
				Send(CMxUSER_LOGIN_FAILE);
		} else
		{
			try
			{
				close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// Server sẽ giử MS tới người dùng và chuyển lệnh từ send sang recive
	private void excuteSendMessage(Message sms) throws IOException {
		int status = sms.getStatus();
		switch (status)
		{
			case MSxPERSONAL:
				if (USER_ONLINE.containsKey(sms.getReciever()))
				{
					sms.setCode(CMxMS_RECIEVE);
					USER_ONLINE.get(sms.getReciever()).Send(sms);
				} 
				else
				{
					sms.setCode(CMxMS_RECIEVE);
					sms.setContent("Khong the giu di");
					USER_ONLINE.get(sms.getSender()).Send(sms);
				}
				break;
			case MSxGROUP:
				Group group = GROUP_IDS.get(sms.getReciever());
				
				
				break;
			
			default:
				break;
		}
	}
	
	private void excuteReciveMessage(Message sms) {
		int status = sms.getStatus();
		switch (status)
		{
			case MSxPERSONAL:
				
				break;
			case MSxGROUP:
				
				break;
			
			default:
				break;
		}
	}
	
	private void excuteExit() {}
	
	public void Error(String message) throws Exception {
		throw new Exception(message);
	}
	
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
