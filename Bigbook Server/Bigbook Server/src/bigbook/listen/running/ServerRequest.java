package bigbook.listen.running;

import java.nio.ByteBuffer;

import bigbook.Platform.Platform;
import bigbook.Platform.Transfer;
import bigbook.listen.action.IDSocket;
import bigbook.reprement.Account;
import bigbook.transfer.DataReader;
import bigbook.transfer.DataTransfer;
import load.resource.Print;
import load.resource.Print.Content;

public class ServerRequest implements Platform, Runnable {
	private IDSocket socketID = null;
	private ServerResponse response = null;
	private DataReader dataReader = null;
	private Account account = null;
	private String content = null;
	private ByteBuffer bufferReader;
	private DataTransfer data;
	

	public ServerRequest(IDSocket socketID) {
		super();
		this.socketID = socketID;
		response = new ServerResponse(socketID);
	}

	public ServerRequest( IDSocket socket, DataTransfer data) {
		super();
		this.socketID = socket;
		this.data = data;
		response = new ServerResponse(socket);
	}

	@Override
	public void run() {
		try {
			bufferReader = ByteBuffer.allocate(Transfer.HAFT_OF_KB);
			socketID.read(bufferReader);
			Print.out("Read data: " + bufferReader.array().length);
			data = DataTransfer.valuesOf(bufferReader.array());
			
			switch (data.request()) {
			case RQxLogin:
				account = (Account) Transfer.getObject(data.data());
				Print.out(Content.ACCOUNT, account);
				response.handle(Respone.RPxLogin, data);
				break;
			case RQxLogout:
				
				break;
			case RQxMAccount:
				
				break;
			case RQxSMessage:
				content = new String(data.data());
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
			default:
				break;
			}
			
			bufferReader.clear();
		} catch (Exception e) {
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

	public DataTransfer getData() {
		return data;
	}

	public void setData(DataTransfer data) {
		this.data = data;
	}
	
	

}
