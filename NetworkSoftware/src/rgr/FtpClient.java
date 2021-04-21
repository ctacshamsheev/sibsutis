package rgr;

import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

/**
 * Сетевое программное обеспечение Павский К.В. 4 семестр. Расчетно-графическое
 * задание «Разработка сетевого приложения. Клиент на базе протокола FTP в
 * пассивном режиме.» Вариант №5.
 */
public class FtpClient {

	private static final int port = 21;
	private static final String hostname = "192.168.1.2";

	private static final String user = "user";
	private static final String pass = "1";

	public static String pasvHost;
	public static int pasvPort;
	public static BufferedReader in;
	public static BufferedWriter out;

	public static boolean pasv(String str) throws IOException, InterruptedException {
		// PASV

		String text = "PASV";
		System.out.println("ftp>" + text);
		out.write(text + "\n\r");
		out.flush();
		text = in.readLine();
		System.out.println(text);
		StringTokenizer st = new StringTokenizer(text, " (,)");
		if (st.hasMoreTokens()) {
			if (st.nextToken().equals("227")) {
				st.nextToken();
				st.nextToken();
				st.nextToken();
				pasvHost = st.nextToken() + "." + st.nextToken() + "." + st.nextToken() + "." + st.nextToken();
				pasvPort = Integer.valueOf(st.nextToken()) * 256 + Integer.valueOf(st.nextToken());
				System.out.println(pasvHost + ":" + pasvPort);
				Thread pasvThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Socket pasvSocket = new Socket(pasvHost, pasvPort);
							if (str.equals("LIST") || str.equals("NLST") || str.regionMatches(0, "RETR", 0, 4)) {
								read(pasvSocket);
							} else if (str.regionMatches(0, "STOR", 0, 4)) {
								write(pasvSocket);
							}

							pasvSocket.close();
						} catch (Exception e) {
							// System.out.println("ERROR NO CONNECTION:"+pasvHost + ":" + pasvPort);
						}
					}

					private void read(Socket pasvSocket) throws IOException {
						String text = "1";
						BufferedReader in = new BufferedReader(
								new InputStreamReader(pasvSocket.getInputStream(), Charset.forName("UTF-8")));
						while (!pasvSocket.isClosed()) {
							text = in.readLine();
							if (text != null) {
								System.out.println(text);
							} else {
								return;
							}
						}
					}

					private void write(Socket pasvSocket) throws IOException {
						BufferedWriter out = new BufferedWriter(
								new OutputStreamWriter(pasvSocket.getOutputStream(), Charset.forName("UTF-8")));
						Scanner scanner = new Scanner(System.in);
						System.out.println("ENTER YOU TEXT:");
						out.write(scanner.nextLine() + "\n\r");
						out.flush();
					}
				});
				pasvThread.start();
				text = str;
				System.out.println("ftp>" + text);
				out.write(text + "\n\r");
				out.flush();

				text = in.readLine();
				System.out.println(text);
				if (text.charAt(0) == '5') {
					System.out.println("ERROR :NO CONNECTION:" + pasvHost + ":" + pasvPort);
					pasvThread.interrupt();
				} else {
					pasvThread.join();
					text = in.readLine();
					System.out.println(text);
				}
			}
			return true;
		}
		System.out.println("ERROR PASV");
		return false;
	}

	public static void main(String[] args) {
		try (Socket socket = new Socket(hostname, port)) {

			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

			Scanner scanner = new Scanner(System.in);
			String text;
			for (int i = 0; i < 3; i++) {
				text = in.readLine();
				System.out.println(text);
			}

			// Логин
			text = "USER " + user;
			System.out.println("ftp>" + text);
			out.write(text + "\n\r");
			out.flush();
			text = in.readLine();
			System.out.println(text);
			text = "PASS " + pass;
			System.out.println("ftp>" + text);
			out.write(text + "\n\r");
			out.flush();
			text = in.readLine();
			System.out.println(text);

			while (!socket.isClosed()) {
				System.out.print("ftp>");
				text = scanner.nextLine();

				if (text.equals("LIST") || text.equals("NLST") || text.regionMatches(0, "RETR", 0, 4)
						|| text.regionMatches(0, "STOR", 0, 4)) {
					pasv(text);
				} else {
					out.write(text + "\n\r");
					out.flush();
					text = in.readLine();
					System.out.println(text);
				}
			}
			socket.close();

		} catch (Exception e) {
			System.out.println("DISCONNECT");
		}
	}
}
