package com.mafashen.structure;

import java.util.Comparator;
import lombok.Data;

/**
 * 二叉树
 * @author mafashen
 * created on 2019/3/12.
 */
public class BinaryTree <T>{


	private TreeNode<T> root;
	private Comparator<T> comparator;

	public BinaryTree() {
	}

	public void add(T t){
		TreeNode<T> node = root;
		TreeNode<T> e = new TreeNode<>(t);
		if(node == null){
			root = e;
		}else{
			TreeNode<T> parent ;
			int compare = 0;
			do{
				parent = node;
				if (comparator != null){
					compare = comparator.compare(t, node.data);
					if (compare > 0){
						node = node.right;
					}else if(compare < 0){
						node = node.left;
					}else{
						return;
					}
				}
			}while (node != null);
			if (compare > 0){
				parent.right = e;
				e.setInLeft(false);
			}else {
				parent.left = e;
				e.setInLeft(true);
			}
			e.parent = parent;
		}
	}

	public TreeNode find(T t){
		TreeNode<T> node = root;
		if (node == null){
			return null;
		}
		do{
			if (node.getData().equals(t)){
				return node;
			}
			if (comparator.compare(t, node.getData()) > 0){
				node = node.right;
			}else{
				node = node.left;
			}
		}while(node != null);

		return null;
	}

	public void remove(T t){
		//先找到对应结点
		TreeNode node = find(t);
		if (node != null){
			if (node.getLeft() == null && node.getRight() == null){
				if (node.isInLeft()){
					node.getParent().setLeft(null);
				}else{
					node.getParent().setRight(null);
				}
			}else if(node.getRight() == null || node.getLeft() == null){
				//父节点
				if (node.isInLeft()){
					node.getParent().setLeft(node.getLeft());
				}else{
					node.getParent().setRight(node.getRight());
				}
			}else{
				TreeNode min = findMin(node.getRight());
				node.setData(min.getData());
				if (min.isInLeft()){
					min.getParent().setLeft(null);
				}else{
					min.getParent().setRight(null);
				}
				if (min.getParent() == node){
					node.setRight(min.getRight());
				}
//				min.getParent().setLeft(min.getRight());
			}
		}
		//无孩子节点,直接删除
		//只有一个孩子,替换
		//两个孩子,选右子树中最左的结点替换
	}

	public TreeNode findMin(TreeNode node){
		if (node == null){
			return null;
		}
		while(node.getLeft() != null){
			node = node.getLeft();
		}
		return node;
	}

	public static void main(String[] args) {
		BinaryTree<Integer> tree = new BinaryTree<>();
		tree.setComparator(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		tree.add(5);
		tree.add(8);
		tree.add(3);
		tree.add(6);
		tree.add(7);
		tree.add(9);
		tree.add(10);

		System.out.println(tree);
		System.out.println(tree.find(8).getData());

		tree.remove(8);
		System.out.println(tree);
	}

	@Data
	static class TreeNode <T>{
		private T data;
		private TreeNode<T> left;
		private TreeNode<T> right;
		private TreeNode<T> parent;
		private boolean inLeft;


		public TreeNode() {
		}

		public TreeNode(T data) {
			this.data = data;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("data=").append(getData());
			if (getLeft() != null){
				sb.append(" | left:").append(getLeft().toString());
			}
			if (getRight() != null){
				sb.append(" | right:").append(getRight().toString());
			}
			return sb.toString();
		}
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public String toString() {

		return root.toString();
	}
}
