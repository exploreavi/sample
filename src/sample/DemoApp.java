package sample;

public class DemoApp {
	public static void main(String[] args) {
		Data d = new Data();
		Logistics l = new Logistics();
		
		Thread producer = new Thread(new Producer(d.getHm(), l));
		producer.start();
		Thread consumer = new Thread(new Consumer(d.getHm(),l));
		consumer.start();
		
		try {
		producer.join();
		consumer.join();
		} catch (InterruptedException ie) {
			System.out.println("Producer/Consumer Thread interrupted");
		}
	}
}
