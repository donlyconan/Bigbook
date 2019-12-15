package bigbook.transfer;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Calculator {
	private static Instrumentation instrum;

	public static void premain(final String args, final Instrumentation inst) {
		instrum = inst;
	}

	public static long getObjectSize(final Object obj) {
		return instrum.getObjectSize(obj);
	}
	
	
	public static void main(String[] args) throws IOException {
		System.out.println(args);
		for(String a : args) System.out.println(a);
		new Calculator();
		new DataPackage(null);
	}
}
