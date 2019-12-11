package bigbook.Platform;

import java.nio.channels.SocketChannel;
import java.util.Hashtable;

import bigbook.reprement.group.Group;

//Nền tảng ứng dụng
public interface Platform {
	//Dữ liệu chia sẻ
	public static final Hashtable<Attribute, Object> Share = new Hashtable<Attribute, Object>();
	public static final Hashtable<String, SocketChannel> USERS_LOGIN = new Hashtable<String, SocketChannel>();
	public static final Hashtable<String, Group> GROUPS = new Hashtable<String, Group>();
	
	
	public static enum Command {
		//Yêu cầu
		RPxMAccount,
		RPxLogin , 
		RPxLogout,
		RPxSMessage,
		RPxSImage, 
		RPxSFile, 
		RPxSConnectVoiceChat,
		RPxSFinishVoiceChat,
		
		
		//Phản hồi
		RQxMAccount,
		RQxLogin, 
		RQxLogout,
		RQxSMessage,
		RQxSImage,
		RQxSFile,
		RQxSConnectVoiceChat,
		RQxSFinishVoiceChat
	}
	
	public static enum Attribute {FXMLControl, ITextArea}
	

	public static Thread start(Runnable run){
		Thread thread = new Thread(run);
		thread.start();
		return thread;
	}
}
