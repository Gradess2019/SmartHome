package smarthome.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScreenBrightnessAndContrast extends Command {

	private int brightness;
	private int contrast;

	public ScreenBrightnessAndContrast(int brightness, int contrast) {
		this.brightness = brightness;
		this.contrast = contrast;
	}

	@Override
	public void execute() {
		String command = "cpp\\ScreenManager.exe " + brightness + " " + contrast;
		ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
		processBuilder.redirectErrorStream(true);
		try {
			Process screenBrightness = processBuilder.start();
			InputStream inputStream = screenBrightness.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
