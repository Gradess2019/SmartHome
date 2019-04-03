package com.gradesscompany.smarthouse.main;

import com.gradesscompany.smarthouse.main.Server.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController implements Logger {

	@FXML
	private TextArea logArea;
	private Server server;

	public void onStartButtonClicked() {
		if (server == null || server.isInterrupted()) {
			server = new Server(this);
		}

		server.start();
	}

	public void onStopButtonClicked() {
		if (server != null) {
			server.interrupt();
		}
	}

	@Override
	public void putLog(String TAG, String MESSAGE) {
		Platform.runLater(() -> logArea.appendText(TAG + MESSAGE + "\n"));
	}
}
