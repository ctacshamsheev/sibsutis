package network.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {

	private final Socket socket;
	private final BufferedReader in;
	private final BufferedWriter out;

	private final Thread rxThread;
	private final TCPConnectionListener eventListener;

	// конструктор с портом дл¤ клиента
	public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port) throws IOException {
		this(eventListener, new Socket(ipAddr, port));
	}

	//конструктор дл¤ сервера
	public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
		this.eventListener = eventListener;
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

		this.rxThread = new Thread(new Runnable() {
			//анонимный класс, поток
			@Override
			public void run() {
				try {
					eventListener.onConnectionReady(TCPConnection.this);
					while (!rxThread.isInterrupted()) {
						String msg = in.readLine(); // „тение
						eventListener.onReceiveString(TCPConnection.this, msg);
					}
				} catch (IOException e) {
					eventListener.onException(TCPConnection.this, e);
				} finally {
					eventListener.onDisconnect(TCPConnection.this); //ќтклчение
				}
			}
		});
		
		rxThread.start();
	}

	public synchronized void sendString(String value) {
		try {
			out.write(value + "\n");
			out.flush();

		} catch (IOException e) {
			// System.out.println("IOException sendString(");
			eventListener.onException(TCPConnection.this, e);
			disconnect();
		}

	}

	public synchronized void disconnect() {
		rxThread.interrupt();
		try {
			socket.close();
		} catch (IOException e) {
			// System.out.println("IOException disconnect(");
			eventListener.onException(TCPConnection.this, e);
		}
	}

	@Override
	public String toString() {
		return "[TCP" + socket.getInetAddress() + ":" + socket.getPort() + "] ";
	}

}
