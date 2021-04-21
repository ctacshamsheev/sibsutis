package rgr;

import java.util.ArrayList;

public class SumHex {

	public static void main(String[] args) {
		// ArrayList clientMsg=new ArrayList<String>();

		// TODO Auto-generated method stub
		byte[] sendingDataBuffer = new byte[1024];

		String msg = "Hello from UDP client sfgj ksfgn sf nklsfg sfg!";

		sendingDataBuffer = msg.getBytes();

		int hexInt = msg.hashCode();

		String msg2 = Integer.toString(hexInt);
		String msg3 = Integer.toString(3252);
		String m = msg3 + " " + msg2 + " " + msg;
		System.out.println(msg3 + " " + msg2 + " " + msg);

		int i = 0;
		while (m.charAt(i) != ' ') {
			i++;
		}
		System.out.println(i);
		System.out.println(Integer.valueOf(m.substring(0, i)));

		int j = i + 1;
		while (m.charAt(j) != ' ') {
			j++;
		}
		System.out.println(Integer.valueOf(m.substring(i + 1, j)));
		System.out.println(m.substring(j + 1, m.length()));
		System.out.println(m.substring(j + 1, m.length()).hashCode());

	}

}
