package com.mafashen.java;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MethodTest {
	public static void main(String[] args){
		String a = "a";
		exChange(a);
		Person p = new Person(1,"a");
		exchange(p);
		System.out.println(p.name);
	}

	static void exChange(String a){
		a = "abc";
	}

	static void exchange(Person p){
		p.name = "abc";
	}

	static class Person{
		String name;
		int no ;
		public Person(int no , String name) {
			this.no = no ;
			this.name = name;
		}
	}

	@Test
	public void testMapList(){
		List<Person> list = new ArrayList();
		Person p1 = new Person(1 , "1");
		Person p2 = new Person(2 , "2");
		Person p3 = new Person(3 , "3");
		Person p4 = new Person(4 , "4");
		Person p5 = new Person(2 , "22");
		Person p6 = new Person(1 , "11");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		list.add(p6);
		Map<Integer , List<String>> map = new HashMap<Integer, List<String>>();
		for (Person person : list) {
			if (map.get(person.no) == null){
				map.put(person.no , new ArrayList<String>());
			}
			map.get(person.no).add(person.name);
		}

		for (Integer integer : map.keySet()) {
			System.out.println(integer);
			for (String s : map.get(integer)) {
				System.out.println("\t" + s);
			}
		}
		map.remove(5);
	}

	@Test
	public void testRegex(){
		String regex = "^`[1-9]{1}[0-9]*(g)?$";
		System.out.println("      ".matches(regex));
	}

	@Test
	public void testSubList(){
		List list = new ArrayList();
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);

		//list.subList(0 ,6);
		String[] sdgOuterShopIds = new String[]{};
		System.out.println(sdgOuterShopIds.length);
		for (String sdgOuterShopId : sdgOuterShopIds) {
			System.out.println(sdgOuterShopId);
		}
	}

	//GB2312,ISO-8859-1,gbk,unicode,utf-8,ASCII,
	@Test
	public void testEncode() throws UnsupportedEncodingException {
		String str = "\u60a8\u7684\u6bcf\u5206\u949f\u8bf7\u6c42\u6570\u5df2\u8d85\u8fc7\u9608\u503c[5\u6b21]";
		System.out.println(new String(str.getBytes("utf-8") , "utf-8"));
	}

	@Test
	public void testStringUtils(){
		List<String> pics = new ArrayList<String>();
		String join = StringUtils.join(pics, ",");

		System.out.println(StringUtils.isEmpty(join));
	}

	@Test
	public void testInstanceOf(){
		String str = "123456";
//		instance_of(str);
		System.out.println(System.currentTimeMillis());
	}

	private static void instance_of(Object o){
		if (o instanceof String)
			System.out.println("String");
		else if (o instanceof Number)
			System.out.println("Number");
	}

	@Test
	public void testL(){
		List<Integer> a = new ArrayList<>();
		for(int i = 1 ; i < 6 ; i++){
			a.add(i);
		}
		while(true){
			if (a.size() == 1){
				break;
			}
			for(int k = 0 ; k < 2; k++){
				a.add(a.remove(0));
			}
			a.remove(0);
		}
		System.out.println(a.get(0));
	}

	@Test
	public void testMap(){
		Map<Integer , String> map = new HashMap<>();
		String s = map.get(1);
		System.out.println(s == null);
	}

	@Test
	public void testJson(){
		Set<Long> items = new HashSet<>();
		items.add(120162354L);

		Gson gson = new GsonBuilder().create();
		String s = gson.toJson(items);
		System.out.println(s);
	}

	@Test
	public void testEqual(){
		Boolean b = null;
		System.out.println(Objects.equals(b , true));
	}

	@Test
	public void testSubtract(){
		List<Integer> list1 = Arrays.asList();
		List<Integer> list2 = Arrays.asList(2,3,4);
		System.out.println(CollectionUtils.subtract(list1 , list2));
	}

	@Test
	public void testWhile(){
		int i = 0;
		do{
			System.out.println(5000*i++);
		}while (i<10);
	}

	@Test
	public void testToString(){
		List<Long> list = new LinkedList<>();
		list.add(1L);
		list.add(2L);
		list.add(3L);
		System.out.println(list);
	}

	@Test
	public void testSplit(){
		String str = "";
		//str.replace("{" , "");
		String[] strings = str.split("[^0-9]+");
		for (String string : strings) {
			System.out.println(string);
		}
		//System.out.println(str);
	}

	@Test
	public void testMapPut(){
		Map<String, String> map = new HashMap<>();
		map.put("gmt_create" , "2018-03-01 14:58:09");
		map.put("subject" , "xxx品牌xxx门店当面付扫码消费");
		map.put("charset" , "utf-8");
		map.put("seller_email" , "fifaun6461@sandbox.com");
		map.put("sign" , "imQw5+azkeVRfKehpQgoY4un0GrkRIOYp1mS/aI09AYk9BEwxMx/" +
				"e0xy+kcjBXVQKlOVytVaMh0Qupynfh7Qz14+urOaiMEC603uydJemYh8678f7MMBV0Qwqg" +
				"NpXaBswjzr+xpWCOYT9IEtAiiLB8L+YYELQqk9zUcdnCpVdNQ+p6fMm0+KxbTNJrNSStc0gtxK" +
				"IQ28HXRnGPZiNjUbo0Rtxw2YonrC4SAqevQNRguQ3aAKQO0WBDBLMGImRme7HRbqhzcFCv10ZBs6" +
				"pYIY6hRQ1QkMKb9Hz0e/3M4t/bZL9uVs/ZCpNh6xOaWvRkrPdt10S0dJhl6NyezAyHoE/g==");
		System.out.println(map.size());
		System.out.println(map.keySet());
		System.out.println(map.values());
	}

	@Test
	public void sub(){
		List<Integer> inner = Arrays.asList();
		List<Integer> outer = Arrays.asList();

		System.out.println("内部多出" + CollectionUtils.subtract(inner , outer));
		System.out.println("外部多出" + CollectionUtils.subtract(inner , outer).size());
	}

	@Test
	public void testSet(){
		Set<Integer> set = new HashSet<>(Arrays.asList(1));
		System.out.println(set);
	}
}
