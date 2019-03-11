package com.mafashen.spring.custom.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @author mafashen
 * created on 2018/12/2.
 */
public class String2DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String text) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(text);
		} catch (ParseException e) {
			return null;
		}
	}
}
