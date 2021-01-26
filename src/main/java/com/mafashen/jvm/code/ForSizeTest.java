package com.mafashen.jvm.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mafashen
 * @since 2021-01-06.
 */
public class ForSizeTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i);
        }
        for (int i = 0, length = list.size(); i < length; i++) {
            System.out.println(i);
        }
    }
}
