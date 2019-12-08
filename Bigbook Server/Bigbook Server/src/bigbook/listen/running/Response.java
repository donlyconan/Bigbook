package bigbook.listen.running;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bigbook.Platform.Platform;
import bigbook.transfer.data.Bytes;

public class Response implements Platform {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Response(ObjectOutputStream oos, ObjectInputStream ois) {
		super();
		this.oos = oos;
		this.ois = ois;
	}

	public Command handle(Bytes data) {
		switch (data.get()) {
		case RPxFinishFile:
			break;
		case RPxLogin:
			break;
		case RPxLogout:
			break;
		case RPxSendImage:
			break;
		case RPxSendMessage:
			break;
		case RPxSendingFile:
			break;
		case RPxStatusFaceTime:
			break;
		case RPxStatusVoiceChat:
		default:
			break;

		}
		return null;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

}
