package main;

public class MainThread {

	public static void main(String[] args) {
		while (true) {
			System.out.println("adf");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
