package com.mafashen.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author mafashen
 * created on 2018/12/8.
 */
public class RefTest {

	public static void main(String[] args) {
		List<String> list = Lists.newArrayList("1","2", null);
		removeNullElement(list);
		System.out.println(list);

		Inner inner = new Inner(list);
		removeNullElement(inner);
		System.out.println(inner.list);
	}

	private static void removeNullElement(Collection<?> collection){
		collection = new ArrayList<>(collection);
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()){
			Object next = iterator.next();
			if (next == null){
				iterator.remove();
			}
		}
	}

	private static void removeNullElement(Inner inner){
		inner.list = new ArrayList<>(inner.list);
		Iterator<?> iterator = inner.list.iterator();
		while (iterator.hasNext()){
			Object next = iterator.next();
			if (next == null){
				iterator.remove();
			}
		}
	}


	static class Inner{
		public Collection<String> list;

		public Inner(Collection<String> list) {
			this.list = list;
		}
	}
}
