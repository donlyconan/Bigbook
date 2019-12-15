package socket.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Buffer {
	private static String a;
	private static volatile long index;

	private static void print(String string) {
		System.out.println(string);
	}

	public synchronized static void Sync() throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		a = "This A";
		index = 0;
		pool.execute(() -> {
			while (true) {
				a = "This thread A:" + index++;
				print(a);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		pool.execute(() -> {
			while (true) {
				a = "This thread B:" + index++;
				print(a);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		pool.wait();
		Thread.sleep(2000);
		pool.notifyAll();
	}

	public static void main(String[] args) throws InterruptedException {

		Sync();
//		ByteBuffer buffer = ByteBuffer.allocate(1000);
//		
//		byte a = 100;
//		buffer.put(a);
//		
//		byte[]b = new byte[100];
//		for(int i = 0; i < b.length; i++)
//			b[i] = (byte) i;
//		
//		buffer.put(b);
//		System.out.println(buffer);
//		
//		for(byte c : buffer.array()) System.out.print(c + "  ");
//		
//		System.out.println("\n"+buffer);
//		buffer.flip();
//		System.out.println("\n"+buffer);
//		b = new byte[50];
// 		buffer.get(b);
// 		buffer.flip();
// 		
// 		System.out.println(buffer);
// 		for(byte c : b) System.out.print(c + "  ");
// 		System.out.println();
// 		for(byte c : buffer.array()) System.out.print(c + "  ");
	}

}
