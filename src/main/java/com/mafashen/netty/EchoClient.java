package com.mafashen.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
	private void connect(String host, int port) throws InterruptedException {
		//配置客户端处理IO读写的NIO线程组
		EventLoopGroup clientGroup = new NioEventLoopGroup();

		try {
			//客户端启动辅助类
			Bootstrap client = new Bootstrap();
			client.group(clientGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
							ch.pipeline()
									.addLast(new DelimiterBasedFrameDecoder(1024, buf))
									.addLast(new StringDecoder())
									.addLast(new EchoClientHandler());
						}
					});
			//发起异步连接操作
			ChannelFuture cf = client.connect(host, port).sync();
			//等待客户端链路关闭
			cf.channel().closeFuture().sync();
		} finally {
			clientGroup.shutdownGracefully();
		}
	}

	static class EchoClientHandler extends ChannelHandlerAdapter {

		static final String ECHO_REQ = "Hi, Mafashen. Welcome to Netty!$_";
		private int counter;

		EchoClientHandler() {
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			//TCP链路建立成功之后回调,发送查询时间指令给服务端
			ByteBuf msg = null;
			for (int i = 0; i < 100; i++) {
				msg = Unpooled.copiedBuffer(ECHO_REQ.getBytes());
				ctx.writeAndFlush(msg);
			}
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			//服务端返回响应信息时回调
			String body = (String) msg;
			System.out.println("Receive : " + body);
			System.out.println("The Counter is " + ++counter);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			System.out.println("Unexpected exception from downstream : " + cause.getMessage());
			cause.printStackTrace();
			ctx.close();
		}
	}

	public static void main(String[] args) {
		try {
			new EchoClient().connect("localhost", 8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

