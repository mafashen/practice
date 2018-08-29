package com.mafashen.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChargenServer {

	public static void main(String[] args) {
		byte[] rotation = new byte[95 * 2];
		for (byte i = ' '; i < '~'; i++) {
			rotation[i - ' '] = i;
			rotation[i + 95 - ' '] = i;
		}

		ServerSocketChannel serverChannel;
		Selector selector;

		try{
			serverChannel = ServerSocketChannel.open();
			//通道(信道) 绑定到指定端口
			serverChannel.bind(new InetSocketAddress(1314));
			//非阻塞模式
			serverChannel.configureBlocking(false);
			//开启选择器
			selector = Selector.open();
			//向Selector注册,Selector会监视相应的通道,这里需要监视socket-accept信息
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		}catch (IOException e){
			e.printStackTrace();
			return;
		}

		while (true){
			try{
				//检查是否有可操作的数据(就绪的通道),可以确保如果连接没有准备好接受数据,就绝不会在这些连接上浪费时间
				selector.select();
			}catch (IOException e){
				e.printStackTrace();
				break;
			}

			//选择器找到就绪通道,selectedKeys方法返回所有就绪的SelectionKey
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()){
				SelectionKey key = iterator.next();
				//从就绪的SelectionKey中删除这个key,从而不会重复处理
				iterator.remove();

				try {
					//测试此键的通道是否已准备好接受新的套接字连接
					//如果就绪的是服务器通道,接受一个新Socket通道,将其添加到选择器
					if (key.isAcceptable()){
						//返回为之创建此键的通道
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						System.out.println("Accepted request from client : " + client);
						//客户端使用非阻塞模式
						client.configureBlocking(false);
						//向选择器注册,Selector会监视相应的通道,这里需要监视write信息
						SelectionKey writeKey = client.register(selector, SelectionKey.OP_WRITE);

						ByteBuffer buffer = ByteBuffer.allocate(74);
						buffer.put(rotation, 0, 72);
						buffer.put((byte) '\r');
						buffer.put((byte) '\n');
						//回绕缓冲区,输出通道会从所读取数据的开头而不是末尾开始写入
						buffer.flip();
						//将给定的对象附加到此键
						writeKey.attach(buffer);
					}
					//就绪的是Socket通道,向通道写入缓冲区中尽可能多的数据
					else if (key.isWritable()){
						//返回为之创建此键的通道
						SocketChannel client = (SocketChannel) key.channel();
						//获取当前Key的附加对象
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						//在当前位置和限制之间没有元素
						if (!buffer.hasRemaining()){
							//重绕此缓冲区,用下一行重新填充缓冲区
							buffer.rewind();
							//得到上一次的首字符
							int first = buffer.get();
							//准备改变缓冲区中的数据
							buffer.rewind();
							//寻找rotation中新的首字符位置
							int position = first - ' ' + 1;
							//将数据从rotation复制到缓冲区
							buffer.put(rotation, position , 72);
							//在缓冲区末尾存储一个行页分隔符
							buffer.put((byte) '\r');
							buffer.put((byte) '\n');
							//准备缓冲区进行写入
							buffer.flip();
						}
						client.write(buffer);
					}
				} catch (IOException e) {
					//取消这个key
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
	}
}
