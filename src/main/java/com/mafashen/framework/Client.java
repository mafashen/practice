package com.mafashen.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.alibaba.fastjson.JSON;

public class Client {

	public void invoke(String serviceName , String methodName , Object[] args){
		try {
			//配置文件 服务提供地址
			Socket socket = new Socket("127.0.0.1" , 9999);

			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();

			Request request = buildRequest(serviceName, methodName, 1, args);
			outputStream.write(JSON.toJSONString(request).getBytes());
			outputStream.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			byte[] bytes = new byte[10240];
			inputStream.read(bytes);
			String s = new String(bytes);
			System.out.println("response " + s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Request buildRequest(String serviceName , String methodName , Integer version , Object[] args ){
		Request request = new Request();
		request.setServiceName(serviceName);
		request.setMethodName(methodName);
		request.setArgs(args);
		request.setVersion(version);

		if (args != null && args.length > 0){
			Class[] parametersType = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				parametersType[i] = args[i].getClass();
			}
			request.setParametersType(parametersType);
		}
		return request;
	}

	public static void main(String[] args) {
		Client client = new Client();
		//如果调用的方法是服务注册表中的服务, 转换成类似形式处理 , 或者生成远程对象的代理,通过代理调用远程对象(RMI RPC)
		client.invoke("com.mafashen.framework.service.CalculateService" , "add" , new Object[]{1 ,1});
	}
}
