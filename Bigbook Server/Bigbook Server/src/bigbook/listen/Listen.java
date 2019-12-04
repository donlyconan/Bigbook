package bigbook.listen;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

import bigbook.Platform.Kernel;
import bigbook.listen.running.SocketRunning;

public class Listen extends Thread implements Kernel {
	public static final int PORT = 8888;
	public static final String SERVER_IP = "localhost";

	// Phân chia nhánh
	private int safe;
	private ServerSocketChannel server;
	private Queue<SocketChannel> queue;

	public Listen() throws IOException {
		Init();
	}

	private void Init() throws IOException {
		server = ServerSocketChannel.open();
		InetSocketAddress inet = new InetSocketAddress("localhost", PORT);
		server.socket().bind(inet);
		queue = new LinkedList<SocketChannel>();
		safe = 50000;
	}

	@Override
	public void start() {
		super.start();
		SocketChannel con;
		while (server.isOpen()) {
			try {
				con = server.accept();
				System.out.println("Accept connect!");
				queue.add(con);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		int sleep = 20, index = 0;

		while (server.isOpen()) {

			try {
				if (queue.size() > 0 && USERS.size() <= safe) {
					SocketChannel socket = queue.peek();
					if (socket.isOpen()) {
						SocketRunning run = new SocketRunning(null);
						run.start();
					}
					sleep /= 2;
				} else if (sleep < 1500) {
					sleep += 20;
				}
				Thread.sleep(sleep);
			} catch (Exception e) {
				try {
					if(index++ == 100) break;
					this.restart();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void restart() throws Exception {
		// Lam sach
		super.stop();
		this.Init();
		this.start();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void xstop() throws Exception {
		super.stop();
		for(SocketRunning item : USERS.values()) 
			item.xstop();
	}

	@Override
	public void close() throws Exception {
		for (Kernel item : USERS.values())
			item.close();
		server.close();
	}

	public int getSafe() {
		return safe;
	}

	public void setSafe(int safe) {
		this.safe = safe;
	}

	public ServerSocketChannel getServer() {
		return server;
	}

	public void setServer(ServerSocketChannel server) {
		this.server = server;
	}

	public Queue<SocketChannel> getQueue() {
		return queue;
	}

	public void setQueue(Queue<SocketChannel> queue) {
		this.queue = queue;
	}
	
	

}
