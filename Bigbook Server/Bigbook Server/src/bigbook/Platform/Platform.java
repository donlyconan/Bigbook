package bigbook.Platform;

import java.util.Hashtable;

import bigbook.listen.running.SocketRunning;
import bigbook.reprement.group.Group;

//Nền tảng ứng dụng
public interface Platform {
	//Dữ liệu chia sẻ
	public static final Hashtable<String, Object> DATA_SHARE = new Hashtable<String, Object>();
	public static final Hashtable<String, SocketRunning> USERS = new Hashtable<String, SocketRunning>();
	public static final Hashtable<String, Group> GROUPS = new Hashtable<String, Group>();
	
	
	public static enum Command {
		//Yêu cầu
		RPxLogin , 
		RPxLogout,
		RPxSendMessage,
		RPxSendImage, 
		RPxSendingFile, 
		RPxFinishFile, 
		RPxStatusVoiceChat,
		RPxStatusFaceTime,
		
		//Phản hồi
		RQxLogin, 
		RQxLogout,
		RQxSendMessage,
		RQxSendImage,
		RQxSendFile,
		RQxConnectVoiceChat,
		RQxFaceTime,
	}
	
	public static enum Noti{
		NTxAccountNotExist,
		NTxAccOrPasswordIncorrect,
		NTxLoginIncorrect, 
		NTxLoginSuccess
	}
	
	public static enum Error
	{
		//Lỗi nghiêm trọng
		 ER0x0001, ER0x0002,ER0x0003,ER0x0004, ER0x1001, ER0x1002, ER0x1003,ER0x1004
	}

	public static Thread start(Runnable run){
		Thread thread = new Thread(run);
		thread.start();
		return thread;
	}
}
