package bigbook.transfer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import bigbook.Platform.Kernel;
import bigbook.Platform.Transfer;
import bigbook.listen.running.ServerRequest;
import bigbook.transfer.DataTransfer;
import load.resource.Print;
import load.resource.Print.Content;

public class Listen implements Kernel, Runnable {
	public static enum Mode {
		ACCEPT, READ, WRITE, CONNECT, VALID
	};
	public static final int PORT = 8888;
	public static final String SERVER_IP = "localhost";
	// Phân chia nhánh
	private int safe;
	private ServerSocketChannel socketServer;
	private Queue<SocketChannel> queue;
	private Selector seletor;
	private ExecutorService execute;

	public Listen() throws IOException {
		Init();
	}

	private void Init() throws IOException {
		seletor = Selector.open();
		socketServer = ServerSocketChannel.open();
		InetSocketAddress inet = new InetSocketAddress(SERVER_IP, PORT);
		socketServer.socket().bind(inet);
		socketServer.configureBlocking(false);
		socketServer.register(seletor, socketServer.validOps());
		queue = new LinkedList<SocketChannel>();
		safe = 50000;
		execute = Executors.newFixedThreadPool(1000);
	}

	@Override
	public void start() {
		Print.out(Content.INFO, "Start Server!");
		ByteBuffer buff = ByteBuffer.allocate(2 * Transfer.KB);

		while (true) {
			try {
				seletor.select();
				Set<SelectionKey> keys = seletor.selectedKeys();
				Iterator<SelectionKey> iter = keys.iterator();

				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					iter.remove();
					Mode mode = getMode(key);
					
					switch (mode) {
					case ACCEPT:
						SocketChannel client = socketServer.accept();
						client.configureBlocking(false);
						client.register(seletor,SelectionKey.OP_READ);
						Print.out(Content.ACCEPT, client.getLocalAddress() + "  timeout:" + client.socket().getSoTimeout());
						break;
					case READ:
						SocketChannel socket = (SocketChannel) key.channel();
						socket.read(buff);
						Request request = new Request(DataTransfer.valuesOf(buff.array()), socket);
						execute.execute(request);
						buff.clear();
						break;
					case WRITE:

						break;
					case VALID:
					case CONNECT:
						Print.out(Content.QUIT, key.channel().toString());
						close(key);
						break;

					default:
						break;
					}
				}

			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.FINE, e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		start();
	}
	
	public Mode getMode(SelectionKey key) {
		if (key.isAcceptable())
			return Mode.ACCEPT;
		if (key.isReadable())
			return Mode.READ;
		if (key.isWritable())
			return Mode.WRITE;
		if (key.isConnectable())
			return Mode.CONNECT;
		if (key.isValid())
			return Mode.VALID;
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	public void close(SelectionKey key) throws IOException
	{
		key.cancel();
		USERS_LOGIN.remove(key.channel());
		Print.out(Content.FINISH, key.channel());
		key.channel().close();
	}

	@Override
	public void restart() throws Exception {
		Print.out(Content.RESTART, "Restart server!");
		this.Init();
		this.start();
	}

	@Override
	public void xstop() throws Exception {}

	@Override
	public void close() throws Exception {
		socketServer.close();
	}

	public int getSafe() {
		return safe;
	}

	public void setSafe(int safe) {
		this.safe = safe;
	}

	public ServerSocketChannel getServer() {
		return socketServer;
	}

	public void setServer(ServerSocketChannel server) {
		this.socketServer = server;
	}

	public Queue<SocketChannel> getQueue() {
		return queue;
	}

	public void setQueue(Queue<SocketChannel> queue) {
		this.queue = queue;
	}

}
