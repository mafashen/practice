package com.mafashen.structure;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * IDE代码提醒功能,根据输入的类名, Class.forName()加载类, 将所有方法构建成Tire树
 * @author mafashen
 * created on 2019/3/28.
 */
public class InputSuggest {

	public static void main(String[] args) {
		TireTree tireTree = new TireTree();
		Scanner scanner = new Scanner(System.in);
		if (scanner.hasNext()){
			String className = scanner.next();
			try {
				Class<?> aClass = Class.forName(className);
				for (Method method : aClass.getMethods()) {
					tireTree.insert(method.getName().toLowerCase().toCharArray());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		while (scanner.hasNext()){
			String next = scanner.next();
			tireTree.suggest(next.toCharArray());
		}
	}
}
