package sample;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {
	HashMap<String, String> hm;
	Object lock;
	Logistics l;
	Logger log;
	public Producer(HashMap<String, String> hm, Logistics l) {
		this.hm = hm;
		this.l = l;
		this.lock = l.getLock();
		this.log = l.get_log();
		log.setLevel(Level.FINER);
		System.out.println(log.getParent().toString());
	}

	private void insertData(String key, String value) {
		hm.put(key, value);
	}

	@Override
	public void run() {
		System.out.println("Producer Started");
		int i = 0;
		while (true) {
			synchronized (lock) {
				if (l.isHasData() == true) {
					try {
						log.log(Level.FINE,"Data not read by consumer hence waiting");
						lock.wait();
					} catch (InterruptedException ie) {
						System.out.println("Lock interrupted in Producer");
					} catch (IllegalMonitorStateException ims) {
						System.out.println("Lock unavailable for Producer");
					}
				} // isHasData
				insertData("stringKey" + Integer.toString(i), "stringValue" + Integer.toString(i));
				i++;
				try {
					Thread.sleep(1000); // simulate work
				} catch (InterruptedException ie) {
					System.out.println("Producer Thread Interrupted");
				}
				l.setHasData(true);
				lock.notifyAll();
			} // end synchronize
		} // end while
	} // end run
}