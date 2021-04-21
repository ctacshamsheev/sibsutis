package lab3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Сетевое программное обеспечение Павский К.В. 4 семестр. Мультипротокольный
 * сервер. Задание: Написать программу (на языке C/C++/Java, консольное
 * приложение), реализующую работу сервера по двум протоколам TCP и UDP с
 * помощью системного вызова select.
 */
public class Server {

	private static final int PORT = 9999;

	public static void main(String[] args) {
		try {
			ServerSocketChannel tcpserver = ServerSocketChannel.open();
			tcpserver.configureBlocking(false);
			tcpserver.socket().bind(new InetSocketAddress(PORT));
			System.out.println("TCP Server runing.....");

			DatagramChannel udpserver = DatagramChannel.open();
			udpserver.socket().bind(new InetSocketAddress(PORT));
			System.out.println("UDP Server runing.....");

			tcpserver.configureBlocking(false);
			udpserver.configureBlocking(false);

			Selector selector = Selector.open();

			tcpserver.register(selector, SelectionKey.OP_ACCEPT);
			udpserver.register(selector, SelectionKey.OP_READ);

			for (;;) {

				try {
					selector.select();
					Set keys = selector.selectedKeys();
					for (java.util.Iterator i = keys.iterator(); i.hasNext();) {
						SelectionKey key = (SelectionKey) i.next();
						i.remove();
						Channel c = (Channel) key.channel();
						new Thread(new Runnable() {

							public synchronized void printMsg(String outStr) {
								System.out.println(outStr);

							}

							@Override
							public void run() {
								try {
									if (key.isAcceptable() && c == tcpserver) {
										SocketChannel client = tcpserver.accept();
										if (client != null) {

											ByteBuffer bufferRead = ByteBuffer.allocate(1024);
											String outStr = "TCP Connection Set:" + client.getRemoteAddress() + "-->";
											// System.out.print("TCP Connection Set:" + client.getRemoteAddress() +
											// "-->");
											while (client.read(bufferRead) > 0) {
												String str = new String(bufferRead.array());
												// System.out.println(str);
												outStr += str;
											}
											printMsg(outStr);
											client.close();
										}
									} else if (key.isReadable() && c == udpserver) {

										ByteBuffer bufferRead = ByteBuffer.allocate(1024);
										SocketAddress clientAddress = udpserver.receive(bufferRead);
										if (clientAddress != null) {
											String outStr = "UDP Connection Set:" + clientAddress.toString() + "-->";
											// System.out.print("UDP Connection Set:" + clientAddress.toString() +
											// "-->");
											String str = new String(bufferRead.array());
											// System.out.println(str);
											outStr += str;
											printMsg(outStr);

											ByteBuffer bufferWrite = ByteBuffer.allocate(1024);
											bufferWrite = Charset.forName("UTF-8")
													.encode(new String("Answer Udp^" + clientAddress.toString() + str));
											udpserver.send(bufferWrite, clientAddress);
										}
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.print("Close all open aplication of server: " + e.getLocalizedMessage());
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
