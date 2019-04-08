package com.gradesscompany.smarthouse.main.Server;

import java.util.ArrayList;

public class Authentificator {

	private ArrayList<Integer> authorizedDevices;

	public Authentificator() {
		loadDatabaseDevices();
	}

	private void loadDatabaseDevices() {
		/* TODO add loading from the database */
	}

	public void addToAuthorizedDevices(int senderID) {
		authorizedDevices.add(senderID);
	}

	public void removeFromAuthorizedDevices(int senderID) {
		authorizedDevices.remove(new Integer(senderID));
	}

	public boolean hasID(final int senderID) {
		return authorizedDevices.contains(senderID);
	}
}
