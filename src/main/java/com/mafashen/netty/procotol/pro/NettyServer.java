package com.mafashen.netty.procotol.pro;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import com.mafashen.netty.procotol.pro.codec.NettyMessageDecoder;
import com.mafashen.netty.procotol.pro.codec.NettyMessageEncoder;
import com.mafashen.netty.procotol.pro.handler.HandShakeResponseHandler;
import com.mafashen.netty.procotol.pro.handler.HeartResponseHandler;

public class NettyServer {

	public void bind(String ip, int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap server = new ServerBootstrap();
		server.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG,100)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
						ch.pipeline().addLast("MessageEncode", new NettyMessageEncoder());
						ch.pipeline().addLast("ReadTimeOutHandler", new ReadTimeoutHandler(50));
						ch.pipeline().addLast("HandShakeHandler", new HandShakeResponseHandler());
						ch.pipeline().addLast("HeartBeatHandler", new HeartResponseHandler());
					}
				});
		try {
			server.bind(ip, port).sync();
			System.out.printf("NettyServer started , listening at port : %d \n" , port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new NettyServer().bind(NettyConstant.REMOTEIP, NettyConstant.PORT);
	}
}
