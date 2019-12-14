package server.gui.run;

public class Threadname {
	private static volatile Thread thread;

	public static void main(String[] args) throws InterruptedException {
		thread = new Thread(()-> {
			int i = 1;
			while(true) {
				System.out.println(i++);
				try {
					Thread.sleep(500);
					synchronized (thread) {
						if(i % 10 == 0)
						{
							System.out.println("Stop!");
							thread.wait();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		try {
				
			thread.start();
			Thread.sleep(10000);
//			synchronized (thread) {
				thread.notifyAll();
//			}
			System.out.println("End");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
