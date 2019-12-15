package bigbook.listen.running;

import java.io.IOException;

import bigbook.Platform.Platform;
import bigbook.listen.action.NIOSocketChannelID;
import bigbook.reprement.Account;
import bigbook.transfer.DataPackage;

public class ServerResponse implements Platform {
	private NIOSocketChannelID socket;
	

	public ServerResponse(NIOSocketChannelID socket) {
		this.socket = socket;
	}

	public void handle(Respone key, DataPackage data) throws IOException, ClassNotFoundException {

		switch (key) {
		case RPxLogin:
			data = new DataPackage(Respone.RPxLogin, Account.Status.ACCxLOGIN_SUCCESS);
			data.setCode(Respone.RPxLogin);
//			socket.write(data.toByteBuffer());
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

	public NIOSocketChannelID getSocket() {
		return socket;
	}

	public void setSocket(NIOSocketChannelID socket) {
		this.socket = socket;
	}

}
