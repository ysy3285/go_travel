package com.proj2;

public class MyThread1 extends Thread {

	private int num;
	private String tck;

	public MyThread1(int num, String tck) {
		this.num = num;
		this.tck = tck;
	}

	@Override
	public void run() {

		System.out.println(tck);

		try {

			sleep(500);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
