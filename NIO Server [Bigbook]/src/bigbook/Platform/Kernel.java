package bigbook.Platform;

//Khởi chạy một đối tượng được xác định
public interface Kernel extends Platform {	
	//Khoi chay 1 doi tuong
	public void start() throws Exception;
	
	//Cham dut moi hanh dong cua mot doi tuong
	public void xstop() throws Exception;
	
	//Khoi dong lai tien trinh
	public void restart() throws Exception;
	
	//Dong doi tuong 
	public void close() throws Exception;
}
