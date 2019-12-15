package bigbook.listen.action;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import bigbook.listen.IServer;
import ui.Print;

public class NIOServer implements IServer {
	public static final int BLOCKING = 2048;
	private int port;
	private NIOSocketProcessor socketProcessor;
	private NIOSocketAdapter socketAdapter;

	public NIOServer() {
		port = 8888;
	}

	public NIOServer(int port) {
		this.port = port;
	}

	@Override
	public void start() throws IOException {
		Print.out("Server start!");
		Print.out("Port: " + port);
		Print.out("InetAddress Local: " + InetAddress.getLocalHost());
		
		Queue<SocketChannel> socketQueue = new ArrayBlockingQueue<SocketChannel>(BLOCKING);

		socketProcessor = new NIOSocketProcessor(port, socketQueue);
		socketAdapter = new NIOSocketAdapter(port, socketQueue);
		Thread adapter = new Thread(socketAdapter);
		Thread process = new Thread(socketProcessor);

		process.start();
		adapter.start();
	}

	@Override
	public void restart() {

	}

	@Override
	public void close() {

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public NIOSocketProcessor getSocketProcessor() {
		return socketProcessor;
	}

	public void setSocketProcessor(NIOSocketProcessor socketProcessor) {
		this.socketProcessor = socketProcessor;
	}


	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
