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
import ui.Print;
import ui.Print.Content;

public class NIOSocketProcessor implements Runnable, Platform {
	public static enum Mode {
		ACCEPT, READ, WRITE, CONNECT, VALID
	};

	private static final int MAX_THREADS = 2000;
	private final Map<String, NIOSocketChannelID> managerSocket = new HashMap<>();
	private final ByteBuffer bufferReader = ByteBuffer.allocate(Transfer.HAFT_OF_KB);
	private final ByteBuffer bufferWriter = ByteBuffer.allocate(Transfer.MB);

	private Queue<SocketChannel> socketQueue;
	private Selector seletor;
	private ExecutorService threadPool;
	private SelectionKey curKey;
	private SocketChannel curSocket;
	private long numberID;

	public NIOSocketProcessor(int port, Queue<SocketChannel> socketQueue) throws IOException {
		super();
		this.socketQueue = socketQueue;
		seletor = Selector.open();
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
			socket.register(seletor, SelectionKey.OP_READ, null);
			Print.out(socket.getRemoteAddress() + (socket.isRegistered() ? " is register" : " is not register"));
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void handle() throws IOException {
		seletor.selectNow();
		Set<SelectionKey> keys = seletor.selectedKeys();
		Iterator<SelectionKey> iter = keys.iterator();

		while (iter.hasNext()) {
			try {
				curKey = iter.next();
				iter.remove();
				Mode mode = getMode(curKey);

//				Print.out("Mode current of Server Processor: " + mode);

				switch (mode) {
				case ACCEPT:
					// SocketChannel chanle = socketServer.accept();
					// Print.out(chanle.getRemoteAddress());
					// chanle.configureBlocking(false);
					// chanle.register(seletor, SelectionKey.OP_READ);
					break;
				case READ:
					bufferReader.clear();
					NIOSocketChannelID IDchannel = new NIOSocketChannelID(curKey);
					IDchannel.read(bufferReader);
					Print.out(bufferReader.toString());

					if (!IDchannel.connect)
					{
						Print.out(Content.MODExCONNECT,"finish connect = " + !IDchannel.connect + " &&" + IDchannel.channel().finishConnect());
						IDchannel.close();
					}
//					ServerRequest request = new ServerRequest(IDchannel, bufferReader);
//					threadPool.execute(request);
					break;
				case WRITE:
					// To do

					break;
				case VALID:
					// To do

					break;

				case CONNECT:
					numberID--;
					curKey.cancel();
					managerSocket.remove(curKey);

					Print.out(Content.MODExCONNECT, "QUIT! " + curKey.attachment());
					close(curKey);
					break;
				}
			} catch (Exception e) {
				SocketChannel socket = (SocketChannel) curKey.channel();
				socket.close();
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
		if (key != null) {
			key.cancel();
			key.channel().close();
		}
	}

	public Queue<SocketChannel> getSocketQueue() {
		return socketQueue;
	}

	public void setSocketQueue(Queue<SocketChannel> socketQueue) {
		this.socketQueue = socketQueue;
	}

	public Selector getSeletorReader() {
		return seletor;
	}

	public void setSeletorReader(Selector seletorReader) {
		this.seletor = seletorReader;
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

	public Map<String, NIOSocketChannelID> getSocketMap() {
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
