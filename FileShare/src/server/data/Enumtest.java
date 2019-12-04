package server.data;

public class Enumtest {
	enum test{M,S,T};
	
	public static void main(String[] args) {
		String a = "T";
		
		switch (test.valueOf(a)) {
		case M:
			
			break;

		default:
			break;
		}
	}
}
