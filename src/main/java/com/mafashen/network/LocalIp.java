package com.mafashen.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class LocalIp {

	private static String localIp = null;

	public static String getLocalIp() throws SocketException {
		if (localIp != null) {
			return localIp;
		}
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				System.out.println(ip);
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					// netip = ip.getHostAddress();
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localIp = ip.getHostAddress();
					return localIp;
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getLocalIp());;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
