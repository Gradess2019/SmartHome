package smarthome.Commands;

import java.io.Serializable;

public abstract class Command implements Serializable {

	public abstract void execute();

}
