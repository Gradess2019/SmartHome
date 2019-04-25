package com.gradesscompany.smarthouse.main.Server;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Random;

public class Authenticator {

	private final URL DATABASE_CONFIG_FILE_URL =
			getClass().getClassLoader().getResource("com/gradesscompany/smarthouse/main/resources/Database.cfg");

	private String databaseURL;
	private String userName;
	private String password;

	public Authenticator() {
		loadConfiguration();
	}

	private void loadConfiguration() {
		assert DATABASE_CONFIG_FILE_URL != null;

		File configFile = new File(DATABASE_CONFIG_FILE_URL.getFile());
		try {

			BufferedReader configReader = new BufferedReader(new FileReader(configFile));

			databaseURL = configReader.readLine();
			userName = configReader.readLine();
			password = configReader.readLine();

		} catch (IOException e) {
			System.out.println("Authenticator error: " + e.getMessage());
		}
	}

	public long addToAuthorizedDevices() throws SQLException {
		PreparedStatement statement = getPreparedStatement();

		long newDeviceID = generateNewDeviceID();

		statement.executeUpdate("insert into authorizeddevices (deviceID) values (" + newDeviceID + ")");
		statement.close();

		return newDeviceID;
	}

	private PreparedStatement getPreparedStatement() throws SQLException {
		Connection connection = DriverManager.getConnection(databaseURL, userName, password);
		return connection.prepareStatement("select * from authorizeddevices");
	}

	private long generateNewDeviceID() {
		Random random = new Random(System.currentTimeMillis());
		return Math.abs(random.nextLong());
	}

	public void removeFromAuthorizedDevices(long deviceID) throws SQLException {
		PreparedStatement statement = getPreparedStatement();
		statement.executeUpdate("delete from authorizeddevices where deviceID = " + deviceID + "");
		statement.close();
	}

	public boolean hasID(final long deviceID) throws SQLException {
		PreparedStatement statement = getPreparedStatement();
		ResultSet queryResult = statement.executeQuery("select deviceID from authorizeddevices where deviceID = " + deviceID + "");

		while (queryResult.next()) {
			final long deviceIDDB = queryResult.getLong(1);
			if (deviceID == deviceIDDB) {
				statement.close();
				return true;
			}
		}
		return false;
	}
}
