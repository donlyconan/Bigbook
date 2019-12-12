package bigbook.listen.action;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class IDSocket {
	private String idSocket;
	private SocketChannel socketChannel;
	public boolean connect;

	public IDSocket() {
		idSocket = "unknow";
		socketChannel = null;
		connect = true;
	}

	public IDSocket(String idSocket, SocketChannel socketChannel) {
		super();
		this.idSocket = idSocket;
		this.socketChannel = socketChannel;
		connect = true;
	}

	public IDSocket(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
		connect = true;
	}

	public int read(ByteBuffer byteBuffer) throws IOException {
		return socketChannel.read(byteBuffer);
	}

	public int write(ByteBuffer byteBuffer) throws IOException {
		return socketChannel.write(byteBuffer);
	}

	public String Id() {
		return idSocket;
	}

	public SocketChannel socket() {
		return socketChannel;
	}

	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}

}
