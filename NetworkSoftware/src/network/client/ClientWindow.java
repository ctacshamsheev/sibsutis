package network.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

	private static final int HEIGHT = 400;
	private static final int WIDHT = 600;

	private static final int PORT = 8888;
	private static final String IP_ADDR = "192.168.1.2";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ClientWindow();
			}
		});
	}

	private final JTextArea log = new JTextArea("");
	private final JTextField fieldInput = new JTextField("");

	private TCPConnection connection;

	private ClientWindow() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(WIDHT, HEIGHT);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		log.setEditable(false);
		log.setLineWrap(true);
		add(log, BorderLayout.CENTER);
		fieldInput.addActionListener(this);
		add(fieldInput, BorderLayout.SOUTH);

		setVisible(true);
		try {
			connection = new TCPConnection(this, IP_ADDR, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			printMsg("IOException" + e);
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub'
		String str = fieldInput.getText();
		if (str == null || str.equals("")) {
			return;
		} else {
			fieldInput.setText(null);
			connection.sendString(str);
		}
	}

	@Override
	public void onConnectionReady(TCPConnection tcpconnection) {
		// TODO Auto-generated method stub
		printMsg("Connected to" + tcpconnection);
	}

	@Override
	public void onReceiveString(TCPConnection tcpconnection, String value) {
		// TODO Auto-generated method stub
		printMsg(value);
	}

	@Override
	public void onDisconnect(TCPConnection tcpconnection) {
		// TODO Auto-generated method stub
		printMsg("Connection close");
	}

	@Override
	public void onException(TCPConnection tcpconnection, IOException e) {
		// TODO Auto-generated method stub
		printMsg("Exception" + e.getMessage());
	}

	private synchronized void printMsg(String msg) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				log.append(msg + "\n");
				log.setCaretPosition(log.getDocument().getLength());
			}
		});

	}
}
