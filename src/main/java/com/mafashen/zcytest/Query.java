package com.mafashen.zcytest;

import java.util.HashMap;
import java.util.Map;

public class Query {

	public void querySpu(long categoryId , Map<String, String> attrMap){
		ZCYAPIInvoke apiInvoke = new ZCYAPIInvoke();
		StringBuilder attr = new StringBuilder();
		for (Map.Entry<String, String> entry : attrMap.entrySet()) {
			attr.append(entry.getKey()).append(":").append(entry.getValue());
		}
		Map<String, Object> requestParam = new HashMap<>();
		requestParam.put("keyAttrs", attr.toString());
		apiInvoke.invoke(API.spu_query, "POST" , requestParam);
	}

	public void queryCat(Integer root, Integer depth){
		ZCYAPIInvoke apiInvoke = new ZCYAPIInvoke();
		Map<String, Object> requestParam = new HashMap<>();
		requestParam.put("root", root);
		requestParam.put("depth", depth);
		apiInvoke.invoke(API.category_get, "POST" , requestParam);
	}

	public static void main(String[] args) {
		new Query().queryCat(0 , 2);
	}
}
