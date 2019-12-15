package socket.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ui.Print;

public class NIOClient {
	public static enum Mode {
		ACCEPT, READ, WRITE, CONNECT, VALID
	};

	private static final int PORT = 8888;
	private static final String IPxSERVER = "localhost";
	private static final int HOK = 512;

	private SocketChannel socket;
	private Selector selector;
	private SelectionKey curKey;
	private SocketChannel chanel;
	private ByteBuffer buffer;

	public NIOClient() throws IOException {
		buffer = ByteBuffer.allocate(512);
		register();
	}

	private void register() throws IOException, ClosedChannelException {
		socket = openConnection();
		socket.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	public void handle() {
		while (true) {
			try {
				selector.select();
				Set<SelectionKey> keySet = selector.selectedKeys();
				Iterator<SelectionKey> iter = keySet.iterator();

				while (iter.hasNext()) {
					curKey = iter.next();
					iter.remove();
					Mode mode = getMode(curKey);

					switch (mode) {
					case READ:
						chanel = (SocketChannel) curKey.channel();
						buffer.flip();
						chanel.read(buffer);

						System.out.println(new String(buffer.array()));
						buffer.clear();
						break;
					case WRITE:
						chanel = (SocketChannel) curKey.channel();
						buffer.flip();
						chanel.write(buffer);

						buffer.clear();
						break;
					case CONNECT:
						register();

						break;

					case VALID:

						break;
					default:

						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void close() throws IOException {
		socket.close();
	}

	public static SocketChannel openConnection() throws IOException {
		InetSocketAddress inet = new InetSocketAddress(IPxSERVER, PORT);
		SocketChannel chanel = SocketChannel.open(inet);
		chanel.configureBlocking(false);
		if (!chanel.finishConnect())
			System.out.println("Socket not connected!");
		return chanel;
	}

	public static Mode getMode(SelectionKey key) {
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

	public SocketChannel getSocket() {
		return socket;
	}

	public void setSocket(SocketChannel socket) {
		this.socket = socket;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public SelectionKey getCurKey() {
		return curKey;
	}

	public void setCurKey(SelectionKey curKey) {
		this.curKey = curKey;
	}

}
