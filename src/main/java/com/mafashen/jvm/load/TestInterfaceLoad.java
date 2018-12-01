package com.mafashen.jvm.load;

public class TestInterfaceLoad {

	public static void main(String[] args) {
		SubInterface sub = new SubImpl();
	}
}

interface SuperInterface1{
	String SUPER_CONST1 = "SUPER_CONST1";
}

interface SuperInterface2{
	String SUPER_CONST2 = "SUPER_CONST2";
}

interface SubInterface extends SuperInterface1, SuperInterface2{

}

/*
[Loaded com.mafashen.jvm.load.SuperInterface1 from file:/Users/mafashen/IdeaProjects/practice/target/classes/]
[Loaded com.mafashen.jvm.load.SuperInterface2 from file:/Users/mafashen/IdeaProjects/practice/target/classes/]
[Loaded com.mafashen.jvm.load.SubInterface from file:/Users/mafashen/IdeaProjects/practice/target/classes/]
[Loaded com.mafashen.jvm.load.SubImpl from file:/Users/mafashen/IdeaProjects/practice/target/classes/]
Sub class init...
 */
class SubImpl implements SubInterface{
	static {
		System.out.println("Sub class init...");
		//接口在初始化时,并不要求其父接口全部都完成了初始化,
		// 只有在真正使用到父接口的时候(如引用接口中定义的常量)才会初始化
		System.out.println(SUPER_CONST1);
	}
}
