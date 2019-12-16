package bigbook.transfer;

public interface NIOBasis {
	
	public void start() throws Exception;
	
	public void stop();
	
	public void restart();
	
	public void close();
}
