package bigbook.Platform;


//Khởi chạy một đối tượng được xác định
public interface Kernel extends Platform {
	//Khoi chay 1 doi tuong
	public void Start() throws Exception;
	
	//Cham dut moi hanh dong cua mot doi tuong
	public void Stop() throws Exception;
	
	//Khoi dong lai tien trinh
	public void Restart() throws Exception;
	
	//Dong doi tuong 
	public void Close() throws Exception;
}
