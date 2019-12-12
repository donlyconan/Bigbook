package bigbook.transfer;

public interface IServer {
	
	public void start() throws Exception;
	
	public void stop();
	
	public void restart();
	
	public void close();
}
