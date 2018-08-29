package com.mafashen.invoke;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ProtocolBuilder {
	private Protocol protocol;

	public ProtocolBuilder(Protocol protocol) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sf.format(new Date());
		protocol.setTimestamp(timestamp);
		protocol.setFormat("json");
		protocol.setV("1.0");
		this.protocol = protocol;
	}

	public Map<String, String> urlParam() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("token", protocol.getToken());
		map.put("app_key", protocol.getApp_key());
		map.put("timestamp", protocol.getTimestamp());
		map.put("format", protocol.getFormat());
		map.put("v", protocol.getV());
		map.put("sign", getSign(protocol));
		return map;
	}

	public Map<String, String> getBodyParam() {
		Map<String, String> body = new HashMap<>();
		body.put("jd_param_json", JSONObject.toJSONString(protocol.getParam()));
		return body;
	}

	private String getSign(Protocol protocol) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("token", protocol.getToken());
		map.put("app_key", protocol.getApp_key());
		map.put("timestamp", protocol.getTimestamp());
		map.put("format", protocol.getFormat());
		map.put("v", protocol.getV());
		map.put("jd_param_json", JSONObject.toJSONString(protocol.getParam()));
		String sysStr = concatParams(map);
		StringBuilder resultStr = new StringBuilder();
		resultStr.append(protocol.getApp_secret()).append(sysStr).append(protocol.getApp_secret());
		return MD5Util.getMD5String(resultStr.toString()).toUpperCase();
	}

	private static String concatParams(Map<String, String> params2) {
		Object[] key_arr = params2.keySet().toArray();
		Arrays.sort(key_arr);
		StringBuilder str = new StringBuilder();
		for (Object key : key_arr) {
			String val = params2.get(key);
			str.append(key).append(val);
		}
		return str.toString();
	}

	private String andParams(Map<String, String> params) {
		Object[] key_arr = params.keySet().toArray();
		Arrays.sort(key_arr);
		String str = "";

		for (Object key : key_arr) {
			String val = params.get(key);
			str += "&" + key + "=" + val;
		}
		return str.replaceFirst("&", "");
	}
}
