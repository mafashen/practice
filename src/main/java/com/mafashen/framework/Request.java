package com.mafashen.framework;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Request implements Serializable{

	public String serviceName;

	public String methodName;

	public Object[] args;

	private Class[] parametersType;

	public int version;

	public Request() {
	}

	public Request(String serviceName, String methodName, Object[] args, Class[] parametersType, int version) {
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.args = args;
		this.parametersType = parametersType;
		this.version = version;
	}

}
