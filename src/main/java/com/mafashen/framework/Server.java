package com.mafashen.framework;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class Server {

	private ConcurrentHashMap<String , String/*impl*/> registerServices = null;

	private void init(){
		registerServices = new ConcurrentHashMap<>();
		//配置文件
		registerService("com.mafashen.framework.service.CalculateService" , "com.mafashen.framework.service.impl.CalculateServiceImpl");
	}

	private void accept(){

		//启动SocketServer监听
		System.out.println("server accept request ... ");
		try (ServerSocket serverSocket = new ServerSocket(9999)) {
			while (true) {
				Socket socket = serverSocket.accept();

				InputStream inputStream = socket.getInputStream();
				byte[] bytes = new byte[10240];
				inputStream.read(bytes);
				String requestStr = new String(bytes);
				System.out.println("receive request " + requestStr);

				Request request = JSON.parseObject(requestStr, Request.class);
				checkRequest(request);
				Class<?> service = getServiceByNameAndVersion(request.getServiceName(), request.getVersion());
				Object result = callMethod(service, request.getMethodName(), request.getParametersType(), request.getArgs());
				System.out.println(JSON.toJSONString(result));
				socket.getOutputStream().write(JSON.toJSONString(result).getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Class<?> getServiceByNameAndVersion(String serviceName , Integer version){
		String impl = registerServices.get(serviceName + (version == null ? "1" : version));
		if (StringUtils.isBlank(impl)) {
			throw new RuntimeException("this service is not register !");
		}
		Class<?> service = null;
		try {
			service = Class.forName(impl);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return service;
	}

	private Object callMethod(Class<?> service , String methodName , Class[] parametersType , Object[] args){
		Object invoke = null;
		try {
			Method method = service.getMethod(methodName, parametersType);
			//TODO singleton
			invoke = method.invoke(service.newInstance(), args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return invoke;
	}

	private void checkRequest(Request request) throws NullPointerException{
		if (request == null)
			throw new NullPointerException();
		if (StringUtils.isBlank(request.getServiceName()))
			throw new RuntimeException("service name can't be empty!");
		if (StringUtils.isBlank(request.getMethodName()))
			throw new RuntimeException("method name can't be empty!");
	}

	public void registerService(String serviceName , String impl){
		registerService(serviceName, 1 , impl);
	}

	public void registerService(String serviceName , int version , String impl){
		registerServices.put(serviceName + version , impl);
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.init();
		server.accept();
	}
}
