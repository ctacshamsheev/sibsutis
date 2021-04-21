package network.client;

import java.io.IOException;

public interface TCPConnectionListener {
	void onConnectionReady(TCPConnection tcpconnection);

	void onReceiveString(TCPConnection tcpconnection, String value);

	void onDisconnect(TCPConnection tcpconnection);

	void onException(TCPConnection tcpconnection, IOException e);
}
