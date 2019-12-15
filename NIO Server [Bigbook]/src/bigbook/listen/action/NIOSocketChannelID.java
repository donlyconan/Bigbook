package bigbook.listen.action;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NIOSocketChannelID {
	private String idSocket;
	private SelectionKey selectrioKey;
	public boolean connect;

	public NIOSocketChannelID(SelectionKey selectrioKey) {
		super();
		this.selectrioKey = selectrioKey;
	}

	public NIOSocketChannelID(String idSocket, SelectionKey selectrioKey) {
		super();
		this.idSocket = idSocket;
		this.selectrioKey = selectrioKey;
	}

	public int read(ByteBuffer byteBuffer) throws IOException {
		int read = channel().read(byteBuffer);

		if (read == -1)
			connect = false;

		return read;
	}

	public int write(ByteBuffer byteBuffer) throws IOException {
		return this.channel().write(byteBuffer);
	}

	public void close() throws IOException {
		selectrioKey.cancel();
		selectrioKey.channel().close();
	}

	public String Id() {
		return idSocket;
	}

	public SocketChannel channel() {
		return (SocketChannel) selectrioKey.channel();
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
		return selectrioKey;
	}

	public void setSelectrioKey(SelectionKey selectrioKey) {
		this.selectrioKey = selectrioKey;
	}

}
