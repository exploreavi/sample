package org.adiwakar.app;

import org.adiwakar.app.data.Data;
import org.adiwakar.app.util.Logistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoApp {
	public static void main(String[] args) {
		Data d = new Data();
		Logistics l = new Logistics();
	    final Logger logger = LogManager.getLogger(DemoApp.class);

		
		Thread producer = new Thread(new Producer(d.getHm(), l));
		producer.start();
		Thread consumer = new Thread(new Consumer(d.getHm(),l));
		consumer.start();
		logger.debug("Wait");
		try {
		producer.join();
		consumer.join();
		} catch (InterruptedException ie) {
			logger.warn("Producer/Consumer Thread interrupted");
		}
	}
}
