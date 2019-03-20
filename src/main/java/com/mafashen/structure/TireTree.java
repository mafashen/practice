package com.mafashen.structure;

/**
 * Tire树适合前缀匹配字符串的场景(搜索框提示词),用数组实现子树节点的方式耗费内存;
 * 查找时间复杂度O(k) k为查找的字符串长度
 * 构建的时间复杂度为O(n) n为主串长度
 * @author mafashen
 * created on 2019/3/15.
 */
public class TireTree {

	TireNode root = new TireNode('/');

	public void insert(char[] text){
		TireNode p = root;
		for (int i = 0; i < text.length; i++) {
			int idx = text[i] - 'a';
			if (p.children[idx] == null){
				p.children[idx] = new TireNode(text[i]);
			}
			p = p.children[idx];
		}
		p.isCharEnding = true;
	}

	public boolean find(char[] text){
		TireNode p = root;
		for (int i = 0; i < text.length; i++) {
			int idx = text[i] - 'a';
			if (p.children[idx] == null){
				return false;
			}
			p = p.children[idx];
		}
		return p.isCharEnding;	//到这里如果返回false,说明匹配的只是前缀,不是完整匹配
	}

	private String[] suggest(char[] text){
		TireNode p = root;
		for (int i = 0; i < text.length; i++) {
			int idx = text[i] - 'a';
			if (p.children[idx] == null){
				return null;
			}
			p = p.children[idx];
		}
		if (p.isCharEnding){
			return null;
		}else{
			//BFS
		}
		return null;
	}

	public static void main(String[] args) {
		TireTree tireTree = new TireTree();
		tireTree.insert("hello".toCharArray());
		tireTree.insert("hi".toCharArray());
		tireTree.insert("her".toCharArray());
		tireTree.insert("how".toCharArray());
		tireTree.insert("see".toCharArray());
		tireTree.insert("so".toCharArray());

		System.out.println(tireTree.find("hello".toCharArray()));
		System.out.println(tireTree.find("how".toCharArray()));
		System.out.println(tireTree.find("he".toCharArray()));
	}

	static class TireNode{
		char data;
		TireNode children[] = new TireNode[26];
		boolean isCharEnding;

		public TireNode(char data) {
			this.data = data;
		}
	}
}
