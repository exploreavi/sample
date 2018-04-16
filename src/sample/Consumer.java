package sample;

import java.util.HashMap;

public class Consumer implements Runnable {
	HashMap<String, String> hm;
	Object lock;
	int lastKnown = -1;

	public Consumer(HashMap<String, String> hm, Object lock) {
		this.hm = hm;
		this.lock = lock;
	}

	private void printData(String key) {
		System.out.println(hm.get(key));
	}

	@Override
	public void run() {
		System.out.println("Consumer Started");
		while (true) {
			try {
				lock.wait();
			} catch (InterruptedException ie) {
				System.out.println("Lock interrupted in Consumer");
			} catch (IllegalMonitorStateException ims) {
				System.out.println("Lock unavailable");
			}
			if (lastKnown < hm.size()) {
				for (int i = lastKnown+1; i < hm.size() - lastKnown; i++) {
					printData("stringKey" + Integer.toString(i));
				}
				lastKnown = hm.size();
				try {
					Thread.sleep(1000); // simulate work
				} catch (InterruptedException ie) {
					System.out.println("Producer Thread Interrupted");
				}
				lock.notifyAll();
			} else // end if
				lock.notifyAll();
		} // end while
	} // end method run
}
