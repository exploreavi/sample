package sample;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logistics {
	Object lock = new Object();
	boolean hasData = false;
	Logger _log = Logger.getLogger(this.getClass().getName(),null);
	public Logistics() {
		_log.setLevel(Level.FINER);
	}
	public Logger get_log() {
		return _log;
	}
	public boolean isHasData() {
		return hasData;
	}
	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}
	public Object getLock() {
		return lock;
	}
}
