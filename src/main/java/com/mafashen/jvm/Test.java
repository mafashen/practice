package com.mafashen.jvm;

/**
 * @author mafashen
 * @since 2019-10-31.
 */
public class Test {
    // t1 在1.7之前位于perm区,instanceKlass末尾, 之后位于heap区Class末尾
    static Test2 t1 = new Test2();

    Test2 t2 = new Test2();

    public void fn(){
        Test2 t3 = new Test2();
        for(;;);
    }
}

class Test2 {}

