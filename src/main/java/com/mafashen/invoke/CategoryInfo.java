package com.mafashen.invoke;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInfo {

	Long id;
	Long pid;
	String categoryName;
	Integer categoryStatus;
	Integer categoryLevel;
	String fullPath;

	public CategoryInfo(){

	}

	public CategoryInfo(Long id){
		this.id = id;
	}

	@Override
	public String toString() {
		return "[ id:" + id + "-name:" + categoryName + "-status:" + categoryStatus + "-parentId:" + pid + "]";
	}
}
