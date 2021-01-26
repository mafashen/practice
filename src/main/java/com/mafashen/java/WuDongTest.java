package com.mafashen.java;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author mafashen
 * @since 2021-01-07.
 */
public class WuDongTest {

    /*
    问题1：// 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。 返回 s 所有可能的分割方案。
     */
    public static List<String> subHuiWenStr(String s){
        if (s == null || s.length() == 0){
            return Collections.emptyList();
        }
        List<String> subList = new LinkedList<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            // FIXME 一个字符算不算回文串, 算的话 j=i, 不算的话 j = i+1
            for (int j = i; j < length; j++) {
                String substring = s.substring(i, j + 1);
                if (isHuiWenStr(substring)){
                    subList.add(substring);
                }
            }
        }
        return subList;
    }

    public static boolean isHuiWenStr(String s){
        for (int i = 0, j = s.length()-1; i <= j ; i++, j--) {
            if (s.charAt(i) != s.charAt(j)){
                return false;
            }
        }
        return true;
    }

    /*
    问题2：用至少两种方法，编写函数用于打印一个Map所有的key和value。
    */
    public static void printMap(Map<String, String> map){
        if (map == null || map.size() == 0){
            return;
        }

        for (String s : map.keySet()) {
            System.out.println("key= " + s + "\t value= " + map.get(s));
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + "\t value= " + entry.getValue());
        }

        Iterator<String> keyIter = map.keySet().iterator();
        while (keyIter.hasNext()){
            String nextKey = keyIter.next();
            System.out.println("key= " + nextKey + "\t value= " + map.get(nextKey));
        }

        Iterator<Map.Entry<String, String>> entryIter = map.entrySet().iterator();
        while (entryIter.hasNext()){
            Map.Entry<String, String> entry = entryIter.next();
            System.out.println("key= " + entry.getKey() + "\t value= " + entry.getValue());
        }
    }

    /*
    问题3：实现strStr()，返回在字符串needle在字符串haystack中第一次出现的位置，如果没有找到则返回-1；如果needle为空。不可以使用String.index()来实现。
     */
    public static int strStr(String hayStack, String needle){
        if (hayStack == null || hayStack.length() == 0 || needle == null || needle.length() == 0){
            return -1;
        }

        int hLen = hayStack.length();
        int nLen = needle.length();
        int len = hLen - nLen;
        for (int i = 0; i <= len; i++) {
            // 第一个匹配的字符
            while(i <= len && hayStack.charAt(i) != needle.charAt(0)) i++;
            if (i >= len){
                break;
            }

            int k=i;
            boolean match = true;
            for (int j = 0; j < nLen; j++) {
                // 循环展开, 同时对比两位, 可以同时展开多位进行对比
                if (hayStack.charAt(k++) != needle.charAt(j) || ( j < nLen-1 && hayStack.charAt(k) != needle.charAt(j+1))){
                    match = false;
                    break;
                }
            }
            if(match){
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(strStr("aacbaaaacbaaaacbaaaacbaaaacbaaaacbaaadcbaa", "d"));
    }
}
