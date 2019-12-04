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
		ACCxNOTEXIT, 
		LGxISCORRECT, 
		LGxLoginSuccess
	}
	
	public static enum Error
	{
		//Lỗi nghiêm trọng
		 ER0x00001, 
		 ER0x00002,
		 ER0x00003,
		 ER0x00004,
		 ER0x10001,
		 ER0x10002,
		 ER0x10003,
		 ER0x10004,
		 
		 //Lỗi có thể khắc phục
		 ER0x20001,
		 ER0x20002,
		 ER0x20003,
		 ER0x20004,
		 
		 //Lỗi nhẹ
		 ER0x30001,
		 ER0x30002,
		 ER0x30003,
		 ER0x30004,
		 
		 //Có thể bỏ qua
		 ER0x40001,
		 ER0x40002,
		 ER0x40003,
		 ER0x40004
	}

	public static Thread start(Runnable run){
		Thread thread = new Thread(run);
		thread.start();
		return thread;
	}
}
