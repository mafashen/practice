package com.mafashen.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateTimeServer {

	public static void main(String[] args) {
		try(ServerSocket server = new ServerSocket(1314)){
			while(true){
				try(Socket client = server.accept()){
					try (Writer writer = new OutputStreamWriter(client.getOutputStream())) {
						writer.write(new Date().toString() + "\r\n");
						writer.flush();
						client.close();
						System.out.println("accept one request");
					}catch (IOException e){
//					e.printStackTrace();
					}
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}

	}

}
