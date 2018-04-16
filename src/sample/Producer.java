package sample;

import java.util.HashMap;

public class Producer implements Runnable {
	HashMap<String, String> hm;
	Object lock;
	public Producer(HashMap<String, String> hm, Object lock) {
		this.hm = hm;
		this.lock = lock;
	}
	
	private void insertData(String key, String value) {
		hm.put(key, value);
	}

	@Override
	public void run() {
		System.out.println("Producer Started");
		int i = 0;
		while (true) {
			try {
				lock.wait();
			} catch (InterruptedException ie) {
				System.out.println("Lock interrupted in Producer");
			} catch (IllegalMonitorStateException ims) {
				System.out.println("Lock unavailable");
			}
			insertData("stringKey"+Integer.toString(i), "stringValue"+Integer.toString(i));
			i++;
			try {
				Thread.sleep(1000); // simulate work
			} catch (InterruptedException ie) {
				System.out.println("Producer Thread Interrupted");
			}
			lock.notifyAll();
		} // end while
	} // end run
}