package org.adiwakar.app;

import java.util.HashMap;

import org.adiwakar.app.util.Logistics;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {
	HashMap<String, String> hm;
	Object lock;
	Logistics l;
	Logger logger;
	int lastKnown = 0;

	public Consumer(HashMap<String, String> hm, Logistics l) {
		this.hm = hm;
		this.l = l;
		this.lock = l.getLock();
		this.logger = l.getLogger();
//		LogManager.
	}

	private void printData(String key) {
		logger.info("Key: " + key + " Value: " + hm.get(key));
	}

	@Override
	public void run() {
		logger.info("Consumer Started");
		while (true) {
			synchronized (lock) {
				if (l.isHasData() == false) {
					try {
						logger.debug("Data not produced hence waiting");
						lock.wait();
					} catch (InterruptedException ie) {
						logger.warn("Lock interrupted in Consumer");
					} catch (IllegalMonitorStateException ims) {
						logger.warn("Lock unavailable for Consumer");
					}
				}
				logger.debug("Size: " + hm.size());
				if (lastKnown < hm.size()) {
					for (int i = lastKnown; i < hm.size(); i++) {
						printData("stringKey" + Integer.toString(i));
					}
					try {
						Thread.sleep(1000); // simulate work
					} catch (InterruptedException ie) {
						logger.warn("Consumer Thread Interrupted while sleeping");
					}
					lastKnown = hm.size();
					l.setHasData(false);
				}
				lock.notifyAll();
			} // end synchronized
		} // end while
	} // end method run
}