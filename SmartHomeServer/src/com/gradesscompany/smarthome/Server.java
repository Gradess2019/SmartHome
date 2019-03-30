package com.gradesscompany.smarthome;

import smarthome.Commands.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Server implements SocketHandler {
	private final int PORT = 2546;
	private ServerThread serverThread;

	public Server() {
		serverThread = new ServerThread(PORT, this);
	}

	public void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
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
