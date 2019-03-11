package com.mafashen.java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mafashen.invoke.CategoryInfo;
import com.mafashen.invoke.Response;

/**
 * @author mafashen
 * created on 2019/1/15.
 */
public class JsonConvertTest {

	public static void main(String[] args) {
		Response<CategoryInfo> categoryInfoResponse = JSON.parseObject("", new TypeReference<Response<CategoryInfo>>() {
		});
	}
}
