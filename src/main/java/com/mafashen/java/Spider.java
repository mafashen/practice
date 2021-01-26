package com.mafashen.java;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 简单的Java爬虫测试,使用Jsoup解析html document
 */
public class Spider {

	public static void parseJd(){
		String url = "https://www.jd.com";
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			Element j_seckill = document.getElementById("J_seckill");
			Elements names = j_seckill.getElementsByClass("sk_item_name");
			Elements newPrices = j_seckill.getElementsByClass("mod_price sk_item_price_new");
			Elements oldPrices = j_seckill.getElementsByClass("mod_price sk_item_price_origin");
			Elements pics = j_seckill.getElementsByClass("lazying_img");

			for (Element name : names) {
				System.out.println(name.html());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void parseMMM(){
		String url = "pro://www.manmanbuy.com/";
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			Elements pics = document.getElementsByClass("pic");

			for (Element pic : pics) {
				Elements a = pic.getElementsByTag("a");
				System.out.println(a.attr("title"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {

		}

		System.out.println();
	}

	public static void parseHouse(){
		List<String> titles = new ArrayList<>();
		try {
			Document document = Jsoup.connect("https://shangrao.anjuke.com/sale/shangraoxian/?from_price=30&to_price=80&from_area=100&to_area=150").userAgent("Mozilla").get();
			Elements itemLists = document.getElementsByClass("item-list");
			for (Element item : itemLists) {
				Elements houseDetails = item.getElementsByClass("house-detail");
				Element detail = houseDetails.get(0);

				Element titleDiv = detail.getElementsByClass("house-title").get(0);
				String title = titleDiv.getElementsByTag("a").attr("title");
				System.out.println(title);
			}
			Elements scripts = document.select("script");
			for (Element script : scripts) {

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseByHtmlUnit() throws Exception{
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(false);


		//获取页面
		String url ="https://shangrao.anjuke.com/sale/shangraoxian/?from_price=30&to_price=80&from_area=100&to_area=150";
		HtmlPage page = webClient.getPage(url);
		HtmlElement ul = page.getHtmlElementById("houselist-mod-new");

		List<String> titles = new ArrayList<>();

		Document document = Jsoup.parse(page.asXml());
		Elements lis = document.select("#houselist-mod-new .list-item");
		for (Element li : lis) {
			String imgUrl = li.getElementsByClass("item-img").get(0).getElementsByTag("img").get(0).attr("src");
			System.out.println("imgUrl:"+imgUrl);

			String title = li.getElementsByClass("house-title").get(0).getElementsByTag("a").get(0).html();
			System.out.println("title:"+title);

			Elements details = li.getElementsByClass("details-item");
			Elements spans = details.get(0).getElementsByTag("span");

			String roomHall = spans.get(0).html();
			String area = spans.get(1).html();
			System.out.println("roomHall:" + roomHall);
			System.out.println("area:" + area);

			if(details.size() > 1){
				String address = details.get(1).getElementsByClass("comm-address").get(0).html();
				System.out.println("address:" + address);
			}

			Element proPrice = li.getElementsByClass("pro-price").get(0);
			Elements priceSpans = proPrice.getElementsByTag("span");
			String totalPrice = priceSpans.get(0).getElementsByTag("strong").get(0).html();
			String unitPrice = priceSpans.get(1).html();
			System.out.println("totalPrice:" + totalPrice);
			System.out.println("unitPrice:" + unitPrice);

			System.out.println("=============");
		}

	}

	public static void main(String[] args) {
		try {
			parseByHtmlUnit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
sk_item_name
mod_price sk_item_price_new
mod_price sk_item_price_origin

id J_seckill
pic lazying_img

mmm

fr r-side -> recommend-box -> bd -> ul outer -> li -> pic -> a title -> img src alt



anjuke

div sale_left > ul houseList-mod-new > li list-item > div
 */
