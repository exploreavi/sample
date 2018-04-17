package org.adiwakar.app.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logistics {
	Object lock = new Object();
	boolean hasData = false;
    private static final Logger logger = LogManager.getLogger("Logistics");
    
    
	public Logger getLogger() {
		return logger;
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
