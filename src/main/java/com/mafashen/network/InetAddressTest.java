package com.mafashen.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

import org.junit.Test;

public class InetAddressTest {

	@Test
	public void testInetAddress(){
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			if (localHost != null){
				System.out.println(localHost.toString() + " " + localHost.getHostAddress());
			}

			InetAddress baidu = InetAddress.getByName("www.baidu.com");
			if (baidu != null){
				System.out.println(baidu.toString() + " reachable " + baidu.isReachable(3000));
			}

			SecurityManager securityManager = new SecurityManager();
			securityManager.checkConnect("www.baidu.com" , -1);

			InetAddress[] taobaos = InetAddress.getAllByName("www.taobao.com");
			if (taobaos != null){
				System.out.println(Arrays.asList(taobaos));
			}
			InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
			if (loopbackAddress != null){
				System.out.println(loopbackAddress);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNetworkInterface(){
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while(networkInterfaces.hasMoreElements()){
				System.out.println(networkInterfaces.nextElement().getDisplayName());
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
