package bigbook.listen;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import bigbook.Platform.Kernel;
import bigbook.Platform.Work;
import bigbook.listen.Running.SocketRunning;
import bigbook.reprement.Group.Group;

public class Listen extends Thread implements Kernel
{
	public static final int PORT = 8888;
	public static final String SERVER_IP = "localhost";

	// Phân chia nhánh
	private int maxLogin;
	private int sleep = 20;
	private ServerSocketChannel server;
	private Queue<SocketChannel> queue;

	public Listen( int Port ) throws IOException
	{
		server		= ServerSocketChannel.open();
		InetSocketAddress inet = new InetSocketAddress("localhost",Port);
		server.socket().bind(inet);
		queue		= new LinkedList<SocketChannel>();
		maxLogin	= 50000;
	}

	@Override
	public void run( )
	{
		while (server.isOpen()) {
			if (queue.size() > 0 && USER_ONLINE.size() < maxLogin) {
				SocketChannel socket = queue.peek();
				if (socket.isOpen()) {
					try {
//						run = new SocketRunning(new Socket(socket);
//						run.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sleep /= 2;
			} else if (sleep < 1500) {
				sleep += 20;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void Start( ) throws Exception
	{
		this.start();
		while (server.isOpen()) {
			try {
				SocketChannel con = server.accept();
				System.out.println("Accept connect!");
				queue.add(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void Restart( ) throws Exception
	{
		// Lam sach
		Stop();
		USER_ONLINE.clear();
		GROUP_ID.clear();

		// Khoi dong lai
		maxLogin	= 50000;
		sleep		= 20;
//		server		= new ServerSocket(PORT);
//		queue		= new LinkedList<Socket>();
		maxLogin	= 50000;
		Start();
	}

	@SuppressWarnings( "deprecation" )
	@Override
	public void Stop( ) throws Exception
	{
		stop();
		for (Work item : USER_ONLINE.values()) {
			item.Stop();
			item.Close();
		}
		server.close();
	}

	@Override
	public void Close( ) throws Exception
	{
		for (Work item : USER_ONLINE.values())
			item.Close();
		server.close();
	}

	public int getMaxLogin( )
	{ return maxLogin; }

	public void setMaxLogin( int maxLogin)
	{ this.maxLogin = maxLogin; }

	public int getSleep( )
	{ return sleep; }

	public void setSleep( int sleep)
	{ this.sleep = sleep; }

	public static int getPort( )
	{ return PORT; }

	public static String getServerIp( )
	{ return SERVER_IP; }

	public static Hashtable<String, SocketRunning> getUserOnline( )
	{ return USER_ONLINE; }

	public static Hashtable<String, Group> getGroupId( )
	{ return GROUP_ID; }

}
