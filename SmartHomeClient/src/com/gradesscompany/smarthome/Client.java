package com.gradesscompany.smarthome;

import smarthome.Commands.ScreenBrightnessAndContrast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.1.69", 2546);
			OutputStream socketOutputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOutputStream);
			objectOutputStream.writeObject(new ScreenBrightnessAndContrast(15, 15));
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
