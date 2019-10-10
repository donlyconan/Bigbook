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
	private static final int LIMIT_MAX_VALUE = 50000;
	
	/*
	 * Cấu trúc chương trình
	 */
	private static final ObservableMap<String, SocketRunning> USER_ONLINE= FXCollections.emptyObservableMap();;
	private static final ObservableMap<String, Group> GROUP_IDS= FXCollections.emptyObservableMap();
	
	//Phân chia nhánh
	private static long SLEEP = 20;
	private static Thread THREAD;
	private static Queue<Socket> queue;
	
	private ServerSocket server;
	
	public Listen() throws IOException
	{
		server = new ServerSocket(PORT);
		queue = new LinkedList<Socket>();
	}
	
	@Override
	public void start() throws IOException, InterruptedException {
		THREAD = new Thread(this);
		THREAD.start();
		
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
		while (!server.isClosed())
		{
			try
			{
				if (getUserOnline().size() < LIMIT_MAX_VALUE && queue.size() > 0)
				{
					Socket socket = queue.poll();
					SocketRunning run = new SocketRunning(socket);
					run.start();
					SLEEP /= 2;
				} else if (SLEEP < 1000)
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
	public void stop() { THREAD.stop(); }
	
	@Override
	public void close() throws Exception {
		for (Work item : getUserOnline().values())
		{
			item.close();
		}
		queue.clear();
		server.close();
	}

	public static Queue<Socket> getQueue() { return queue; }

	public static void setQueue(Queue<Socket> queue) { Listen.queue = queue; }

	public ServerSocket getServer() { return server; }

	public void setServer(ServerSocket server) { this.server = server; }

	public static int getLimitMaxValue() { return LIMIT_MAX_VALUE; }

	public static ObservableMap<String, SocketRunning> getUserOnline() { return USER_ONLINE; }

	public static ObservableMap<String, Group> getGroupIds() { return GROUP_IDS; }

	
	
}
