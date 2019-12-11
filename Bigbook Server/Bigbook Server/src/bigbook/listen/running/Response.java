package bigbook.listen.running;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import bigbook.Platform.Platform;
import bigbook.transfer.data.DataTransfer;

public class Response implements Platform {
	private SocketChannel socket;

	public Response(SocketChannel socket) {
		this.socket = socket;
	}

	public void handle(DataTransfer data) throws IOException {
		DataTransfer res = null;
		switch (data.get()) {
		case RPxLogin:
			res = new DataTransfer(Command.RPxLogin, "Login success".getBytes());
			socket.write(res.toByteBuffer());
			break;
		case RPxLogout:
			break;
		case RPxMAccount:
			break;
		case RPxSConnectVoiceChat:
			break;
		case RPxSFile:
			break;
		case RPxSFinishVoiceChat:
			break;
		case RPxSImage:
			break;
		case RPxSMessage:
			break;
		default:
			break;
		}
	}

	public SocketChannel getSocket() {
		return socket;
	}

	public void setSocket(SocketChannel socket) {
		this.socket = socket;
	}

}
