package lab3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientTCP {
	private static final int PORT = 9999;
	private static final String IP_ADDR = "192.168.1.2";

	public static void main(String[] args) {

		try {
			SocketChannel server = SocketChannel.open();

			SocketAddress socketAddr = new InetSocketAddress(IP_ADDR, PORT);
			server.connect(socketAddr);
			System.out.println("Connect");

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			String str = "New messenges" + server.getLocalAddress() + " Hello world!";
			buffer = Charset.forName("UTF-8").encode(str);

			server.write(buffer);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(new String(buffer.array()));
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("First start server:" + e.getLocalizedMessage());
		}
	}
}