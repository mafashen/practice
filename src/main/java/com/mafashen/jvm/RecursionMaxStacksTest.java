package com.mafashen.jvm;

/**
 * 递归函数的max_stacks测试
 */
public class RecursionMaxStacksTest {

	/*
		public long recursion(long);
		descriptor: (J)J
		flags: ACC_PUBLIC
		Code:
      stack=6(操作栈最大深度), locals=3, args_size=2
         0: lload_1                           //将第二个long型本地变量推送至栈顶
         1: dup2                              //复制栈顶一个long类型元素，并将复制值压入栈顶
         2: lconst_1                          //将long型1推送至栈顶
         3: lsub                              //将栈顶两long型数值相减并将结果压入栈顶
         4: lstore_1                          //将栈顶long型数值存入第二个本地变量
         5: lconst_0                          //将long型0推送至栈顶
         6: lcmp                              //比较栈顶两long型数值的大小，并将结果（0，-1，1）压入栈顶
         7: ifle          16                  //当栈顶int型数值小于或等于0时跳转
        10: aload_0                           //将第一个引用类型本地变量推送至栈顶
        11: lload_1                           //将第二个long型本地变量推送至栈顶
        12: invokevirtual #2                  //调用实例方法 #2
        15: lreturn                           //从当前方法返回long
        16: lconst_0                          //将long型0推送至栈顶
        17: lreturn                           //从当前方法返回long
      LineNumberTable:
        line 9: 0
        line 10: 10
        line 12: 16
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      18     0  this   Lcom/mafashen/jvm/RecursionMaxStacksTest;
            0      18     1     a   J
      StackMapTable: number_of_entries = 1
        frame_type = 16
	 */
	public long recursion(long a){
		if (a-- > 0){
			return recursion(a);
		}else{
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println(new RecursionMaxStacksTest().recursion(10));
	}
}
