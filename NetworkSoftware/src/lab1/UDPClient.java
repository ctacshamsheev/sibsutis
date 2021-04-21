package lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPClient {

	public final static int SERVICE_PORT = 50000;

	public static void main(String[] args) throws IOException {

		try {

			DatagramSocket clientDatagramSocket = new DatagramSocket();
			clientDatagramSocket.setSoTimeout(1000);
			InetAddress IPAddress = InetAddress.getByName("localhost");

			// Создайте соответствующие буферы
			byte[] sendingDataBuffer = new byte[1024];
			byte[] receivingDataBuffer = new byte[1024];

			String[] msg = { "Всем зимою холодно,", "В дом бегут скорее.", "Остаётся солнышко.", "Кто его согреет?",
					"Раньше согревало,", "Были солнцу рады.", "А теперь любуются", "Танцем снегопада.",
					"Нарисую солнышко", "У себя в альбоме,", "И как будто станет", "Посветлее в доме." };
			for (int i = 0; i < msg.length; i++) {
				msg[i] = clientDatagramSocket.getLocalPort() + " " + msg[i] + "\0";
			}
			;

			int i = 0;
			int resend = 0;
			while (i < msg.length && resend < 5) {

				sendingDataBuffer = (i + " " + msg[i].hashCode() + " " + msg[i]).getBytes();

				// Создайте UDP-пакет
				DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length,
						IPAddress, SERVICE_PORT);
				// Отправьте UDP-пакет серверу

				clientDatagramSocket.send(sendingPacket);

				// Получите ответ от сервера, т.е. предложение из заглавных букв
				DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);

				try {
					clientDatagramSocket.receive(receivingPacket);
					// Выведите на экране полученные данные
					String receivedData = new String(receivingPacket.getData());

					System.out.println("Sent from the server: " + receivedData);
					i++; // переходим к следующему сообщению
					resend = 0;

				} catch (SocketTimeoutException e1) {
					// TODO Auto-generated catch block
					System.out.println("Timed out after 1 seconds");

					resend++;
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (resend == 5) {
				System.out.println("Server is not available");
			}
			// Закройте соединение с сервером через сокет
			clientDatagramSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}