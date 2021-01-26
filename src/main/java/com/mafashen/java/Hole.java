package com.mafashen.java;

import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mafashen
 * @since 2019-08-16.
 */
public class Hole {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(i);
            set.remove(i-1);
        }

        System.out.println(set.size());//1

        Set<Short> shortSet = new HashSet<>();
        for (short i = 0; i < 100; i++) {
            shortSet.add(i);
            shortSet.remove(i-1); //remove Integer
        }
        System.out.println(shortSet.size()); //100

        //优先级 == > ?: > =
        Object i = 1 == 1 ? new Integer(3) : new Float(1); //双目运算符类型提升
        System.out.println(i); //3.0

        // id, name
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "1111");
        map.put(2L, "2222");
        map.put(3L, "3333");

        // 后面需要调用的一个方法入参是long[] 类型，
        // map.keySet()可以拿到id的列表，toArray方法可以转化数组
        // ArrayUtils.toPrimitive方法可以把对象数据转化为基本类型数组
        long[] ids = ArrayUtils.toPrimitive((Long[])map.keySet().toArray()); //toArray 返回Object[]
    }
}
