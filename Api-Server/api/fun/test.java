package fun;

public class test {
	public static void main(String[] args) {
		MessageBuffer mes = MessageBuffer.allocate(10);
		System.out.println(mes);
		
		byte[] bb = {0x00,0x0f, 0x0a};
		mes.put(bb);
		
		long time = System.currentTimeMillis();
		
		bb = new byte[MessageBuffer.MB];
		mes.put(bb);
		
		System.out.println(System.currentTimeMillis()-time + " ms");
		System.out.println(mes);
		
//		System.out.println(mes);
//		mes.put(ByteBuffer.wrap(bb));
//		System.out.println(mes);
//		System.out.println(Arrays.toString(mes.buffer().array()));
	}
}
