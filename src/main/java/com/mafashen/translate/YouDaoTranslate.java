package com.mafashen.translate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 有道翻译
 */
public class YouDaoTranslate {

	private final String url = "http://openapi.youdao.com/api";
	private final String appKey = "4c220d03c9f4198c";
	private final String secret = "0Biu6sA1ss4KxYSmecCh6RFCslkikgTY";

	private final String basePath = "/Users/mafashen/Documents/JDKSRC/java/util/concurrent/locks";
	private final String outputPath = "/Users/mafashen/Documents/";

	public String translate(String meta) {
		String translate = null;

		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
//			post.addHeader(new BasicHeader("Accept" , "application/json, text/javascript, */*; q=0.0"));
//			post.addHeader(new BasicHeader("Cookie" , "OUTFOX_SEARCH_USER_ID=-570031223@10.169.0.83; JSESSIONID=aaa_EbH4CBjJufNERXjlw; OUTFOX_SEARCH_USER_ID_NCOO=1797144193.38287; ___rl__test__cookies=1523803672700"));
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("from" , "auto"));
			params.add(new BasicNameValuePair("to" , "auto"));
//			params.add(new BasicNameValuePair("smartresult" , "dict"));
//			params.add(new BasicNameValuePair("client" , "fanyideskweb"));
			String salt = Long.toString(System.currentTimeMillis());
			params.add(new BasicNameValuePair("salt" , salt));
			params.add(new BasicNameValuePair("sign" , md5(meta , salt)));
//			params.add(new BasicNameValuePair("doctype" , "json"));
//			params.add(new BasicNameValuePair("version" , "2.1"));
//			params.add(new BasicNameValuePair("keyfrom" , "fanyi.web"));
//			params.add(new BasicNameValuePair("action" , "FY_BY_REALTIME"));
//			params.add(new BasicNameValuePair("typoResult" , "false"));
//			params.add(new BasicNameValuePair("User-Agent" , "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"));
			params.add(new BasicNameValuePair("q" , meta));
			params.add(new BasicNameValuePair("appKey" , "4c220d03c9f4198c"));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
			post.setEntity(entity);

			CloseableHttpResponse response = httpClient.execute(post);
			if (200 == response.getStatusLine().getStatusCode()){
				String result = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = JSON.parseObject(result);
				if (jsonObject != null && jsonObject.get("translation") != null){
					translate = jsonObject.get("translation").toString().replace("[" , "").replace("\"" , "").replace("]" , "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return translate;
	}

	private String md5( String q , String salt){
		if(appKey == null){
			return null;
		}
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F'};

		try{
			String string = appKey + q + salt + secret;
			byte[] btInput = string.getBytes("utf-8");
			/** 获得MD5摘要算法的 MessageDigest 对象 */
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			/** 使用指定的字节更新摘要 */
			mdInst.update(btInput);
			/** 获得密文 */
			byte[] md = mdInst.digest();
			/** 把密文转换成十六进制的字符串形式 */
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
			return null;
		}
	}

	private void readFile(String name){
		File file = new File(basePath ,name);
		File outputFile = new File(outputPath, "translate.java");
		if (file.exists() && !file.isDirectory()){
			if (!outputFile.exists()){
				try {
					outputFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try (FileReader fis = new FileReader(file);
				 BufferedReader bis = new BufferedReader(fis);
				 FileWriter fw = new FileWriter(outputFile);
				 BufferedWriter bw = new BufferedWriter(fw);
			) {
				String line = null;
				StringBuilder sb = new StringBuilder();
				Stack codeStack = new Stack();
				Integer c = 0;
				do {
					line = bis.readLine();
					if(StringUtils.isNotBlank(line)){
						if (line.trim().startsWith("/*") || line.trim().startsWith("/**")){
							fw.write(line + "\n");
						}
						else if (line.endsWith("*/")){
							String q = process(sb);

//							int turn = q.length() / 500 + 1;
//							int len = q.length();
//							for (int i = 0; i < turn ; i++) {
//								String translate = translate(q.substring(i * 500 , (i+1)*500 > len ? len : (i+1)*500));
//								if (translate != null){
//									fw.write( translate + "\n*/\n");
//									sb.delete(0, sb.length());
//								}else{
//									fw.write("\n*/\n");
//								}
//							}
							fw.write(line + "\n");
						}
						else if (line.contains("*") && !line.contains("import")){
							if (line.endsWith("{") || (!line.contains("@link") && line.contains("{"))){
								codeStack.push(c);
							}
							if (line.endsWith("}") && !line.contains("@link")){
								codeStack.pop();
							}
							if (codeStack.empty()){
								sb.append(line);
								if (line.endsWith(".")){
									String q = process(sb);
									String translate = translate(q);
									if (translate != null){
										fw.write("\t" + translate +"\n");
										sb.delete(0, sb.length());
									}
									sb.delete(0, sb.length());
								}
							}else{
								fw.write(line + "\n");
							}
						}
						else{
							fw.write(line + "\n");
						}
					}
				}while (line != null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new YouDaoTranslate().readFile("AbstractQueuedSynchronizer.java");

	}

	private String process(StringBuilder builder){
		String q = builder.toString().replace("*", "")
				.replace("&quot", "")
				.replace("/" , "")
				.replace("<h3>" , "")
				.replace("<ul>" , "")
				.replace("<li>" , "")
				.replace("<h4>" , "")
				.replace("<p>", "")
				.replace("<em>", "")
				.replace("<pre>", "")
				.replace("<pre>", "")
				.replace("<tt>", "")
				.replace("{ @link #", "")
				.replace("}", "");
		return q;
	}

	@Getter
	@Setter
	class YouDaoResponse {
		private int errorCode;
		private String tSpeakUrl;
		private String query;
		private String translation;
		private String speakUrl;
		private String webdict;
		private String dict;
	}
}
