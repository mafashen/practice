package com.mafashen.structure.arithmetic;

/**
 * 正则表达
 * @author mafashen
 * created on 2019/3/21.
 */
public class Pattern {

	public char[] pattern;
	public boolean matched = false;
	public int plen;

	public Pattern(char[] pattern, int plen) {
		this.pattern = pattern;
		this.plen = plen;
	}

	public boolean match(char[] text, int tlen){
		matched = false;
		rematch(0, 0, text, tlen);
		return matched;
	}

	public void rematch(int ti, int pj, char[] text, int tlen){
		if (matched){
			return;
		}
		//模式串到尾了,同时待匹配串也到尾了
		if(pj == plen){
			if(ti == tlen){
				matched = true;
			}
			return;
		}

		//通配符*匹配一个或多个,先往后匹配,不匹配的会返回这里继续跳过
		if(pattern[pj] == '*'){
			for (int k = 0; k < tlen-ti; k++) {
				rematch(ti+k, pj+1, text, tlen);
			}
		}else if(pattern[pj] == '?'){
			//匹配0个
			rematch(ti, pj+1, text, tlen);
			//匹配1个
			rematch(ti+1, pj+1, text, tlen);
		}else if(ti < tlen && pattern[pj] == text[ti]){
			//对应字符匹配才能继续往后匹配
			rematch(ti+1, pj+1, text, tlen);
		}
	}

	public static void main(String[] args) {
		Pattern pattern = new Pattern("abc*abc".toCharArray(), 7);
		System.out.println(pattern.match("abcaabc".toCharArray(), 7));
		System.out.println(pattern.match("abcabcbc".toCharArray(), 7));
	}
}
