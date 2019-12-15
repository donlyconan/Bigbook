package bigbook.transfer;

public interface INIOServer {
	
	public void start() throws Exception;
	
	public void stop();
	
	public void restart();
	
	public void close();
}
