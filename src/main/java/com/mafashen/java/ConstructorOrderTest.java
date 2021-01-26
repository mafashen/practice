package com.mafashen.java;

/**
 * @author mafashen
 * @since 2019-10-28.
 */
public class ConstructorOrderTest {

    static class Father{
        static {
            System.out.println("Father 静态构造块");
        }
        {
            System.out.println("Father 构造块");
        }
        Father(){
            System.out.println("Father 构造函数");
        }
    }

    static class Son extends Father{
        static {
            System.out.println("Son 静态构造块");
        }
        {
            System.out.println("Son 构造块");
        }
        Son(){
            System.out.println("Son 构造函数");
        }
    }

    public static void main(String[] args) {
        /*
        Father 静态构造块        先加载父类           初始化阶段 执行 <cinit>
        Son 静态构造块           再加载子类
        Father 构造块            执行父类          new init方法 先于构造函数
        Father 构造函数          执行父类 构造方法
        Son 构造块               执行子类 init 方法
        Son 构造函数             执行子类 构造方法
         */
        System.out.println(new Son());
    }
}
