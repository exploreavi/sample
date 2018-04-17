package org.adiwakar.app;

import java.util.HashMap;

import org.adiwakar.app.util.Logistics;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
	HashMap<String, String> hm;
	Object lock;
	Logistics l;
	Logger logger;
	
	public Producer(HashMap<String, String> hm, Logistics l) {
		this.hm = hm;
		this.l = l;
		this.lock = l.getLock();
		this.logger = l.getLogger();
	}

	private void insertData(String key, String value) {
		hm.put(key, value);
	}

	@Override
	public void run() {
		logger.info("Producer Started");
		int i = 0;
		while (true) {
			synchronized (lock) {
				if (l.isHasData() == true) {
					try {
						logger.debug("Data not read by consumer hence waiting");
						lock.wait();
					} catch (InterruptedException ie) {
						logger.warn("Lock interrupted in Producer");
					} catch (IllegalMonitorStateException ims) {
						logger.warn("Lock unavailable for Producer");
					}
				} // isHasData
				insertData("stringKey" + Integer.toString(i), "stringValue" + Integer.toString(i));
				i++;
				try {
					Thread.sleep(1000); // simulate work
				} catch (InterruptedException ie) {
					logger.warn("Producer Thread Interrupted");
				}
				l.setHasData(true);
				lock.notifyAll();
			} // end synchronize
		} // end while
	} // end run
}