package bigbook.listen.running;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import bigbook.Platform.Platform;
import bigbook.Platform.Transfer;
import bigbook.listen.action.NIOSocketChannelID;
import bigbook.reprement.Account;
import bigbook.transfer.DataPackage;
import bigbook.transfer.DataReader;
import ui.Print;
import ui.Print.Content;

public class ServerRequest implements Platform, Runnable {
	private NIOSocketChannelID IDchannel = null;
	private ServerResponse response = null;
	private DataReader dataReader = null;
	private Account account = null;
	private String content = null;
	private ByteBuffer bufferReader;
	private DataPackage data;

	public ServerRequest(NIOSocketChannelID socketID) {
		super();
		this.IDchannel = socketID;
		response = new ServerResponse(socketID);
	}
	
	public ServerRequest(NIOSocketChannelID socket, ByteBuffer bufferReader) {
		super();
		this.IDchannel = socket;
		this.bufferReader = bufferReader;
		response = new ServerResponse(socket);
	}

	@Override
	public void run() {
		try {
			data = DataPackage.valuesOf(bufferReader.array());
			
			switch (data.request()) {
			case RQxLogin:
				account = (Account) Transfer.getObject(data.data());
				Print.out(Content.ACCOUNT, account);
				response.handle(Respone.RPxLogin, data);
				break;
			case RQxMAccount:

				break;
			case RQxSMessage:
				content = (String) data.getObject();
				Print.out(Content.MESSAGE, content);
				break;
			case RQxSConnectVoiceChat:

				break;
			case RQxSFile:

				break;
			case RQxSFinishVoiceChat:

				break;
			case RQxSImage:

				break;
			case RQxLogout:
				Print.out(IDchannel.channel().getRemoteAddress() + " is quiting");
				IDchannel.close();
				break;
			default:
				break;
			}

			bufferReader.clear();
		} catch (Exception e) {
			Print.out(Content.ERROR, e.getMessage() + " code=" + (data != null ? data.getCode() : null));
			IDchannel.getSelectrioKey().interestOps(SelectionKey.OP_CONNECT);
			e.printStackTrace();
		}

	}

	public ServerResponse getResponse() {
		return response;
	}

	public void setResponse(ServerResponse response) {
		this.response = response;
	}

	public DataReader getDataReader() {
		return dataReader;
	}

	public void setDataReader(DataReader dataReader) {
		this.dataReader = dataReader;
	}

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}

}
