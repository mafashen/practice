package com.mafashen.netty.procotol.pro.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.mafashen.netty.procotol.pro.NettyMessage;
import com.mafashen.netty.procotol.pro.util.MessageBuildFactory;
import com.mafashen.netty.procotol.pro.util.MessageUtil;

/**
 * 心跳响应处理器,接收心跳请求消息并返回心跳响应消息
 */
public class HeartResponseHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		//心跳请求消息,发送心跳响应消息
		if (MessageUtil.isHeartRequest(message)){
			System.out.println("Server Receive Heart Beat Request ");
			NettyMessage resp = MessageBuildFactory.buildHeartResponseMessage();
			System.out.println("Server Send Heart Beat Response");
			ctx.writeAndFlush(resp);
		}else{
			ctx.fireChannelRead(msg);
		}
	}
}
