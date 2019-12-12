package bigbook.listen.running;

import java.io.IOException;
import java.nio.ByteBuffer;

import bigbook.Platform.Platform;
import bigbook.listen.action.IDSocket;
import bigbook.reprement.Account;
import bigbook.transfer.DataTransfer;

public class ServerResponse implements Platform {
	private IDSocket socket;

	public ServerResponse(IDSocket socket) {
		this.socket = socket;
	}

	public void handle(Respone key, DataTransfer data) throws IOException, ClassNotFoundException {

		switch (key) {
		case RPxLogin:
			data = new DataTransfer(Respone.RPxLogin, Account.Status.ACCxLOGIN_SUCCESS);
			data.setCode(Respone.RPxLogin);
			socket.write(data.toByteBuffer());
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

	public IDSocket getSocket() {
		return socket;
	}

	public void setSocket(IDSocket socket) {
		this.socket = socket;
	}

}
