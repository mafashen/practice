package com.mafashen.java;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Sets;

public class ClassTest {

	public static void main(String[] args) {
		System.out.println(Set.class.isAssignableFrom(JSONArray.class));
		System.out.println(Set.class.isArray());

		Set<Integer> sets = Sets.newHashSet(19570, 16755, 19587, 19588, 19508, 19540, 19605, 19541, 19577, 19580);
		System.out.println(JSON.toJSONString(sets));
		List<Object> arg = JSON.parseArray("[" + "[19570, 16755, 19587, 19588, 19508, 19540, 19605, 19541, 19577, 19580]" + "]", Object.class);
		System.out.println();
	}
}
