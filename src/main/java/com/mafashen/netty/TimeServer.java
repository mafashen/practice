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
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.util.Date;

public class TimeServer {

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
					.childHandler(new ChildChannelHandler());		//IO事件处理类
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

	static class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline()
					.addLast(new LineBasedFrameDecoder(1024))
					.addLast(new StringDecoder())
					.addLast(new TimeServerHandler());
		}
	}

	static class TimeServerHandler extends ChannelHandlerAdapter {

		private int counter ;

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//			ByteBuf buf = (ByteBuf) msg;
//			byte[] req = new byte[buf.readableBytes()];
//			buf.readBytes(req);
//			String body = new String(req, "UTF-8")
//								.substring(0, req.length - System.getProperty("line.separator").length());
			String body = (String) msg;
			System.out.println("The Time Server Receive Order : " + body);
			String currentTime =
					"QUERY TIME ORDER".equalsIgnoreCase(body) ?
							new Date(System.currentTimeMillis()).toString() : "Bad Order";
			System.out.println("The Counter is " + ++counter);
			currentTime = currentTime + System.getProperty("line.separator");
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			ctx.writeAndFlush(resp);
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
			new TimeServer().bind(8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
