package bigbook.Platform;


//Khởi chạy một đối tượng được xác định
public interface Kernel extends Platform{
	//Khoi chay 1 doi tuong
	public void start() throws Exception;
	
	//Cham dut moi hanh dong cua mot doi tuong
	public void stop();
	
	//Dong doi tuong 
	public void close() throws Exception;
}
