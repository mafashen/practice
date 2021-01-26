package com.mafashen.invoke;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class JdApiInvoke {

	@Test
	public void getCat(){
		List<CategoryInfo> categoryInfos = new ArrayList<>(3000);
		getJdCat(0L , categoryInfos);

		File file = new File("/Users/mafashen/Documents", "jdCat.txt");
		try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)){
			file.createNewFile();
			for (CategoryInfo categoryInfo : categoryInfos) {
				bw.write(categoryInfo.toString() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getJdCat(Long catId , List<CategoryInfo> categoryInfos){
		Map<String, Object> param = new HashMap<>();
		param.put("fields", Arrays.asList("ID", "PID", "CATEGORY_NAME", "CATEGORY_LEVEL" , "CATEGORY_STATUS" , "FULLPATH"));
		param.put("id", catId);
		String result = buildProtocolAndRequest("api/queryChildCategoriesForOP", param);
		if (StringUtils.isNotBlank(result)){
			try{
				List<CategoryInfo> cats = parse(result);
				if (CollectionUtils.isNotEmpty(cats)){
					for (CategoryInfo cat : cats) {
						if (1 == cat.getCategoryStatus()){
							categoryInfos.add(cat);
							System.out.println(cat);
							Thread.sleep(200);
							getJdCat(cat.getId() , categoryInfos);
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}
	}

	@Test
	public void getBrand() throws Exception {

		Map<String, Object> param = new HashMap<>();
		param.put("fields", Arrays.asList("BRAND_ID", "BRAND_NAME", "BRAND_STATUS"));
		param.put("brandName", "李锦记");
		param.put("pageSize" , 50);
		param.put("pageNo" , 1);
		buildProtocolAndRequest("pms/queryPageBrandInfo" , param);
	}

	public static String buildProtocolAndRequest(String API , Map<String , Object> param ){

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Protocol protocol = new Protocol();
		protocol.setApp_key("a2eaebefa579489f91e4e953f268781e");
		protocol.setApp_secret("1b893150fa884e7f86ec4762cbf92abb");
		protocol.setToken("72d6f21e-ec2a-4125-b297-374a3265f563");

		param.put("pageSize", 50);
		protocol.setParam(param);
		try {
			ProtocolBuilder protocolBuilder = new ProtocolBuilder(protocol);
			Map<String, Object> params = new HashMap<>();
			params.putAll(protocolBuilder.urlParam());
			params.putAll(protocolBuilder.getBodyParam());
			HttpPost httpPost = HttpUtil.buildHttpPost("https://openo2o.jd.com/djapi/" + API, params);
			String result = HttpUtil.launchHttp(httpPost);
			System.out.println(result);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
//			return buildProtocolAndRequest(API, param);
			throw new RuntimeException(e);
		}

	}

	public static List<CategoryInfo> parse(String result){
		List<CategoryInfo> cats = new ArrayList<>();
		try {
			if (StringUtils.isNotBlank(result)){
				JSONObject jsonObject = JSONObject.parseObject(result);
				String dataStr = jsonObject.getString("data");
				JSONObject data = JSONObject.parseObject(dataStr);
				if (data != null){
					JSONArray retList = data.getJSONArray("result");

					if (CollectionUtils.isNotEmpty(retList)){
						for (int i = 0; i < retList.size(); i++) {
							CategoryInfo categoryInfo = retList.getObject(i,CategoryInfo.class);
							if (categoryInfo != null){
								cats.add(categoryInfo);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cats;
	}

	private ConcurrentHashMap allCats = new ConcurrentHashMap();

	@Test
	public void testGet(){
		Map<String, Object> param = new HashMap<>();
		param.put("fields", Arrays.asList("ID", "PID", "CATEGORY_NAME", "CATEGORY_LEVEL" , "CATEGORY_STATUS" , "FULLPATH"));
		param.put("id", 21216);
		String result = buildProtocolAndRequest("api/queryChildCategoriesForOP", param);
		System.out.println(result);
	}

	public static void main(String[] args) {

	}
}


