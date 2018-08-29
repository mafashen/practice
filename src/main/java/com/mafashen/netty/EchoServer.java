package com.mafashen.netty;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoServer {

	private void bind(int port) throws InterruptedException {
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//NIO服务端启动辅助类
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)	//TCP参数配置
					.childHandler(new EchoServer.ChildChannelHandler());		//IO事件处理类
			//绑定端口,同步等待成功
			ChannelFuture sf = server.bind(port).sync();
			//等待服务端监听端口关闭
			sf.channel().closeFuture().sync();
		} finally {
			//优雅退出,释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
			ch.pipeline()
					.addLast(new FixedLengthFrameDecoder(20))
//					.addLast(new DelimiterBasedFrameDecoder(1024, buf))
					.addLast(new StringDecoder())
					.addLast(new EchoServerHandler());
		}
	}

	static class EchoServerHandler extends ChannelHandlerAdapter {

		private int counter ;

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			String body = (String) msg;
			System.out.println("The Echo Server Receive Order : " + body + " the count is " + ++counter);
//			body += "$_";
//			ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
//			ctx.writeAndFlush(resp);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}
	}

	public static void main(String[] args) {
		try {
			new EchoServer().bind(8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
