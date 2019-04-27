package com.gradesscompany.smarthouse;

import com.gradesscompany.smarthouse.main.Server.Server;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

public class AddingNewDevice extends Command {

	private String deviceName;
	private CountDownLatch locker;

	AddingNewDevice(String deviceName) {
		this.deviceName = deviceName;
	}

	@Override
	public void execute() {
		locker = new CountDownLatch(1);
		Platform.runLater(this::showDialog);
		waitUserInput();
	}

	private void showDialog() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("New Device");
		alert.setHeaderText("New device request: " + deviceName);

		handleInput(alert.showAndWait());
		locker.countDown();
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private void handleInput(Optional<ButtonType> input) {
		if (input.isPresent()) {
			if (input.get() == ButtonType.OK) {
				AddDevice();
			}
		}
	}

	private void AddDevice() {
		try {
			long deviceID = Server.getCurrentServer().getAuthenticator().addToAuthorizedDevices();
			setSenderID(deviceID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void waitUserInput() {
		try {
			locker.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
