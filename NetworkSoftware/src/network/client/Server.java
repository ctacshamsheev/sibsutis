package network.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Сетевое программное обеспечение Павский К.В. 4 семестр. Разработка сетевого
 * приложения «Чат». Мультипоточная реализация на базе протоколов TCP, IPv4.
 */
public class Server implements TCPConnectionListener {
	public final static int SERVICE_PORT = 8888;
	private final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>(); // Список соединений

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		System.out.println("Server runing.....");

		try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {
			while (true) {
				try {
					new TCPConnection(this, serverSocket.accept());
				} catch (IOException e) {
					System.out.println("IOException...{Server()}.." + e);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void onConnectionReady(TCPConnection tcpconnection) {
		connections.add(tcpconnection);
		sendToAll("Client Connected:" + tcpconnection);
	}

	@Override
	public synchronized void onReceiveString(TCPConnection tcpconnection, String value) {
		// System.out.println("From:"+tcpconnection+value);
		sendToAll("From:" + tcpconnection + value);

	}

	@Override
	public synchronized void onDisconnect(TCPConnection tcpconnection) {
		connections.remove(tcpconnection);
		sendToAll("Client Disconnect:" + tcpconnection); ///
	}

	@Override
	public synchronized void onException(TCPConnection tcpconnection, IOException e) {
		System.out.println("Exception:" + tcpconnection + e.getMessage());
	}

	private void sendToAll(String value) {
		System.out.println(value);
		for (TCPConnection connection : connections) {
			connection.sendString(value);
		}
	}
}
