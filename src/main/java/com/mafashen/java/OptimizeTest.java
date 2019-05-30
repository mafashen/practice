package com.mafashen.java;

/**
 * @author mafashen
 * created on 2019/5/4.
 */
public class OptimizeTest {
	static long start, end, end2;;


	private static void lower1(String s){
		int i ;
		for(i =0; i < s.length(); i++){
			if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
				s.charAt(i);
			}
		}
	}

	private static void lower2(String s){
		int len = s.length();
		for(int i =0; i < len; i++){
			if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
				s.charAt(i);
			}
		}
	}

	/**
	 * for循环内方法调用消耗测试
	 * 不过java中String的length方式是直接读取的value字段,没什么差异
	 */
	static void methodCallExpend(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 102400000; i++) {
			sb.append((i%26) + 'A');
		}
		start = System.currentTimeMillis();
		lower1(sb.toString());
		end = System.currentTimeMillis();
		lower2(sb.toString());
		end2 = System.currentTimeMillis();
		System.out.println("low1 takes : " + (end - start));
		System.out.println("low2 takes : " + (end2 - end));
	}

	static int sumarrayrows(int a[][]){
		int sum = 0;
		for (int i = 0; i < a.length; ++i)
		{
			for (int j = 0; j < a[i].length; ++j)
			{
				sum += a[i][j];
			}
		}
		return sum;
	}

	static int sumarraycols(int a[][]){
		int sum = 0;
		for (int j = 0; j < a.length; ++j)
		{
			for (int i = 0; i < a[j].length; ++i)
			{
				sum += a[i][j];
			}
		}
		return sum;
	}

	/**
	 * 局部性验证
	 */
	static void locality(){
		int n = 1024 * 16;
		int[][] a = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = i+j;
			}
		}

		//局部性远离验证
		start = System.currentTimeMillis();
		sumarrayrows(a);
		end = System.currentTimeMillis();
		sumarraycols(a);
		end2 = System.currentTimeMillis();

		System.out.println("sumarrayrows takes : " + (end - start));	//137,132,147
		System.out.println("sumarraycols takes : " + (end2 - end));		//10931,9209,9547
	}

	static void hit(int[] a, int[] b){
		int sum =0;
		for (int i = 0; i < b.length; i++) {
			if (i < a.length){
				sum += a[i] * b[i];
			}else{
				sum +=  b[i];
			}
		}
	}

	/**
	 * 缓存冲突不命中测试
	 */
	static void cachingHit(){
		int a[] = {1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6};
		int n = 1024*640;
		int b[] = new int[n];
		for (int i = 0; i < n; i++) {
			b[i] = i;
		}

		start = System.currentTimeMillis();
		hit(a, b);
		end = System.currentTimeMillis();
		System.out.println("hit1 takes : " + (end - start));

		int a1[] = {1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8};
		start = System.currentTimeMillis();
		hit(a1, b);
		end = System.currentTimeMillis();
		System.out.println("hit2 takes : " + (end - start));
	}

	public static void main(String[] args) {
		cachingHit();
	}
}
