package com.mafashen.spring.custom.editor;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义属性编辑器
 * @author mafashen
 * created on 2018/12/1.
 */
public class DatePropertyEditor extends PropertyEditorSupport {
	private String format = "yyyy-MM-dd";

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("format text to date : " + text);

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Date date = sdf.parse(text);
			this.setValue(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
