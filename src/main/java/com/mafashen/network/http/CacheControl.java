package com.mafashen.network.http;

import java.util.Date;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class CacheControl {

	private Date maxAge ;
	private Date sMaxAge;
	private boolean publicCache;
	private boolean privateCache;
	private boolean noCache;
	private boolean noStore;
	private boolean mustRevalidate ;
	private boolean proxyRevalidate;

	public CacheControl(String head){
		if (StringUtils.isBlank(head)){
			return ;
		}
		String[] values = head.split(",");

		Date now = new Date();
		for (String value : values) {
			try {
				value = value.toLowerCase().trim();
				if (value.startsWith("max-age")){
					int seconds = Integer.parseInt(value.substring(8));
					maxAge = new Date(now.getTime() + 1000 * seconds);
				}else if(value.startsWith("s-maxage")){
					int seconds = Integer.parseInt(value.substring(8));
					sMaxAge = new Date(now.getTime() + 1000 * seconds);
				}else if(value.startsWith("no-cache")){
					noCache = true;
				}else if(value.startsWith("no-store")){
					noStore = true;
				}else if(value.startsWith("public")){
					publicCache = true;
				}else if(value.startsWith("private")){
					privateCache = true;
				}else if(value.startsWith("must-revalidate")){
					mustRevalidate = true;
				}else if(value.startsWith("proxy-revalidate")){
					proxyRevalidate = true;
				}
			} catch (RuntimeException e) {
				continue;
			}
		}
	}
}
