package com.mafashen.netty.procotol.pro.util;

import com.mafashen.netty.procotol.pro.MessageType;
import com.mafashen.netty.procotol.pro.NettyMessage;

public final class MessageBuildFactory {

	public static NettyMessage buildHandShakeRequestMessage(){
		return buildMessage(MessageType.HANDSHAKE_REQ , null);
	}

	public static NettyMessage buildHandShakeSucResponseMessage(){
		return buildMessage(MessageType.HANDSHAKE_RESP , (byte)0);
	}

	public static NettyMessage buildHandShakeFailResponseMessage(){
		return buildMessage(MessageType.HANDSHAKE_RESP , (byte)-1);
	}

	public static NettyMessage buildHeartRequestMessage(){
		return buildMessage(MessageType.HEART_REQ , null);
	}

	public static NettyMessage buildHeartResponseMessage(){
		return buildMessage(MessageType.HEART_RESP , null);	//心跳响应消息不需要消息体
	}

	private static NettyMessage buildMessage(byte type , Object body){
		NettyMessage message = new NettyMessage();
		NettyMessage.Header header = new NettyMessage.Header();
		header.setType(type);
		message.setHeader(header);
		if (body != null){
			message.setBody(body);
		}
		return message;
	}
}
