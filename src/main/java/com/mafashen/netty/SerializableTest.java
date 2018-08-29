package com.mafashen.netty;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class SerializableTest {

	static class UserInfo implements Serializable{
		private static final long serialVersionUID = 1L;

		private int userId;
		private String userName;

		public UserInfo(int userId, String userName) {
			this.userId = userId;
			this.userName = userName;
		}

		public byte[] codeC(){
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			byte[] value = this.userName.getBytes();
			buffer.putInt(value.length);
			buffer.put(value);
			buffer.putInt(this.userId);
			buffer.flip();
			value = null;

			byte[] result = new byte[buffer.remaining()];
			buffer.get(result);
			return result;
		}

		public byte[] codeC(ByteBuffer buffer){
			buffer.clear();
			byte[] value = this.userName.getBytes();
			buffer.putInt(value.length);
			buffer.put(value);
			buffer.putInt(this.userId);
			buffer.flip();
			value = null;

			byte[] result = new byte[buffer.remaining()];
			buffer.get(result);
			return result;
		}
	}

	public static void main(String[] args) throws Exception{
		UserInfo user = new UserInfo(10000, "mafashen");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(user);
		os.flush();
		os.close();

		byte[] bytes = bos.toByteArray();
		System.out.println("The JDK serializable length is : " + bytes.length );
		bos.close();

		System.out.println("-----------------");

		byte[] r2 = user.codeC();
		System.out.println("The byte array Serializable length is : " + r2.length);

		System.out.println("\n-----------------\n");
		/*
		 The JDK serializable length is : 121
		 -----------------
		 The byte array Serializable length is : 16
		 */

		long jdkStartTime = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(user);
			os.flush();
			os.close();
			byte[] b = bos.toByteArray();
			bos.close();
		}
		long jdkEndTime = System.currentTimeMillis();
		System.out.println("JDK Serializable takes : " + (jdkEndTime - jdkStartTime));

		System.out.println("\n-----------------\n");

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long byteArrayStartTime = System.currentTimeMillis();

		for (int i = 0; i < 1000000; i++) {
			user.codeC(buffer);
		}

		long byteArrayEndTime = System.currentTimeMillis();
		System.out.println("Byte Array Serializable takes : " + (byteArrayEndTime - byteArrayStartTime));

		/*
		JDK Serializable takes : 1923
		-----------------
		Byte Array Serializable takes : 131
		 */
	}
}
