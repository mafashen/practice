package com.mafashen.netty.procotol.pro.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mafashen.netty.procotol.pro.NettyMessage;
import com.mafashen.netty.procotol.pro.util.AuthUtil;
import com.mafashen.netty.procotol.pro.util.MessageBuildFactory;
import com.mafashen.netty.procotol.pro.util.MessageUtil;

/**
 * 握手响应处理器,针对握手请求消息进行处理
 */
public class HandShakeResponseHandler extends ChannelHandlerAdapter {

	/**
	 * 已经连接的节点,用于对重复连接进行过滤
	 */
	private Map<String, Boolean> existNode = new ConcurrentHashMap<>();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		//如果是握手请求消息则处理,其他消息透传
		if (MessageUtil.isHandShakeRequest(message)){
			System.out.println("Server Receive Hand Shake Request");

			InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
			String nodeIndex = address.toString();
			NettyMessage resp = MessageBuildFactory.buildHandShakeFailResponseMessage();
			//重复登录,拒绝请求
			if (!existNode.containsKey(nodeIndex)){
				if (AuthUtil.allow(address.getAddress().getHostAddress())){
					existNode.put(nodeIndex , true);
					resp = MessageBuildFactory.buildHandShakeSucResponseMessage();
				}
			}
			System.out.println("Server Send HandShake Response");
			ctx.writeAndFlush(resp);
		}else{
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//异常时从已存在节点中删除
		existNode.remove(ctx.channel().remoteAddress().toString());
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}

}
