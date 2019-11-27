package bigbook.Platform;

import java.util.Hashtable;

import bigbook.listen.Running.SocketRunning;
import bigbook.reprement.Group.Group;

//Nền tảng ứng dụng
public interface Platform {
	//Trung tâm dữ liệu
	public static final Hashtable<String, Object> DATA_SHARE = new Hashtable<String, Object>();
	public static final Hashtable<String, SocketRunning> USER_ONLINE = new Hashtable<String, SocketRunning>();
	public static final Hashtable<String, Group> GROUP_ID = new Hashtable<String, Group>();

	/*
	 * Cấu trúc lệnh:
	 * CM: Command 
	 * MS: Message
	 * Send : Action
	 * CM x Lệnh cha _ Lệnh con _ Lệnh con
	 * 
	 * Từ 1000 - 1100 dành cho đăng nhập lỗi
	 */
	public static final int CMxUSER_LOGIN = 1000;
	public static final int CMxUSER_LOGOUT = 1001;
	//Loi dang nhap
	public static final int CMxUSER_LOGIN_FAILE= 1002;
	//Dang nam trong hang cho
	public static final int CMxUSER_LOGIN_INQUEUE = 1003;
	//Dang nhap thanh cong
	public static final int CMxUSER_LOGIN_SUCCESS = 1004;
	
	//Từ 1100 - 10000 dành cho các câu lệnh
	public static final int CMxMS_SEND = 2000;
	public static final int CMxMS_RECIEVE = 20001;
	
	//9999-10.000 2 giá tri đặt biệt cho vòng lặp
	public static final int CMxEXIT = 9998;
	//Hủy dữ liệu
	public static final int CMxDISTROY = 9999;
	
	
	//Xử lý dữ liệu Message
	public static final int MSxPERSONAL = 200;
	public static final int MSxGROUP = 201;
	public static final int MSxSERVER = 202;
	public static final int MSxVOICHAT_START = 203;
	public static final int MSxVOICHAT_END = 204;
	public static final int MSxFACE_TIME_START = 205;
	public static final int MSxFACE_TIME_END = 206;
	
	//Tai khoan khong ton tai
	public static final int MSxACCOUNT_NOTEXITS = 207;
	//Tai khoan hoac mat khau khong chinh xac
	public static final int MSxUSERorPASS_NOTEXCULY = 208;
	
	public static void start(Runnable run) throws Exception {new Thread(run).start();}
}
