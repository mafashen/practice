package com.mafashen.netty.procotol.pro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NettyMessage implements Serializable{

	private Header header;
	private Object body;

	@Override
	public String toString() {
		return "NettyMessage{" +
				"header=" + header +
				", body=" + body +
				'}';
	}

	@Getter
	@Setter
	public static class Header{

		/**
		 * 消息检验码
		 */
		private int crcCode = 0xabef0101;

		/**
		 * 消息长度,包括header+body
		 */
		private int length;

		/**
		 * 会话ID,集群节点全局唯一
		 */
		private long sessionId;

		/**
		 * 消息类型
		 * 	0	业务请求消息
		 * 	1	业务响应消息
		 * 	2	业务ONE WAY消息,即是请求也是响应消息
		 * 	3	握手请求消息
		 * 	4	握手响应消息
		 * 	5	心跳请求消息
		 * 	6	心跳响应消息
		 */
		private byte type;

		/**
		 * 优先级
		 */
		private byte priority;

		/**
		 * 扩展消息头,可选
		 */
		private Map<String, Object> attachment = new HashMap<>();

		@Override
		public String toString() {
			return "Header{" +
					"crcCode=" + crcCode +
					", length=" + length +
					", sessionId=" + sessionId +
					", type=" + type +
					", priority=" + priority +
					", attachment=" + attachment +
					'}';
		}
	}
}
