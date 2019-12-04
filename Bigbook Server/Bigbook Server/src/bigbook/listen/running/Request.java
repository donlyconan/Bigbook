package bigbook.listen.running;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bigbook.Platform.Platform;
import bigbook.transfer.data.Message;

public class Request implements Platform {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Request(ObjectOutputStream oos, ObjectInputStream ois) {
		super();
		this.oos = oos;
		this.ois = ois;
	}

	public Command handle(Command key, Message data) {
		switch (data.get()) {
		case RQxConnectVoiceChat:

			break;

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