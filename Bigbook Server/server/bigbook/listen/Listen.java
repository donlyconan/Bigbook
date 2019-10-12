package bigbook.listen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import bigbook.Platform.Work;
import bigbook.listen.Running.SocketRunning;
import bigbook.reprement.Group.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Listen implements Work
{
	public static final int PORT = 10000;
	public static final String SERVER_IP = "localhost";
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Cấu trúc chương trình
	 */
	private static final ObservableMap<String, SocketRunning> USER_ONLINE= FXCollections.emptyObservableMap();;
	private static final ObservableMap<String, Group> GROUP_IDS= FXCollections.emptyObservableMap();
	
	//Phân chia nhánh
	private Thread thread;
	private Queue<Socket> queue;
	private int maxLogin;
	private ServerSocket server;
	private int SLEEP = 20;
	private String name;
	
	public Listen(int Port) throws IOException
	{
		server = new ServerSocket(Port);
		queue = new LinkedList<Socket>();
		maxLogin = 40000;
		thread = new Thread(this);
	}
	
	@Override
	public void start() throws Exception {
		thread.start();
		
		while (!server.isClosed())
		{
			System.out.println("Wating to connect... ");
			Socket socket = server.accept();
			
			queue.add(socket);
			System.out.println("Accepted client Queue: index = " + queue.size());
		}
	}
	
	/*
	 * Tạo hàng đợi để ngăn cản việc đăng nhập dẫn đến sập server khi giới hạn limitmax 
	 * tăng nên việc các user kết nối đc chấp thuận cũng tăng nên
	 */
	@Override
	public void run() {
		System.out.println("Thread - start. Listen.");
		int index = 0;
		while (!server.isClosed())
		{
			try
			{
				if (USER_ONLINE.size() < maxLogin && queue.size() > 0)
				{
					Socket socket = queue.poll();
					SocketRunning run = new SocketRunning(socket);
					USER_ONLINE.put("" + (index++), run);
					SLEEP /= 2;
				} else if (SLEEP < 1100)
					SLEEP += 10;
				

				Thread.sleep(SLEEP);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void Send(Object data) throws Exception {}
	
	@Override
	public Object Recieve() throws Exception { return null; }
	
	@SuppressWarnings ("deprecation")
	@Override
	public void stop() { thread.stop(); }
	
	@Override
	public void close() throws Exception {
		for (Work item : getUserOnline().values())
			item.close();
		queue.clear();
		server.close();
	}


	public Queue<Socket> getQueue() { return queue; }

	public void setQueue(Queue<Socket> queue) { this.queue = queue; }

	public int getMaxLogin() { return maxLogin; }

	public void setMaxLogin(int maxLogin) { this.maxLogin = maxLogin; }

	public static int getPort() { return PORT; }

	public static ObservableMap<String,SocketRunning> getUserOnline() { return USER_ONLINE; }

	public ServerSocket getServer() { return server; }

	public void setServer(ServerSocket server) { this.server = server; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public static ObservableMap<String, Group> getGroupIds() { return GROUP_IDS; }

	
	
}
