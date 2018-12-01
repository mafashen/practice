package com.mafashen.java;

import java.lang.reflect.Field;
import lombok.Data;

import com.alibaba.fastjson.JSONObject;

/**
 * @author mafashen
 * created on 2018/11/20.
 */
public class JsonGenerator {

	public static void generate(String className) throws ClassNotFoundException {
		JSONObject jsonObject = new JSONObject();

		Class<?> clz = Class.forName(className);
		Field[] declaredFields = clz.getDeclaredFields();

		for (Field field : declaredFields) {
			JSONObject subJson = new JSONObject();
			subJson.put("type", getType(field.getGenericType().getTypeName()));
			jsonObject.put(field.getName(), subJson);
		}

		System.out.println(jsonObject);
	}

	private static String getType(String clzName){
		String[] split = clzName.split("java.lang.");
		if (split.length > 1){
			switch (split[1]){
				case "String":
					return "text";
				default:
					return split[1].toLowerCase();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			generate("com.mafashen.java.TestDO");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

@Data
class TestDO{
	public Long id;
	private String text;
	private Integer type;
}
