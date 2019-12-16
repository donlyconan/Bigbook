package bigbook.transfer;

import java.awt.BufferCapabilities.FlipContents;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

import bigbook.transfer.buffer.MessageBuffer;

public class Calculator {
	private static Instrumentation instrum;

	public static void premain(final String args, final Instrumentation inst) {
		instrum = inst;
	}

	public static long getObjectSize(final Object obj) {
		return instrum.getObjectSize(obj);
	}
	
	
	public static void main(String[] args) throws IOException {
		MessageBuffer message = MessageBuffer.allocate(10);
		
		byte[] b = {1,3,4};
		
		System.out.println(message);
		message.put(b);
		System.out.println(message);
		message.put(b);
		System.out.println(message);
//		message.flip();
		System.out.println(message);
		
		byte[] c = {1,8,2,8,1,2,8};
		message.put(c,5,c.length);
		System.out.println(message);
		message.flip();
		message.put(c,1,c.length);
		System.out.println(message);
//		
//		System.out.println("remain" +  message.remaining());
//		message.reset();
//		System.out.println(message);
//		
//		
//		byte[] z = new byte[5];
//		message.get(z,3);
//		System.out.println("Z = 6: " + Arrays.toString(z));
////		message.get(z);
//		
		
		while(message.hasNext()) {
			System.out.println(Arrays.toString(message.next()));
		}
	}
}
