package com.mafashen.network.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			final int finalI = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try (Socket client = new Socket("localhost", 1314);
						 Reader reader = new InputStreamReader(client.getInputStream());
						 BufferedReader bufferedReader = new BufferedReader(reader)) {
						Thread.sleep(finalI * 1000);
						System.out.println(bufferedReader.readLine() + "  " +Thread.currentThread().getName() + System.currentTimeMillis());
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
