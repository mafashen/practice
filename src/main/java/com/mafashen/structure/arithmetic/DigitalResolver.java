package com.mafashen.structure.arithmetic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Scanner;

/**
 * 数字分解
 * @author mafashen
 * @since 2019-11-25.
 */
@Slf4j
public class DigitalResolver {

    public static void main(String[] args) {
        Integer ret = 0;
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        for (int i = input - 1; i >= 2; i--) {

            ret ++ ;
            for (int j = input - i; j > 1 ; j--) {
                System.out.println("input = " + i + " + " + j);
                resolve(j);
            }
        }
//        System.out.println(ret);
    }

    public static void resolve(int input){
        for (int i = input-1; i > 1 ; i--) {
            System.out.print(input + " =  + " + i);
            resolve(i);
        }
    }

    @Test
    public void testLog(){
        try {
            int i = 1/0;
        } catch (Exception e) {
        }
    }
}
