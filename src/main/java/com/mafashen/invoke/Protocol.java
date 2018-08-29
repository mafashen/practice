package com.mafashen.invoke;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Protocol {
	private String token;
	private String app_key;
	private String app_secret;
	private String sign;
	private String timestamp;
	private String format;
	private String v;
	private Object param;
}
