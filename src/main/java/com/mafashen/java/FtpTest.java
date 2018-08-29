package com.mafashen.java;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

public class FtpTest {

	public static void main(String[] args) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect("39.108.228.121");
			boolean loginRet = ftpClient.login("ftpuser", "123456");
			System.out.println(loginRet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
