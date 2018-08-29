package com.mafashen.sdg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ComponentScan(basePackages = "com.sdg")
public class Cast {

	public static List<OuterCategory> cast(List<MeituanCategory> meituanCategories){
		List<OuterCategory> outerCategories = new ArrayList<>();

		for (MeituanCategory meituanCategory : meituanCategories) {
			OuterCategory outerCategory = new OuterCategory();
			cast(meituanCategory , outerCategory);
			outerCategories.add(outerCategory);
		}

		return outerCategories;
	}

	public static void cast (MeituanCategory cat , OuterCategory category){
		category.setOuterId(Integer.toString(cat.getSequence()));
		category.setName(cat.getName());

		if (cat.getChildren() != null && !cat.getChildren().isEmpty()){
			category.setChildren(new ArrayList<OuterCategory>());
			for (MeituanCategory meituanCategory : cat.getChildren()) {
//				OuterCategory sub = new OuterCategory();
//				cast(meituanCategory , sub);
				category.getChildren().add(cast(meituanCategory));
			}
		}
	}

	public static OuterCategory cast (MeituanCategory cat ){
		OuterCategory category = new OuterCategory();
		category.setOuterId(Integer.toString(cat.getSequence()));
		category.setName(cat.getName());

		if (cat.getChildren() != null && !cat.getChildren().isEmpty()){
			category.setChildren(new ArrayList<OuterCategory>());
			for (MeituanCategory meituanCategory : cat.getChildren()) {
				category.getChildren().add(cast(meituanCategory));
			}
		}
		return category;
	}

	public static void main(String[] args){
		MeituanCategory m1 = new MeituanCategory();
		m1.setName("纯水饮料");
		m1.setSequence(1);
		MeituanCategory m2 = new MeituanCategory();
		m2.setSequence(2);
		m2.setName("饮用水");
		m1.setChildren(new ArrayList<MeituanCategory>());
		m1.getChildren().add(m2);

		OuterCategory out = new OuterCategory();
		cast(m1 , out);
		//System.out.println(out);

		System.out.println(cast(Arrays.asList(m1 , m2 )));
	}
}

@Getter
@Setter
class MeituanCategory{
	String name;
	int sequence;
	List<MeituanCategory> children;
}

@Setter
@Getter
class OuterCategory{
	String outerId;
	String name;
	List<OuterCategory> children;

	@Override
	public String toString() {
		return "outerId : " + outerId + " name : " + name + " children : " + children;
	}
}
