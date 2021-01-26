package com.mafashen.structure.arithmetic.leetcode;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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

	public static List<List<Integer>> threeSum(int[] nums){
		List<List<Integer>> ret = new LinkedList<>();
		Set<String> distinct = new HashSet<>();
//		long count = 0;
		Arrays.sort(nums);
		// 三数之和=0, 1: 负数+负数 = 正数 ; 2: 正数+正数 = 负数
		int length = nums.length;
		if (length < 3){
			return ret;
		}
		int mid = 0;
		int zeroNum = 0;
		for (int i = 0; i <= length - 1; i++) {
			if (nums[i] == 0){
				zeroNum++;
			}
			if (i<= length-2 && nums[i] <=0 && nums[i+1]>0){
				mid=i;
			}
		}
		if (mid > 0){
			for (int i = 0; i <= mid; i++) {
				for (int j = length -1; j >= mid; j--) {
					if (nums[j] < 0){
						break;
					}
					if (nums[j] + nums[i] > 0){
						continue;
					}else{
						for (int k = j-1; k >= mid; k--) {
//						count++;
							if (nums[k] + nums[j] + nums[i] == 0 && distinct.add(nums[i] + "" + nums[j] + "" + nums[k])){
								ret.add(Arrays.asList(nums[i], nums[j], nums[k]));
							}
						}
					}
				}
			}
			for (int i = length - 1; i >= mid; i--) {
				if (nums[i] == nums[i-1]){
					continue;
				}
				for (int j = 0; j <= mid; j++) {
					if (nums[j] > 0 ){
						break;
					}
					if (nums[i] + nums[j] >= 0){
						for (int k = j+1; k <= mid; k++) {
							if (nums[k] >= 0 ){
								continue;
							}
//						count++;
							if (nums[j] + nums[k] + nums[i] == 0 && distinct.add(nums[k] + "" + nums[j] + "" + nums[i])){
								ret.add(Arrays.asList(nums[i], nums[j], nums[k]));
							}
						}
					}
				}

			}
		}

		if (zeroNum >= 3){
			ret.add(Arrays.asList(0, 0, 0));
		}
//		System.out.println(count);
		return ret;
	}

	public static List<List<Integer>> threeSum2(int[] nums){
		List<List<Integer>> ans = new ArrayList();
		int count = 0;
		int len = nums.length;
		if(nums == null || len < 3) return ans;
		Arrays.sort(nums); // 排序
		for (int i = 0; i < len ; i++) {
			if(nums[i] > 0) break; // 如果当前数字大于0，则三数之和一定大于0，所以结束循环
			if(i > 0 && nums[i] == nums[i-1]) continue; // 去重
			int L = i+1;
			int R = len-1;
			while(L < R){
				count ++;
				int sum = nums[i] + nums[L] + nums[R];
				if(sum == 0){
					ans.add(Arrays.asList(nums[i],nums[L],nums[R]));
					while (L<R && nums[L] == nums[L+1]) L++; // 去重
					while (L<R && nums[R] == nums[R-1]) R--; // 去重
					L++;
					R--;
				}
				else if (sum < 0) L++;
				else if (sum > 0) R--;
			}
		}
		System.out.println(count);
		return ans;
	}

	//有效的括号
	public static boolean validParenthesis(String s){
		if ( "".equals(s)){
			return true;
		}

//		执行用时：2 ms, 在所有 Java 提交中击败了 79.41% 的用户
//		内存消耗：37.8 MB, 在所有 Java 提交中击败了 34.76% 的用户
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(' || c == '[' || c == '{'){
				stack.push(c);
			}else if((c == ')' || c == ']' || c == '}') && !stack.isEmpty()){
				Character f = stack.lastElement();
				if ((f == '(' && c == ')') || (f == '[' && c == ']') || (f == '{' && c == '}')){
					stack.pop();
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		return stack.empty();
	}

	public static boolean validParenthesis2(String s){
		if ( "".equals(s)){
			return true;
		}
		if (s.length() % 2 == 1){
			return false;
		}

		//执行用时：1 ms, 在所有 Java 提交中击败了98.53%的用户
		//内存消耗：37.8 MB, 在所有 Java 提交中击败了42.96%的用户
		int count = 0;
		int j = 0;
		char[] lefts = new char[s.length() / 2];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(' || c == '[' || c == '{'){
				count++;
				lefts[j++] = c;
			}else if((c == ')' || c == ']' || c == '}') && i != 0){
				char f = lefts[j-1];
				if ((f == '(' && c == ')') || (f == '[' && c == ']') || (f == '{' && c == '}')){
					count--;j--;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		return count == 0;
	}

	// 删除有序数组重复元素
	//执行用时：1 ms, 在所有 Java 提交中击败了98.41%的用户
	//内存消耗: 41.5 MB, 在所有 Java 提交中击败了72.91%的用户
	public static int removeDuplicates(int[] nums) {
		int k = 0;// 新排序后下标
		int count = nums.length;
		for (int i = 1; i < nums.length; i++) {
			if (nums[k] == nums[i]){
				count--;
			}else{
				nums[++k] = nums[i];
			}
		}
		for (int i = 0; i < count; i++) {
			System.out.print(nums[i]);
		}
		System.out.println();
		return count;
	}

	// 移除出现的元素,移动元素
	//执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
	//内存消耗：38.4 MB, 在所有 Java 提交中击败了32.13%的用户
	// [0,1,2,2,3,0,4,2], 2 => 5 [0,1,3,0,4]
	public static int removeElement(int[] nums, int val) {
		int i = 0;
		int k = nums.length - 1;
		while(i<k){
			if (nums[i] != val){
				i++;
				continue;
			}
			if (nums[k] == val){
				k--;
				continue;
			}
			nums[i] = nums[k--];
		}
		if (i == k && nums[i] != val){
			i++;
		}
		return i;
	}

	// 求平方根,只保留小数部分
	//执行用时：1 ms, 在所有 Java 提交中击败了100.00%的用户
	// 内存消耗：36.8 MB, 在所有 Java 提交中击败了84.90%的用户
	public static int mySqrt(int x) {
		return (int)Math.sqrt(x);
	}

	// 字符串相乘,超大数
	public static String multiply(String num1, String num2) {

		return null;
	}

	// 相同的树
	//执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
	//内存消耗：37.5 MB, 在所有 Java 提交中击败了8.70%的用户
	public static boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null){
			return true;
		}else if(p == null || q == null || p.val != q.val)
			return false;
		return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
	}

	// 二叉树最大深度
	//执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
	//内存消耗：39.6 MB, 在所有 Java 提交中击败了78.23%的用户
	public static int maxDepth(TreeNode root) {
		if (root == null){
			return 0;
		}
		return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
	}

	// 最后一个单词的长度
	//执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
	//内存消耗：37.7 MB, 在所有 Java 提交中击败了76.67%的用户
	public static int lengthOfLastWord(String s) {
		int c = 0;
		for (int i = s.length()-1; i>=0; i--) {
			if (s.charAt(i) != ' '){
				c++;
			}else if(c > 0){
				break;
			}
		}
		return c;
	}

	// 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点
	//执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
	//内存消耗：38 MB, 在所有 Java 提交中击败了30.69%的用户
	public static ListNode removeNthFromEnd(ListNode head, int n) {
		// 使用哑结点, 在链表长度为1的时候,不需要额外判断
		ListNode dump = new ListNode(0);
		dump.next = head;
		ListNode first = dump;
		ListNode nStep = dump;
		for (int i = 0; i <= n; i++) {
			first = first.next;
		}
		while (first != null){
			first = first.next;
			nStep = nStep.next;
		}

		nStep.next = nStep.next.next;
		return dump.next;
	}

	// 1+2+...+n
	//执行用时：1 ms, 在所有 Java 提交中击败了62.90%的用户
	//内存消耗：36.7 MB, 在所有 Java 提交中击败了67.40%的用户
	public static int sumNums(int n) {
		return n >= 1 ? n + sumNums(n-1) : n;
	}

	public static int sumNums2(int n){
		boolean[][] arr = new boolean[n][n+1];
		return (arr.length * arr[0].length) >> 1;
	}

	// 不使用临时变量交换数字
	// 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
	// 内存消耗：37.5 MB, 在所有 Java 提交中击败了59.59%的用户
	public static int[] swapNumbers(int[] numbers) {
		numbers[0] = numbers[0] + numbers[1];
		numbers[1] = numbers[0] - numbers[1];
		numbers[0] = numbers[0] - numbers[1];
		return numbers;
	}

	// TODO 灯泡开关
	public static int minFlips(String target) {
		return 0;
	}

	// 有序数组的评分
	//执行用时：2 ms, 在所有 Java 提交中击败了64.68%的用户
	//内存消耗：41.5 MB, 在所有 Java 提交中击败了58.75%的用户
	public static int[] sortedSquares(int[] A) {
		for (int i = 0; i < A.length; i++) {
			A[i] = A[i] * A[i];
		}
		Arrays.sort(A);
		return A;
	}

	// 头尾遍历
	//执行用时：2 ms, 在所有 Java 提交中击败了64.68%的用户
	//内存消耗：41.7 MB, 在所有 Java 提交中击败了24.55%的用户
	public static int[] sortedSquares2(int[] A) {
		int[] sortA = new int[A.length];

		int l = 0, k = 0;
		for (; l < A.length-1; l++) {
			if (A[l] < 0 && A[l+1] >=0){
				break;
			}
		}
		// 全部是负数
		if (l == A.length-1 && A[0] < 0){
			for (int i1 = A.length-1; i1 >=0; i1--) {
				sortA[k++] = A[i1] * A[i1];
			}
		}
		// 全部是正数
		else if (l == A.length-1 && A[0] >= 0){
			for (int i1 = 0; i1 < A.length; i1++) {
				sortA[i1] = A[i1] * A[i1];
			}
		}else{
			int i = l, j = l+1;
			while(k < A.length){
				// 剩下的都是正数
				if (i < 0){
					sortA[k++] = A[j] * A[j];
					j++;
				}

				// 剩下的都是负数
				else if (j >= A.length){
					sortA[k++] = A[i] * A[i];
					i--;
				}else{
					int i2 = A[i] * A[i];
					int j2 = A[j] * A[j];
					if (i2<j2){
						sortA[k++] = i2;
						i--;
					}else {
						sortA[k++] = j2;
						j++;
					}
				}
			}
		}

		return sortA;
	}

	// 1ms 100%; 41.7MB, 16.5%
	public static int[] sortedSquares3(int[] A) {
		int[] ans = new int[A.length];
		int i = 0, j = A.length-1, k = A.length-1;
		while(i <= j){
			if(A[i] + A[j] < 0){
				ans[k--] = A[i]*A[i];
				i++;
			}else{
				ans[k--] = A[j]*A[j];
				j--;
			}
		}
		return ans;
	}

	// 数组拆分, 排序后,相邻两个组成的min(a,b) 相加和最大
	// 13ms 97.27%; 41.8MB, 52.8%
	public static int arrayPairSum(int[] nums) {
		Arrays.sort(nums);
		int count = 0;
		for (int i = 0; i < nums.length; i+=2) {
			count += nums[i];
		}
		return count;
	}

	// 最大礼物价值
	public static int maxValue(int[][] grid) {

		return 0;
	}

	public static String reverseWords(String s) {
		String[] words = s.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			for (int i = word.length()-1; i >= 0; i--) {
				sb.append(word.charAt(i));
			}
			sb.append(" ");
		}

		return sb.deleteCharAt(sb.length()-1).toString();
	}

	public static String reverseWords2(String s) {
		char[] str = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {

			}
		}
		return new String(str);
	}

	/**
	 * 链表翻转
	 * @param header
	 * @param node
	 */
	public static void linkedListReverse(ListNode header, int node){
		ListNode h = header, p = header, q;
		while(p.next != null){
			if (p.val == node){
				q = p;
			}
			p = p.next;
		}
		// 翻转h->q
		// 翻转q->p
	}

	public static ListNode reverse(ListNode header){
		if (header == null || header.next == null){
			return header;
		}
		ListNode pre = null, cur = header, pNext = null;
		while(cur != null){
			pNext = cur.next;

			cur.next = pre;
			pre = cur;
			cur = pNext;
		}
//		pre.print();
		return pre;
	}


	public static void main(String[] args) throws UnsupportedEncodingException {
		long start = System.currentTimeMillis();
//		System.out.println(longestPalindromeLength("aaabaaa"));

//		System.out.println(JSONObject.toJSONString(sortedSquares2(new int[]{0,2})));

		ListNode header = new ListNode(new int[]{1,2,3,4,5,6}, 0);
		header = reverse(header);
		header.print();

		System.out.println("takes : " + (System.currentTimeMillis() - start));
	}

	static class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
  }

	static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }

		ListNode(int[] arr, int start){
			if (start < arr.length){
				val = arr[start];
				if (start < arr.length - 1)
					next = new ListNode(arr, start + 1);
			}
		}

		public ListNode add(int val){
			next = new ListNode(val);
			return next;
		}

		public void print(){
			System.out.printf("%d ", val);
			if (next != null){
				next.print();
			}
		}

	}
}
