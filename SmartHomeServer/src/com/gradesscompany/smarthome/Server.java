package com.gradesscompany.smarthome;

import smarthome.Commands.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements SocketHandler {
	public static final int PORT = 2546;
	private ServerThread serverThread;

	public static void main(String[] args) {
		Server server = new Server();
	}

	public Server() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(PORT);
			serverThread = new ServerThread(serverSocket, this);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

	public void stop() {
		if (!serverThread.isInterrupted()) {
			serverThread.interrupt();
		}
	}

	@Override
	public void handleSocket(Socket socket) {
		try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
			Command command = (Command) objectInputStream.readObject();
			if (command != null) {
				command.execute();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
