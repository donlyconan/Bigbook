package bigbook.listen.action;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import bigbook.listen.IServer;

/**
 * Created by jjenkov on 24-10-2015.
 */
public class Server implements IServer {
	public static final int BLOCKING = 2048;
	private int port;
	private SocketProcessor socketProcessor;
	private SocketAdapter socketAdapter;

	public Server() {
		port = 8888;
	}

	public Server(int port) {
		this.port = port;
	}

	@Override
	public void start() throws IOException {

		Queue<SocketChannel> socketQueue = new ArrayBlockingQueue<SocketChannel>(BLOCKING);

		socketProcessor = new SocketProcessor(port, socketQueue);
		socketAdapter = new SocketAdapter(port, socketQueue);
		Thread process = new Thread(socketAdapter);
		Thread adapter = new Thread(socketProcessor);

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

	public SocketProcessor getSocketProcessor() {
		return socketProcessor;
	}

	public void setSocketProcessor(SocketProcessor socketProcessor) {
		this.socketProcessor = socketProcessor;
	}


	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
