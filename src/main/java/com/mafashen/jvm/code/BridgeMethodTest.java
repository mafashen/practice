package com.mafashen.jvm.code;

/**
 * @author mafashen
 * created on 2018/11/18.
 */
public class BridgeMethodTest {

	interface Cusomer {
		boolean isVIP();
	}

	static class Merchant {
		public Number actionPrice(double price, Cusomer cusomer) {
			return 1;
		}
	}

/*
NaiveMerchant:actionPrice(DLcom/mafashen/jvm/invoke/BridgeMethodTest$Cusomer;)Ljava/lang/Double;
Merchant:	actionPrice(DLcom/mafashen/jvm/invoke/BridgeMethodTest$Cusomer;)Ljava/lang/Number;
子类重写父类的方法,但是返回类型时父类的子类,jvm会生成桥接方法
0 aload_0
1 dload_1
2 aload_3
3 invokevirtual #5 <com/mafashen/jvm/invoke/BridgeMethodTest$NaiveMerchant.actionPrice>
6 areturn

public java.lang.Number actionPrice(double, com.mafashen.jvm.code.BridgeMethodTest$Cusomer);
    descriptor: (DLcom/mafashen/jvm/invoke/BridgeMethodTest$Cusomer;)Ljava/lang/Number;
    flags: ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
    Code:
      stack=4, locals=4, args_size=3
         0: aload_0
         1: dload_1
         2: aload_3
         3: invokevirtual #5   // Method actionPrice:(DLcom/mafashen/jvm/invoke/BridgeMethodTest$Cusomer;)Ljava/lang/Double;
         6: areturn
      LineNumberTable:
        line 19: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       7     0  this   Lcom/mafashen/jvm/invoke/BridgeMethodTest$NaiveMerchant;
 */
	static class NaiveMerchant extends Merchant {
		@Override
		public Double actionPrice(double price, Cusomer cusomer) {
			return 2.0;
		}
	}
	static class Merchant2<T extends Cusomer> {
		public double actionPrice(double price, T cusomer) {
			return 3.0;
		}
	}

	static class VIP implements Cusomer {
		@Override
		public boolean isVIP() {
			return true;
		}
	}

/*
父类方法签名:actionPrice(DLcom/mafashen/jvm/invoke/BridgeMethodTest$Cusomer;)D
子类方法签名:actionPrice(DLcom/mafashen/jvm/invoke/BridgeMethodTest$VIP;)D
不符合重写的定义,为了保证编译而成的 Java 字节码能够保留重写的语义，Java 编译器额外添加了一个桥接方法。
该 桥接方法在字节码层面重写了父类的方法，并将调用子类的方法。

class 文件里允许出现两个同名、同参数类型但是不同返回类型的方法。
这里的原 方法和桥接方法便是其中一个例子。由于该桥接方法同样标注了 ACC_SYNTHETIC，因此，
当在 Java 程序中调用 NaiveMerchant.actionPrice 时，我们只会调用到原方法

桥接方法 access_flags : public synthetic(对于源代码不可见) bridge
0 aload_0
1 dload_1
2 aload_3
3 checkcast #5 <com/mafashen/jvm/invoke/BridgeMethodTest$VIP>
6 invokevirtual #6 <com/mafashen/jvm/invoke/BridgeMethodTest$VIPOnlyMerchant.actionPrice>
9 dreturn
 */
	static class VIPOnlyMerchant  extends Merchant2<VIP> {
		@Override
		public double actionPrice(double price, VIP cusomer) {
			return 4.0;
		}
	}

	public static void main(String[] args) throws Exception {
		Merchant2 vip = new VIPOnlyMerchant();
		Cusomer cusomer = new VIP();
//		vip.actionPrice(cusomer);  桥接方法对于源码不可见
		System.out.println(vip.getClass().getMethod("actionPrice", Cusomer.class).invoke(vip, cusomer));;
		NaiveMerchant naiveMerchant = new NaiveMerchant();
	}
}
