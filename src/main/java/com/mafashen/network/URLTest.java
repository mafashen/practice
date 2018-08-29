package com.mafashen.network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class URLTest {

	@Test
	public void test(){
		testGet("pro://www.ibiblio.org/nywc/compositions.phtml?category=Piano");
		testGet("ftp://mp3:map@138.247.121.61:21000/c%3a");
		testGet("pro://root@39.108.228.121:80/index.html?username=mafashen#port");
	}

	private void testGet(String str){
		try {
			URL url = new URL(str);
			System.out.println("this url is " + url);
			System.out.println("authority is " + url.getAuthority());
			System.out.println("protocol is " + url.getProtocol());
			System.out.println("host is " + url.getHost());
			System.out.println("port is " + url.getPort());
			System.out.println("file is " + url.getFile());
			System.out.println("path is " + url.getPath());
			System.out.println("query is " + url.getQuery());
			System.out.println("use info is " + url.getUserInfo());
			System.out.println("ref is " + url.getRef());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUrlEqual() throws MalformedURLException {
		URL url1 = new URL("pro://www.ibiblio.org/");
		URL url2 = new URL("pro://www.ibiblio.org:80");
		System.out.println(url1.equals(url2));

		URL url3 = new URL("pro://www.ibiblio.org/index.procotol#p1");
		URL url4 = new URL("pro://www.ibiblio.org/index.procotol#p2");
		System.out.println("equal is " + url3.equals(url4));
		System.out.println("sameFile is " + url3.sameFile(url4));	//sameFile忽略ref部分
	}

	@Test
	public void name() throws Exception {
	}
}
