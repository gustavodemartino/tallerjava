package tcp_service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TerminalServer implements Runnable {
	private static final int port = 1218;

	@Override
	public void run() {
		ServerSocket server = null;
		System.out.println("Waiting for connections");
		int connectionNum = 0;
		try {
			server = new ServerSocket(port);
			while (true) {
				Socket socket = server.accept();
				connectionNum++;
				System.out.println(
						"#" + connectionNum + " Connection started from " + socket.getInetAddress().getHostAddress());
				try {
					Thread t = new Thread(new TerminalSession(socket, connectionNum));
					t.start();
				} catch (IOException e) {
					System.out.println("#" + connectionNum + " ErrorMessage starting connection");
				}
			}
		} catch (Exception e) {
			System.out.println("Terminal Service error");
		} finally {
			try {
				server.close();
				System.out.println("Conecction closed");
			} catch (Exception e) {
				System.out.println("ErrorMessage closing connection");
			}
		}
	}
}