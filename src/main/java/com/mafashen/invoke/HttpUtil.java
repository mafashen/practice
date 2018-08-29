package com.mafashen.invoke;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static HttpPost buildHttpPost(String uri, Map<String, Object> params) throws UnsupportedEncodingException {
		return buildHttpPost(uri, null, params);
	}

	public static HttpPost buildHttpPost(String uri, Map<String , String> headerMap , Map<String, Object> params) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(uri);
		Header[] headers = new Header[headerMap.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
		}
		httpPost.setHeaders(headers);
		List<NameValuePair> formParams = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				formParams.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
		}

		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
		httpPost.setEntity(uefEntity);
		return httpPost;
	}

	public static HttpGet buildHttpGet(String uri, Map<String , String> headerMap , Map<String, Object> params){
		Header[] headers = new Header[headerMap.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
		}
		List<NameValuePair> formParams = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				formParams.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
		}

		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setHost(uri);
		HttpGet httpGet = new HttpGet(uriBuilder.toString());
		httpGet.setHeaders(headers);
		return httpGet;
	}

	public static HttpPost buildHttpPost(String uri,HttpEntity httpEntity){
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(httpEntity);
		return httpPost;
	}



	public static HttpPost buildHttpPost(String uri, String body, ContentType contentType) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(uri);
		StringEntity se = new StringEntity(body, contentType);
		httpPost.setEntity(se);
		return httpPost;
	}

	public static String launchHttp(HttpRequestBase req) throws IOException {
		HttpClient hc = HttpClientBuilder.create().build();
		RequestConfig rc = RequestConfig.custom()
				.setConnectTimeout(2000)
				.setConnectionRequestTimeout(2000)
				.setSocketTimeout(2000).build();
		req.setConfig(rc);
		printRequest(req);
		HttpResponse httpResponse = hc.execute(req);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			return EntityUtils.toString(entity, "UTF-8");
		}
		return null;
	}

	private static void printRequest(HttpRequestBase post) {
		Header[] headers = post.getAllHeaders();
		System.out.println("request uri : " + post.getURI());
		System.out.println("Headers:");
		for (Header header : headers) {
			System.out.println("\t" + header.getName() + " : " + header.getValue());
		}

	}
}