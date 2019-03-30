package com.gradesscompany.smarthome.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private SocketHandler socketHandler;

	ServerThread(ServerSocket serverSocket, SocketHandler socketHandler) {
		this.serverSocket = serverSocket;
		this.socketHandler = socketHandler;
		setDaemon(false);
		start();
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
