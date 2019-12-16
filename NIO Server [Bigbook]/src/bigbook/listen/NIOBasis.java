package bigbook.listen;

public interface NIOBasis {
	
	public void start() throws Exception;
	
	public void stop();
	
	public void restart();
	
	public void close();
}
