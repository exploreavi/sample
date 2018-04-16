package sample;

public class DemoApp {
	public static void main(String[] args) {
		Data d = new Data();
		Object lock = new Object();
		Thread producer = new Thread(new Producer(d.getHm(), lock));
		producer.start();
		Thread consumer = new Thread(new Consumer(d.getHm(),lock));
		consumer.start();
		
		try {
		producer.join();
		consumer.join();
		} catch (InterruptedException ie) {
			System.out.println("Producer/Consumer Thread interrupted");
		}
	}
}
