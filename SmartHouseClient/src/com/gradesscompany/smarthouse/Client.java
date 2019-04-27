package com.gradesscompany.smarthouse;

import java.io.*;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.1.69", 2546);

			final OutputStream socketOutputStream = socket.getOutputStream();
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOutputStream);
			objectOutputStream.writeObject(new AddingNewDevice("Stepan PC"));
			objectOutputStream.close();

			final InputStream socketInputStream = socket.getInputStream();
			final ObjectInputStream socketObjectInputStream = new ObjectInputStream(socketInputStream);

			long id = socketObjectInputStream.readLong();

			socketObjectInputStream.close();

			System.out.println("ID = " + id);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
