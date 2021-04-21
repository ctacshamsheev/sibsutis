package lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import rgr.Key;

/**
 * Сетевое программное обеспечение Павский К.В. 4 семестр. Лабораторная работа
 * №1 «Сервер на основе UDP протокола. Поддержка подтверждения получения
 * информации.»
 *
 */
public class UDPServer {

	// Серверный UDP-сокет запущен на этом порту
	public final static int SERVICE_PORT = 50000;

	public static void main(String[] args) throws IOException {

		try {
			HashMap<Key, String> states = new HashMap<Key, String>();

			DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT); // udp socet

			byte[] receivingDataBuffer = new byte[1024]; // буфер приема
			byte[] sendingDataBuffer = new byte[1024]; // буфер передачи

			DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
			System.out.println("Waiting for a client to connect...");

			// ArrayList<ClientMsg> list = new ArrayList<ClientMsg> ();

			for (int i = 0; i < 50; i++) {
				// Получите данные от клиента и сохраните их в inputPacket
				serverSocket.receive(inputPacket);
				// Выведите на экран отправленные клиентом данные
				String receivedData = new String(inputPacket.getData());

				//////
				int first = 0;
				while (receivedData.charAt(first) != ' ') {
					first++;
				}

				int second = first + 1;
				while (receivedData.charAt(second) != ' ') {
					second++;
				}
				int third = second + 1;
				while (receivedData.charAt(third) != '\0') {
					third++;
				}

				String receivedDataMsg = receivedData.substring(second + 1, third) + "\0";
				int receivedDataNum = Integer.valueOf(receivedData.substring(0, first));
				int receivedDataHashCode = Integer.valueOf(receivedData.substring(first + 1, second));

				states.put(new Key(inputPacket.getPort(), receivedDataNum), receivedDataMsg);

				if (i % 3 != 0) {

					System.out.print("Sent from the client: " + inputPacket.getAddress() + ":" + inputPacket.getPort()
							+ "  Data:{" + receivedDataMsg + "} ");
					if (receivedDataMsg.hashCode() == receivedDataHashCode) {

						sendingDataBuffer = Integer.toString(receivedDataNum).getBytes();
						// Получите IP-адрес и порт клиента
						InetAddress senderAddress = inputPacket.getAddress();
						int senderPort = inputPacket.getPort();
						// Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
						DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length,
								senderAddress, senderPort);
						// Отправьте пакет клиенту
						serverSocket.send(outputPacket);
						System.out.println("msg:" + receivedDataNum + " OK");

					} else {
						System.out.println("msg:" + receivedDataNum + " ERROR " + receivedDataMsg.hashCode() + " "
								+ receivedDataHashCode);
					}

				} else {
					System.out.println("Sent from the client: " + inputPacket.getAddress() + ":" + inputPacket.getPort()
							+ "  Data:{" + receivedDataNum + "} %3 ERROR ");
				}
			}
			// Закройте соединение сокетов
			serverSocket.close();

			TreeMap<Key, String> sortedMap = new TreeMap<>(states);
			// System.out.println(sortedMap);
			Key kpred = new Key(0, 0);
			for (Entry<Key, String> pair : sortedMap.entrySet()) {
				Key k = pair.getKey();
				if (kpred.x != k.x) {
					System.out.println(k.x);
					kpred = k;
				}
				String value = pair.getValue();
				System.out.println(value);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}