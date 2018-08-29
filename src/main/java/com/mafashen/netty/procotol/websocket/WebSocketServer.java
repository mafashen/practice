package com.mafashen.netty.procotol.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

public class WebSocketServer {
	private static final int port = 8080;

	public void bind() {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		ServerBootstrap server = new ServerBootstrap();
		server.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("pro-codec", new HttpServerCodec());
						ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
						ch.pipeline().addLast("pro-chunked", new ChunkedWriteHandler());
						ch.pipeline().addLast("handler", new WebSocketServerHandler());
					}
				});
		try {
			Channel channel = server.bind(port).sync().channel();
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	static class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{

		private WebSocketServerHandshaker handshaker = null;

		@Override
		protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
			//传统HTTP接入
			if (msg instanceof FullHttpRequest){
				handlerHttpRequest(ctx, (FullHttpRequest) msg);
			}

			//WebSocket接入
			else if (msg instanceof WebSocketFrame){
				handlerWebSocketRequest(ctx, (WebSocketFrame) msg);
			}
		}

		private void handlerWebSocketRequest(final ChannelHandlerContext ctx, WebSocketFrame frame) {
			//判断是否关闭链路的指令
			if (frame instanceof CloseWebSocketFrame){
				handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
				return;
			}

			//判断是否ping消息
			if (frame instanceof PingWebSocketFrame){
				ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
				return;
			}

			//只支持文本消息
			if (!(frame instanceof TextWebSocketFrame)){
				throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
			}

			//返回应答消息
			final String request = ((TextWebSocketFrame) frame).text();
			System.out.println(String.format("%s received %s", ctx.channel(), request));

			ctx.channel().write(new TextWebSocketFrame(request + " , welcome to use Netty WebSocket service , now is " + new Date().toString()));

			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					ctx.channel().write(new TextWebSocketFrame("welcome to use Netty WebSocket service , now is " + new Date().toString()));
				}
			} , 3000);
		}

		private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
			//http解码失败或者不包含websocket头信息,返回404
			if (req.getDecoderResult().isFailure() || !"websocket".equals(req.headers().get("Upgrade"))){
				sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
				return;
			}

			//构造握手响应返回
			WebSocketServerHandshakerFactory wsFactory =
					new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
			handshaker = wsFactory.newHandshaker(req);
			if (handshaker == null){
				WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
			}else{
				//运行时会动态添加WebSocket相关的编解码类,后续handler可以直接对WebSocket对象进行操作
				handshaker.handshake(ctx.channel(), req);
			}
		}

		private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse resp) {
			if (resp.getStatus().code() != 200){
				ByteBuf buf = Unpooled.copiedBuffer(resp.getStatus().toString(), CharsetUtil.UTF_8);
				resp.content().writeBytes(buf);
				buf.release();
				setContentLength(resp, resp.content().readableBytes());
			}

			ChannelFuture future = ctx.channel().writeAndFlush(resp);
			//请求不是keep-alive 或者 响应不是200,关闭连接
			if (!isKeepAlive(req) || resp.getStatus().code() != 200){
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}
	}

	public static void main(String[] args) {
		new WebSocketServer().bind();
	}
}
