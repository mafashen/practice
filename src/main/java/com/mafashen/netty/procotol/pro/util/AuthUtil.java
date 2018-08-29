package com.mafashen.netty.procotol.pro.util;

import java.util.HashSet;
import java.util.Set;

public class AuthUtil {

	private static Set<String> reqIpWhiteSet ;

	static {
		reqIpWhiteSet = new HashSet<>();
		reqIpWhiteSet.add("127.0.0.1");
		reqIpWhiteSet.add("192.168.1.104");
	}

	public static boolean allow(String ip){
		return reqIpWhiteSet.contains(ip);
	}
}
