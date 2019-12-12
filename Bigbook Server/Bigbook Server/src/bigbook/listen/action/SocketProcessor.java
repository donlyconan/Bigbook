package bigbook.listen.action;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import bigbook.Platform.Platform;
import bigbook.Platform.Transfer;
import bigbook.listen.running.ServerRequest;
import load.resource.Print;
import load.resource.Print.Content;

public class SocketProcessor implements Runnable, Platform {
	public static enum Mode {
		ACCEPT, READ, WRITE, CONNECT, VALID
	};

	private static final int MAX_THREADS = 2000;
	private final Map<String, IDSocket> managerSocket = new HashMap<>();
	private final ByteBuffer bufferReader = ByteBuffer.allocate(Transfer.HAFT_OF_KB);
	private final ByteBuffer bufferWriter = ByteBuffer.allocate(Transfer.MB);

	private Queue<SocketChannel> socketQueue;
	private Selector seletorReader;
	private Selector seletorWriter;
	private ExecutorService threadPool;
	private SelectionKey curKey;
	private SocketChannel curSocket;
	private long numberID;

	public SocketProcessor(int port, Queue<SocketChannel> socketQueue) throws IOException {
		super();
		this.socketQueue = socketQueue;
		seletorReader = Selector.open();
		seletorWriter = Selector.open();
		numberID = 0;
		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
	}

	@Override
	public void run() {
		Print.out("Start thread Processor!");
		
		while (true) {
			try {
				register();
				handle();
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.FINE, e.getMessage());
			}
		}
	}

	public void register() throws IOException {
		SocketChannel socket = null;
		while ((socket = socketQueue.poll()) != null) {
			socket.configureBlocking(false);
			socket.register(seletorReader, SelectionKey.OP_READ);
			Print.out(socket + " is register: " + socket.isRegistered());
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void handle() throws IOException {
		seletorReader.selectNow();
		Set<SelectionKey> keys = seletorReader.selectedKeys();
		Iterator<SelectionKey> iter = keys.iterator();

		while (iter.hasNext()) {
			curKey = iter.next();
			Print.out(curKey);
			iter.remove();
			Mode mode = getMode(curKey);

			switch (mode) {
			case ACCEPT:
				break;
			case READ:
				curSocket = (SocketChannel) curKey.channel();
				Print.out(Content.INFO, "Request: " + curSocket.toString());
				ServerRequest request = new ServerRequest(new IDSocket(curSocket));
				threadPool.execute(request);
				break;
			case WRITE:
				// To do
				break;
			case VALID:
			case CONNECT:
				managerSocket.remove(curKey);
				numberID--;
				Print.out(Content.QUIT, curKey.channel().toString());
				close(curKey);
				break;

			default:
				break;
			}
		}

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

	public void close(SelectionKey key) throws IOException {
		key.cancel();
		key.channel().close();
	}

	public Queue<SocketChannel> getSocketQueue() {
		return socketQueue;
	}

	public void setSocketQueue(Queue<SocketChannel> socketQueue) {
		this.socketQueue = socketQueue;
	}

	public Selector getSeletorReader() {
		return seletorReader;
	}

	public void setSeletorReader(Selector seletorReader) {
		this.seletorReader = seletorReader;
	}

	public Selector getSeletorWriter() {
		return seletorWriter;
	}

	public void setSeletorWriter(Selector seletorWriter) {
		this.seletorWriter = seletorWriter;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public long getNumberID() {
		return numberID;
	}

	public void setNumberID(long numberID) {
		this.numberID = numberID;
	}

	public Map<String, IDSocket> getSocketMap() {
		return managerSocket;
	}

	public ByteBuffer getBufferReader() {
		return bufferReader;
	}

	public ByteBuffer getBufferWriter() {
		return bufferWriter;
	}

	public SelectionKey getCurKey() {
		return curKey;
	}

	public void setCurKey(SelectionKey curKey) {
		this.curKey = curKey;
	}

	public SocketChannel getCurSocket() {
		return curSocket;
	}

	public void setCurSocket(SocketChannel curSocket) {
		this.curSocket = curSocket;
	}

}
