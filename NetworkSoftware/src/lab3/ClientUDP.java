package lab3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class ClientUDP {
	private static final int PORT = 9999;
	private static final String IP_ADDR = "192.168.1.2";

	public static void main(String[] args) {

		try {
			DatagramChannel udpclient = DatagramChannel.open();
			udpclient.connect(new InetSocketAddress(IP_ADDR, PORT));

			// подготовка текста
			ByteBuffer bufferOut = ByteBuffer.allocate(1024);
			String str = "Udp messenges" + udpclient.getLocalAddress().toString() + " Udp any long text!";
			bufferOut = Charset.forName("UTF-8").encode(str);

			// отправка
			udpclient.write(bufferOut);
			System.out.println("OUT: " + new String(bufferOut.array()));

			// прием
			ByteBuffer bufferIn = ByteBuffer.allocate(1024);
			udpclient.receive(bufferIn);
			System.out.println("IN:" + new String(bufferIn.array()));

			udpclient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("First start server:" + e.getLocalizedMessage());
		}
	}
}
