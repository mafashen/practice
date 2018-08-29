package com.mafashen.zcytest;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ZCYInvokeManager {
	private static final String APP_KEY = "917381";
	private static final String SECRET  = "KZVFFka8r2CS";
	private static final String API_GATEWAY = "pro://sandbox.zcy.gov.cn";

	public static void invokePost(String uri)  {
		ZcyOpenRequest zcyOpenRequest = new ZcyOpenRequest(APP_KEY,SECRET,API_GATEWAY);
		zcyOpenRequest.setUri(uri);
		zcyOpenRequest.setMethod(HttpMethod.POST);

        /*组装Body参数*/
		Map<String, Object> bodyMap = new HashMap<String, Object>();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("root", 0);
		jsonObject.put("depth",2);
		bodyMap.put("_data_", jsonObject.toString());
		zcyOpenRequest.setParamMap(bodyMap);

        /*发送http request*/
		String result = null;
		try {
			result = ZcyOpenClient.excute(zcyOpenRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

        /*打印返回结果*/
		System.out.println(result);
	}

	public static void invokeGet(String uri){
		ZcyOpenRequest zcyOpenRequest = new ZcyOpenRequest(APP_KEY,SECRET,API_GATEWAY);
		zcyOpenRequest.setUri(uri);
		zcyOpenRequest.setMethod(HttpMethod.GET);
		/*发送http request*/
		String result = null;
		try {
			result = ZcyOpenClient.excute(zcyOpenRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

        /*打印返回结果*/
		System.out.println(result);
	}

	public static void main(String[] args) {
		invokePost(API.category_get);
//		invokeGet("zcy.category.get?root=0&depth=3");
	}
}
