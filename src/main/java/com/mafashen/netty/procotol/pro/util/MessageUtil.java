package com.mafashen.netty.procotol.pro.util;

import com.mafashen.netty.procotol.pro.MessageType;
import com.mafashen.netty.procotol.pro.NettyMessage;

public class MessageUtil {

	public static byte getMessageType(NettyMessage message){
		return message.getHeader().getType();
	}

	public static boolean isHandShakeRequest(NettyMessage message){
		return equalsMessageType(message, MessageType.HANDSHAKE_REQ);
	}

	public static boolean isHandShakeResponse(NettyMessage message){
		return equalsMessageType(message, MessageType.HANDSHAKE_RESP);
	}

	public static boolean isHeartRequest(NettyMessage message){
		return equalsMessageType(message, MessageType.HEART_REQ);
	}

	public static boolean isHeartResponse(NettyMessage message){
		return equalsMessageType(message, MessageType.HEART_RESP);
	}

	public static boolean equalsMessageType(NettyMessage message, byte type){
		if (message != null && message.getHeader() != null
				&& type ==  message.getHeader().getType() ){
			return true;
		}
		return false;
	}

	public static boolean isHandShakeRequestSuccess(NettyMessage message){
		if (isHandShakeResponse(message)){
			byte result = (byte) message.getBody();
			return (byte) 0 == result;
		}
		return false;
	}
}
