package sample;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {
	HashMap<String, String> hm;
	Object lock;
	Logistics l;
	Logger log;
	int lastKnown = 0;

	public Consumer(HashMap<String, String> hm, Logistics l) {
		this.hm = hm;
		this.l = l;
		this.lock = l.getLock();
		this.log = l.get_log();
	}

	private void printData(String key) {
		System.out.println("Key: " + key + " Value: " + hm.get(key));
	}

	@Override
	public void run() {
		System.out.println("Consumer Started");
		while (true) {
			synchronized (lock) {
				if (l.isHasData() == false) {
					try {
						log.log(Level.FINE,"Data not produced hence waiting");
						lock.wait();
					} catch (InterruptedException ie) {
						System.out.println("Lock interrupted in Consumer");
					} catch (IllegalMonitorStateException ims) {
						System.out.println("Lock unavailable for Consumer");
					}
				}
				System.out.println("Size: " + hm.size());
				if (lastKnown < hm.size()) {
					for (int i = lastKnown; i < hm.size(); i++) {
						printData("stringKey" + Integer.toString(i));
					}
					try {
						Thread.sleep(1000); // simulate work
					} catch (InterruptedException ie) {
						System.out.println("Consumer Thread Interrupted while sleeping");
					}
					lastKnown = hm.size();
					l.setHasData(false);
				}
				lock.notifyAll();
			} // end synchronized
		} // end while
	} // end method run
}
