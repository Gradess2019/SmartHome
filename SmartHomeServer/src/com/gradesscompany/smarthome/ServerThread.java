package com.gradesscompany.smarthome;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private SocketHandler socketHandler;

	ServerThread(int port, SocketHandler socketHandler) {
		try {
			this.serverSocket = new ServerSocket(port);
			this.socketHandler = socketHandler;
			setDaemon(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				socketHandler.handleSocket(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
