package rgr;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Serv extends Thread {
	Socket s;
	int num;

	public final static int SERVICE_PORT = 50000;

	public static void main(String[] args) {
		try {
			int i = 1;
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket Server = ssc.socket();
			InetSocketAddress isa = new InetSocketAddress(SERVICE_PORT);
			Server.bind(isa);
			Selector selector = Selector.open();
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Listening on port " + SERVICE_PORT);

			DatagramChannel dc = DatagramChannel.open();
			dc.configureBlocking(false);
			DatagramSocket Server2 = dc.socket();
			InetSocketAddress isa2 = new InetSocketAddress(SERVICE_PORT);
			Server2.bind(isa2);

			dc.register(selector, SelectionKey.OP_READ);
			System.out.println("Listening on port " + SERVICE_PORT);

			HashMap map = new HashMap();
			byte[] data = new byte[1024];

			int f = 0;

			System.out.println("Server is started");

			while (true) {
				int num = selector.select();

				if (num == 0) {
					continue;
				}

				if (num != 0) {
					Set keys = selector.selectedKeys();
					Iterator it = keys.iterator();

					while (it.hasNext()) {
						SelectionKey key = (SelectionKey) it.next();

						if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
							new Serv(i, Server.accept());
							i++;
						}

						if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
							f = 1;

							ByteBuffer bb = ByteBuffer.allocate(data.length);
							bb.asIntBuffer();
							bb.clear();
							dc.receive(bb);

							data = bb.array();
							int len = data.length;
							InetAddress addr = InetAddress.getByName(null);
							byte[] d = new byte[len - 1];
							System.arraycopy(data, 1, d, 0, len - 1);

							switch (data[0]) {
							case 'N':
								String fname = new String(d);
								FileOutputStream file = new FileOutputStream(fname);
								System.out.println("File trasfer on UDP");
								map.put(addr, file);
								break;

							case 'F':
								file = (FileOutputStream) map.get(addr);
								file.write(d);
								break;

							case 'C':
								((FileOutputStream) map.get(addr)).close();
								System.out.println("Closing...");
								map.remove(addr);
								f = 0;
								break;
							}
						}
					}
					keys.clear();
				}
			}
		} catch (Exception e) {
			System.out.println("Serv init error: " + e);
		}
	}

	public Serv(int num, Socket s) {
		this.num = num;
		this.s = s;

		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}

	public void run() {
		try {
			InputStream is = s.getInputStream();
			FileOutputStream wfile;

			System.out.println("Connection from: " + s.getInetAddress());
			int readedBytesCount = 0;
			byte[] buf = new byte[2 * 1024];

			System.out.println("File trasfer on TCP");
			int len = is.read();

			byte[] bname = new byte[len];
			is.read(bname, 0, len);

			String name = new String(bname);
			wfile = new FileOutputStream(name);

			while ((readedBytesCount = is.read(buf)) != -1) {
				wfile.write(buf, 0, readedBytesCount);
			}

			wfile.close();
			System.out.println("Closing...");

			s.close();
		} catch (Exception e) {
			System.out.println("Server init error(2): " + e);
		}
	}
}
