package com.mafashen.invoke;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

	String code ;
	String msg;
	Data data;

	@Getter
	@Setter
	static  class  Data{
		String code;
		String msg;
		List<CategoryInfo> result;
	}

	boolean success;
}
