package com.mafashen.netty.procotol.pro.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.mafashen.netty.procotol.pro.NettyMessage;
import com.mafashen.netty.procotol.pro.util.MessageUtil;
import com.mafashen.netty.procotol.pro.util.MessageBuildFactory;

/**
 * 握手请求处理器,在连接建立时发送握手请求消息,收到消息后针对握手响应处理,握手失败关闭连接
 */
public class HandShakeRequestHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client Send Hand Shake Request");
		//客户端在连接建立的时候发送握手请求消息
		ctx.writeAndFlush(MessageBuildFactory.buildHandShakeRequestMessage());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		//如果是握手应答消息则处理,其他消息透传
		if (MessageUtil.isHandShakeResponse(message)){
			//获取消息体,判断结果,如果握手失败,关闭连接
			if (MessageUtil.isHandShakeRequestSuccess(message)){
				System.out.println("Client HandShake Success");
				ctx.fireChannelRead(msg);
			}else {
				ctx.close();
			}
		}else{
			ctx.fireChannelRead(msg);
		}
	}
}
