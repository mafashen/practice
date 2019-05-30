package com.mafashen.structure.arithmetic.leetcode;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mafashen
 * created on 2019/4/7.
 */
public class LeetCode {

	//两数相加
	public int[] twoSum(int[] nums, int target) {
		for(int i=0; i<nums.length-2; i++){
			for(int j=i+1; j<nums.length-1; j++){
				if(nums[i] + nums[j] == target){
					int[] sol = new int[2];
					sol[0] = i;
					sol[1] = j;
					return sol;
				}
			}
		}
		return null;
	}

	public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }

		public void print() {
			System.out.print(val);
			if (next != null){
				next.print();
			}else{
				System.out.println();
			}
		}
	}

	//执行用时 : 11 ms, 在Add Two Numbers的Java提交中击败了100.00% 的用户
	//内存消耗 : 39.6 MB, 在Add Two Numbers的Java提交中击败了64.07% 的用户
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode ret = new ListNode(0);
		//进位
		int cub = 0;
		ListNode curNode1 = l1;
		ListNode curNode2 = l2;
		ListNode next = ret;
		while(curNode1 != null || curNode2 != null || cub > 0){
			int curVar1 = curNode1 == null ? 0 : curNode1.val;
			int curVar2 = curNode2 == null ? 0 : curNode2.val;
			int val = (curVar1 + curVar2 + cub) % 10;
			cub = curVar1 + curVar2 + cub >= 10 ? 1 : 0;
			next.next = new ListNode(val);
			next = next.next;
			curNode1 = curNode1 == null ? null : curNode1.next;
			curNode2 = curNode2 == null ? null : curNode2.next;
		}

		return ret.next;
	}

	//执行用时 : 535 ms, 在Longest Substring Without Repeating Characters的Java提交中击败了2.22% 的用户
	//内存消耗 : 66.3 MB, 在Longest Substring Without Repeating Characters的Java提交中击败了8.34% 的用户
	public static int lengthOfLongestSubstring(String s) {
		char[] chars = s.toCharArray();
		int  max=0;
		Set<Character> set = new HashSet<>(s.length());
		//pwwkew -> wke
		if (chars.length == 1){
			return 1;
		}
		int i =0, j=0;
		//"abcabcbb"
		while(i < chars.length && j < chars.length){
			if (!set.contains(chars[j])) {
				set.add(chars[j++]);
				if (set.size() > max){
					max = set.size();
				}
			}else{
				set.remove(chars[i++]);
			}
		}
		return max;
	}

	//思路:根据个数总和,求出是去第 k 大的数, 或者  l+r/2
	//执行用时 : 13 ms, 在Median of Two Sorted Arrays的Java提交中击败了100.00% 的用户
	//内存消耗 : 53.4 MB, 在Median of Two Sorted Arrays的Java提交中击败了19.70% 的用户
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int k=0, r=0;
		if ((nums1.length + nums2.length) % 2 == 1){
			k = (nums1.length + nums2.length -1) / 2;
		}else{
			r = (nums1.length + nums2.length) / 2 ;
		}
		int i =0, j = 0;
		int[] all = new int[(nums1.length + nums2.length)/2+1];
		int n = 0;
		while(n < (nums1.length + nums2.length)/2+1){
			if (j >= nums2.length || (i< nums1.length && nums1[i] < nums2[j])){
				all[n] = nums1[i++];
			}else {
				all[n] = nums2[j++];
			}
			if (k > 0 && n==k){
				return all[n];
			}else if(r > 0 && n == r){
				return ((double)all[n-1] + all[n]) /2;
			}
			n++;
		}
		return all[0];
	}

	/**
	 * 最长回文子串长度
	 * 动态规划: 从 i-j 最长回文长度, 初始值 max[i][i]=1
	 * 	max[i][j] = max[i+1][j-1] + 2  if s[i]=s[j]
	 * 	max[i][j] = max(max[i][j-1],max[i+1][j]) if s[i] != s[j]
	 */
	public static int longestPalindromeLength(String s) {
		int max[][] = new int[s.length()][s.length()];

		//遍历s
		for (int j = 0; j < s.length(); j++) {
			max[j][j] = 1;
			for (int i = j-1; i >= 0 ; i--) {
				if (s.charAt(j) == s.charAt(i)){
					max[i][j] = max[i+1][j-1] + 2;
				}else{
					max[i][j] = Math.max(max[i][j-1], max[i+1][j]);
				}
			}
		}
		return max[0][s.length() - 1];
	}

	//动态规划
	//dp[i][j] = 2  (j-i=1)
	//  = 1 (s[i] != s[j])
	//  = dp[i+1][j-1] + 2 (s[i]=s[j] && j-i > 1)
	public static String longestPalindrome(String s) {
		int n = s.length();
		if(n <= 1){
			return s;
		}
		int dp[][] = new int[n][n];

		int start = 0, max = 1;
		for (int j = 1; j < n;j++){
			dp[j][j] = 1;
			//从后向前遍历,保证dp[i+1][j-1]先赋值
			for (int i = j - 1; i >= 0; i--) {
				if (s.charAt(i) == s.charAt(j)) {
					if(j-i==1)
						dp[i][j] = 2;
					else {
						//aaa -> dp[1][1]>0 dp[0][2]=1+2=3
						if (dp[i + 1][j - 1]>0) {
							dp[i][j] = dp[i + 1][j - 1] + 2;
						} else
							dp[i][j] = 0;
					}
				} else
					dp[i][j] = 0;
				if (dp[i][j]>max) {
					max = dp[i][j];
					start = i;
				}
			}
		}

		return s.substring(start,start+max);
	}

	//z字形变换
	//执行用时 : 43 ms, 在ZigZag Conversion的Java提交中击败了79.59% 的用户
	//内存消耗 : 48.7 MB, 在ZigZag Conversion的Java提交中击败了55.28% 的用户
	public static String convert(String str, int n) {
		if (str == null || str.length() == 1 || n == 1){
			return str;
		}

		char[][] c = new char[n][str.length()];
		boolean down = true;
		int i =0, j = 0;
		int maxj = 0;
		for(int l=0; l < str.length(); l++){
			if(i == 0)
				down = true;
			else if(i == n-1){
				down = false;
			}

			//System.out.printf("c[1][2] = %c \n\n", c[1][2]);
			if(down){
				System.out.printf("i=%d,j=%d, c=%c \n", i,j,str.charAt(l));
				c[i++][j] = str.charAt(l);
			}else{
				System.out.printf("i=%d,j=%d, c=%c \n", i,j,str.charAt(l));
				c[i--][j++] = str.charAt(l);
				if(j > maxj)
					maxj = j;
			}
		}
		//System.out.printf("c[1][2] = %c \n\n", c[1][2]);

		StringBuilder sb = new StringBuilder();
		char[] newStr = new char[str.length()];		//50ms
		int k = 0;
		for(int l=0; l<n; l++){
			for(int m=0; m<=maxj; m++){
				System.out.printf("i=%d,j=%d, c=%c \n", l,m,c[l][m]);

				if(c[l][m] > 0){
					sb.append(c[l][m]);
					newStr[k++] = c[l][m];
				}
			}
		}

		return sb.toString();
	}

	//整数反转 123->321
	//执行用时 : 9 ms, 在Reverse Integer的Java提交中击败了90.71% 的用户
	//内存消耗 : 35.6 MB, 在Reverse Integer的Java提交中击败了71.83% 的用户
	public static int reverse(int x) {
		boolean negative = x < 0;
		x = Math.abs(x);
		List<Integer> bits = new LinkedList<>();
		int pow = 0 ;
		while((int) x / Math.pow(10, pow) >= 1){
			bits.add((int) (x / Math.pow(10, pow++) % 10));
		}
		long revert = 0;
		for (int i = 0; i < bits.size(); i++) {
			revert += bits.get(i) * Math.pow(10, bits.size() - i-1);
		}
		return revert > Integer.MAX_VALUE ? 0 : (negative ? (int)-revert : (int)revert);
	}

	//字符串转换整数
	//执行用时 : 8 ms, 在String to Integer (atoi)的Java提交中击败了96.60% 的用户
	//内存消耗 : 37.3 MB, 在String to Integer (atoi)的Java提交中击败了68.12% 的用户
	public static int myAtoi(String str) {
		str = str.trim();
		StringBuilder sb = new StringBuilder();
		boolean nagtive = false;
		boolean first = true;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) > 47 && str.charAt(i) < 58){
				sb.append(str.charAt(i));
				first = false;
			}else if((str.charAt(i) == '-' || str.charAt(i) == '+') && first){
				nagtive = str.charAt(i) == '-';
				first = false;
			}
			else{
				break;
			}
		}
		if (sb.length() == 0){
			sb.append(0);
		}

		int ret = 0;
		try{
			ret = Integer.parseInt(nagtive ? "-" + sb.toString() : sb.toString());
		}catch (Exception e){
			ret = nagtive ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}
		return ret;
	}

	//回文数
	//执行用时 : 53 ms, 在Palindrome Number的Java提交中击败了94.72% 的用户
	//内存消耗 : 37.3 MB, 在Palindrome Number的Java提交中击败了87.78% 的用户
	public static boolean isPalindrome(int x) {
		if (x < 0){
			return false;
		}
		if (x < 10){
			return true;
		}
		//执行用时 : 53 ms, 在Palindrome Number的Java提交中击败了94.72% 的用户
		//内存消耗 : 35.7 MB, 在Palindrome Number的Java提交中击败了97.46% 的用户
		StringBuilder sb = new StringBuilder().append(x);
		return sb.toString().equals(sb.reverse().toString());
//		String str = String.valueOf(x);
//		int i = 0, j = str.length()-1;
//		while(i < j){
//			if (str.charAt(i++) != str.charAt(j--)){
//				return false;
//			}
//		}
//		return true;
	}

	//正则表达式匹配
	//执行用时 : 9 ms, 在Regular Expression Matching的Java提交中击败了95.51% 的用户
	//内存消耗 : 36.4 MB, 在Regular Expression Matching的Java提交中击败了87.12% 的用户
	//s[:i] s中从i到结尾组成的子串
	//p[:j] p中从j到结尾组成的子串
	//dp[i][j] 标识 s[:i] 与 [:j] 的匹配情况
	//dp[i][j] = curMatch && dp[i+1][j+1] (p[j+1] != * )
	//dp[i][j] = dp[i][j+2](匹配0次) || curMatch && dp[i+1][j](匹配n次) (p[j+1] == * )
	public static boolean isMatch(String s, String p) {
		int sLen = s.length(), pLen = p.length();
		boolean[][] dp = new boolean[sLen+1][pLen+1];
		dp[sLen][pLen] = true;
		for (int i = sLen; i >= 0 ; i--) {
			for (int j = pLen-1; j >= 0 ; j--) {
				boolean currentMatch = i<sLen && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
				if (j+1<pLen && p.charAt(j+1) == '*'){
					dp[i][j] = dp[i][j+2] || currentMatch && dp[i+1][j];
				}else{
					dp[i][j] = currentMatch && dp[i+1][j+1];
				}
				System.out.printf("dp[%d][%d] = %b \n", i, j, Boolean.valueOf(dp[i][j]));
			}
		}

		return dp[0][0];
	}

	//盛最多水的容器
	//执行用时 : 6 ms, 在Container With Most Water的Java提交中击败了94.44% 的用户
	//内存消耗 : 47.2 MB, 在Container With Most Water的Java提交中击败了20.91% 的用户
	public static int maxArea(int[] height) {
		int maxArea = 0;
//		boolean[][] exist = new boolean[height.length][height.length];
//		for (int i = 0; i < height.length; i++) {
//			for (int j = i+1; j < height.length; j++) {
//				if (i == j || exist[j][i]){
//					continue;
//				}
//				int area = Math.abs(i-j) * Math.min(height[i] , height[j]);
//				if (area > maxArea){
//					maxArea = area;
//				}
//				exist[i][j] =true;
//			}
//		}

		int left =0 , right = height.length -1;
		while(left <= right && right > 0){
			maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));
			//后边的高度更大,保留更高的边
			if (height[left] < height[right]){
				left++;
			}else{
				right--;
			}
		}
		return maxArea;
	}

	//整数转为罗马数字
	//执行用时 : 17 ms, 在Integer to Roman的Java提交中击败了99.70% 的用户
	//内存消耗 : 37.2 MB, 在Integer to Roman的Java提交中击败了99.64% 的用户
	public static String intToRoman(int num) {
//		StringBuilder roman = new StringBuilder();
//		//1000
//		int r1000 = num / 1000;
//		num -= r1000 * 1000;
//		if (r1000 > 0){
//			for (int i = 0; i < r1000; i++) {
//				roman.append("M");
//			}
//		}
//		//100
//		int r100 = num / 100;
//		num -= r100*100;
//		if (r100 > 0){
//			if (r100 == 9){
//				roman.append("CM");
//			}else if (r100 >= 5){
//				roman.append("D");
//				for (int i = 0; i < r100 - 5; i++) {
//					roman.append("C");
//				}
//			}else if (r100 == 4){
//				roman.append("CD");
//			}else{
//				for (int i = 0; i < r100; i++) {
//					roman.append("C");
//				}
//			}
//		}
//		//10
//		int r10 = num / 10;
//		num -= r10*10;
//		if (r10 > 0){
//			if (r10 == 9){
//				roman.append("XC");
//			}else if (r10 >= 5){
//				roman.append("L");
//				for (int i = 0; i < r10 - 5; i++) {
//					roman.append("X");
//				}
//			}else if (r10 == 4){
//				roman.append("XL");
//			}else{
//				for (int i = 0; i < r10; i++) {
//					roman.append("X");
//				}
//			}
//		}
//		//1
//		int r1 = num ;
//		if (r1 > 0){
//			if (r1 == 9){
//				roman.append("IX");
//			}else if (r1 >= 5){
//				roman.append("V");
//				for (int i = 0; i < r1 - 5; i++) {
//					roman.append("I");
//				}
//			}else if (r1 == 4){
//				roman.append("IV");
//			}else{
//				for (int i = 0; i < r1; i++) {
//					roman.append("I");
//				}
//			}
//		}
//		return roman.toString();


		int values[]={1000,900,500,400,100,90,50,40,10,9,5,4,1};
		String reps[]={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

		String res = "";
		for(int i=0; i<13; i++){
			while(num>=values[i]){
				num -= values[i];
				res += reps[i];
			}
		}
		return res;
	}

	//罗马数字转整数
	//执行用时 : 38 ms, 在Roman to Integer的Java提交中击败了90.48% 的用户
	//内存消耗 : 39.7 MB, 在Roman to Integer的Java提交中击败了75.83% 的用户
	public static int romanToInt(String roman){
		int values[]={1000,900,500,400,100,90,50,40,10,9,5,4,1};
		String reps[]={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
		Map<String, Integer> romanValMap = new HashMap<>();
		for (int i = 0; i < values.length; i++) {
			romanValMap.put(reps[i], values[i]);
		}
		int val = 0;
		//values[s[i+1]] > values[s[i]]
		for (int i = 0; i < roman.length(); i++) {
			if (i<roman.length() - 1 && romanValMap.get("" + roman.charAt(i) + roman.charAt(i+1)) != null){
				val += romanValMap.get("" + roman.charAt(i) + roman.charAt(i+1));
				i++;
			}else if (romanValMap.get("" + roman.charAt(i)) != null){
				val += romanValMap.get("" + roman.charAt(i));
			}
		}
		return val;
	}

	//最长公共前缀
	//执行用时 : 4 ms, 在Longest Common Prefix的Java提交中击败了92.41% 的用户
	//内存消耗 : 37.2 MB, 在Longest Common Prefix的Java提交中击败了80.75% 的用户
	public static String longestCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0){
			return "";
		}
		if (strs.length == 1){
			return strs[0];
		}
		String commonPrefix = "";
		boolean prefix = true;
		for (int i = 0; i < strs[0].length(); i++) {
			for (int j = 1; j < strs.length; j++) {
				if (strs[j].length() <= i || strs[j].charAt(i) != strs[0].charAt(i)){
					prefix = false;
					break;
				}
			}
			if (prefix){
				commonPrefix += strs[0].charAt(i);
			}else{
				return commonPrefix;
			}
		}
		return commonPrefix;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		ListNode l1 = new ListNode(1);
//		l1.next = new ListNode(4);
//		l1.next.next = new ListNode(3);

		ListNode l2 = new ListNode(9);
		l2.next = new ListNode(9);
//		l2.next.next = new ListNode(4);

//		addTwoNumbers(l1, l2).print();

//		lengthOfLongestSubstring("abcabcbb");

//		System.out.println(findMedianSortedArrays(new int[]{}, new int[]{1}));;

//		System.out.println(longestPalindrome("abbb"));

//		System.out.println(convert("AB", 3));

//		System.out.println(reverse(1534236469));

//		System.out.println(myAtoi("-42"));

//		System.out.println(isPalindrome(10));

//		System.out.println(isMatch("aab", "c*a*b"));

//		System.out.println("aaa".matches("ab*ac*a"));

//		System.out.println(maxArea(new int[]{1,8,6,2,5,4,8,3,7}));

		System.out.println(intToRoman(1994));

		System.out.println(romanToInt("LVIII"));

		System.out.println(longestCommonPrefix(new String[]{"flower","flow","flight"}));

	}

}
