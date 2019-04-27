package com.gradesscompany.smarthouse.main.Server;

import com.gradesscompany.smarthouse.AddingNewDevice;
import com.gradesscompany.smarthouse.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server extends Thread implements SocketHandler {

	public static final int ERROR_ACCESS_DENIED = 1;

	@SuppressWarnings("FieldCanBeLocal")
	private final int PORT = 2546;
	private static Server currentServer;
	private final Authenticator authenticator;
	private ServerSocket serverSocket;
	private boolean isInterrupted;

	private Server() {
		this.authenticator = new Authenticator();

		isInterrupted = false;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDaemon(true);
	}

	public final Authenticator getAuthenticator() {
		return authenticator;
	}

	public static Server createNewServer() {
		currentServer = new Server();
		return currentServer;
	}

	public static Server getCurrentServer() {
		return currentServer;
	}

	@Override
	public synchronized void start() {
		if (!isAlive() && !isInterrupted()) {
			super.start();
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

			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				new Thread(() -> handleSocket(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void handleSocket(Socket socket) {
		try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

			Command command = (Command) objectInputStream.readObject();

			if (isSafeCommand(command)) {
				command.execute();
			} else if (command instanceof AddingNewDevice) {
				command.execute();
				sendData(socket, command.getSenderID());
			} else {
				sendError(socket);
			}

		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isSafeCommand(Command command) throws SQLException {
		return command != null && authenticator.hasID(command.getSenderID());
	}

	private void sendData(final Socket socket, final long data) throws IOException {
		OutputStream socketOutputStream = socket.getOutputStream();
		ObjectOutputStream socketObjectOutputStream = new ObjectOutputStream(socketOutputStream);
		socketObjectOutputStream.writeLong(data);
		socketObjectOutputStream.flush();
	}

	private void sendError(Socket socket) throws IOException {
		sendData(socket, ERROR_ACCESS_DENIED);
	}
}
