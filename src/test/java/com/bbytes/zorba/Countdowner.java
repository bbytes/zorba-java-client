package com.bbytes.zorba;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Dhanush Gopinath
 * 
 */
public class Countdowner {

	public void testWaitNotify() throws Exception {
		Thread spwn = new Thread() {

			@Override
			public void run() {
				final CountDownLatch latch = new CountDownLatch(1); // just one
				// time
				Thread t = new Thread() {
					public void run() {
						// no lock to acquire!
						System.out.println("Going to count down...");
						try {
							Thread.sleep(120000);
							System.out.println("I will not be called...");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						latch.countDown();
					}
				};

				t.start(); // start her up and let her wait()
				System.out.println("Going to await...");
				try {
					if(!latch.await(50000, TimeUnit.MILLISECONDS)){
						t.interrupt();
					}
					
					System.out.println("time out done...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Done waiting!");

			}
		};
		spwn.start();

	}

	public static void main(String args[]) throws Exception {
		Countdowner dn = new Countdowner();
		dn.testWaitNotify();
		dn.testWaitNotify();
	}

}
