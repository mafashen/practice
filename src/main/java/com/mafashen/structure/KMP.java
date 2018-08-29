package com.mafashen.structure;

public class KMP {

	public static void getNext(char[] t , int[] next){
		int i = 1 , j = 0;
		next[1] = 0;
		while(i < 9){
			if (j == 0 || t[i] == t[j]){
				++i;
				System.out.print("i="+i);
				++j;
				System.out.print(" , j="+j);
				next[i] = j;
				System.out.println(" , next["+i+"] = " + j);
			}else{
				System.out.printf("\tj=%d , next[%d]=%d" , j , j , next[j]);
				j = next[j];
				System.out.println(" , j = " + j);
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		String t = "ababaaaba";
		char[] chars = {'9' , 'a' , 'b' , 'a', 'b' ,'a' , 'a' , 'a' , 'b' , 'a'};
		int next[] = new int[10];
		getNext( chars, next);
		for (int i : next) {
			System.out.print(i);
		}
	}
}
