package com.gradesscompany.smarthouse;

import com.gradesscompany.smarthouse.main.Server.Server;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Optional;

public class AddingNewDevice extends Command {

	private String deviceName;

	AddingNewDevice(String deviceName) {
		this.deviceName = deviceName;
	}

	@Override
	public void execute() {
		Platform.runLater(this::showDialog);
	}

	private void showDialog() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("New Device");
		alert.setHeaderText("New device request: " + deviceName);

		handleInput(alert.showAndWait());
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
}
