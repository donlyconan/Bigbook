package Parent;

public interface Platform
{
	public static enum CMD
	{
		MSxMessage,MSxFilexOpen, MSxFilexSending, MSxFilexClose,
		MSxAcceptxConnect, MSxConnectxSuccess, MSxGetxIP,
		MSxSendxConnect, MSxRequestxItem, MSxResponexItem, MSxOpenLoad,
		MSxOpenGiveFile, MSxGivingFile, MSxGiveClose
	}
	
	public static void start(Runnable run)
	{
		new Thread(run).start();
	}
	
}
