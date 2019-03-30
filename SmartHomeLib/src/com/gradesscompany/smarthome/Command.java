package com.gradesscompany.smarthome;

import java.io.Serializable;

public abstract class Command implements Serializable {

	public abstract void execute();

}
