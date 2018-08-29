package com.mafashen.netty.procotol.pro.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerAppender;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.mafashen.netty.procotol.pro.NettyMessage;
import com.mafashen.netty.procotol.pro.util.Counter;
import com.mafashen.netty.procotol.pro.util.MessageBuildFactory;
import com.mafashen.netty.procotol.pro.util.MessageUtil;

/**
 * 心跳请求消息处理器,发送心跳请求消息,并对接收的心跳响应消息处理
 * 握手成功后,由客户端定时发起心跳去请求消息,服务端返回心跳应答消息
 */
public class HeartRequestHandler extends ChannelHandlerAdapter {
	private volatile ScheduledFuture<?> heartBeat ;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		//清空计数器
		Counter.clear();

		//握手成功,开始定时发送心跳请求消息
		if(MessageUtil.isHandShakeResponse(message)){
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		}
		//心跳响应消息
		else if (MessageUtil.isHeartResponse(message)){
			System.out.println("Client Received Heart Beat Response Message");
		}else{
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//异常时,停止发送心跳消息
		if (heartBeat != null){
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("The Channel is Inactive , Stop The Heart Beat Request");
		if (heartBeat != null){
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireChannelInactive();
	}

	private static final class HeartBeatTask implements Runnable{

		private ChannelHandlerContext ctx;

		HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			Counter.inc();
			NettyMessage message = MessageBuildFactory.buildHeartRequestMessage();
			System.out.println("Client Send Heart Beat Message");
			ctx.writeAndFlush(message);
		}
	}
}
