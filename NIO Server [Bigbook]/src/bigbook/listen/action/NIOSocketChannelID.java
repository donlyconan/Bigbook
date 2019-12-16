package bigbook.listen.action;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Handle socket Write, Read, Mode select
 * 
 * @author donly
 */
public class NIOSocketChannelID {
	private String idSocket;
	private SelectionKey selectionKey;
	public boolean connect;

	public NIOSocketChannelID(SelectionKey selectrioKey) {
		super();
		this.selectionKey = selectrioKey;
		connect = true;
	}

	public NIOSocketChannelID(String idSocket, SelectionKey selectrioKey) {
		super();
		this.idSocket = idSocket;
		this.selectionKey = selectrioKey;
		connect = true;
	}

	public int read(ByteBuffer byteBuffer) throws IOException {
		int read = channel().read(byteBuffer);

		if (read == -1) {
			System.out.println(read + " bytebuffer=" + byteBuffer.toString() + " \t" + channel().getRemoteAddress());
			connect = false;
		}

		return read;
	}

	public int write(ByteBuffer byteBuffer) throws IOException {
		int write = 0;

		if (byteBuffer.limit() > byteBuffer.position() && byteBuffer.limit() != 0) {
			write = channel().write(byteBuffer);
		}
		return write;
	}

	public void flip() {
		int flipping = selectionKey.interestOps();
		if (flipping == SelectionKey.OP_READ)
			selectionKey.interestOps(SelectionKey.OP_WRITE);
		else
			selectionKey.interestOps(SelectionKey.OP_READ);
	}

	public void enableReadMode() {
		selectionKey.interestOps(SelectionKey.OP_READ);
	}

	public void enableWriteMode() {
		selectionKey.interestOps(SelectionKey.OP_WRITE);
	}

	public void enableConnectMode() {
		selectionKey.interestOps(SelectionKey.OP_CONNECT);
	}

	public void attach(Object obj) {
		selectionKey.attach(obj);
	}

	public Object attachment() {
		Object obj = selectionKey.attachment();
		selectionKey.attach(null);
		return obj;
	}

	public void close() throws IOException {
		selectionKey.cancel();
		selectionKey.channel().close();
	}

	public String Id() {
		return idSocket;
	}

	public SocketChannel channel() {
		return (SocketChannel) selectionKey.channel();
	}

	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}

	public String getIdSocket() {
		return idSocket;
	}

	public void setIdSocket(String idSocket) {
		this.idSocket = idSocket;
	}

	public SelectionKey getSelectrioKey() {
		return selectionKey;
	}

	public void setSelectrioKey(SelectionKey selectrioKey) {
		this.selectionKey = selectrioKey;
	}

}
