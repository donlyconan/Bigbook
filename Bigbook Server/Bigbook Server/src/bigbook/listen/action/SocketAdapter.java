package bigbook.listen.action;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

import load.resource.Print;
import load.resource.Print.Content;

public class SocketAdapter implements Runnable {

	private int port;
	private ServerSocketChannel socketServer;
	private Queue<SocketChannel> socketQueue;

	public SocketAdapter(int port, Queue<SocketChannel> socketQueue) throws IOException {
		super();
		this.port = port;
		this.socketQueue = socketQueue;
		this.socketServer = ServerSocketChannel.open();
		this.socketServer.bind(new InetSocketAddress("localhost", port));
	}

	@Override
	public void run() {
		Print.out("Start thread Adapter!");
		
		while (true) {
			try {
				SocketChannel socketChannel = this.socketServer.accept();
				socketQueue.add(socketChannel);
				Print.out(Content.ACCEPT, socketChannel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() throws IOException {
		socketServer.close();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerSocketChannel getSocketServer() {
		return socketServer;
	}

	public void setSocketServer(ServerSocketChannel socketServer) {
		this.socketServer = socketServer;
	}

	public ServerSocketChannel getServerSocket() {
		return socketServer;
	}

	public void setServerSocket(ServerSocketChannel serverSocket) {
		this.socketServer = serverSocket;
	}

	public Queue<SocketChannel> getSocketQueue() {
		return socketQueue;
	}

	public void setSocketQueue(Queue<SocketChannel> socketQueue) {
		this.socketQueue = socketQueue;
	}

}
