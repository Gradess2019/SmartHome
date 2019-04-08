package com.gradesscompany.smarthouse.main.Server;

import com.gradesscompany.smarthouse.Command;
import com.gradesscompany.smarthouse.main.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread implements SocketHandler {
	private static final String TAG = "Server: ";

	@SuppressWarnings("FieldCanBeLocal")
	private final int PORT = 2546;

	private ServerSocket serverSocket;
	private Logger logger;

	private boolean isInterrupted;

	public Server(Logger logger) {
		this.logger = logger;
		isInterrupted = false;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDaemon(false);
	}

	@Override
	public synchronized void start() {
		if (!isAlive() && !isInterrupted()) {
			super.start();
			logger.putLog(TAG, "Start");
		} else {
			logger.putLog(TAG, "Server is alive");
		}
	}

	@Override
	public boolean isInterrupted() {
		return isInterrupted;
	}

	@Override
	public void interrupt() {
		if (isAlive()) {
			super.interrupt();
			isInterrupted = true;
			logger.putLog(TAG, "Stop");
			try {
				serverSocket.close();
			} catch (IOException e) {
				logger.putLog(TAG, "Exception: " + e.getMessage());
			}
		} else {
			logger.putLog(TAG, "Server is stopped");
		}
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				handleSocket(socket);
			} catch (IOException e) {
				logger.putLog(TAG, "Close server socket");
			}
		}
	}

	@Override
	public void handleSocket(Socket socket) {
		try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
			Command command = (Command) objectInputStream.readObject();
			if (command != null) {
				command.execute();
				logger.putLog(TAG, "Run client command");
			}
		} catch (IOException | ClassNotFoundException e) {
			logger.putLog(TAG, "Exception: " + e.getMessage());
		}
	}
}
