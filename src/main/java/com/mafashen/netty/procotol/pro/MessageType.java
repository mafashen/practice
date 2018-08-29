package com.mafashen.netty.procotol.pro;

import lombok.Getter;

/**
 * 	请求消息类型
 */
@Getter
public final class MessageType {

	/**
	 * 业务请求消息
	 */
	public static final byte BIZ_REQ = 0;

	/**
	 * 业务响应消息
	 */
	public static final byte BIZ_RESP = 1;

	/**
	 * 业务ONE WAY消息,既是请求也是响应消息
	 */
	public static final byte BIZ_ONE_WAY = 2;

	/**
	 * 握手请求消息
	 */
	public static final byte HANDSHAKE_REQ = 3;

	/**
	 * 握手响应消息
	 */
	public static final byte HANDSHAKE_RESP = 4;

	/**
	 * 心跳请求消息
	 */
	public static final byte HEART_REQ = 5;

	/**
	 * 心跳响应消息
	 */
	public static final byte HEART_RESP = 6;
}
