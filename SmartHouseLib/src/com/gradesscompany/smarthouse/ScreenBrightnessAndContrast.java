package com.gradesscompany.smarthouse;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ScreenBrightnessAndContrast extends Command {

	private int brightness;
	private int contrast;
	private final String EXE_FILE_NAME = "com/gradesscompany/smarthouse/cpp/ScreenManager.exe";

	@SuppressWarnings("WeakerAccess")
	public ScreenBrightnessAndContrast(int brightness, int contrast) {
		this.brightness = brightness;
		this.contrast = contrast;
	}

	@Override
	public void execute() {
		try {
			final String command = getScreenManagerPath() + " " + brightness + " " + contrast;

			ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
			processBuilder.start();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	private String getScreenManagerPath() throws IOException, URISyntaxException {
		URI screenManagerURI = getFileURI();
		return screenManagerURI.getPath().replaceFirst("/", "");
	}

	private URI getFileURI() throws IOException, URISyntaxException {
		final URI jarURI = getJarURI();
		final File location = new File(jarURI);
		final URI fileURI;

		if (location.isDirectory()) {
			fileURI = URI.create(jarURI.toString() + EXE_FILE_NAME);
		} else {
			ZipFile zipFile = new ZipFile(location);
			fileURI = extract(zipFile);
		}

		return fileURI;
	}

	private URI getJarURI() throws URISyntaxException {
		return getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
	}

	private URI extract(ZipFile zipFile) throws IOException {
		final String filePrefix = EXE_FILE_NAME.replaceFirst("\\..*", "");
		final String fileSuffix = ".exe";
		final File tempFile = File.createTempFile(filePrefix, fileSuffix);
		tempFile.deleteOnExit();

		ZipEntry entry = zipFile.getEntry(EXE_FILE_NAME);
		if (entry == null) {
			throw new FileNotFoundException("cannot find file: " + EXE_FILE_NAME + " in archive: " + zipFile.getName());
		}

		final InputStream zipStream = zipFile.getInputStream(entry);
		OutputStream fileStream = new FileOutputStream(tempFile);

		final byte[] buf = new byte[1024];
		int data;
		while ((data = zipStream.read(buf)) != -1) {
			fileStream.write(buf, 0, data);
		}

		zipStream.close();
		fileStream.close();

		return tempFile.toURI();
	}
}
