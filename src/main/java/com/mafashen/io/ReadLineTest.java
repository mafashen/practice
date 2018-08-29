package com.mafashen.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ReadLineTest {
	static boolean flag = true;

	public static void main(String[] args) throws InterruptedException {
		File file = new File("/Users/mafashen/Documents", "readLine.txt");
		new WriteThread(file).start();
		Thread.sleep(100);
		new ReadThread(file).start();
	}

	static class ReadThread extends Thread{
		File file = null;

		ReadThread(File file){
			this.file = file;
			this.setName("read-thread");
		}

		@Override
		public void run() {
			try {
				String line = null;
				BufferedReader reader = new BufferedReader(new FileReader(file));
				while((line = reader.readLine()) != null){
					System.out.println(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class WriteThread extends Thread{
		File file = null;

		WriteThread(File file){
			this.file = file;
			this.setName("write-thread");
		}

		@Override
		public void run() {
			try {
				if (file.exists()){
					file.delete();
				}else{
					file.createNewFile();
				}

				FileOutputStream fos = new FileOutputStream(file);
				while(true && flag){
					fos.write(121);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
