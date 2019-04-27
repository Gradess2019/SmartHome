package com.gradesscompany.smarthouse;

import java.io.Serializable;

@SuppressWarnings("unused")
public abstract class Command implements Serializable {

	private long senderID;

	Command() {
		senderID = -1;
	}

	Command(final long senderID) {
		this.senderID = senderID;
	}

	public long getSenderID() {
		return senderID;
	}

	void setSenderID(long newID) {
		senderID = newID;
	}

	public abstract void execute();

}
