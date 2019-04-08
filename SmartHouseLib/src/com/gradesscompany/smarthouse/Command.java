package com.gradesscompany.smarthouse;

import java.io.Serializable;

public abstract class Command implements Serializable {

	private final int senderID;

	public Command() {
		senderID = -1;
	}

	public Command(final int senderID) {
		this.senderID = senderID;
	}

	public int getsenderID() {
		return senderID;
	}

	public abstract void execute();

}
