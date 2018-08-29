package com.mafashen.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;


public class NonBlockingSingleFileHttpServer {

	private ByteBuffer contentBuffer;
	private int port = 80;

	public NonBlockingSingleFileHttpServer(ByteBuffer data , String encoding , String MIMETye , int port){
		this.port = port;
		String header = "HTTP/1.0 200 OK\r\n"
				+ "Server: NonBlockingSingleFileHttpServer\r\n"
				+ "Content-length: " + data.limit() + "\r\n"
				+ "Content-type: " + MIMETye + "\r\n\r\n" ;
		byte[] headerData = header.getBytes(Charset.forName("US-ASCII"));
		ByteBuffer buffer = ByteBuffer.allocate(data.limit() + headerData.length);
		buffer.put(headerData);
		buffer.put(data);
		buffer.flip();
		this.contentBuffer = buffer;
	}

	public void run() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverSocketChannel.socket();
		InetSocketAddress localPort = new InetSocketAddress(port);
		serverSocket.bind(localPort);
		serverSocketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while(true){
			selector.select();
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while (keys.hasNext()){
				SelectionKey key = keys.next();
				keys.remove();

				try {
					if (key.isAcceptable()){
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);
					}else if (key.isWritable()){
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (buffer.hasRemaining()){
							channel.write(buffer);
						}else{
							channel.close();
						}
					}else if (key.isReadable()){
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(4096);
						channel.read(buffer);
						buffer.flip();
						byte[] bytes = new byte[100];
						buffer.get(bytes);
						System.out.println(new String(bytes));
						key.interestOps(SelectionKey.OP_WRITE);
						key.attach(contentBuffer.duplicate());
					}
				} catch (IOException e) {
					key.cancel();
					key.channel().close();
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			Path file = FileSystems.getDefault().getPath("/Users/mafashen/Documents/LockCallback.java");
			byte[] bytes = Files.readAllBytes(file);
			ByteBuffer data = ByteBuffer.wrap(bytes);
			NonBlockingSingleFileHttpServer httpServer =
					new NonBlockingSingleFileHttpServer(data, "UTF-8", "text/json", 8080);
			httpServer.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
