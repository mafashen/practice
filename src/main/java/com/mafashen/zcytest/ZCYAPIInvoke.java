package com.mafashen.zcytest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import com.mafashen.invoke.HttpUtil;
import com.mafashen.zcytest.auth.ShaHmac256;
import com.mafashen.zcytest.auth.Signer;


public class ZCYAPIInvoke {
	private static final String secret = "KZVFFka8r2CS";
	private static final String appId = "917381";
	private static final String url_prefix = "pro://sandbox.zcy.gov.cn/";


/*
HTTP Header必须携带开放平台指定的系统级Header参数，开放平台通过系统级Header信息获取用户信息并验证用户身份有效性。

系统级Header参数
-【必选】X-Ca-Key：API调用者AppKey。
-【必选】X-Ca-Signature：API调用者加签签名。
-【必选】X-Ca-Signature-Headers：Header中所有参与签名的Key，以英文逗号分隔，不限先后顺序。例：“X-Ca-Timestamp,X-Ca-Key,X-Ca-Format”。
-【可选】X-Ca-Timestamp：API调用者传递时间戳，为时间的毫秒值。默认15分钟内有效。
-【可选】X-Ca-Format：当API入参为JSON格式时，填“json2”。
-【可选】X-Ca-Nonce：API调用者自定义请求UUID，结合时间戳放重放。使用时需要配合时间戳
-【可选】Content-MD5：当请求Body为Stream时，可以计算Body的MD5值传递给开放平台校验。
 */

	public void invoke (String url , String httpMethod, Map<String, Object> params){

		if (Objects.equals("GET" , httpMethod)){
			HttpGet get = HttpUtil.buildHttpGet(url_prefix + url, buildHeader("GET", url, params), params);
			try {
				String result = HttpUtil.launchHttp(get);
				System.out.println(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (Objects.equals("POST" , httpMethod)){
			Map<String, Object> bodyMap = new HashMap<>();
			JSONObject jsonObject = new JSONObject();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
			bodyMap.put("_data_", jsonObject.toString());
			try {
				HttpPost post = HttpUtil.buildHttpPost(url_prefix + url, buildHeader("POST", url, bodyMap), bodyMap);
				String result = HttpUtil.launchHttp(post);
				System.out.println(result);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, String> buildHeader(String method, String url, Map<String, Object> params){
		Map<String, String> headerMap = new TreeMap<>();
		headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		headerMap.put("Accept", "application/json");
		headerMap.put("X-Ca-Key", appId);
		headerMap.put("X-Ca-Format", "json2");

		String stringToSign = stringToSign(method, headerMap.get("Accept"), "", headerMap.get("Content-Type"), null, getHeaderString(headerMap), getUrlString(url, params));
		StringBuilder allHeaderStr = new StringBuilder();
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			if (entry.getKey().startsWith("X-Ca"))
				allHeaderStr.append(entry.getKey()).append(",");
		}
		allHeaderStr.delete(allHeaderStr.length() - 1, allHeaderStr.length());
		headerMap.put("X-Ca-Signature-Headers", allHeaderStr.toString());
		headerMap.put("X-Ca-Signature", signature(stringToSign));
		return headerMap;
	}

	private String signature(String stringToSign) {
		try {
//			byte[] keyBytes = secret.getBytes("UTF-8");
//			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
//			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
//			String sign = new String(Base64.encodeBase64(stringToSign.getBytes("UTF-8")),"UTF-8");
//			System.out.println("sign : " + sign);

			/*HmacSHA256签名*/
			Signer signer = new ShaHmac256();
			String signature = signer.sign(secret, stringToSign, "utf-8");
			System.out.println("stringToSign : \n" + stringToSign);
			System.out.println("signature : " + signature);
			return signature;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getHeaderString(Map<String , String> headerMap){
		StringBuilder headerStr = new StringBuilder();
		for (Map.Entry<String, String> header : headerMap.entrySet()) {
			if (header.getKey().startsWith("X-Ca"))
			headerStr.append(header.getKey()).append(":").append(header.getValue()).append("\n");
		}
		headerStr.delete(headerStr.length() - 1, headerStr.length());
		return headerStr.toString();
	}

	private String getUrlString(String path, Map<String , Object> params){
		StringBuilder urlStr = new StringBuilder(path);
		if (params != null && !params.isEmpty()){
			urlStr.append("?");
			for (Map.Entry<String, Object> param : params.entrySet()) {
				urlStr.append(param.getKey()).append("=").append(param.getValue()).append("&");
			}
			urlStr.delete(urlStr.length()-1, urlStr.length());
		}
		return urlStr.toString();
	}

	private String stringToSign(String httpMethod , String accept, String contendMd5, String contentType, Date date, String headers, String url){
		String stringToSign=
				httpMethod + "\n" +
				accept  + "\n" +
				(contendMd5==null ? "" : contendMd5) + "\n" +
				(contentType == null ? "" : contentType) + "\n" +
				(date == null ? "" : date) + "\n" +
				headers + "\n" +
				url + "\n";
		return stringToSign;
	}

	public static void main(String[] args) {
		ZCYAPIInvoke invoke = new ZCYAPIInvoke();
		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("keyAttrs", "品牌:测试");
//		String signature = invoke.signature(invoke.stringToSign("POST", "", "", "", new Date(), null, invoke.getUrlString(API.spu_query, requestParam)));
//		System.out.println(signature);

		String str2sign = "POST\n" +
				"application/json\n" +
				"\n" +
				"application/x-www-form-urlencoded; charset=utf-8\n" +
				"\n" +
				"X-Ca-Format:json2\n" +
				"X-Ca-Key:917381\n" +
				"/open/zcy.mall.category.get?_data_={\"root\":0,\"depth\":2}";
		invoke.signature(str2sign);

		System.out.println("\n\n==================\n\n");

		String str2sign2 = "POST\n" +
				"application/json\n" +
				"\n" +
				"application/x-www-form-urlencoded; charset=utf-8\n" +
				"\n" +
				"X-Ca-Format:json2\n" +
				"X-Ca-Key:917381\n" +
				"/open/zcy.mall.category.get?_data_={\"root\":0,\"depth\":2}";
		invoke.signature(str2sign2);

		System.out.println("\n\n==================\n\n");

		String str2sign3 = "POST\n" +
				"application/json\n" +
				"\n" +
				"application/x-www-form-urlencoded; charset=utf-8\n" +
				"\n" +
				"X-Ca-Format:json2\n" +
				"X-Ca-Key:917381\n" +
				"/open/zcy.mall.category.get?_data_={\"root\":0,\"depth\":2}";
		invoke.signature(str2sign3);

		System.out.println(str2sign.equals(str2sign2));
		System.out.println(str2sign.equals(str2sign3));
		System.out.println(str2sign2.equals(str2sign3));
	}
}
