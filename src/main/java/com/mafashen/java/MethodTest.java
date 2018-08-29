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
		String[] sdgOuterShopIds = new String[]{"meituan_18170","meituan_18189","meituan_18288","meituan_18287","meituan_18164","meituan_18160","meituan_18138","meituan_18125","meituan_18321","meituan_18783","meituan_17047","meituan_6572","meituan_17019","meituan_18907","meituan_18911","meituan_18165","meituan_18918","meituan_18148","meituan_17110","meituan_18202","meituan_17327","meituan_17451","meituan_17112","meituan_17611","meituan_17554","meituan_18863","meituan_17193","meituan_17526","meituan_17529","meituan_17533","meituan_17508","meituan_17518","meituan_17796","meituan_17566","meituan_17510","meituan_17604","meituan_17543","meituan_17507","meituan_17619","meituan_17614","meituan_17609","meituan_17804","meituan_17701","meituan_17530","meituan_17726","meituan_17647","meituan_17644","meituan_17991","meituan_17928","meituan_18045","meituan_17917","meituan_17914","meituan_17910","meituan_17865","meituan_17662","meituan_17660","meituan_17658","meituan_17712","meituan_17707","meituan_18044","meituan_18053","meituan_17891","meituan_17988","meituan_17882","meituan_17861","meituan_18274","meituan_18275","meituan_18353","meituan_18273","meituan_18260","meituan_18241","meituan_18688","meituan_7635","2553684","2553636","2553619","2553637","2553662","2553665","2553663","2553862","2553661","2553659","2553946","2553778","2554944","2553779","2554037","2553680","2553951","2553688","2553952","2553692","2553691","2553693","2553686"};
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
		String str = "{(2281812)},{(2281811)},{(2281799)},{(2281798)},{(2281788)},{(2281782)},{(2281780)},{(2281779)},{(2281777)},{(2281774)},{(2281772)},{(2281771)},{(2281767)},{(2281766)},{(2281764)},{(2281763)},{(2281733)},{(2281726)},{(2281725)},{(2281714)},{(2281706)},{(2281695)},{(2281693)},{(2281687)},{(2281683)},{(2281781)},{(1073127)},{(1073215)},{(1073216)},{(157204725)},{(157204726)},{(157204727)},{(157204761)},{(157205142)},{(1073068)},{(1073077)},{(1073104)},{(1073110)},{(1073678)},{(157204728)},{(157204764)},{(157204768)},{(1073602)},{(1073518)},{(1073517)},{(1073416)},{(1073407)},{(1073420)},{(1073624)},{(1073535)},{(1073626)},{(1073452)},{(1073508)},{(1073448)},{(1073410)},{(1073537)},{(1073658)},{(157205105)},{(157205109)},{(157205112)},{(157205114)},{(157205886)},{(157205887)},{(157205117)},{(157205118)},{(157205892)},{(157249224)},{(157205899)},{(157205121)},{(157205133)},{(157205134)},{(157205136)},{(157205918)},{(157205139)},{(157205925)},{(1074857)},{(1074859)},{(1074849)},{(1074904)},{(1075483)},{(1075485)},{(1073330)},{(1073346)},{(1073351)},{(1073356)},{(1073372)},{(1075655)},{(1075665)},{(157451739)},{(157451744)},{(157451753)},{(157493830)},{(157493833)},{(157493836)},{(157493837)},{(157493839)},{(157493843)},{(157493853)},{(157493929)},{(157493934)},{(157493937)},{(157493938)},{(157493939)},{(157494038)},{(157494039)},{(157494040)},{(157494056)},{(157494060)},{(157494061)},{(157494062)},{(157494063)},{(157512220)},{(157512221)},{(157512222)},{(157512223)},{(157512224)},{(157512225)},{(157512226)},{(157512227)},{(157512228)},{(157512229)},{(157512237)},{(157512347)},{(157525215)},{(157525216)},{(157525219)},{(157525220)},{(157525222)},{(157525223)},{(157525225)},{(157525226)},{(157525227)},{(157525228)},{(157525229)},{(157525230)},{(157525676)},{(157526624)},{(157526631)},{(157526632)},{(157526634)},{(157526635)},{(157526637)},{(157526638)},{(157526640)},{(157526641)},{(157526642)},{(157526643)},{(157526644)},{(157526648)},{(157526653)},{(157526654)},{(157526655)},{(157526656)},{(157526658)},{(157526660)},{(157526986)},{(157526992)},{(157526993)},{(157526995)},{(157526996)},{(157526997)},{(157526998)},{(157526999)},{(157527000)},{(157527005)},{(157527090)},{(157527091)},{(157527094)},{(157527095)},{(157527097)},{(157527099)},{(157527100)},{(157527101)},{(157527104)},{(157527105)},{(157527106)},{(157527107)},{(157527109)},{(157527117)},{(157527119)},{(156974109)},{(156974108)},{(156974107)},{(156974105)},{(156974118)},{(156974101)},{(156974098)},{(156974097)},{(156974144)},{(156974142)},{(156974141)},{(156974129)},{(156974134)},{(156974131)},{(156983424)},{(156983409)},{(156983415)},{(157993376)},{(157963712)},{(157963755)},{(157963757)},{(158168924)},{(157983453)},{(158171431)},{(156983435)},{(158172874)},{(158172891)},{(156983433)},{(157984304)},{(157993288)},{(158173173)},{(157993159)},{(157993291)},{(157993163)},{(157993181)},{(157993191)},{(157526636)},{(157526639)},{(158909626)},{(158729393)},{(158910581)},{(158908459)},{(158725360)},{(158908466)},{(158729318)},{(158729320)}";
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
		map.put("sign" , "imQw5+azkeVRfKehpQgoY4un0GrkRIOYp1mS/aI09AYk9BEwxMx/e0xy+kcjBXVQKlOVytVaMh0Qupynfh7Qz14+urOaiMEC603uydJemYh8678f7MMBV0QwqgNpXaBswjzr+xpWCOYT9IEtAiiLB8L+YYELQqk9zUcdnCpVdNQ+p6fMm0+KxbTNJrNSStc0gtxKIQ28HXRnGPZiNjUbo0Rtxw2YonrC4SAqevQNRguQ3aAKQO0WBDBLMGImRme7HRbqhzcFCv10ZBs6pYIY6hRQ1QkMKb9Hz0e/3M4t/bZL9uVs/ZCpNh6xOaWvRkrPdt10S0dJhl6NyezAyHoE/g==");
		System.out.println(map.size());
		System.out.println(map.keySet());
		System.out.println(map.values());
	}

	@Test
	public void sub(){
		List<Integer> inner = Arrays.asList();
		List<Integer> outer = Arrays.asList();

		System.out.println("内部多出" + CollectionUtils.subtract(inner , outer));
		System.out.println("外部多出" + CollectionUtils.subtract(outer , inner));
	}

	@Test
	public void testSet(){
		Set<Integer> set = new HashSet<>(Arrays.asList(1));
		System.out.println(set);
	}
}
