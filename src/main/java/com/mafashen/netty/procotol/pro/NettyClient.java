package com.mafashen.netty.procotol.pro;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mafashen.netty.procotol.pro.codec.NettyMessageDecoder;
import com.mafashen.netty.procotol.pro.codec.NettyMessageEncoder;
import com.mafashen.netty.procotol.pro.handler.HandShakeRequestHandler;
import com.mafashen.netty.procotol.pro.handler.HeartRequestHandler;

public class NettyClient {

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	private EventLoopGroup group;

	public void connect(final String ip , final int port) throws InterruptedException {
		group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
						ch.pipeline().addLast("MessageEncode", new NettyMessageEncoder());
						ch.pipeline().addLast("ReadTimeOutHandler", new ReadTimeoutHandler(50));
						ch.pipeline().addLast("HandShakeHandler", new HandShakeRequestHandler());
						ch.pipeline().addLast("HeartBeatHandler", new HeartRequestHandler());
					}
				});
		//发起异步连接
		try {
			ChannelFuture channelFuture = b.connect(
					new InetSocketAddress(ip, port),
					new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT)).sync();
			System.out.println("Client Connect To Remote Server");
			//等待关闭,连接没有关闭前,会一直在这里,关闭后执行finally语句块
			channelFuture.channel().closeFuture().sync();
		} finally {
			//重连
			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(5);
						System.out.println("Re-Connect");
						connect(ip, port);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new NettyClient().connect(NettyConstant.REMOTEIP, NettyConstant.PORT);
	}
}
