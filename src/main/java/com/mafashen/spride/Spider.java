package com.mafashen.spride;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.StreamUtils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Spider {

	private static void parseByHtmlUnit(String url) throws Exception{
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(false);


		//获取页面
		HtmlPage page = webClient.getPage(url);
		Document document = Jsoup.parse(page.asXml());
		Elements imgs = document.getElementsByTag("img");
		for (Element img : imgs) {
			String src = img.attr("src");
			if (StringUtils.isNotBlank(src)){
				System.out.println(src);
			}
		}

	}

	public static void jsoupParse(String url){
		try {
			Document document = Jsoup.connect(url).get();
			Elements imgs = document.getElementsByTag("img");
			List<String> imgSrcs = new ArrayList<>();
			for (Element img : imgs) {
				String src = img.attr("src");
				String dataSrc = img.attr("data-src");

				if (StringUtils.isNotBlank(src)){
					imgSrcs.add(src);
				}
				if (StringUtils.isNotBlank(dataSrc)){
					imgSrcs.add(dataSrc);
				}
			}

			for (String imgSrc : imgSrcs) {
				imgSrc = "pro://" +imgSrc.substring(2);
				downloadImg(imgSrc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void downloadImg(String url){
		HttpClient httpClient = HttpClients.createDefault();
			HttpUriRequest request = new HttpGet(url);
			try {
				HttpResponse response = httpClient.execute(request);
				if (response.getStatusLine().getStatusCode() == 200){
					InputStream inputStream = response.getEntity().getContent();
					String filename = url.substring(url.lastIndexOf("/"));
					File file = new File("/Users/mafashen/Documents/temp/SpiderImg", filename);
					if (!file.exists())
						file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					StreamUtils.copy(inputStream, fos);
				}
			} catch (IOException e) {
				e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "https://book.tmall.com";
		try {
			jsoupParse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test(){
		//login1 需要做的工作
		//login2 需要做的工作
		//login3 需要做的工作
	}
}