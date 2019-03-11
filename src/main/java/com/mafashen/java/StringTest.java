package com.mafashen.java;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author mafashen
 * created on 2019/2/14.
 */
public class StringTest {

	public static void main(String[] args) throws IOException {
//		String s1=new String("StringTest");
//		System.out.println(s1.intern()==s1); //false

		String s2 = new StringBuilder().append("String").append("Test").toString(); //toString => new String
		System.out.println(s2.intern() == s2);//true

//		String s3 = new StringBuilder("StringTest").toString();
//		System.out.println(s3.intern() == s3);//false

		Integer value = 10;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(value);
		// 读出当前对象的二进制流信息
		System.out.println(bos.size());
	}
}
